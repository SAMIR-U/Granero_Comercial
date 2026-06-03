package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import co.elgranero.controller.SaleManager;
import co.elgranero.net.PaymentMethod;

public class PanelPaymentMethods extends PanelBase {

    private JTextField txtName;
    private int selectedId = -1;
    private SaleManager saleManager;

    public PanelPaymentMethods() {
        super("💳  Gestión de Formas de Pago", new String[] { "ID", "Forma de Pago" });
        try {
            this.saleManager = new SaleManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de Forma de Pago");
        txtName = addField("Nombre de Forma de Pago *");
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        if (saleManager == null)
            return;

        ArrayList<PaymentMethod> methods = saleManager.obtainPaymentMethods();
        for (PaymentMethod pm : methods) {
            tableModel.addRow(new Object[] {
                    pm.getIdPaymentMethod(),
                    pm.getPaymentMethodName()
            });
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

        boolean success;
        if (selectedId == -1) {
            success = saleManager.registPaymentMethod(name);
        } else {
            PaymentMethod pmToUpdate = new PaymentMethod();
            pmToUpdate.setIdPaymentMethod(selectedId);
            pmToUpdate.setPaymentMethodName(name);
            success = saleManager.modifyPaymentMethod(pmToUpdate);
        }

        if (success) {
            loadData();
            setInitialState();
            clearForm();
        } else {
            showError("No se pudo guardar la forma de pago en la base de datos.");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0)
            return;

        showError(
                "La eliminación de formas de pago no está habilitada para proteger la integridad de las ventas existentes.");
    }
}