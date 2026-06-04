package co.elgranero.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import co.elgranero.controller.ProductsManager;
import co.elgranero.net.Category;

public class PanelCategories extends PanelBase {

    private JTextField txtName;
    private int selectedId = -1;
    private ProductsManager productsManager;

    public PanelCategories() {
        super("Gestión de Categorías de Productos", new String[] { "ID", "Categoría" });
        try {
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            showError("No se pudo conectar con el gestor de productos: " + e.getMessage());
        }
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
        if (productsManager == null)
            return;

        ArrayList<Category> categorias = productsManager.obtainCategories();
        for (Category cat : categorias) {
            Object[] row = {
                    cat.getId(),
                    cat.getName()
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
            exito = productsManager.registCategory(name);
        } else {
            Category catModificada = new Category(selectedId, name);
            exito = productsManager.modifyCategory(catModificada);
        }

        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Categoría guardada exitosamente!");
        } else {
            showError("Hubo un error al guardar la categoría en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0) {
            showError("Seleccione una categoría de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar esta categoría?")) {
            return;
        }

        boolean exito = productsManager.deleteCategory(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Categoría eliminada correctamente.");
        } else {
            showError("No se pudo eliminar la categoría de la base de datos.");
        }
    }
}