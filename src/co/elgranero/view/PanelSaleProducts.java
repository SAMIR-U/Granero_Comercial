package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelSaleProducts extends PanelBase {

    private JTextField txtSaleId, txtQuantity, txtUnitPrice;
    private JComboBox<Object> cboProduct;
    private int selectedIndex = -1;
    private final List<Object[]> data = new ArrayList<>();

    private final String[] PRODUCTS = { "Arroz Diana 500g", "Leche Alquería 1L", "Aceite Deleite 900ml",
            "Azúcar Manuelita 1kg", "Sal Refisal 500g", "Frijol Bolo 500g" };

    public PanelSaleProducts() {
        super("🧾  Detalle de Productos en Venta",
                new String[] { "ID Venta", "Producto", "Cantidad", "Precio Unitario" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Producto en Venta");
        txtSaleId = addField("ID Venta *");
        cboProduct = addCombo("Producto *");
        txtQuantity = addField("Cantidad *");
        txtUnitPrice = addField("Precio Unitario *");
        for (String p : PRODUCTS)
            cboProduct.addItem(p);
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
        selectedIndex = row;
        txtSaleId.setText(String.valueOf(tableModel.getValueAt(row, 0)));
        cboProduct.setSelectedItem(tableModel.getValueAt(row, 1));
        txtQuantity.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        txtUnitPrice.setText(String.valueOf(tableModel.getValueAt(row, 3)));
    }

    @Override
    protected void clearForm() {
        selectedIndex = -1;
        txtSaleId.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");
        if (cboProduct.getItemCount() > 0)
            cboProduct.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        try {
            int saleId = Integer.parseInt(txtSaleId.getText().trim());
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            double price = Double.parseDouble(txtUnitPrice.getText().trim());
            if (cboProduct.getSelectedItem() == null) {
                showError("Seleccione un producto.");
                return;
            }
            Object[] row = { saleId, cboProduct.getSelectedItem(), qty, price };
            if (selectedIndex == -1)
                data.add(row);
            else
                data.set(selectedIndex, row);
            loadData();
            setInitialState();
            clearForm();
        } catch (NumberFormatException e) {
            showError("Los campos numéricos deben ser valores válidos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0 || selectedIndex < 0)
            return;
        if (!confirm("¿Eliminar este registro?"))
            return;
        data.remove(selectedIndex);
        loadData();
        setInitialState();
        clearForm();
    }
}
