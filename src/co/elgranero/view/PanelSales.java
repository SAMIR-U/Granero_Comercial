package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelSales extends PanelBase {

    private JComboBox<Object> cboCustomer, cboPaymentMethod;
    private JTextField txtDate, txtDiscount;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    private final String[] CUSTOMERS = { "Juan Pérez", "María López", "Carlos Gómez", "Ana Torres", "Luis Martínez" };
    private final String[] PAYMENT_METHODS = { "Efectivo", "Transferencia", "Tarjeta Débito", "Tarjeta Crédito",
            "Fiado" };

    public PanelSales() {
        super("💰  Gestión de Ventas",
                new String[] { "ID", "Cliente", "Forma de Pago", "Fecha", "Descuento" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de la Venta");
        cboCustomer = addCombo("Cliente *");
        cboPaymentMethod = addCombo("Forma de Pago *");
        txtDate = addField("Fecha (dd/MM/yyyy) *");
        txtDiscount = addField("Descuento (%) — opcional");
        for (String c : CUSTOMERS)
            cboCustomer.addItem(c);
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
        cboCustomer.setSelectedItem(tableModel.getValueAt(row, 1));
        cboPaymentMethod.setSelectedItem(tableModel.getValueAt(row, 2));
        txtDate.setText((String) tableModel.getValueAt(row, 3));
        txtDiscount.setText(String.valueOf(tableModel.getValueAt(row, 4)));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtDate.setText("");
        txtDiscount.setText("0");
        if (cboCustomer.getItemCount() > 0)
            cboCustomer.setSelectedIndex(0);
        if (cboPaymentMethod.getItemCount() > 0)
            cboPaymentMethod.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String date = txtDate.getText().trim();
        if (date.isEmpty() || cboCustomer.getSelectedItem() == null) {
            showError("Cliente y Fecha son obligatorios.");
            return;
        }
        try {
            String discountStr = txtDiscount.getText().trim();
            double discount = discountStr.isEmpty() ? 0.0 : Double.parseDouble(discountStr);
            Object[] row = { selectedId == -1 ? nextId++ : selectedId,
                    cboCustomer.getSelectedItem(), cboPaymentMethod.getSelectedItem(), date, discount };
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
        } catch (NumberFormatException e) {
            showError("El descuento debe ser un número válido.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0)
            return;
        if (!confirm("¿Eliminar esta venta?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
