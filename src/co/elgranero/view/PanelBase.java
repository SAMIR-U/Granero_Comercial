package co.elgranero.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;

public abstract class PanelBase extends JPanel {

    protected static final Color COLOR_NAV_BG = MainWindow.COLOR_NAV_BG;
    protected static final Color COLOR_ACCENT = MainWindow.COLOR_ACCENT;
    protected static final Color COLOR_APP_BG = MainWindow.COLOR_APP_BG;
    protected static final Color COLOR_PANEL_TITLE = MainWindow.COLOR_PANEL_TITLE;
    protected static final Color COLOR_WHITE = MainWindow.COLOR_WHITE;
    protected static final Color COLOR_BORDER = MainWindow.COLOR_BORDER;
    protected static final Color COLOR_CARD_BG = MainWindow.COLOR_CARD_BG;

    private static final Color BTN_NEW = new Color(40, 120, 60);
    private static final Color BTN_EDIT = new Color(44, 100, 168);
    private static final Color BTN_DELETE = new Color(188, 52, 52);
    private static final Color BTN_SAVE = new Color(30, 68, 42);
    private static final Color BTN_CANCEL = new Color(120, 120, 120);

    protected JTable table;
    protected DefaultTableModel tableModel;
    protected JPanel formPanel;

    protected JButton btnNew, btnEdit, btnDelete, btnSave, btnCancel;

