package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.Presentation;

public class PanelPresentations extends PanelBase {

    private JTextField txtName;
    private int selectedId = -1;
    private ProductsManager productsManager;

    public PanelPresentations() {
        super("🏷  Gestión de Presentaciones", new String[] { "ID", "Presentación" });
        try {
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            showError("No se pudo conectar con el gestor de productos: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de Presentación");
        txtName = addField("Nombre de Presentación * (ej: Bolsa 500g, Caja x12, Litro...)");
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (productsManager == null)
            return;

        ArrayList<Presentation> presentaciones = productsManager.obtainPresentations();
        for (Presentation p : presentaciones) {
            Object[] row = {
                    p.getId(),
                    p.getName()
            };
            tableModel.addRow(row);
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
            showError("El nombre es obligatorio.");
            return;
        }

        boolean exito;
        if (selectedId == -1) {
            exito = productsManager.registPresentation(name);
        } else {
            Presentation preModificada = new Presentation(selectedId, name);
            exito = productsManager.modifyPresentation(preModificada);
        }

        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "¡Presentación guardada exitosamente!");
        } else {
            showError("Hubo un error al guardar la presentación en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0) {
            showError("Seleccione una presentación de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar esta presentación?")) {
            return;
        }

        boolean exito = productsManager.deletePresentation(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Presentación eliminada correctamente.");
        } else {
            showError("No se pudo eliminar la presentación de la base de datos.");
        }
    }
}