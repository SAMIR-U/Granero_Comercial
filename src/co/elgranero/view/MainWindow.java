package co.elgranero.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;

public class MainWindow extends JFrame {

    public static final Color COLOR_NAV_BG = new Color(30, 68, 42);
    public static final Color COLOR_NAV_HOVER = new Color(50, 100, 64);
    public static final Color COLOR_NAV_ACTIVE = new Color(62, 120, 76);
    public static final Color COLOR_ACCENT = new Color(212, 175, 55);
    public static final Color COLOR_HEADER = new Color(22, 52, 32);
    public static final Color COLOR_APP_BG = new Color(240, 246, 238);
    public static final Color COLOR_PANEL_TITLE = new Color(34, 85, 48);
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_SECTION_LABEL = new Color(180, 210, 140);
    public static final Color COLOR_SEPARATOR = new Color(52, 88, 60);
    public static final Color COLOR_CARD_BG = new Color(250, 253, 248);
    public static final Color COLOR_BORDER = new Color(190, 218, 190);

    public static final Font FONT_MENU = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 10);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 17);

    private final JPanel contentPanel = new JPanel(new BorderLayout());
    private JButton activeBtn = null;

    public MainWindow() {
        setTitle("Granero Comercial — Sistema de Gestión");
        setSize(1300, 800);
        setMinimumSize(new Dimension(960, 620));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_APP_BG);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildNav(), BorderLayout.WEST);

        contentPanel.setBackground(COLOR_APP_BG);
        contentPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        contentPanel.add(buildWelcome(), BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(COLOR_HEADER);
        h.setPreferredSize(new Dimension(0, 62));
        h.setBorder(new EmptyBorder(0, 24, 0, 24));

        h.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(212, 175, 55, 120)),
                new EmptyBorder(0, 24, 0, 24)));

        JLabel brand = new JLabel("Granero Comercial");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brand.setForeground(COLOR_ACCENT);
        h.add(brand, BorderLayout.WEST);

        JPanel rightInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightInfo.setOpaque(false);

        JLabel sub = new JLabel("Sistema de Gestión Comercial");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(150, 195, 150));
        rightInfo.add(sub);
        h.add(rightInfo, BorderLayout.EAST);

        return h;
    }

    private JScrollPane buildNav() {
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(COLOR_NAV_BG);
        nav.setBorder(new EmptyBorder(12, 0, 12, 0));

        addSection(nav, "PRODUCTOS");
        addNavBtn(nav, "  Productos", () -> showPanel(new PanelProducts()));
        addNavBtn(nav, "  Categorías", () -> showPanel(new PanelCategories()));
        addNavBtn(nav, "  Subcategorías", () -> showPanel(new PanelSubcategories()));
        addNavBtn(nav, "  Presentaciones", () -> showPanel(new PanelPresentations()));
        addNavBtn(nav, "  Pres. × Producto", () -> showPanel(new PanelProductPresentations()));

        addSection(nav, "COMPRAS");
        addNavBtn(nav, "  Compras", () -> showPanel(new PanelPurchases()));
        addNavBtn(nav, "  Detalle Compras", () -> showPanel(new PanelPurchaseProducts()));
        addNavBtn(nav, "  Proveedores", () -> showPanel(new PanelSuppliers()));

        addSection(nav, "VENTAS");
        addNavBtn(nav, "  Ventas", () -> showPanel(new PanelSales()));
        addNavBtn(nav, "  Detalle Ventas", () -> showPanel(new PanelSaleProducts()));

        addSection(nav, "CLIENTES");
        addNavBtn(nav, "  Personas / Clientes", () -> showPanel(new PanelPersons()));
        addNavBtn(nav, "  Ciudades", () -> showPanel(new PanelCities()));
        addNavBtn(nav, "  Países", () -> showPanel(new PanelCountries()));

        addSection(nav, "GENERAL");
        addNavBtn(nav, "  Formas de Pago", () -> showPanel(new PanelPaymentMethods()));

        addSection(nav, "REPORTES");
        addNavBtn(nav, "  Análisis de Ventas", () -> showPanel(new PanelReportSalesComparison()));
        addNavBtn(nav, "  Historial de Clientes", () -> showPanel(new PanelReportCustomerHistory()));
        addNavBtn(nav, "  Rendimiento Productos", () -> showPanel(new PanelReportTopProducts()));

        nav.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("  v1.0  •  El Granero");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footer.setForeground(new Color(100, 140, 110));
        footer.setBorder(new EmptyBorder(8, 6, 4, 0));
        footer.setAlignmentX(LEFT_ALIGNMENT);
        nav.add(footer);

        JScrollPane sp = new JScrollPane(nav,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setPreferredSize(new Dimension(222, 0));
        sp.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(50, 88, 60)));
        sp.getVerticalScrollBar().setUnitIncrement(14);
        sp.getVerticalScrollBar().setBackground(COLOR_NAV_BG);
        return sp;
    }

    private void addSection(JPanel nav, String txt) {
        JLabel lbl = new JLabel("   " + txt);
        lbl.setFont(FONT_SECTION);
        lbl.setForeground(COLOR_SECTION_LABEL);
        lbl.setBorder(new EmptyBorder(16, 6, 4, 0));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        nav.add(lbl);

        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_SEPARATOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        nav.add(sep);
    }

    private void addNavBtn(JPanel nav, String txt, Runnable action) {
        JButton b = new JButton(txt);
        b.setFont(FONT_MENU);
        b.setForeground(new Color(210, 235, 210));
        b.setBackground(COLOR_NAV_BG);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(new EmptyBorder(9, 14, 9, 10));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(222, 40));
        b.setAlignmentX(LEFT_ALIGNMENT);

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (b != activeBtn) {
                    b.setBackground(COLOR_NAV_HOVER);
                    b.setForeground(Color.WHITE);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (b != activeBtn) {
                    b.setBackground(COLOR_NAV_BG);
                    b.setForeground(new Color(210, 235, 210));
                }
            }
        });

        b.addActionListener(e -> {
            if (activeBtn != null) {
                activeBtn.setBackground(COLOR_NAV_BG);
                activeBtn.setForeground(new Color(210, 235, 210));
            }
            activeBtn = b;
            b.setBackground(COLOR_NAV_ACTIVE);
            b.setForeground(COLOR_ACCENT);
            action.run();
        });

        nav.add(b);
    }

    private JPanel buildWelcome() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(COLOR_APP_BG);

        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 18, 18));
                g2.setColor(COLOR_BORDER);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 18, 18));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(480, 340));
        card.setBorder(new EmptyBorder(40, 60, 40, 60));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel icono = new JLabel("", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI", Font.PLAIN, 58));
        c.gridy = 0;
        c.insets = new Insets(0, 0, 18, 0);
        card.add(icono, c);

        JLabel titulo = new JLabel("Bienvenido al Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(COLOR_PANEL_TITLE);
        c.gridy = 1;
        c.insets = new Insets(0, 0, 12, 0);
        card.add(titulo, c);

        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_BORDER);
        c.gridy = 2;
        c.insets = new Insets(0, 20, 18, 20);
        card.add(sep, c);

        JLabel sub = new JLabel(
                "<html><div style='text-align:center;color:#5a8a6a;font-size:12px'>" +
                        "Seleccione una opción del menú lateral<br>para comenzar a gestionar el granero.</div></html>",
                SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.gridy = 3;
        c.insets = new Insets(0, 0, 0, 0);
        card.add(sub, c);

        outer.add(card);
        return outer;
    }

    public void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}