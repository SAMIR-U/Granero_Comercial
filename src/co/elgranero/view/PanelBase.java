package co.elgranero.view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public abstract class PanelBase extends JPanel {

    protected static final Color COLOR_NAV_BG     = MainWindow.COLOR_NAV_BG;
    protected static final Color COLOR_ACCENT      = MainWindow.COLOR_ACCENT;
    protected static final Color COLOR_APP_BG      = MainWindow.COLOR_APP_BG;
    protected static final Color COLOR_PANEL_TITLE = MainWindow.COLOR_PANEL_TITLE;
    protected static final Color COLOR_WHITE       = MainWindow.COLOR_WHITE;

    protected JTable            table;
    protected DefaultTableModel tableModel;
    protected JPanel            formPanel;

    protected JButton btnNew, btnEdit, btnDelete, btnSave, btnCancel;

    public PanelBase(String title, String[] cols) {
        setLayout(new BorderLayout(0, 0));
        setBackground(COLOR_APP_BG);
        add(buildTitle(title), BorderLayout.NORTH);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildTablePanel(cols), buildFormPanel());
        split.setResizeWeight(0.62);
        split.setDividerSize(5);
        split.setDividerLocation(0.62);
        split.setBorder(null);
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
        p.setBorder(new EmptyBorder(11, 18, 11, 18));
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        p.add(lbl, BorderLayout.WEST);
        return p;
    }

    private JPanel buildTablePanel(String[] cols) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(COLOR_APP_BG);
        p.setBorder(new EmptyBorder(12, 0, 8, 8));

        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setIntercellSpacing(new Dimension(8, 0));
        table.setSelectionBackground(new Color(195, 230, 195));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(220, 232, 220));
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(COLOR_NAV_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 32));
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(
                e -> { if (!e.getValueIsAdjusting()) onRowSelected(); });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                if (!sel) setBackground(r % 2 == 0 ? Color.WHITE : new Color(246, 250, 246));
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new LineBorder(new Color(200, 218, 200)));
        sp.getViewport().setBackground(Color.WHITE);
        p.add(sp, BorderLayout.CENTER);
        p.add(buildButtons(), BorderLayout.SOUTH);
        return p;
    }

    private JPanel buildButtons() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 8));
        p.setBackground(COLOR_APP_BG);

        btnNew    = mkBtn("➕  Nuevo",    new Color(39, 119, 58));
        btnEdit   = mkBtn("✏  Editar",   new Color(52, 110, 175));
        btnDelete = mkBtn("🗑  Eliminar", new Color(185, 50, 50));

        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 28));
        sep.setForeground(new Color(180, 200, 180));

        btnSave   = mkBtn("💾  Guardar",  COLOR_NAV_BG);
        btnCancel = mkBtn("✖  Cancelar", Color.GRAY);

        btnNew   .addActionListener(e -> onNew());
        btnEdit  .addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> actionDelete());
        btnSave  .addActionListener(e -> actionSave());
        btnCancel.addActionListener(e -> onCancel());

        p.add(btnNew); p.add(btnEdit); p.add(btnDelete);
        p.add(sep);
        p.add(btnSave); p.add(btnCancel);
        return p;
    }

    protected JButton mkBtn(String txt, Color bg) {
        JButton b = new JButton(txt);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(7, 14, 7, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JScrollPane buildFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        JScrollPane sp = new JScrollPane(formPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(new CompoundBorder(
                new EmptyBorder(12, 8, 8, 0),
                new LineBorder(new Color(200, 218, 200))));
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    protected JTextField addField(String label) {
        formPanel.add(mkLabel(label));
        formPanel.add(Box.createVerticalStrut(3));
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        tf.setAlignmentX(LEFT_ALIGNMENT);
        tf.setBorder(new CompoundBorder(
                new LineBorder(new Color(190, 210, 190)),
                new EmptyBorder(4, 8, 4, 8)));
        formPanel.add(tf);
        formPanel.add(Box.createVerticalStrut(11));
        return tf;
    }

    protected JComboBox<Object> addCombo(String label) {
        formPanel.add(mkLabel(label));
        formPanel.add(Box.createVerticalStrut(3));
        JComboBox<Object> cb = new JComboBox<>();
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        cb.setAlignmentX(LEFT_ALIGNMENT);
        cb.setBackground(Color.WHITE);
        formPanel.add(cb);
        formPanel.add(Box.createVerticalStrut(11));
        return cb;
    }

    protected JTextArea addArea(String label, int rows) {
        formPanel.add(mkLabel(label));
        formPanel.add(Box.createVerticalStrut(3));
        JTextArea ta = new JTextArea(rows, 20);
        ta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ta.setLineWrap(true); ta.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(ta);
        sp.setAlignmentX(LEFT_ALIGNMENT);
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, rows * 22 + 12));
        sp.setBorder(new LineBorder(new Color(190, 210, 190)));
        formPanel.add(sp);
        formPanel.add(Box.createVerticalStrut(11));
        return ta;
    }

    private JLabel mkLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(50, 80, 55));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    protected void addFormTitle(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(COLOR_PANEL_TITLE);
        l.setAlignmentX(LEFT_ALIGNMENT);
        formPanel.add(l);
        formPanel.add(Box.createVerticalStrut(14));
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
            if (comp instanceof Container) traverseComponents((Container) comp, v);
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
        if (table.getSelectedRow() < 0) { showError("Seleccione un registro."); return; }
        setFormEnabled(true);
        btnNew.setEnabled(false);
    }

    private void onCancel() {
        setInitialState();
        table.clearSelection();
        clearForm();
    }

    private void onRowSelected() {
        boolean hasSelection = table.getSelectedRow() >= 0;
        btnEdit.setEnabled(hasSelection);
        btnDelete.setEnabled(hasSelection);
        if (hasSelection) loadIntoForm(table.getSelectedRow());
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
