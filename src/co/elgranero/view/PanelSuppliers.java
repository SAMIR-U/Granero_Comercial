package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import co.elgranero.controller.InventoryManager;
import co.elgranero.net.Provider;

public class PanelSuppliers extends PanelBase {

    private JTextField txtName, txtDocument, txtPhone;
    private int selectedId = -1;
    private InventoryManager inventoryManager;

    public PanelSuppliers() {
        super("🏭  Gestión de Proveedores", new String[] { "ID", "Nombre", "Documento", "Celular" });
        try {
            this.inventoryManager = new InventoryManager();
        } catch (IOException e) {
            showError("No se pudo conectar con el gestor de inventario: " + e.getMessage());
        }
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
        if (inventoryManager == null)
            return;

        ArrayList<Provider> proveedores = inventoryManager.obtainProviders();
        for (Provider p : proveedores) {
            Object[] row = {
                    p.getId(),
                    p.getName(),
                    p.getDocument(),
                    p.getCellphone()
            };
            tableModel.addRow(row);
        }
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
        String document = txtDocument.getText().trim();
        String phone = txtPhone.getText().trim();

        if (name.isEmpty() || document.isEmpty()) {
            showError("El nombre y el documento son obligatorios.");
            return;
        }

        boolean exito;
        if (selectedId == -1) {
            exito = inventoryManager.registProvider(name, document, phone);
        } else {
            Provider provModificado = new Provider(selectedId, name, document, phone);
            exito = inventoryManager.modifyProvider(provModificado);
        }

        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "¡Proveedor guardado exitosamente!");
        } else {
            showError("Hubo un error al guardar el proveedor en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0) {
            showError("Seleccione un proveedor de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar este proveedor?")) {
            return;
        }

        boolean exito = inventoryManager.deleteProvider(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
        } else {
            showError("No se pudo eliminar el proveedor de la base de datos.");
        }
    }
}