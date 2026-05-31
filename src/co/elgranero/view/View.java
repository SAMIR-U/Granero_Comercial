package co.elgranero.view;

import javax.swing.JFrame;

public class View extends JFrame{
    public static final String[] states ={
        "La app no ha podido Inciar Correctamente",
        "Cheking tables",
        "initializing tables",
        "Successful login",
        "Login locked"
    };
    private String status;

	public void setStatus(int stateId) {
        this.status = states[stateId];
    }

    public Login showLogin() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'showLogin'");
	}

    public void showMainMenu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showMainMenu'");
    }

    public void setStatus(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showMainMenu'");
    }
    
}
