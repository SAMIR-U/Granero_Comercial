package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import co.elgranero.controller.ReportManager;
import co.elgranero.net.reports.CustomerPurchaseHistory;

public class PanelReportCustomerHistory extends PanelBase {

    private JTextField txtClientId;
    private ReportManager reportManager;

    public PanelReportCustomerHistory() {
        super("👤 Historial de Compras por Cliente",
                new String[] { "ID Cliente", "Nombre", "Doc", "ID Venta", "Fecha", "Producto", "Cant", "Precio",
                        "Subtotal", "Desc", "Pago" });
        try {
            this.reportManager = new ReportManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
        if (btnSave != null)
            btnSave.setVisible(false);
        if (btnDelete != null)
            btnDelete.setVisible(false);
    }

    @Override
    protected void buildForm() {
        addFormTitle("Filtro por Cliente");
        txtClientId = addField("ID del Cliente *");

        JButton btnConsult = new JButton("🔍 Buscar Historial");
        btnConsult.addActionListener(e -> loadData());
        formPanel.add(btnConsult);
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        if (reportManager == null || tableModel == null)
            return;
        tableModel.setRowCount(0);
        String idStr = txtClientId.getText().trim();
        if (idStr.isEmpty())
            return;

        try {
            int clientId = Integer.parseInt(idStr);
            ArrayList<CustomerPurchaseHistory> list = reportManager.obtainCustomerPurchaseHistory(clientId);
            for (CustomerPurchaseHistory h : list) {
                tableModel.addRow(new Object[] {
                        h.getClientId(), h.getClientName(), h.getClientDocument(), h.getSaleId(),
                        h.getSaleDate(), h.getProductName(), h.getQuantity(), h.getUnitPrice(),
                        h.getSubtotal(), h.getSaleDiscount(), h.getPaymentMethodName()
                });
            }
        } catch (NumberFormatException e) {
            showError("El ID del cliente debe ser un número entero.");
        }
    }

    @Override
    protected void loadIntoForm(int row) {
    }

    @Override
    protected void clearForm() {
        txtClientId.setText("");
    }

    @Override
    protected void actionSave() {
    }

    @Override
    protected void actionDelete() {
    }
}