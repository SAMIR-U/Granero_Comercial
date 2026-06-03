package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelProductPresentations extends PanelBase {

    private JComboBox<Object> cboPresentation, cboProduct;
    private JTextField txtPrice;
    private int selectedIndex = -1;
    private final List<Object[]> data = new ArrayList<>();

    private final String[] PRESENTATIONS = { "Bolsa 500g", "Caja x12", "Litro", "Unidad", "Bulto 50kg", "Paquete x6" };
    private final String[] PRODUCTS = { "Arroz Diana", "Leche Alquería", "Aceite Deleite", "Azúcar Manuelita",
            "Sal Refisal" };

    public PanelProductPresentations() {
        super("🔗  Presentaciones por Producto",
                new String[] { "Presentación", "Producto", "Precio ($)" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Asignar Presentación");
        cboPresentation = addCombo("Presentación *");
        cboProduct = addCombo("Producto *");
        txtPrice = addField("Precio de Presentación *");
        for (String p : PRESENTATIONS)
            cboPresentation.addItem(p);
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
        cboPresentation.setSelectedItem(tableModel.getValueAt(row, 0));
        cboProduct.setSelectedItem(tableModel.getValueAt(row, 1));
        txtPrice.setText(String.valueOf(tableModel.getValueAt(row, 2)));
    }

    @Override
    protected void clearForm() {
        selectedIndex = -1;
        txtPrice.setText("");
        if (cboPresentation.getItemCount() > 0)
            cboPresentation.setSelectedIndex(0);
        if (cboProduct.getItemCount() > 0)
            cboProduct.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        if (cboPresentation.getSelectedItem() == null || cboProduct.getSelectedItem() == null
                || txtPrice.getText().trim().isEmpty()) {
            showError("Todos los campos son obligatorios.");
            return;
        }
        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            Object[] row = { cboPresentation.getSelectedItem(), cboProduct.getSelectedItem(), price };
            if (selectedIndex == -1)
                data.add(row);
            else
                data.set(selectedIndex, row);
            loadData();
            setInitialState();
            clearForm();
        } catch (NumberFormatException e) {
            showError("El precio debe ser un número válido.");
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
