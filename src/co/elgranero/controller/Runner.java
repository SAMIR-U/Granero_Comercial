package co.elgranero.controller;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import co.elgranero.controller.util.BDConnection;
import co.elgranero.controller.util.DatabaseUtils;
import co.elgranero.persistence.ConfigReader;
import co.elgranero.persistence.SqlInstructionsReader;
import co.elgranero.view.Login;
import co.elgranero.view.LoginCallback;
import co.elgranero.view.MainWindow;

public final class Runner {

    private static Runner runner;
    private int tries;
    private BDConnection bdConn;
    private SqlInstructionsReader sqlInstReader;
    private JFrame loginFrame;
    private Login login;
    private volatile boolean loginSuccess;

    private Runner() throws IOException {
        this.tries = 3;
        this.loginSuccess = false;
        this.bdConn = BDConnection.getInstance();
        this.sqlInstReader = SqlInstructionsReader.getInstance();
    }

    public static Runner getInstance() throws IOException {
        if (runner == null) {
            runner = new Runner();
        }
        return runner;
    }

    public void init() {
        try {
            List<String> tablesConfig = sqlInstReader.getCreateTablesOrder();
            Connection tempConn = BDConnection.getInstance().initConnection();
            checkTables(tablesConfig, tempConn);
            login();
            while (!loginSuccess) {
            }
            tempConn.close();
            SwingUtilities.invokeLater(MainWindow::new);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    public void fastInit() {
        tries = 3;
        try {
            login();
            while (!loginSuccess) {
            }
            SwingUtilities.invokeLater(MainWindow::new);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void login() throws SQLException, IOException {
        login = new Login((user, password) -> {
            try {
                loginSuccess = bdConn.initUserConnection(user, password);
                if (tries >= 0) {
                    SwingUtilities.invokeLater(() -> {
                        login.setValidLogin(loginSuccess, tries--);
                    });
                }
            } catch (Exception e) {
            }
        });
        try {
            SwingUtilities.invokeAndWait(() -> {
                loginFrame = new JFrame("Granero Comercial");
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setSize(900, 600);
                loginFrame.setMinimumSize(new Dimension(700, 480));
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setContentPane(login);
                loginFrame.setVisible(true);
            });
        } catch (Exception e) {
        }
    }

    private void checkTables(List<String> tablesConfig, Connection tempConn)
            throws SQLException, IOException, IllegalStateException {
        int errCount = 0;
        for (String tc : tablesConfig) {
            try {
                if (!DatabaseUtils.tableExist(tempConn, tc)) {
                    errCount++;
                }
            } catch (SQLException e) {
            }
        }
        if (errCount != 0 && (errCount != tablesConfig.size() || !initTables(tablesConfig, tempConn))) {
            throw new IllegalStateException("fatal error: database in inconsistent state.");
        }
    }

    private boolean initTables(List<String> tablesConfig, Connection tempConn)
            throws SQLException, IOException {
        for (String tc : tablesConfig) {
            try {
                PreparedStatement pSt = sqlInstReader.getCreateTableQueryOf(tempConn, tc);
                DatabaseUtils.sendBDRequest(tempConn, pSt);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        createFirstUser(tempConn);
        return true;
    }

    private void createFirstUser(Connection tempConn) throws SQLException, IOException {
        DatabaseUtils.sendBDRequest(
                tempConn,
                sqlInstReader.getInsertsQueryOf(tempConn, "firstUserData"));
    }
}