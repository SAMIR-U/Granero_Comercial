package co.elgranero.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Login extends JPanel {
    private LoginCallback callback;

    private JTextField id;
    private JPasswordField passwordField;
    private JButton submit;
    private JLabel tries;

    private String password;
    private String identifier;

    private final Color colorFondo = new Color(236, 244, 234);
    private final Color colorVerde = new Color(34, 85, 48);
    private final Color colorNavBg = new Color(30, 68, 42);
    private final Color colorNavHover = new Color(50, 100, 64);
    private final Color colorBorde = new Color(185, 218, 185);
    private final Color colorLabel = new Color(44, 88, 55);
    private final Color colorTexto = new Color(28, 35, 28);
    private final Color colorGris = new Color(140, 155, 140);
    private final Color colorRojo = new Color(200, 44, 44);
    private final Color colorAccent = new Color(212, 175, 55);

    public Login(LoginCallback callback) {
        this.callback = callback;
        password = null;
        identifier = null;

        setLayout(new GridBagLayout());

        setOpaque(false);

        JPanel tarjeta = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(colorBorde);
                g2.setStroke(new BasicStroke(1.2f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 20, 20));
                g2.dispose();
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setBorder(new EmptyBorder(44, 52, 44, 52));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);

        JLabel icono = new JLabel("", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI", Font.PLAIN, 44));
        icono.setAlignmentX(CENTER_ALIGNMENT);

        JLabel nombre = new JLabel("Granero Comercial", SwingConstants.CENTER);
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nombre.setForeground(colorVerde);
        nombre.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitulo.setForeground(colorGris);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        encabezado.add(icono);
        encabezado.add(Box.createVerticalStrut(8));
        encabezado.add(nombre);
        encabezado.add(Box.createVerticalStrut(4));
        encabezado.add(subtitulo);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 28, 0);
        tarjeta.add(encabezado, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(210, 230, 210));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 24, 0);
        tarjeta.add(sep, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        tarjeta.add(mkLabel("Identificación de Usuario"), gbc);

        id = new JTextField();
        estilizarCampo(id);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 18, 0);
        tarjeta.add(id, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 5, 0);
        tarjeta.add(mkLabel("Contraseña"), gbc);

        passwordField = new JPasswordField();
        estilizarCampo(passwordField);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 30, 0);
        tarjeta.add(passwordField, gbc);

        submit = new JButton("Ingresar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = isEnabled() ? (getModel().isPressed() ? colorNavHover.darker()
                        : getModel().isRollover() ? colorNavHover : colorNavBg)
                        : new Color(160, 175, 160);
                g2.setColor(base);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setOpaque(false);
        submit.setContentAreaFilled(false);
        submit.setBorderPainted(false);
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submit.setPreferredSize(new Dimension(0, 44));
        submit.addActionListener(e -> {
            submit.setEnabled(false);
            identifier = id.getText();
            password = new String(passwordField.getPassword());
            if (callback != null)
                callback.onLogin(identifier, password);
        });
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 12, 0);
        tarjeta.add(submit, gbc);

        tries = new JLabel(" ", SwingConstants.CENTER);
        tries.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        tries.setForeground(colorGris);
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        tarjeta.add(tries, gbc);

        tarjeta.setPreferredSize(new Dimension(400, 460));
        add(tarjeta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(232, 244, 230),
                getWidth(), getHeight(), new Color(215, 236, 215));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(colorTexto);
        campo.setBackground(new Color(248, 252, 248));
        campo.setPreferredSize(new Dimension(296, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde),
                new EmptyBorder(5, 11, 5, 11)));
        campo.setCaretColor(colorVerde);
    }

    private JLabel mkLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(colorLabel);
        return l;
    }

    public String[] getCredentials() {
        return new String[] { identifier, password };
    }

    public void setValidLogin(boolean isValid, int numTries) {
        if (isValid) {
            Component top = SwingUtilities.getAncestorOfClass(Window.class, this);
            if (top instanceof Window)
                ((Window) top).dispose();
        } else {
            submit.setEnabled(true);
            tries.setText("Intentos restantes: " + numTries);
            tries.setForeground(colorRojo);
        }
    }
}