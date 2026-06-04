package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import co.elgranero.controller.InventoryManager;
import co.elgranero.controller.SaleManager;
import co.elgranero.net.PaymentMethod;
import co.elgranero.net.Provider;
import co.elgranero.net.Purchase;

public class PanelPurchases extends PanelBase {

    private JComboBox<Object> cboSupplier, cboPaymentMethod;
    private JTextField txtDate;
    private int selectedId = -1;

    private InventoryManager inventoryManager;
    private SaleManager saleManager;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PanelPurchases() {
        super("🛒  Gestión de Compras",
                new String[] { "ID", "Proveedor", "Forma de Pago", "Fecha" });
        try {
            this.inventoryManager = new InventoryManager();
            this.saleManager = new SaleManager();
        } catch (IOException e) {
            showError("Error al conectar con los controladores: " + e.getMessage());
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de la Compra");
        cboSupplier = addCombo("Proveedor *");
        cboPaymentMethod = addCombo("Forma de Pago *");
        txtDate = addField("Fecha (dd/MM/yyyy) *");

        cargarCombos();

        formPanel.add(Box.createVerticalGlue());
    }

    private void cargarCombos() {
        if (inventoryManager != null) {
            cboSupplier.removeAllItems();
            ArrayList<Provider> proveedores = inventoryManager.obtainProviders();
            for (Provider p : proveedores) {
                cboSupplier.addItem(p);
            }
        }

        if (saleManager != null) {
            cboPaymentMethod.removeAllItems();
            ArrayList<PaymentMethod> metodos = saleManager.obtainPaymentMethods();
            for (PaymentMethod pm : metodos) {
                cboPaymentMethod.addItem(pm);
            }
        }
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (inventoryManager == null)
            return;

        ArrayList<Purchase> compras = inventoryManager.obtainPurchases();
        for (Purchase pur : compras) {
            String fechaStr = (pur.getDate() != null)
                    ? dateFormat.format(pur.getDate())
                    : "N/A";

            Object[] row = {
                    pur.getId(),
                    pur.getName(),
                    pur.getPaymentMethodName(),
                    fechaStr
            };
            tableModel.addRow(row);
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);

        String provTabla = (String) tableModel.getValueAt(row, 1);
        String pagoTabla = (String) tableModel.getValueAt(row, 2);
        txtDate.setText((String) tableModel.getValueAt(row, 3));

        for (int i = 0; i < cboSupplier.getItemCount(); i++) {
            Provider p = (Provider) cboSupplier.getItemAt(i);
            if (p.getName().equalsIgnoreCase(provTabla)) {
                cboSupplier.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < cboPaymentMethod.getItemCount(); i++) {
            PaymentMethod pm = (PaymentMethod) cboPaymentMethod.getItemAt(i);
            // AQUÍ ESTÁ EL CAMBIO
            if (pm.getName().equalsIgnoreCase(pagoTabla)) {
                cboPaymentMethod.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtDate.setText("");
        if (cboSupplier.getItemCount() > 0)
            cboSupplier.setSelectedIndex(0);
        if (cboPaymentMethod.getItemCount() > 0)
            cboPaymentMethod.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String dateText = txtDate.getText().trim();
        Provider selectedProv = (Provider) cboSupplier.getSelectedItem();
        PaymentMethod selectedPay = (PaymentMethod) cboPaymentMethod.getSelectedItem();

        if (dateText.isEmpty() || selectedProv == null || selectedPay == null) {
            showError("Todos los campos son obligatorios.");
            return;
        }

        try {
            java.util.Date parsedDate = dateFormat.parse(dateText);
            Date sqlDate = new Date(parsedDate.getTime());

            boolean success;
            if (selectedId == -1) {
                // AQUÍ ESTÁ EL CAMBIO
                success = inventoryManager.registPurchase(selectedProv.getId(), selectedPay.getId(),
                        sqlDate);
            } else {
                Purchase compModificada = new Purchase(
                        selectedId,
                        selectedProv.getId(),
                        selectedPay.getId(), // AQUÍ ESTÁ EL CAMBIO
                        sqlDate,
                        selectedProv.getName(),
                        selectedPay.getName(), // AQUÍ ESTÁ EL CAMBIO
                        0);
                success = inventoryManager.modifyPurchase(compModificada);
            }

            if (success) {
                loadData();
                setInitialState();
                clearForm();
                JOptionPane.showMessageDialog(this, "¡Compra guardada exitosamente!");
            } else {
                showError("Hubo un error al guardar la compra en la base de datos.");
            }

        } catch (ParseException e) {
            showError("El formato de la fecha es incorrecto. Debe ser dd/MM/yyyy.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0) {
            showError("Seleccione una compra de la tabla para eliminar.");
            return;
        }
        if (!confirm("¿Realmente desea eliminar esta compra?")) {
            return;
        }

        boolean exito = inventoryManager.deletePurchase(selectedId);
        if (exito) {
            loadData();
            setInitialState();
            clearForm();
            JOptionPane.showMessageDialog(this, "Compra eliminada correctamente.");
        } else {
            showError("No se pudo eliminar la compra de la base de datos.");
        }
    }
}