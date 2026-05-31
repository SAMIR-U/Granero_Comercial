package co.elgranero.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import co.elgranero.net.BDConnection;
import co.elgranero.persistence.SqlCrudsReader;
import co.elgranero.persistence.TableConfig;
import co.elgranero.view.Login;
import co.elgranero.view.View;

public class Runner {
    private View view;
    private int tries;
    private BDConnection bdConn;
    private SqlCrudsReader crudsReader;
    private Connection conn;

    public Runner(){
        this.view = new View();
        this.tries = 0;
        this.bdConn = BDConnection.getInstance();
    }

    public void init(){
        boolean result = checkTables();
        if (result) {
            result = login();
            if (result) {
                
            }
        }
    }

    private boolean login(){
        Login login = view.showLogin();

        boolean result = false;

        while (tries < 10 && !result){
            while (login.isWaiting());
            String[] credentials = login.getCredentials();
            result = bdConn.initConnection(credentials[0],credentials[1]);
            //desactivar el botón de submit
            login.setValidLogin(result, tries);
            tries++;
        }

        if (result) {
            conn = bdConn.getConnection();
        }

        return result;
    }

    private boolean checkTables() {
        List<TableConfig> tablesConfig = crudsReader.getCreateTablesOrder();
        boolean result = false;
        int errCount = 0;

        for (int i = 0; i<tablesConfig.size(); i++) {
            TableConfig tc = tablesConfig.get(i);
            String queryConsult = tc.select;
            try {
                PreparedStatement pSt = conn.prepareStatement(queryConsult);
                ResultSet rs = pSt.executeQuery();
            } catch (Exception e) {
                errCount++;
            }
        }

        if (errCount == tablesConfig.size()) {
            initTables();
        }else{
            throw new IllegalStateException("fatal error: database in inconsistent state.");
        }

        return false;
    }

    
    private boolean initTables() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTables'");
    }

}
