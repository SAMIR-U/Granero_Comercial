package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelPurchases extends PanelBase {

    private JComboBox<Object> cboSupplier, cboPaymentMethod;
    private JTextField txtDate;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    private final String[] SUPPLIERS = { "Distribuidora Alimentos SAS", "Lácteos del Norte", "Carnes Finas Ltda",
            "Bebidas Andinas" };
    private final String[] PAYMENT_METHODS = { "Efectivo", "Transferencia", "Cheque", "Crédito 30 días" };

    public PanelPurchases() {
        super("🛒  Gestión de Compras",
                new String[] { "ID", "Proveedor", "Forma de Pago", "Fecha" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de la Compra");
        cboSupplier = addCombo("Proveedor *");
        cboPaymentMethod = addCombo("Forma de Pago *");
        txtDate = addField("Fecha (dd/MM/yyyy) *");
        for (String s : SUPPLIERS)
            cboSupplier.addItem(s);
        for (String p : PAYMENT_METHODS)
            cboPaymentMethod.addItem(p);
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        for (Object[] r : data)
            tableModel.addRow(r);
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);
        cboSupplier.setSelectedItem(tableModel.getValueAt(row, 1));
        cboPaymentMethod.setSelectedItem(tableModel.getValueAt(row, 2));
        txtDate.setText((String) tableModel.getValueAt(row, 3));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtDate.setText("");
        if (cboSupplier.getItemCount() > 0)
            cboSupplier.setSelectedIndex(0);
        if (cboPaymentMethod.getItemCount() > 0)
            cboPaymentMethod.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String date = txtDate.getText().trim();
        if (date.isEmpty() || cboSupplier.getSelectedItem() == null) {
            showError("Todos los campos son obligatorios.");
            return;
        }
        Object[] row = { selectedId == -1 ? nextId++ : selectedId,
                cboSupplier.getSelectedItem(), cboPaymentMethod.getSelectedItem(), date };
        if (selectedId == -1)
            data.add(row);
        else
            for (int i = 0; i < data.size(); i++)
                if ((int) data.get(i)[0] == selectedId) {
                    data.set(i, row);
                    break;
                }
        loadData();
        setInitialState();
        clearForm();
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0)
            return;
        if (!confirm("¿Eliminar esta compra?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
