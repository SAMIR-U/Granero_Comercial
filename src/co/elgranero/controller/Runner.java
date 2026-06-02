package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import co.elgranero.controller.util.BDConnection;
import co.elgranero.controller.util.DatabaseUtils;
import co.elgranero.persistence.ConfigReader;
import co.elgranero.persistence.SqlInstructionsReader;
import co.elgranero.view.Login;
import co.elgranero.view.View;

public final class Runner {
    private static Runner runner;
    private View view;
    private int tries;
    private BDConnection bdConn;
    private SqlInstructionsReader sqlInstReader;

    private Runner(){
        this.view = new View();
        this.tries = 3;
        this.bdConn = BDConnection.getInstance();
    }

    public static Runner getInstance(){
        if (runner==null) {
            runner = new Runner();
        }
        return runner;
    }

    public void init(){
        try {
            List<String> tablesConfig = sqlInstReader.getCreateTablesOrder();
            Connection tempConn = BDConnection.getInstance().initConnection();
            checkTables(tablesConfig, tempConn);
            if (login()) {
                tempConn.close();
                view.showMainMenu();
            }else{
                view.setStatus(4);
            }
        } catch (SQLException|IOException e) {
            view.setStatus(0);
        } catch (IllegalStateException e){
            view.setStatus(e.getMessage());
        }
    }

    public void fastInit(){
        try {
            if (login()) {
                view.showMainMenu();
            }else{
                view.setStatus(4);
            }
        } catch (SQLException|IOException e) {
            view.setStatus(0);
        }
    }

    private boolean login() throws SQLException, IOException{
        Login login = view.showLogin();

        boolean result = false;

        while (tries < 1 && !result){
            while (login.isWaiting());
            String[] credentials = login.getCredentials();
            result = bdConn.initUserConnection(credentials[0],credentials[1]);
            //desactivar el botón de submit
            tries--;
            login.setValidLogin(result, tries);
        }

        return result;
    }

    private void checkTables(List<String> tablesConfig, Connection tempConn) throws SQLException, IOException, IllegalStateException{
        view.setStatus(1);
        int errCount = 0;
        
        for (String tc:tablesConfig) {
            try {
                if (!DatabaseUtils.tableExist(tempConn, tc)) {
                    errCount++;
                }
            } catch (SQLException e) {}
        }
        
        if (!(errCount == tablesConfig.size() && initTables(tablesConfig,tempConn))) {
            throw new IllegalStateException("fatal error: database in inconsistent state.");
        }
    }
    
    
    private boolean initTables(List<String> tablesConfig, Connection tempConn) throws SQLException, IOException{
        view.setStatus(2);
        for (String tc:tablesConfig) {
            try {
                PreparedStatement pSt = sqlInstReader.getCreateQueryOf(tempConn, tc, "TABLE");
                DatabaseUtils.sendBDRequest(tempConn, pSt);
            } catch (SQLException| IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        createFirstUser(tempConn);
        return true;
    }
    
    private void createFirstUser(Connection tempConn) throws SQLException, IOException{
        String firstUserQuery = ConfigReader.getInstance().getFirstUser();        
        DatabaseUtils.sendBDRequest(
            tempConn,
            DatabaseUtils.obtainPreparedStament(tempConn, firstUserQuery)
        );
    }

}
