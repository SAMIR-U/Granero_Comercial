package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import co.elgranero.controller.ReportManager;
import co.elgranero.net.reports.SalesComparison;

public class PanelReportSalesComparison extends PanelBase {

    private JTextField txtP1Start, txtP1End, txtP2Start, txtP2End;
    private ReportManager reportManager;

    public PanelReportSalesComparison() {
        super("⏳ Comparación de Ventas en el Tiempo",
                new String[] { "Periodo", "Total Ventas", "Unidades Vendidas", "Total Recaudado" });
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
        addFormTitle("Rango Periodo 1");
        txtP1Start = addField("Inicio P1 (yyyy-MM-dd) *");
        txtP1End = addField("Fin P1 (yyyy-MM-dd) *");

        addFormTitle("Rango Periodo 2");
        txtP2Start = addField("Inicio P2 (yyyy-MM-dd) *");
        txtP2End = addField("Fin P2 (yyyy-MM-dd) *");

        JButton btnConsult = new JButton("📊 Comparar Rangos");
        btnConsult.addActionListener(e -> loadData());
        formPanel.add(btnConsult);
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        if (reportManager == null || tableModel == null)
            return;

        String p1S = txtP1Start.getText().trim();
        String p1E = txtP1End.getText().trim();
        String p2S = txtP2Start.getText().trim();
        String p2E = txtP2End.getText().trim();

        if (p1S.isEmpty() || p1E.isEmpty() || p2S.isEmpty() || p2E.isEmpty()) {
            return; // Espera a que llenen todos los campos obligatorios
        }

        tableModel.setRowCount(0);
        ArrayList<SalesComparison> list = reportManager.compareSalesOverTime(p1S, p1E, p2S, p2E);
        for (SalesComparison c : list) {
            tableModel.addRow(new Object[] {
                    c.getPeriod(), c.getTotalSales(), c.getTotalUnitsSold(), c.getTotalRevenue()
            });
        }
    }

    @Override
    protected void loadIntoForm(int row) {
    }

    @Override
    protected void clearForm() {
        txtP1Start.setText("");
        txtP1End.setText("");
        txtP2Start.setText("");
        txtP2End.setText("");
    }

    @Override
    protected void actionSave() {
    }

    @Override
    protected void actionDelete() {
    }
}