package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import co.elgranero.controller.ReportManager;
import co.elgranero.net.reports.TopSellingProduct;

public class PanelReportTopProducts extends PanelBase {

    private JTextField txtLimit;
    private ReportManager reportManager;

    public PanelReportTopProducts() {
        super("📈 Reporte: Productos Más Vendidos",
                new String[] { "ID Prod", "Nombre", "Subcategoría", "Categoría", "Unidades", "Total Recaudado" });
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
        addFormTitle("Filtros de Búsqueda");
        txtLimit = addField("Límite de Filas a Mostrar *");
        txtLimit.setText("10");

        JButton btnConsult = new JButton("🔍 Consultar");
        btnConsult.addActionListener(e -> loadData());
        formPanel.add(btnConsult);
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        if (reportManager == null || tableModel == null)
            return;
        tableModel.setRowCount(0);
        try {
            int limit = Integer.parseInt(txtLimit.getText().trim());
            ArrayList<TopSellingProduct> list = reportManager.obtainTopSellingProducts(limit);
            for (TopSellingProduct p : list) {
                tableModel.addRow(new Object[] {
                        p.getProductId(), p.getProductName(), p.getSubcategoryName(),
                        p.getCategoryName(), p.getTotalUnitsSold(), p.getTotalRevenue()
                });
            }
        } catch (NumberFormatException e) {
            showError("Por favor ingrese un número entero válido.");
        }
    }

    @Override
    protected void loadIntoForm(int row) {
    }

    @Override
    protected void clearForm() {
        txtLimit.setText("10");
    }

    @Override
    protected void actionSave() {
    }

    @Override
    protected void actionDelete() {
    }
}