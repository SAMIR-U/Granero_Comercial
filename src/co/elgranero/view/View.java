package co.elgranero.view;

import javax.swing.JFrame;

public class View extends JFrame{
    public static final String[] states ={
        "Login",
        "Cheking tables",
        "Creating tables"
    };
    public String status;

	public void setStatus(int stateId) {
        this.status = states[stateId];
    }

    public Login showLogin() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'showLogin'");
	}
    
}
