package co.elgranero.net;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.elgranero.persistence.ConfigReader;

public final class BDConnection {
    private static BDConnection bdConnection;
    private Connection conn;
    private String id_user;

    private BDConnection(){}

    public static BDConnection getInstance(){
        if (bdConnection == null) {
            bdConnection = new BDConnection();
        }
        return bdConnection;
    }

    public Connection getConnection(){
        if (conn == null) {
            throw new NullPointerException("The connection has not been initialized. Use \"initConnection\" method first");
        }
        return conn;
    }

    private Connection getConnectionSql(){
        return conn;
    }

    public boolean initConnection(String id, String password){
        try {
            ConfigReader cr = ConfigReader.getInstance();
            String bd_ip = cr.getBdIp();
            String bd_user = cr.getBdUser();
            conn = DriverManager.getConnection(
                bd_ip, 
                bd_user,
                password
            );
        } catch (SQLException|IOException e) {
            return false;
        }
        return true;
    }

}