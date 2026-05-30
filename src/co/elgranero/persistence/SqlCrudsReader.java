package co.elgranero.persistence;

public final class SqlCrudsReader {
    private static SqlCrudsReader sqlCrudReader;

    private SqlCrudsReader(){}


    public static SqlCrudsReader getInstance(){
        if(sqlCrudReader == null){
            sqlCrudReader = new SqlCrudsReader();
        }
        return sqlCrudReader;
    }
}
