package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import co.elgranero.controller.UserManager;
import co.elgranero.net.City;
import co.elgranero.net.Country;
import co.elgranero.net.User;

public class PanelPersons extends PanelBase {

    private JTextField txtName, txtDocument, txtPhone;
    private JComboBox<Object> cboCity, cboType;
    private int selectedId = -1;

    private UserManager userManager;

    public PanelPersons() {
        super("👤  Gestión de Personas / Clientes",
                new String[] { "ID", "Nombre", "Documento", "Teléfono", "Tipo", "Ciudad" });
        try {
            this.userManager = new UserManager();
        } catch (IOException e) {
            showError("Error de conexión: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos del Cliente");
        txtName = addField("Nombre Completo *");
        txtDocument = addField("Documento / CC *");
        txtPhone = addField("Teléfono");

        cboType = addCombo("Tipo de Persona *");
        cboType.addItem("CLIENTE");
        cboType.addItem("VENDEDOR");

        cboCity = addCombo("Ciudad *");
        if (userManager != null) {
            ArrayList<Country> countries = userManager.obtainCountries();
            for (Country country : countries) {
                ArrayList<City> cities = userManager.obtainCities(country.getId());
                for (City city : cities) {
                    cboCity.addItem(city);
                }
            }
        }

        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (userManager == null)
            return;

        ArrayList<User> users = userManager.obtainUsers();
        for (User u : users) {
            tableModel.addRow(new Object[] {
                    u.getId(),
                    u.getName(),
                    u.getDocument(),
                    u.getPhone(),
                    u.getType(),
                    u.getCityName()
            });
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtName.setText((String) tableModel.getValueAt(row, 1));
        txtDocument.setText((String) tableModel.getValueAt(row, 2));
        txtPhone.setText((String) tableModel.getValueAt(row, 3));

        String type = (String) tableModel.getValueAt(row, 4);
        if (type != null) {
            for (int i = 0; i < cboType.getItemCount(); i++) {
                if (cboType.getItemAt(i).toString().equalsIgnoreCase(type)) {
                    cboType.setSelectedIndex(i);
                    break;
                }
            }
        }

        String cityName = (String) tableModel.getValueAt(row, 5);
        if (cityName != null) {
            for (int i = 0; i < cboCity.getItemCount(); i++) {
                City c = (City) cboCity.getItemAt(i);
                if (c.getName().equalsIgnoreCase(cityName)) {
                    cboCity.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        txtDocument.setText("");
        txtPhone.setText("");
        if (cboType.getItemCount() > 0) {
            cboType.setSelectedIndex(0);
        }
        if (cboCity.getItemCount() > 0) {
            cboCity.setSelectedIndex(0);
        }
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        String doc = txtDocument.getText().trim();
        String phone = txtPhone.getText().trim();
        City selectedCity = (City) cboCity.getSelectedItem();
        int personTypeIndex = cboType.getSelectedIndex();

        if (name.isEmpty() || doc.isEmpty() || selectedCity == null || personTypeIndex < 0) {
            showError("Nombre, Documento, Tipo y Ciudad son obligatorios.");
            return;
        }

        try {
            int docInt = Integer.parseInt(doc);
            boolean exito;

            if (selectedId == -1) {
                exito = userManager.registUser(selectedCity.getId(), name, docInt, phone, personTypeIndex);
            } else {
                User u = new User(
                        selectedId,
                        selectedCity.getId(),
                        name,
                        doc,
                        phone,
                        cboType.getSelectedItem().toString(),
                        selectedCity.getName(),
                        selectedCity.getCountryName());
                exito = userManager.modifyUser(u);
            }

            if (exito) {
                loadData();
                setInitialState();
                clearForm();
                JOptionPane.showMessageDialog(this, "Persona guardada correctamente.");
            } else {
                showError("Error al guardar la persona en la base de datos.");
            }

        } catch (NumberFormatException ex) {
            showError("El documento debe ser un valor numérico válido.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0 || selectedId == -1) {
            showError("Seleccione una persona de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar esta persona?")) {
            return;
        }

        boolean exito = userManager.deleteUser(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Persona eliminada correctamente.");
        } else {
            showError("Error al eliminar la persona.");
        }
    }
}