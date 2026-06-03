package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelProducts extends PanelBase {

    private JTextField txtName, txtExpiry;
    private JTextArea txtDescription;
    private JComboBox<Object> cboSubcategory;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    private final String[] SUBCATEGORIES = { "Arroz", "Frijol", "Leche", "Queso", "Pollo", "Res",
            "Agua", "Jugo", "Jabón", "Shampoo", "Manzana", "Tomate" };

    public PanelProducts() {
        super("📦  Gestión de Productos",
                new String[] { "ID", "Nombre", "Subcategoría", "Vencimiento", "Descripción" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos del Producto");
        txtName = addField("Nombre del Producto *");
        txtDescription = addArea("Descripción", 3);
        txtExpiry = addField("Fecha Vencimiento (dd/MM/yyyy)");
        cboSubcategory = addCombo("Subcategoría *");
        for (String s : SUBCATEGORIES)
            cboSubcategory.addItem(s);
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
        txtName.setText((String) tableModel.getValueAt(row, 1));
        cboSubcategory.setSelectedItem(tableModel.getValueAt(row, 2));
        txtExpiry.setText((String) tableModel.getValueAt(row, 3));
        txtDescription.setText((String) tableModel.getValueAt(row, 4));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        txtDescription.setText("");
        txtExpiry.setText("");
        if (cboSubcategory.getItemCount() > 0)
            cboSubcategory.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        if (name.isEmpty() || cboSubcategory.getSelectedItem() == null) {
            showError("Nombre y Subcategoría son obligatorios.");
            return;
        }
        Object[] row = { selectedId == -1 ? nextId++ : selectedId, name,
                cboSubcategory.getSelectedItem(), txtExpiry.getText().trim(),
                txtDescription.getText().trim() };
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
        if (!confirm("¿Eliminar este producto?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
