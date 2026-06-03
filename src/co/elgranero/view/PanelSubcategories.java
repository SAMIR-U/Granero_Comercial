package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelSubcategories extends PanelBase {

    private JTextField txtName;
    private JComboBox<Object> cboCategory;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    private final String[] CATEGORIES = { "Granos", "Lácteos", "Carnes", "Bebidas", "Aseo", "Frutas y Verduras" };

    public PanelSubcategories() {
        super("📁  Gestión de Subcategorías", new String[] { "ID", "Subcategoría", "Categoría" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de Subcategoría");
        txtName = addField("Nombre de Subcategoría *");
        cboCategory = addCombo("Categoría *");
        for (String c : CATEGORIES)
            cboCategory.addItem(c);
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
        cboCategory.setSelectedItem(tableModel.getValueAt(row, 2));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        if (cboCategory.getItemCount() > 0)
            cboCategory.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        if (name.isEmpty() || cboCategory.getSelectedItem() == null) {
            showError("Todos los campos son obligatorios.");
            return;
        }
        Object[] row = { selectedId == -1 ? nextId++ : selectedId, name, cboCategory.getSelectedItem() };
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
        if (!confirm("¿Eliminar esta subcategoría?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
