package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.Presentation;
import co.elgranero.net.Product;
import co.elgranero.net.ProductPresentation;

public class PanelProductPresentations extends PanelBase {

    private JComboBox<Object> cboPresentation, cboProduct;
    private JTextField txtPrice;
    private int selectedIndex = -1;
    private ProductsManager productsManager;

    public PanelProductPresentations() {
        super("🔗  Presentaciones por Producto",
                new String[] { "Presentación", "Producto", "Precio ($)" });
        try {
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            showError("No se pudo conectar con el gestor de productos: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Asignar Presentación");
        cboPresentation = addCombo("Presentación *");
        cboProduct = addCombo("Producto *");
        txtPrice = addField("Precio de Presentación *");

        cargarCombos();

        formPanel.add(Box.createVerticalGlue());
    }

    private void cargarCombos() {
        if (productsManager == null)
            return;

        cboPresentation.removeAllItems();
        ArrayList<Presentation> presentaciones = productsManager.obtainPresentations();
        for (Presentation p : presentaciones) {
            cboPresentation.addItem(p);
        }

        cboProduct.removeAllItems();
        ArrayList<Product> productos = productsManager.obtainProducts();
        for (Product p : productos) {
            cboProduct.addItem(p);
        }
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (productsManager == null)
            return;

        ArrayList<Product> productos = productsManager.obtainProducts();
        for (Product p : productos) {
            ArrayList<ProductPresentation> pps = productsManager.obtainProductPresentations(p.getIdProduct());
            for (ProductPresentation pp : pps) {
                Object[] row = {
                        pp.getPresentationName(),
                        p.getProductName(),
                        pp.getPresentationPrice()
                };
                tableModel.addRow(row);
            }
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedIndex = row;
        String nomPres = (String) tableModel.getValueAt(row, 0);
        String nomProd = (String) tableModel.getValueAt(row, 1);
        txtPrice.setText(String.valueOf(tableModel.getValueAt(row, 2)));

        for (int i = 0; i < cboPresentation.getItemCount(); i++) {
            Presentation pre = (Presentation) cboPresentation.getItemAt(i);
            if (pre.getName().equalsIgnoreCase(nomPres)) {
                cboPresentation.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < cboProduct.getItemCount(); i++) {
            Product prod = (Product) cboProduct.getItemAt(i);
            if (prod.getProductName().equalsIgnoreCase(nomProd)) {
                cboProduct.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearForm() {
        selectedIndex = -1;
        txtPrice.setText("");
        if (cboPresentation.getItemCount() > 0)
            cboPresentation.setSelectedIndex(0);
        if (cboProduct.getItemCount() > 0)
            cboProduct.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        Presentation selectedPre = (Presentation) cboPresentation.getSelectedItem();
        Product selectedProd = (Product) cboProduct.getSelectedItem();
        String priceText = txtPrice.getText().trim();

        if (selectedPre == null || selectedProd == null || priceText.isEmpty()) {
            showError("Todos los campos son obligatorios.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            boolean exito;

            if (selectedIndex == -1) {
                exito = productsManager.registProductPresentation(selectedPre.getId(), selectedProd.getIdProduct(),
                        price);
            } else {
                ProductPresentation ppModificada = new ProductPresentation(
                        selectedPre.getId(),
                        selectedProd.getIdProduct(),
                        price,
                        selectedPre.getName());
                exito = productsManager.modifyProductPresentation(ppModificada);
            }

            if (exito) {
                loadData();
                setInitialState();
                clearForm();
                JOptionPane.showMessageDialog(this, "¡Asignación guardada exitosamente!");
            } else {
                showError("Hubo un error al guardar en la base de datos.");
            }

        } catch (NumberFormatException e) {
            showError("El precio debe ser un número válido.");
        }
    }

    @Override
    protected void actionDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Seleccione un registro de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar esta presentación para el producto?")) {
            return;
        }

        String nomPres = (String) tableModel.getValueAt(row, 0);
        String nomProd = (String) tableModel.getValueAt(row, 1);
        int idPres = -1;
        int idProd = -1;

        for (int i = 0; i < cboPresentation.getItemCount(); i++) {
            Presentation pre = (Presentation) cboPresentation.getItemAt(i);
            if (pre.getName().equalsIgnoreCase(nomPres)) {
                idPres = pre.getId();
                break;
            }
        }

        for (int i = 0; i < cboProduct.getItemCount(); i++) {
            Product prod = (Product) cboProduct.getItemAt(i);
            if (prod.getProductName().equalsIgnoreCase(nomProd)) {
                idProd = prod.getIdProduct();
                break;
            }
        }

        boolean exito = productsManager.deleteProductPresentation(idPres, idProd);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
        } else {
            showError("No se pudo eliminar el registro de la base de datos.");
        }
    }
}