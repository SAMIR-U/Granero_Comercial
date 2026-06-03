package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import co.elgranero.controller.SaleManager;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.SaleProduct;
import co.elgranero.net.Product;

public class PanelSaleProducts extends PanelBase {

    private JTextField txtSaleId, txtQuantity, txtUnitPrice;
    private JComboBox<Object> cboProduct;
    private int selectedIndex = -1;

    private SaleManager saleManager;
    private ProductsManager productsManager;
    private ArrayList<Product> productList;

    public PanelSaleProducts() {
        super("🧾  Detalle de Productos en Venta",
                new String[] { "ID Venta", "ID Producto", "Cantidad", "Precio Unitario", "Nombre Producto" });
        try {
            this.saleManager = new SaleManager();
            this.productsManager = new ProductsManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Producto en Venta");
        txtSaleId = addField("ID Venta *");
        cboProduct = addCombo("Producto *");
        txtQuantity = addField("Cantidad *");
        txtUnitPrice = addField("Precio Unitario *");

        try {
            if (productsManager == null) {
                this.productsManager = new ProductsManager();
            }
            productList = productsManager.obtainProducts();
            for (Product p : productList) {
                cboProduct.addItem(p.getProductName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (saleManager == null)
            return;

        String saleIdStr = txtSaleId.getText().trim();
        if (saleIdStr.isEmpty())
            return;

        try {
            int saleId = Integer.parseInt(saleIdStr);
            ArrayList<SaleProduct> saleProducts = saleManager.obtainSaleProducts(saleId);
            for (SaleProduct sp : saleProducts) {
                tableModel.addRow(new Object[] {
                        sp.getIdSale(),
                        sp.getIdProduct(),
                        sp.getQuantity(),
                        sp.getUnitPrice(),
                        sp.getProductName()
                });
            }
        } catch (NumberFormatException e) {
            // Si el ID de venta ingresado no es numérico, no carga datos
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedIndex = row;
        txtSaleId.setText(String.valueOf(tableModel.getValueAt(row, 0)));

        int idProduct = (int) tableModel.getValueAt(row, 1);
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getIdProduct() == idProduct) {
                cboProduct.setSelectedIndex(i);
                break;
            }
        }

        txtQuantity.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        txtUnitPrice.setText(String.valueOf(tableModel.getValueAt(row, 3)));
    }

    @Override
    protected void clearForm() {
        selectedIndex = -1;
        txtSaleId.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");
        if (cboProduct.getItemCount() > 0)
            cboProduct.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        try {
            int saleId = Integer.parseInt(txtSaleId.getText().trim());
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            double price = Double.parseDouble(txtUnitPrice.getText().trim());

            if (cboProduct.getSelectedItem() == null) {
                showError("Seleccione un producto.");
                return;
            }

            int productIdx = cboProduct.getSelectedIndex();
            int idProduct = productList.get(productIdx).getIdProduct();

            boolean success;
            if (selectedIndex == -1) {
                success = saleManager.registSaleProduct(saleId, idProduct, qty, price);
            } else {
                SaleProduct spToUpdate = new SaleProduct();
                spToUpdate.setIdSale(saleId);
                spToUpdate.setIdProduct(idProduct);
                spToUpdate.setQuantity(qty);
                spToUpdate.setUnitPrice(price);

                success = saleManager.modifySaleProduct(spToUpdate);
            }

            if (success) {
                loadData();
                setInitialState();
                clearForm();
            } else {
                showError("No se pudo guardar el detalle del producto en la base de datos.");
            }
        } catch (NumberFormatException e) {
            showError("Los campos numéricos deben ser valores válidos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0 || selectedIndex < 0)
            return;
        if (!confirm("¿Eliminar este registro?"))
            return;

        int saleId = (int) tableModel.getValueAt(selectedIndex, 0);
        int productId = (int) tableModel.getValueAt(selectedIndex, 1);

        boolean success = saleManager.deleteSaleProduct(saleId, productId);
        if (success) {
            loadData();
            setInitialState();
            clearForm();
        } else {
            showError("No se pudo eliminar el registro del producto.");
        }
    }
}