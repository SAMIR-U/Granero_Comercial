package co.elgranero.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Login extends JPanel {

    private JTextField id;
    private JPasswordField passwordField;
    private JButton submit;
    private JLabel tries;

    private boolean waiting;
    private String password;
    private String identifier;

    private final Color colorFondo      = new Color(242, 246, 240);
    private final Color colorVerde      = new Color(38, 90, 52);
    private final Color colorNavBg      = new Color(28, 56, 38);
    private final Color colorNavHover   = new Color(45, 90, 58);
    private final Color colorBorde      = new Color(200, 218, 200);
    private final Color colorLabelVerde = new Color(50, 80, 55);
    private final Color colorTexto      = new Color(33, 33, 33);
    private final Color colorGris       = new Color(150, 150, 150);
    private final Color colorRojo       = new Color(211, 47, 47);

    public Login() {
        waiting    = true;
        password   = null;
        identifier = null;

        setLayout(new GridBagLayout());
        setBackground(colorFondo);

        // ── Tarjeta: usa GridBagLayout para control total ────────────────────
        JPanel tarjeta = new JPanel(new GridBagLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(new CompoundBorder(
                new LineBorder(colorBorde, 1, true),
                new EmptyBorder(40, 50, 40, 50)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        // 1) Encabezado: icono + nombre centrados
        JPanel encabezado = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        encabezado.setOpaque(false);

        JLabel icono = new JLabel("🌾");
        icono.setFont(new Font("Serif", Font.PLAIN, 34));

        JLabel nombre = new JLabel("Granero Comercial");
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nombre.setForeground(colorVerde);

        encabezado.add(icono);
        encabezado.add(nombre);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        tarjeta.add(encabezado, gbc);

        // 2) Label usuario
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 4, 0);
        tarjeta.add(mkLabel("Identificación de Usuario:"), gbc);

        // 3) Campo usuario
        id = new JTextField();
        estilizarCampo(id);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 16, 0);
        tarjeta.add(id, gbc);

        // 4) Label contraseña
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 4, 0);
        tarjeta.add(mkLabel("Contraseña:"), gbc);

        // 5) Campo contraseña
        passwordField = new JPasswordField();
        estilizarCampo(passwordField);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 28, 0);
        tarjeta.add(passwordField, gbc);

        // 6) Botón Ingresar
        submit = new JButton("Ingresar");
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submit.setBackground(colorNavBg);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setOpaque(true);
        submit.setBorderPainted(false);
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submit.setPreferredSize(new Dimension(0, 42));
        submit.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { submit.setBackground(colorNavHover); }
            @Override public void mouseExited(MouseEvent e)  { submit.setBackground(colorNavBg); }
        });
        submit.addActionListener(e -> {
            identifier = id.getText();
            password   = new String(passwordField.getPassword());
            waiting    = false;
        });
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 0);
        tarjeta.add(submit, gbc);

        // 7) Intentos
        tries = new JLabel(" ");
        tries.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        tries.setForeground(colorGris);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        tarjeta.add(tries, gbc);

        // Tamaño fijo de la tarjeta
        tarjeta.setPreferredSize(new Dimension(380, 380));

        add(tarjeta);
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(colorTexto);
        campo.setBackground(new Color(252, 252, 252));
        campo.setPreferredSize(new Dimension(280, 36));
        campo.setBorder(new CompoundBorder(
                new LineBorder(colorBorde),
                new EmptyBorder(4, 8, 4, 8)));
    }

    private JLabel mkLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(colorLabelVerde);
        return l;
    }

    public String[] getCredentials() {
        return new String[]{ identifier, password };
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setValidLogin(boolean isValid, int numTries) {
        if (isValid) {
            Component top = SwingUtilities.getAncestorOfClass(Window.class, this);
            if (top instanceof Window) ((Window) top).dispose();
        } else {
            waiting = true;
            tries.setText("⚠️ Intentos restantes: " + numTries);
            tries.setForeground(colorRojo);
        }
    }
}