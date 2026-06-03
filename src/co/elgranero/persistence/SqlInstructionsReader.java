package co.elgranero.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

public final class SqlInstructionsReader {
    private static SqlInstructionsReader sqlInstReader;
    private SqlQueryInstructions sqlQI;

    private SqlInstructionsReader() throws IOException {
        readSqlInstructions();
    }

    private void readSqlInstructions() throws IOException {
        try {
            String json = new String(
                    Files.readAllBytes(Paths.get("sql/paths.json")));
            Gson gson = new Gson();
            this.sqlQI = gson.fromJson(json, SqlQueryInstructions.class);
        } catch (Exception e) {
            throw new IOException("fatal error: it was not possible to read \"sql/paths.json\"");
        }
    }

    // borrar este metodo
    public SqlQueryInstructions getSqlQueryInstructions() {
        return sqlQI;
    }

    public static SqlInstructionsReader getInstance() throws IOException {
        if (sqlInstReader == null) {
            sqlInstReader = new SqlInstructionsReader();
        }
        return sqlInstReader;
    }

    public List<String> getCreateTablesOrder() {
        return sqlQI.create_tables_order;
    }

    public PreparedStatement getCreateTableQueryOf(Connection conn, String key)
            throws IOException, SQLException {
        String url = sqlQI.creates.get(key.toUpperCase());
        return obtainSQLQuery(conn, url);
    }

    public PreparedStatement getInsertsQueryOf(Connection conn, String key) throws IOException, SQLException {
        String url = sqlQI.inserts.get(key);
        return obtainSQLQuery(conn, url);
    }

    public PreparedStatement getUpdateQueryOf(Connection conn, String key) throws IOException, SQLException {
        String url = sqlQI.updates.get(key);
        return obtainSQLQuery(conn, url);
    }

    public PreparedStatement getDeleteQueryOf(Connection conn, String key) throws IOException, SQLException {
        String url = sqlQI.deletes.get(key);
        return obtainSQLQuery(conn, url);
    }

    public PreparedStatement getConsultOf(Connection conn, String key) throws IOException, SQLException {
        String url = sqlQI.selects.get(key);
        return obtainSQLQuery(conn, url);
    }

    public PreparedStatement getReportOf(Connection conn, String key) throws IOException, SQLException {
        String url = sqlQI.reports.get(key);
        return obtainSQLQuery(conn, url);
    }

    private PreparedStatement obtainSQLQuery(Connection conn, String url) throws IOException, SQLException {
        String sql = new String(Files.readAllBytes(Paths.get(url)));
        return conn.prepareStatement(sql);
    }
}