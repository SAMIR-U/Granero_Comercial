package co.elgranero.controller.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DatabaseUtils {
    public static boolean tableExist(Connection conn, String tableName) throws SQLException{
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet resultSet = metaData.getTables(null, null, tableName, new String[] {"TABLE"})){
            return resultSet.next();
        }
    }
    public static PreparedStatement obtainPreparedStament(Connection conn, String queryPath) throws SQLException, IOException{
        String sql = new String(Files.readAllBytes(Paths.get(queryPath)));
        return conn.prepareStatement(sql);
    }
    public static ResultSet sendBDRequest(Connection conn, PreparedStatement pSt) throws SQLException{
        ResultSet rs = pSt.executeQuery();
        return rs;
    }


}
