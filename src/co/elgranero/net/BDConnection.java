package co.elgranero.net;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.elgranero.persistence.ConfigReader;

public final class BDConnection {
    private static Connection conn;

    public static Connection getInstance(){
        if (conn == null) {
            throw new NullPointerException("The connection has not been initialized. Use \"initConnection\" method first");
        }
        return conn;
    }

    public static boolean initConnection(String password){
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
