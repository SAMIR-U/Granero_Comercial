package co.elgranero.controller;

import co.elgranero.view.Login;
import co.elgranero.view.View;

public class Runner {
    public void init(){
        Login login = new Login();
        while (!login.isValidLogin()) {
            while (login.isWaiting());
        }
        if (checkTables()){
            new View();
        }
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
