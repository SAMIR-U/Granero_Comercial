package co.elgranero.view;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.Product;
import co.elgranero.net.Subcategory;

public class PanelProducts extends PanelBase {

    private JTextField txtName, txtExpiry;
    private JTextArea txtDescription;
    private JComboBox<Object> cboSubcategory;
    private int selectedId = -1;
    private ProductsManager productsManager;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PanelProducts() {
        super("Gestión de Productos",
                new String[] { "ID", "Nombre", "Subcategoría", "Vencimiento", "Descripción" });
        try {
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            showError("No se pudo conectar con el gestor de productos: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos del Producto");
        txtName = addField("Nombre del Producto *");
        txtDescription = addArea("Descripción", 3);
        txtExpiry = addField("Fecha Vencimiento (dd/MM/yyyy)");
        cboSubcategory = addCombo("Subcategoría *");
        cargarSubcategoriasEnCombo();
        formPanel.add(Box.createVerticalGlue());
    }

    private void cargarSubcategoriasEnCombo() {
        if (productsManager == null)
            return;
        ArrayList<Subcategory> lista = productsManager.obtainSubcategories();
        cboSubcategory.removeAllItems();
        for (Subcategory sub : lista) {
            cboSubcategory.addItem(sub);
        }
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (productsManager == null)
            return;
        ArrayList<Product> productos = productsManager.obtainProducts();
        for (Product p : productos) {
            String fechaStr = (p.getExpirationDate() != null)
                    ? dateFormat.format(p.getExpirationDate())
                    : "N/A";
            Object[] row = {
                    p.getId(),
                    p.getName(),
                    p.getSubcategoryName(),
                    fechaStr,
                    p.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);
        txtName.setText((String) tableModel.getValueAt(row, 1));
        txtExpiry.setText((String) tableModel.getValueAt(row, 3));
        txtDescription.setText((String) tableModel.getValueAt(row, 4));
        String subcategoriaTabla = (String) tableModel.getValueAt(row, 2);
        for (int i = 0; i < cboSubcategory.getItemCount(); i++) {
            Subcategory sub = (Subcategory) cboSubcategory.getItemAt(i);
            if (sub.getName().equalsIgnoreCase(subcategoriaTabla)) {
                cboSubcategory.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtName.setText("");
        txtDescription.setText("");
        txtExpiry.setText("");
        if (cboSubcategory.getItemCount() > 0)
            cboSubcategory.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String name = txtName.getText().trim();
        Subcategory selectedSub = (Subcategory) cboSubcategory.getSelectedItem();
        if (name.isEmpty() || selectedSub == null) {
            showError("Nombre y Subcategoría son obligatorios.");
            return;
        }
        Date sqlDate = null;
        String expiryText = txtExpiry.getText().trim();
        if (!expiryText.isEmpty()) {
            try {
                java.util.Date parsedDate = dateFormat.parse(expiryText);
                sqlDate = new Date(parsedDate.getTime());
            } catch (ParseException e) {
                showError("Formato de fecha inválido. Use dd/MM/yyyy");
                return;
            }
        }
        boolean exito;
        if (selectedId == -1) {
            exito = productsManager.registProduct(selectedSub.getId(), name, txtDescription.getText().trim(),
                    sqlDate);
        } else {
            Product prodModificado = new Product(
                    selectedId,
                    selectedSub.getId(),
                    name,
                    txtDescription.getText().trim(),
                    sqlDate,
                    selectedSub.getName(),
                    "");
            exito = productsManager.modifyProduct(prodModificado);
        }
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "¡Producto guardado exitosamente!");
        } else {
            showError("Hubo un error al guardar el producto en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0) {
            showError("Seleccione un producto de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar este producto?")) {
            return;
        }
        boolean exito = productsManager.deleteProduct(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
        } else {
            showError("No se pudo eliminar el producto de la base de datos.");
        }
    }
}