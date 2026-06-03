package co.elgranero.controller.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.elgranero.persistence.ConfigReader;
import co.elgranero.persistence.SqlInstructionsReader;

public final class BDConnection {
    private static BDConnection bdConnection;
    private Connection conn;
    private String id_user;

    private BDConnection() {
    }

    public static BDConnection getInstance() {
        if (bdConnection == null) {
            bdConnection = new BDConnection();
        }
        return bdConnection;
    }

    public Connection getConnection() {
        if (conn == null) {
            throw new NullPointerException(
                    "The connection has not been initialized. Use \"initConnection\" method first");
        }
        return conn;
    }

    public boolean initUserConnection(String document, String password) throws SQLException, IOException {
        boolean result = false;
        if (validateId(document)) {
            ConfigReader cr = ConfigReader.getInstance();
            String bd_ip = cr.getBdIp();
            String bd_user = cr.getBdUser();
            conn = DriverManager.getConnection(
                    bd_ip,
                    bd_user,
                    password);
            result = true;
        }
        return result;
    }

    public Connection initConnection() {
        Connection tempConn = null;
        try {
            ConfigReader cr = ConfigReader.getInstance();
            String bd_ip = cr.getBdIp();
            String bd_user = cr.getBdUser();
            String bd_pass = cr.getBdPassword();
            tempConn = DriverManager.getConnection(
                    bd_ip,
                    bd_user,
                    bd_pass);
        } catch (SQLException | IOException e) {
        }
        return tempConn;
    }

    private boolean validateId(String document) throws SQLException, IOException {
        Connection tempConn = initConnection();
        PreparedStatement pSt = SqlInstructionsReader.getInstance().getConsultOf(tempConn, "validate_seller");
        pSt.setString(1, document);
        ResultSet rs = pSt.executeQuery();
        boolean result = rs.next();
        tempConn.close();
        pSt.close();
        return result;
    }

}