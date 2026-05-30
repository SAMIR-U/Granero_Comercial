package co.elgranero.view;

import javax.swing.JFrame;

public class Login extends JFrame{
    private boolean waiting;

    public Login(){
        this.waiting = true;
    }

    public String getCredentials(){
        //Unimplemented method
        return null;
    }

    public boolean isWaiting() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isWaiting'");
    }

    public void setValidLogin(boolean result) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setValidLogin'");
    }
    
}
