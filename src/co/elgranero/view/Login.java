package co.elgranero.view;

import javax.swing.JFrame;

public class Login extends JFrame{
    private boolean waiting;

    public Login(){
        this.waiting = false;
    }

    public String getCredentials(){
        //Unimplemented method
        return null;
    }

    public boolean isValidLogin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidLogin'");
    }

    public boolean isWaiting() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isWaiting'");
    }
    
}
