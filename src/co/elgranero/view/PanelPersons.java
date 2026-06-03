package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelPersons extends PanelBase {

    private JTextField txtName, txtDocument, txtPhone;
    private JComboBox<Object> cboCity, cboType;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    private final String[] CITIES = { "Tunja", "Bogotá", "Medellín", "Cali", "Bucaramanga", "Barranquilla" };
    private final String[] TYPES = { "NATURAL", "JURIDICA" };

    public PanelPersons() {
        super("👤  Gestión de Personas / Clientes",
                new String[] { "ID", "Nombre", "Documento", "Teléfono", "Tipo", "Ciudad" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos del Cliente");
        txtName = addField("Nombre Completo *");
        txtDocument = addField("Documento / CC *");
        txtPhone = addField("Teléfono");
        cboType = addCombo("Tipo de Persona *");
        for (String t : TYPES)
            cboType.addItem(t);
        cboCity = addCombo("Ciudad *");
        for (String c : CITIES)
            cboCity.addItem(c);
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
        cboType.setSelectedItem(tableModel.getValueAt(row, 4));
        cboCity.setSelectedItem(tableModel.getValueAt(row, 5));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        txtDocument.setText("");
        txtPhone.setText("");
        if (cboType.getItemCount() > 0)
            cboType.setSelectedIndex(0);
        if (cboCity.getItemCount() > 0)
            cboCity.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim(), doc = txtDocument.getText().trim();
        if (name.isEmpty() || doc.isEmpty()) {
            showError("Nombre y Documento son obligatorios.");
            return;
        }
        Object[] row = { selectedId == -1 ? nextId++ : selectedId, name, doc,
                txtPhone.getText().trim(), cboType.getSelectedItem(), cboCity.getSelectedItem() };
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
        if (!confirm("¿Eliminar esta persona?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
