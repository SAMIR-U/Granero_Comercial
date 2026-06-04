package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import co.elgranero.controller.UserManager;
import co.elgranero.net.City;
import co.elgranero.net.Country;

public class PanelCities extends PanelBase {

    private JTextField txtName;
    private JComboBox<Object> cboCountry;
    private int selectedId = -1;
    private UserManager userManager;

    public PanelCities() {
        super("🏙  Gestión de Ciudades", new String[] { "ID", "Ciudad", "País" });
        try {
            this.userManager = new UserManager();
        } catch (IOException e) {
            showError("Error de conexión: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de la Ciudad");
        txtName = addField("Nombre de la Ciudad *");
        cboCountry = addCombo("País *");

        if (userManager != null) {
            ArrayList<Country> countries = userManager.obtainCountries();
            for (Country c : countries) {
                cboCountry.addItem(c);
            }
        }

        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (userManager == null)
            return;

        ArrayList<Country> countries = userManager.obtainCountries();
        for (Country country : countries) {
            ArrayList<City> cities = userManager.obtainCities(country.getId());
            for (City city : cities) {
                tableModel.addRow(new Object[] {
                        city.getId(),
                        city.getName(),
                        city.getCountryName()
                });
            }
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtName.setText((String) tableModel.getValueAt(row, 1));
        String countryName = (String) tableModel.getValueAt(row, 2);

        for (int i = 0; i < cboCountry.getItemCount(); i++) {
            Country c = (Country) cboCountry.getItemAt(i);
            if (c.getName().equalsIgnoreCase(countryName)) {
                cboCountry.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        if (cboCountry.getItemCount() > 0) {
            cboCountry.setSelectedIndex(0);
        }
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        Country selectedCountry = (Country) cboCountry.getSelectedItem();

        if (name.isEmpty() || selectedCountry == null) {
            showError("Todos los campos son obligatorios.");
            return;
        }

        boolean exito;
        if (selectedId == -1) {
            exito = userManager.registCity(selectedCountry.getId(), name);
        } else {
            City c = new City(selectedId, selectedCountry.getId(), name, selectedCountry.getName());
            exito = userManager.modifyCity(c);
        }

        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Ciudad guardada correctamente.");
        } else {
            showError("No se pudo guardar la ciudad en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0 || selectedId == -1) {
            showError("Seleccione una ciudad de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar esta ciudad?")) {
            return;
        }

        boolean exito = userManager.deleteCity(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Ciudad eliminada correctamente.");
        } else {
            showError("Error al eliminar la ciudad.");
        }
    }
}