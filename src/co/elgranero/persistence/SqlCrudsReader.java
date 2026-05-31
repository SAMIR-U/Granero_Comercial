package co.elgranero.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public final class SqlCrudsReader {
    private static SqlCrudsReader sqlCrudReader;
    private SqlCruds sqlCruds;

    private SqlCrudsReader()throws IOException{
        readSqlCruds();
    }

    private void readSqlCruds() throws IOException{
        try {
            String json = new String(
                Files.readAllBytes(Paths.get("sql/paths.json"))
            );
            Gson gson = new Gson();
            this.sqlCruds = gson.fromJson(json, SqlCruds.class);
        } catch (Exception e) {
            throw new IOException("fatal error: it was not possible to read \"sql/paths.json\"");
        }
    }

    public static SqlCrudsReader getInstance()throws IOException{
        if(sqlCrudReader == null){
            sqlCrudReader = new SqlCrudsReader();
        }
        return sqlCrudReader;
    }

    public List<TableConfig> getCreateTablesOrder(){
        return sqlCruds.create_tables_order;
    }

    public String getCreateQueryOf(String key){
        return sqlCruds.creates.get(key);
    }

    public String getInsertsQueryOf(String key){
        return sqlCruds.inserts.get(key);
    }

    public String getConsultOf(String key){
        return sqlCruds.selects.get(key);
    }
}