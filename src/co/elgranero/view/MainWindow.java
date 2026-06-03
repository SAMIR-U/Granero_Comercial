package co.elgranero.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

    public static final Color COLOR_NAV_BG = new Color(28, 56, 38);
    public static final Color COLOR_NAV_HOVER = new Color(45, 90, 58);
    public static final Color COLOR_ACCENT = new Color(210, 160, 40);
    public static final Color COLOR_HEADER = new Color(20, 44, 28);
    public static final Color COLOR_APP_BG = new Color(242, 246, 240);
    public static final Color COLOR_PANEL_TITLE = new Color(38, 90, 52);
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Font FONT_MENU = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 10);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    private final JPanel contentPanel = new JPanel(new BorderLayout());

    public MainWindow() {
        setTitle("Granero Comercial — Sistema de Gestión");
        setSize(1280, 780);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_APP_BG);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildNav(), BorderLayout.WEST);

        contentPanel.setBackground(COLOR_APP_BG);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.add(buildWelcome(), BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(COLOR_HEADER);
        h.setPreferredSize(new Dimension(0, 58));
        h.setBorder(new EmptyBorder(0, 22, 0, 22));

        JLabel brand = new JLabel("🌾  Granero Comercial");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 21));
        brand.setForeground(COLOR_ACCENT);
        h.add(brand, BorderLayout.WEST);

        JLabel sub = new JLabel("Sistema de Gestión Comercial");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(160, 200, 160));
        h.add(sub, BorderLayout.EAST);
        return h;
    }

    private JScrollPane buildNav() {
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(COLOR_NAV_BG);
        nav.setBorder(new EmptyBorder(8, 0, 8, 0));

        addSection(nav, "PRODUCTOS");
        addNavBtn(nav, "  📦  Productos", () -> showPanel(new PanelProducts()));
        addNavBtn(nav, "  📂  Categorías", () -> showPanel(new PanelCategories()));
        addNavBtn(nav, "  📁  Subcategorías", () -> showPanel(new PanelSubcategories()));
        addNavBtn(nav, "  🏷  Presentaciones", () -> showPanel(new PanelPresentations()));
        addNavBtn(nav, "  🔗  Pres. × Producto", () -> showPanel(new PanelProductPresentations()));

        addSection(nav, "COMPRAS");
        addNavBtn(nav, "  🛒  Compras", () -> showPanel(new PanelPurchases()));
        addNavBtn(nav, "  📋  Detalle Compras", () -> showPanel(new PanelPurchaseProducts()));
        addNavBtn(nav, "  🏭  Proveedores", () -> showPanel(new PanelSuppliers()));

        addSection(nav, "VENTAS");
        addNavBtn(nav, "  💰  Ventas", () -> showPanel(new PanelSales()));
        addNavBtn(nav, "  🧾  Detalle Ventas", () -> showPanel(new PanelSaleProducts()));

        addSection(nav, "CLIENTES");
        addNavBtn(nav, "  👤  Personas / Clientes", () -> showPanel(new PanelPersons()));
        addNavBtn(nav, "  🏙  Ciudades", () -> showPanel(new PanelCities()));
        addNavBtn(nav, "  🌍  Países", () -> showPanel(new PanelCountries()));

        addSection(nav, "GENERAL");
        addNavBtn(nav, "  💳  Formas de Pago", () -> showPanel(new PanelPaymentMethods()));

        addSection(nav, "REPORTES Y ESTADÍSTICAS");
        addNavBtn(nav, "  📊  Análisis de Ventas", () -> showPanel(new PanelReportSalesComparison()));
        addNavBtn(nav, "  👥  Historial de Clientes", () -> showPanel(new PanelReportCustomerHistory()));
        addNavBtn(nav, "  📈  Rendimiento Productos", () -> showPanel(new PanelReportTopProducts()));

        nav.add(Box.createVerticalGlue());

        JScrollPane sp = new JScrollPane(nav,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setPreferredSize(new Dimension(218, 0));
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(12);
        return sp;
    }

    private void addSection(JPanel nav, String txt) {
        JLabel lbl = new JLabel("  " + txt);
        lbl.setFont(FONT_SECTION);
        lbl.setForeground(new Color(200, 170, 80));
        lbl.setBorder(new EmptyBorder(14, 6, 3, 0));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        nav.add(lbl);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(50, 80, 55));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        nav.add(sep);
    }

    private void addNavBtn(JPanel nav, String txt, Runnable action) {
        JButton b = new JButton(txt);
        b.setFont(FONT_MENU);
        b.setForeground(new Color(220, 240, 220));
        b.setBackground(COLOR_NAV_BG);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(new EmptyBorder(8, 12, 8, 8));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(218, 38));
        b.setAlignmentX(LEFT_ALIGNMENT);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(COLOR_NAV_HOVER);
                b.setForeground(COLOR_ACCENT);
            }

            public void mouseExited(MouseEvent e) {
                b.setBackground(COLOR_NAV_BG);
                b.setForeground(new Color(220, 240, 220));
            }
        });
        b.addActionListener(e -> action.run());
        nav.add(b);
    }

    private JPanel buildWelcome() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(COLOR_APP_BG);
        JLabel lbl = new JLabel(
                "<html><div style='text-align:center'>" +
                        "<span style='font-size:52px'>🌾</span><br/><br/>" +
                        "<span style='font-size:22px;color:#26593A;font-weight:bold'>Bienvenido al Sistema</span><br/><br/>"
                        +
                        "<span style='font-size:13px;color:#666666'>Seleccione una opción del menú lateral para comenzar</span>"
                        +
                        "</div></html>",
                SwingConstants.CENTER);
        p.add(lbl);
        return p;
    }

    public void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}