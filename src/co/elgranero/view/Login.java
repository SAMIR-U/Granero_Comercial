package co.elgranero.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
    private boolean waiting;
    private JPasswordField passwordField;
    private JTextField id;
    private JButton submit;
    private String password;
    private String identifier;

    public Login(){
        this.waiting = true;
        this.password = null;
        this.submit = new JButton("Iniciar");
        this.submit.addActionListener(e->{
            identifier = id.getText();
            new String(passwordField.getPassword());
        });
    }

    public String getCredentials(){
        return password;
    }

    public boolean isWaiting() {
        return this.waiting;
    }

    public void setValidLogin(boolean result) {
        
    }
    
}
