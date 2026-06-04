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

import co.elgranero.controller.SaleManager;
import co.elgranero.controller.ProductsManager;
import co.elgranero.net.SaleProduct;
import co.elgranero.net.Product;
import co.elgranero.net.Sale;
import co.elgranero.net.ProductPresentation;

public class PanelSaleProducts extends PanelBase {

    private JComboBox<Object> cboSaleId, cboProduct, cboPresentation;
    private JTextField txtQuantity;
    private int selectedIndex = -1;
    private boolean isLoadingForm = false;

    private SaleManager saleManager;
    private ProductsManager productsManager;
    private ArrayList<Product> productList;
    private ArrayList<ProductPresentation> presentationList = new ArrayList<>();

    public PanelSaleProducts() {
        super("Detalle de Productos en Venta",
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
        cboSaleId = addCombo("Venta (ID | Cliente | Fecha) *");
        cboProduct = addCombo("Producto *");
        txtQuantity = addField("Cantidad *");
        cboPresentation = addCombo("Presentación del Producto *");

        cargarCombos();

        cboSaleId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLoadingForm && cboSaleId.getSelectedItem() != null) {
                    loadData();
                }
            }
        });

        cboProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLoadingForm) {
                    cargarPresentaciones();
                }
            }
        });

        cboSaleId.addPropertyChangeListener("enabled", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (Boolean.FALSE.equals(evt.getNewValue())) {
                    cboSaleId.setEnabled(true);
                }
            }
        });

        cboSaleId.setEnabled(true);

        formPanel.add(Box.createVerticalGlue());
    }

    private void cargarCombos() {
        try {
            if (productsManager == null) {
                this.productsManager = new ProductsManager();
            }
            cboProduct.removeAllItems();
            productList = productsManager.obtainProducts();
            for (Product p : productList) {
                cboProduct.addItem(p.getName());
            }

            cargarPresentaciones();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (saleManager != null) {
            cboSaleId.removeAllItems();
            ArrayList<Sale> sales = saleManager.obtainSales();

            Collections.sort(sales, new Comparator<Sale>() {
                @Override
                public int compare(Sale s1, Sale s2) {
                    try {
                        return String.valueOf(s2.getDate()).compareTo(String.valueOf(s1.getDate()));
                    } catch (Exception e) {
                        return Integer.compare(s2.getId(), s1.getId());
                    }
                }
            });

            for (Sale s : sales) {
                String display = s.getId() + " | " + s.getClientName() + " | " + s.getDate();
                cboSaleId.addItem(display);
            }
        }
    }

    private void cargarPresentaciones() {
        if (cboPresentation == null || productsManager == null || cboProduct.getSelectedItem() == null) {
            return;
        }

        cboPresentation.removeAllItems();
        presentationList.clear();
        int productIdx = cboProduct.getSelectedIndex();

        if (productIdx >= 0 && productIdx < productList.size()) {
            Product selectedProd = productList.get(productIdx);
            try {
                ArrayList<ProductPresentation> pps = productsManager.obtainProductPresentations(selectedProd.getId());
                if (pps != null) {
                    presentationList.addAll(pps);
                    for (ProductPresentation pp : pps) {
                        cboPresentation.addItem(pp.getPresentationName() + " ($" + pp.getPresentationPrice() + ")");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (saleManager == null || cboSaleId == null || cboSaleId.getSelectedItem() == null)
            return;

        try {
            String selectedStr = cboSaleId.getSelectedItem().toString();
            int saleId = Integer.parseInt(selectedStr.split("\\|")[0].trim());

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
        } catch (Exception e) {
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        isLoadingForm = true;
        try {
            selectedIndex = row;
            int idSale = (int) tableModel.getValueAt(row, 0);

            for (int i = 0; i < cboSaleId.getItemCount(); i++) {
                String itemStr = cboSaleId.getItemAt(i).toString();
                if (itemStr.startsWith(idSale + " | ")) {
                    cboSaleId.setSelectedIndex(i);
                    break;
                }
            }

            int idProduct = (int) tableModel.getValueAt(row, 1);
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getId() == idProduct) {
                    cboProduct.setSelectedIndex(i);
                    break;
                }
            }

            cargarPresentaciones();

            double unitPrice = (double) tableModel.getValueAt(row, 3);
            for (int i = 0; i < presentationList.size(); i++) {
                if (Math.abs(presentationList.get(i).getPresentationPrice() - unitPrice) < 0.01) {
                    cboPresentation.setSelectedIndex(i);
                    break;
                }
            }

            txtQuantity.setText(String.valueOf(tableModel.getValueAt(row, 2)));

            cboSaleId.setEnabled(true);
        } finally {
            isLoadingForm = false;
        }
    }

    @Override
    protected void clearForm() {
        selectedIndex = -1;
        txtQuantity.setText("");
        if (cboProduct.getItemCount() > 0) {
            cboProduct.setSelectedIndex(0);
        }
        cargarPresentaciones();
        cboSaleId.setEnabled(true);
    }

    @Override
    protected void actionSave() {
        try {
            if (cboSaleId.getSelectedItem() == null) {
                showError("Seleccione una venta válida.");
                return;
            }

            String selectedStr = cboSaleId.getSelectedItem().toString();
            int saleId = Integer.parseInt(selectedStr.split("\\|")[0].trim());

            int qty = Integer.parseInt(txtQuantity.getText().trim());

            if (cboProduct.getSelectedItem() == null) {
                showError("Seleccione un producto.");
                return;
            }

            if (cboPresentation.getSelectedItem() == null) {
                showError("Seleccione una presentación válida para el producto.");
                return;
            }

            int productIdx = cboProduct.getSelectedIndex();
            int idProduct = productList.get(productIdx).getId();

            int presIdx = cboPresentation.getSelectedIndex();
            double price = presentationList.get(presIdx).getPresentationPrice();

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
                JOptionPane.showMessageDialog(this, "Producto en venta guardado exitosamente!");
            } else {
                showError("No se pudo guardar el detalle del producto en la base de datos.");
            }
        } catch (NumberFormatException e) {
            showError("La cantidad debe ser un valor numérico válido.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0 || selectedIndex < 0) {
            showError("Seleccione un registro de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Eliminar este registro?"))
            return;

        int saleId = (int) tableModel.getValueAt(selectedIndex, 0);
        int productId = (int) tableModel.getValueAt(selectedIndex, 1);

        boolean success = saleManager.deleteSaleProduct(saleId, productId);
        if (success) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
        } else {
            showError("No se pudo eliminar el registro del producto.");
        }
    }

    @Override
    public void setInitialState() {
        super.setInitialState();
        if (cboSaleId != null) {
            cboSaleId.setEnabled(true);
        }
    }
}