package co.elgranero.controller;

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
        status = states[0];
        tries = 0;
    }

    public void init(){
        boolean result = checkTables();
        if (result) {
            result = login();
            if (result) {
                new View();
            }
        }
    }

    private boolean login(){
        Login login = new Login();
        status = states[0];

        BDConnection bdConn = BDConnection.getInstance();
        boolean result = false;

        while (tries < 10 && !result){
            while (login.isWaiting());
            String[] credentials = login.getCredentials();
            result = bdConn.initConnection(credentials[0],credentials[1]);
            //desactivar el botón de submit
            login.setValidLogin(result, tries);
            tries++;
        }

        return result;
    }

    public String getStatus() {
        return status;
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
