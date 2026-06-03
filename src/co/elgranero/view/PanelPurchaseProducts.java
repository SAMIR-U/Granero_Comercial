package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import co.elgranero.controller.InventoryManager;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.Product;
import co.elgranero.net.PurchaseProduct;

public class PanelPurchaseProducts extends PanelBase {

    private JTextField txtPurchaseId, txtQuantity, txtUnitPrice;
    private JComboBox<Object> cboProduct;
    private boolean isEditing = false;
    private int currentPurchaseId = -1;
    private int currentProductId = -1;

    private InventoryManager inventoryManager;
    private ProductsManager productsManager;

    public PanelPurchaseProducts() {
        super("📋  Detalle de Productos en Compra",
                new String[] { "ID Compra", "Producto", "Cantidad", "Precio Unitario" });
        try {
            this.inventoryManager = new InventoryManager();
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            showError("Error de conexión: " + e.getMessage());
        }
        initialize();

        txtPurchaseId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                loadData();
            }
        });
    }

    @Override
    protected void buildForm() {
        addFormTitle("Producto en Compra");
        txtPurchaseId = addField("ID Compra *");
        cboProduct = addCombo("Producto *");
        txtQuantity = addField("Cantidad *");
        txtUnitPrice = addField("Precio Unitario *");

        if (productsManager != null) {
            ArrayList<Product> products = productsManager.obtainProducts();
            for (Product p : products) {
                cboProduct.addItem(p);
            }
        }

        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (inventoryManager == null)
            return;

        try {
            int purchaseId = Integer.parseInt(txtPurchaseId.getText().trim());
            ArrayList<PurchaseProduct> list = inventoryManager.obtainPurchaseProducts(purchaseId);
            for (PurchaseProduct pp : list) {
                Object[] row = {
                        pp.getIdPurchase(),
                        pp.getProductName(),
                        pp.getPurchaseProductQuantity(),
                        pp.getPurchaseProductUnitPrice()
                };
                tableModel.addRow(row);
            }
        } catch (NumberFormatException e) {
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        currentPurchaseId = (int) tableModel.getValueAt(row, 0);
        String prodName = (String) tableModel.getValueAt(row, 1);

        txtPurchaseId.setText(String.valueOf(currentPurchaseId));
        txtQuantity.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        txtUnitPrice.setText(String.valueOf(tableModel.getValueAt(row, 3)));

        for (int i = 0; i < cboProduct.getItemCount(); i++) {
            Product p = (Product) cboProduct.getItemAt(i);
            if (p.getProductName().equalsIgnoreCase(prodName)) {
                cboProduct.setSelectedIndex(i);
                currentProductId = p.getIdProduct();
                break;
            }
        }
        isEditing = true;
    }

    @Override
    protected void clearForm() {
        isEditing = false;
        currentPurchaseId = -1;
        currentProductId = -1;
        txtPurchaseId.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");
        if (cboProduct.getItemCount() > 0) {
            cboProduct.setSelectedIndex(0);
        }
        tableModel.setRowCount(0);
    }

    @Override
    protected void actionSave() {
        try {
            int purchaseId = Integer.parseInt(txtPurchaseId.getText().trim());
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            double price = Double.parseDouble(txtUnitPrice.getText().trim());
            Product selectedProd = (Product) cboProduct.getSelectedItem();

            if (selectedProd == null) {
                showError("Seleccione un producto válido.");
                return;
            }

            boolean exito;
            if (!isEditing) {
                exito = inventoryManager.registPurchaseProduct(selectedProd.getIdProduct(), purchaseId, qty, price);
            } else {
                PurchaseProduct pp = new PurchaseProduct(
                        selectedProd.getIdProduct(),
                        purchaseId,
                        qty,
                        price,
                        selectedProd.getProductName());
                exito = inventoryManager.modifyPurchaseProduct(pp);
            }

            if (exito) {
                loadData();
                JOptionPane.showMessageDialog(this, "¡Producto de compra guardado exitosamente!");
                isEditing = false;
                txtQuantity.setText("");
                txtUnitPrice.setText("");
                if (cboProduct.getItemCount() > 0)
                    cboProduct.setSelectedIndex(0);
            } else {
                showError("Hubo un error al guardar el registro.");
            }

        } catch (NumberFormatException e) {
            showError("El ID de compra, la cantidad y el precio deben ser valores numéricos válidos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0 || !isEditing) {
            showError("Seleccione un registro de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar este producto de la compra?")) {
            return;
        }

        boolean exito = inventoryManager.deletePurchaseProduct(currentProductId, currentPurchaseId);
        if (exito) {
            loadData();
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
            isEditing = false;
            txtQuantity.setText("");
            txtUnitPrice.setText("");
        } else {
            showError("No se pudo eliminar el registro.");
        }
    }
}