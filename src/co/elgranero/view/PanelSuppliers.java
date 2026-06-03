package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelSuppliers extends PanelBase {

    private JTextField txtName, txtDocument, txtPhone;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    public PanelSuppliers() {
        super("🏭  Gestión de Proveedores", new String[] { "ID", "Nombre", "Documento", "Celular" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos del Proveedor");
        txtName = addField("Nombre / Razón Social *");
        txtDocument = addField("Documento / NIT *");
        txtPhone = addField("Celular");
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
        txtDocument.setText((String) tableModel.getValueAt(row, 2));
        txtPhone.setText((String) tableModel.getValueAt(row, 3));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        txtDocument.setText("");
        txtPhone.setText("");
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            showError("El nombre es obligatorio.");
            return;
        }
        Object[] row = { selectedId == -1 ? nextId++ : selectedId, name,
                txtDocument.getText().trim(), txtPhone.getText().trim() };
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
        if (!confirm("¿Eliminar este proveedor?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
