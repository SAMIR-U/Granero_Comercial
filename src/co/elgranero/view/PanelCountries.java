package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import co.elgranero.controller.UserManager;
import co.elgranero.net.Country;

public class PanelCountries extends PanelBase {

    private JTextField txtName;
    private int selectedId = -1;
    private UserManager userManager;

    public PanelCountries() {
        super("🌍  Gestión de Países", new String[] { "ID", "Nombre País" });
        try {
            this.userManager = new UserManager();
        } catch (IOException e) {
            showError("Error de conexión con la base de datos: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos del País");
        txtName = addField("Nombre del País *");
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (userManager == null)
            return;

        ArrayList<Country> countries = userManager.obtainCountries();
        for (Country c : countries) {
            tableModel.addRow(new Object[] { c.getId(), c.getName() });
        }
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
            showError("El nombre del país es obligatorio.");
            return;
        }

        boolean exito;
        if (selectedId == -1) {
            exito = userManager.registCountry(name);
        } else {
            Country c = new Country(selectedId, name);
            exito = userManager.modifyCountry(c);
        }

        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "País guardado correctamente.");
        } else {
            showError("No se pudo guardar el país en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0 || selectedId == -1) {
            showError("Seleccione un país de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar este país?")) {
            return;
        }

        boolean exito = userManager.deleteCountry(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "País eliminado correctamente.");
        } else {
            showError("Error al eliminar el país.");
        }
    }
}