package co.elgranero.net;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class BDConnection {
    private static Connection conn;

    public static Connection getInstance(){
        if (conn == null) {
            throw new NullPointerException("The connection was not inicialized. Use before the method ");
        }
        return conn;
    }

    public static boolean initConnection(String password){
        try {
            //toca cambiar esto config.json
            conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@192.168.1.12:15022/sebq", 
                "system",
                password
            );
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