    public PanelBase(String title, String[] cols) {
        setLayout(new BorderLayout(0, 0));
        setBackground(COLOR_APP_BG);
        add(buildTitle(title), BorderLayout.NORTH);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildTablePanel(cols), buildFormPanel());
        split.setResizeWeight(0.62);
        split.setDividerSize(6);
        split.setDividerLocation(0.62);
        split.setBorder(null);
        split.setBackground(COLOR_APP_BG);
        add(split, BorderLayout.CENTER);
    }

    protected final void initialize() {
        buildForm();
        loadData();
        setInitialState();
    }

    private JPanel buildTitle(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_PANEL_TITLE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(212, 175, 55, 100)),
                new EmptyBorder(12, 20, 12, 20)));
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        p.add(lbl, BorderLayout.WEST);
        return p;
    }

    private JPanel buildTablePanel(String[] cols) {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(COLOR_APP_BG);
        p.setBorder(new EmptyBorder(14, 2, 10, 8));

        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 0));
        table.setSelectionBackground(new Color(180, 225, 185));
        table.setSelectionForeground(new Color(20, 55, 28));
        table.setGridColor(new Color(218, 235, 218));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(COLOR_NAV_BG);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 34));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                if (!sel) {
                    setBackground(r % 2 == 0 ? Color.WHITE : new Color(243, 249, 243));
                    setForeground(new Color(33, 40, 33));
                } else {
                    setForeground(new Color(20, 55, 28));
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        });

        table.getSelectionModel().addListSelectionListener(
                e -> {
                    if (!e.getValueIsAdjusting())
                        onRowSelected();
                });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        sp.getViewport().setBackground(Color.WHITE);
        sp.getVerticalScrollBar().setUnitIncrement(14);

        p.add(sp, BorderLayout.CENTER);
        p.add(buildButtons(), BorderLayout.SOUTH);
        return p;
    }

    private JPanel buildButtons() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 10));
        p.setBackground(COLOR_APP_BG);

        btnNew = mkBtn("Nuevo", BTN_NEW);
        btnEdit = mkBtn("Editar", BTN_EDIT);
        btnDelete = mkBtn("Eliminar", BTN_DELETE);

        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 26));
        sep.setForeground(new Color(190, 210, 190));

        btnSave = mkBtn("Guardar", BTN_SAVE);
        btnCancel = mkBtn("Cancelar", BTN_CANCEL);

        btnNew.addActionListener(e -> onNew());
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> actionDelete());
        btnSave.addActionListener(e -> actionSave());
        btnCancel.addActionListener(e -> onCancel());

        p.add(btnNew);
        p.add(btnEdit);
        p.add(btnDelete);
        p.add(sep);
        p.add(btnSave);
        p.add(btnCancel);
        return p;
    }

    protected JButton mkBtn(String txt, Color bg) {
        JButton b = new JButton(txt) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = isEnabled() ? getBackground() : new Color(180, 180, 180);
                if (getModel().isPressed()) {
                    g2.setColor(base.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(base.brighter());
                } else {
                    g2.setColor(base);
                }
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorder(new EmptyBorder(7, 15, 7, 15));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JScrollPane buildFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane sp = new JScrollPane(formPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(14, 8, 10, 2),
                BorderFactory.createLineBorder(COLOR_BORDER, 1)));
        sp.getViewport().setBackground(Color.WHITE);
        sp.getVerticalScrollBar().setUnitIncrement(14);
        return sp;
    }

    protected JTextField addField(String label) {
        formPanel.add(mkLabel(label));
        formPanel.add(Box.createVerticalStrut(4));
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        tf.setAlignmentX(LEFT_ALIGNMENT);
        tf.setBackground(new Color(250, 253, 250));
        tf.setForeground(new Color(30, 40, 30));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(185, 215, 185)),
                new EmptyBorder(5, 10, 5, 10)));
        formPanel.add(tf);
        formPanel.add(Box.createVerticalStrut(12));
        return tf;
    }

    protected JComboBox<Object> addCombo(String label) {
        formPanel.add(mkLabel(label));
        formPanel.add(Box.createVerticalStrut(4));
        JComboBox<Object> cb = new JComboBox<>();
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        cb.setAlignmentX(LEFT_ALIGNMENT);
        cb.setBackground(new Color(250, 253, 250));
        cb.setBorder(BorderFactory.createLineBorder(new Color(185, 215, 185)));
        formPanel.add(cb);
        formPanel.add(Box.createVerticalStrut(12));
        return cb;
    }

    protected JTextArea addArea(String label, int rows) {
        formPanel.add(mkLabel(label));
        formPanel.add(Box.createVerticalStrut(4));
        JTextArea ta = new JTextArea(rows, 20);
        ta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBackground(new Color(250, 253, 250));
        JScrollPane sp = new JScrollPane(ta);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, rows * 24 + 12));
        sp.setBorder(BorderFactory.createLineBorder(new Color(185, 215, 185)));
        formPanel.add(sp);
        formPanel.add(Box.createVerticalStrut(12));
        return ta;
    }

    private JLabel mkLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(44, 88, 55));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    protected void addFormTitle(String txt) {
        if (formPanel.getComponentCount() > 0)
            formPanel.add(Box.createVerticalStrut(8));
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(COLOR_PANEL_TITLE);
        l.setAlignmentX(LEFT_ALIGNMENT);
        formPanel.add(l);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(190, 220, 190));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(LEFT_ALIGNMENT);
        formPanel.add(sep);
        formPanel.add(Box.createVerticalStrut(12));
    }

    protected void setInitialState() {
        setFormEnabled(false);
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnNew.setEnabled(true);
    }

    protected void setFormEnabled(boolean v) {
        traverseComponents(formPanel, v);
        btnSave.setEnabled(v);
        btnCancel.setEnabled(v);
    }

    private void traverseComponents(Container c, boolean v) {
        for (Component comp : c.getComponents()) {
            comp.setEnabled(v);
            if (comp instanceof Container)
                traverseComponents((Container) comp, v);
        }
    }

    private void onNew() {
        table.clearSelection();
        clearForm();
        setFormEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnNew.setEnabled(false);
    }

    private void onEdit() {
        if (table.getSelectedRow() < 0) {
            showError("Seleccione un registro.");
            return;
        }
        setFormEnabled(true);
        btnNew.setEnabled(false);
    }

    private void onCancel() {
        setInitialState();
        table.clearSelection();
        clearForm();
    }

    private void onRowSelected() {
        boolean sel = table.getSelectedRow() >= 0;
        btnEdit.setEnabled(sel);
        btnDelete.setEnabled(sel);
        if (sel)
            loadIntoForm(table.getSelectedRow());
    }

    protected void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    protected boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    protected void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    protected abstract void buildForm();

    protected abstract void loadData();

    protected abstract void loadIntoForm(int row);

    protected abstract void clearForm();

    protected abstract void actionSave();

    protected abstract void actionDelete();
}