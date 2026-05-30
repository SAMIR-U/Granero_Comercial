package co.elgranero.controller;

import java.sql.Connection;

import co.elgranero.net.BDConnection;
import co.elgranero.view.Login;
import co.elgranero.view.View;

public class Runner {
    private final String[] states ={
        "Login",
        "Cheking tables",
        "Creating tables"
    };
    private int tries;
    private String status;

    public Runner(){
        tries = 0;
    }

    public void init(){
        login();
        if (checkTables()){
            new View();
        }
    }

    private boolean login(){
        boolean result = false;
        status = states[0];
        Login login = new Login();
        while (tries < 10 && !result){
            while (login.isWaiting());
            String password = login.getCredentials();
            result = BDConnection.initConnection(password);
            login.setValidLogin(result);
            tries++;
        }
        return result;
    }

    private boolean checkTables() {
        //no implement
        createTables();
        return false;
    }

    private void createTables() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTables'");
    }

}
