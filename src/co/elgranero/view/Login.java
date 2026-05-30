package co.elgranero.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
    private JTextField id;
    private JPasswordField passwordField;
    private JButton submit;
    private JLabel tries;
    
    private boolean waiting;
    private String password;
    private String identifier;

    public Login(){
        //JFrame configs

        this.waiting = true;
        this.password = null;
        this.identifier = null;
    
        this.submit = new JButton("Entrar");
        this.id = new JTextField();
        this.tries = new JLabel();

        this.submit.addActionListener(e->{
            identifier = id.getText();
            password = new String(passwordField.getPassword());
            waiting = false;
        });
    }

    public String[] getCredentials(){
        return new String[]{identifier,password};
    }

    public boolean isWaiting() {
        return this.waiting;
    }

    public void setValidLogin(boolean isValid, int numTries) {
        if (isValid) {
            //show correct login
            //close frame just the frame
        }else{
            //show error login
            waiting = true;
            tries.setText("Intentos restantes: "+numTries);
        }
    }
}
