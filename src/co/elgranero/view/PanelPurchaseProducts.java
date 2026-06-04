package co.elgranero.view;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import co.elgranero.controller.InventoryManager;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.Product;
import co.elgranero.net.PurchaseProduct;
import co.elgranero.net.Purchase;

public class PanelPurchaseProducts extends PanelBase {

    private JTextField txtQuantity, txtUnitPrice;
    private JComboBox<Object> cboPurchaseId, cboProduct;
    private boolean isEditing = false;
    private boolean isLoadingForm = false;
    private int currentPurchaseId = -1;
    private int currentProductId = -1;

    private InventoryManager inventoryManager;
    private ProductsManager productsManager;

    public PanelPurchaseProducts() {
        super("Detalle de Productos en Compra",
                new String[] { "ID Compra", "Producto", "Cantidad", "Precio Unitario" });
        try {
            this.inventoryManager = new InventoryManager();
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            showError("Error de conexión: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Producto en Compra");
        cboPurchaseId = addCombo("Compra (ID | Proveedor | Fecha) *");
        cboProduct = addCombo("Producto *");
        txtQuantity = addField("Cantidad *");
        txtUnitPrice = addField("Precio Unitario *");

        cargarCombos();

        cboPurchaseId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLoadingForm && cboPurchaseId.getSelectedItem() != null) {
                    loadData();
                }
            }
        });

        cboPurchaseId.addPropertyChangeListener("enabled", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (Boolean.FALSE.equals(evt.getNewValue())) {
                    cboPurchaseId.setEnabled(true);
                }
            }
        });

        cboPurchaseId.setEnabled(true);

        formPanel.add(Box.createVerticalGlue());
    }

    private void cargarCombos() {
        if (productsManager != null) {
            cboProduct.removeAllItems();
            ArrayList<Product> products = productsManager.obtainProducts();
            for (Product p : products) {
                cboProduct.addItem(p);
            }
        }

        if (inventoryManager != null) {
            cboPurchaseId.removeAllItems();
            ArrayList<Purchase> purchases = inventoryManager.obtainPurchases();

            Collections.sort(purchases, new Comparator<Purchase>() {
                @Override
                public int compare(Purchase p1, Purchase p2) {
                    try {
                        return String.valueOf(p2.getDate()).compareTo(String.valueOf(p1.getDate()));
                    } catch (Exception e) {
                        return Integer.compare(p2.getId(), p1.getId());
                    }
                }
            });

            for (Purchase p : purchases) {
                String display = p.getId() + " | " + p.getName() + " | " + p.getDate();
                cboPurchaseId.addItem(display);
            }
        }
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (inventoryManager == null || cboPurchaseId == null || cboPurchaseId.getSelectedItem() == null)
            return;

        try {
            String selectedStr = cboPurchaseId.getSelectedItem().toString();
            int purchaseId = Integer.parseInt(selectedStr.split("\\|")[0].trim());
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
        } catch (Exception e) {
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        isLoadingForm = true;
        try {
            currentPurchaseId = (int) tableModel.getValueAt(row, 0);
            String prodName = (String) tableModel.getValueAt(row, 1);

            for (int i = 0; i < cboPurchaseId.getItemCount(); i++) {
                String itemStr = cboPurchaseId.getItemAt(i).toString();
                if (itemStr.startsWith(currentPurchaseId + " | ")) {
                    cboPurchaseId.setSelectedIndex(i);
                    break;
                }
            }

            txtQuantity.setText(String.valueOf(tableModel.getValueAt(row, 2)));
            txtUnitPrice.setText(String.valueOf(tableModel.getValueAt(row, 3)));

            for (int i = 0; i < cboProduct.getItemCount(); i++) {
                Product p = (Product) cboProduct.getItemAt(i);
                if (p.getName().equalsIgnoreCase(prodName)) {
                    cboProduct.setSelectedIndex(i);
                    currentProductId = p.getId();
                    break;
                }
            }

            isEditing = true;
            cboPurchaseId.setEnabled(true);
        } finally {
            isLoadingForm = false;
        }
    }

    @Override
    protected void clearForm() {
        isEditing = false;
        currentPurchaseId = -1;
        currentProductId = -1;
        txtQuantity.setText("");
        txtUnitPrice.setText("");

        if (cboProduct.getItemCount() > 0) {
            cboProduct.setSelectedIndex(0);
        }

        cboPurchaseId.setEnabled(true);
    }

    @Override
    protected void actionSave() {
        try {
            if (cboPurchaseId.getSelectedItem() == null) {
                showError("Seleccione un ID de compra válido.");
                return;
            }

            String selectedStr = cboPurchaseId.getSelectedItem().toString();
            int purchaseId = Integer.parseInt(selectedStr.split("\\|")[0].trim());

            int qty = Integer.parseInt(txtQuantity.getText().trim());
            double price = Double.parseDouble(txtUnitPrice.getText().trim());
            Product selectedProd = (Product) cboProduct.getSelectedItem();

            if (selectedProd == null) {
                showError("Seleccione un producto válido.");
                return;
            }

            boolean exito;
            if (!isEditing) {
                exito = inventoryManager.registPurchaseProduct(selectedProd.getId(), purchaseId, qty, price);
            } else {
                PurchaseProduct pp = new PurchaseProduct(
                        selectedProd.getId(),
                        purchaseId,
                        qty,
                        price,
                        selectedProd.getName());
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
            showError("La cantidad y el precio deben ser valores numéricos válidos.");
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

    @Override
    public void setInitialState() {
        super.setInitialState();
        if (cboPurchaseId != null) {
            cboPurchaseId.setEnabled(true);
        }
    }
}