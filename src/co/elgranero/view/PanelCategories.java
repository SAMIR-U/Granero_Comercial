package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelCategories extends PanelBase {

    private JTextField txtName;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    public PanelCategories() {
        super("📂  Gestión de Categorías de Productos", new String[] { "ID", "Categoría" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de Categoría");
        txtName = addField("Nombre de Categoría *");
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
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            showError("El nombre es obligatorio.");
            return;
        }
        if (selectedId == -1)
            data.add(new Object[] { nextId++, name });
        else
            for (Object[] r : data)
                if ((int) r[0] == selectedId) {
                    r[1] = name;
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
        if (!confirm("¿Eliminar esta categoría?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
