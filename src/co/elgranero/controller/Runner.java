package co.elgranero.controller;

import co.elgranero.net.BDConnection;
import co.elgranero.view.Login;
import co.elgranero.view.View;

public class Runner {
    private View view;
    private int tries;

    public Runner(){
        this.view = new View();
        this.tries = 0;
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

    private boolean checkTables() {
        
        createTables();
        return false;
    }

    private void createTables() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTables'");
    }

}
