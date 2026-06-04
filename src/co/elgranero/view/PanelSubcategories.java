package co.elgranero.view;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.IOException;
import java.util.ArrayList;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.Category;
import co.elgranero.net.Subcategory;

public class PanelSubcategories extends PanelBase {

    private JTextField txtName;
    private JComboBox<Object> cboCategory;
    private int selectedId = -1;
    private ProductsManager productsManager;

    public PanelSubcategories() {
        super("Gestión de Subcategorías", new String[] { "ID", "Subcategoría", "Categoría" });
        try {
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            showError("No se pudo conectar con el gestor de productos: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de Subcategoría");
        txtName = addField("Nombre de Subcategoría *");
        cboCategory = addCombo("Categoría *");
        cargarCategoriasEnCombo();
        formPanel.add(Box.createVerticalGlue());
    }

    private void cargarCategoriasEnCombo() {
        if (productsManager == null)
            return;
        ArrayList<Category> lista = productsManager.obtainCategories();
        cboCategory.removeAllItems();
        for (Category cat : lista) {
            cboCategory.addItem(cat);
        }
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (productsManager == null)
            return;

        ArrayList<Subcategory> subcategorias = productsManager.obtainSubcategories();
        for (Subcategory sub : subcategorias) {
            Object[] row = {
                    sub.getId(),
                    sub.getName(),
                    sub.getCategoryName()
            };
            tableModel.addRow(row);
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtName.setText((String) tableModel.getValueAt(row, 1));

        String categoriaTabla = (String) tableModel.getValueAt(row, 2);
        for (int i = 0; i < cboCategory.getItemCount(); i++) {
            Category cat = (Category) cboCategory.getItemAt(i);
            if (cat.getName().equalsIgnoreCase(categoriaTabla)) {
                cboCategory.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        if (cboCategory.getItemCount() > 0)
            cboCategory.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        Category selectedCat = (Category) cboCategory.getSelectedItem();

        if (name.isEmpty() || selectedCat == null) {
            showError("Todos los campos son obligatorios.");
            return;
        }

        boolean exito;
        if (selectedId == -1) {
            exito = productsManager.registSubcategory(selectedCat.getId(), name);
        } else {
            Subcategory subModificada = new Subcategory(
                    selectedId,
                    selectedCat.getId(),
                    name,
                    selectedCat.getName());
            exito = productsManager.modifySubcategory(subModificada);
        }

        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "¡Subcategoría guardada exitosamente!");
        } else {
            showError("Hubo un error al guardar la subcategoría en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0) {
            showError("Seleccione una subcategoría de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar esta subcategoría?")) {
            return;
        }

        boolean exito = productsManager.deleteSubcategory(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Subcategoría eliminada correctamente.");
        } else {
            showError("No se pudo eliminar la subcategoría de la base de datos.");
        }
    }
}