package co.elgranero.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelCities extends PanelBase {

    private JTextField txtName;
    private JComboBox<Object> cboCountry;
    private int selectedId = -1;
    private final List<Object[]> data = new ArrayList<>();
    private int nextId = 1;

    private final String[] SAMPLE_COUNTRIES = { "Colombia", "Venezuela", "Ecuador", "Perú", "Brasil" };

    public PanelCities() {
        super("🏙  Gestión de Ciudades", new String[] { "ID", "Ciudad", "País" });
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de la Ciudad");
        txtName = addField("Nombre de la Ciudad *");
        cboCountry = addCombo("País *");
        for (String c : SAMPLE_COUNTRIES)
            cboCountry.addItem(c);
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
        cboCountry.setSelectedItem(tableModel.getValueAt(row, 2));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        if (cboCountry.getItemCount() > 0)
            cboCountry.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        if (name.isEmpty() || cboCountry.getSelectedItem() == null) {
            showError("Todos los campos son obligatorios.");
            return;
        }
        Object country = cboCountry.getSelectedItem();
        if (selectedId == -1)
            data.add(new Object[] { nextId++, name, country });
        else
            for (Object[] r : data)
                if ((int) r[0] == selectedId) {
                    r[1] = name;
                    r[2] = country;
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
        if (!confirm("¿Eliminar esta ciudad?"))
            return;
        data.removeIf(r -> (int) r[0] == selectedId);
        loadData();
        setInitialState();
        clearForm();
    }
}
