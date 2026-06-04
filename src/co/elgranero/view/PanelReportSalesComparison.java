package co.elgranero.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import co.elgranero.controller.ReportManager;
import co.elgranero.net.reports.SalesComparison;

public class PanelReportSalesComparison extends PanelBase {

    private JTextField txtP1Start, txtP1End, txtP2Start, txtP2End;
    private ReportManager reportManager;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PanelReportSalesComparison() {
        super("Comparación de Ventas en el Tiempo",
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

        if (this.btnEdit != null) {
            this.btnEdit.setVisible(false);
        }

        if (this.btnNew != null) {
            this.btnNew.setText("Limpiar Filtros");
        }
    }

    @Override
    protected void buildForm() {
        addFormTitle("Rango Periodo 1");
        txtP1Start = addField("Inicio P1 (dd/MM/yyyy) *");
        txtP1End = addField("Fin P1 (dd/MM/yyyy) *");

        addFormTitle("Rango Periodo 2");
        txtP2Start = addField("Inicio P2 (dd/MM/yyyy) *");
        txtP2End = addField("Fin P2 (dd/MM/yyyy) *");

        JButton btnConsult = mkStyledButton("Comparar Rangos");
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
            return;
        }

        try {
            Date p1Start = Date.valueOf(LocalDate.parse(p1S, dateFormatter));
            Date p1End = Date.valueOf(LocalDate.parse(p1E, dateFormatter));
            Date p2Start = Date.valueOf(LocalDate.parse(p2S, dateFormatter));
            Date p2End = Date.valueOf(LocalDate.parse(p2E, dateFormatter));

            tableModel.setRowCount(0);
            ArrayList<SalesComparison> list = reportManager.compareSalesOverTime(p1Start, p1End, p2Start, p2End);
            for (SalesComparison c : list) {
                tableModel.addRow(new Object[] {
                        c.getPeriod(), c.getTotalSales(), c.getTotalUnitsSold(), c.getTotalRevenue()
                });
            }
        } catch (DateTimeParseException e) {
            showError("Por favor, introduce las fechas en el formato correcto: día/mes/año (Ej: 03/06/2026)");
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

    private JButton mkStyledButton(String txt) {
        JButton b = new JButton(txt);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(new Color(30, 68, 42));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 100, 64)),
                new EmptyBorder(9, 18, 9, 18)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.setAlignmentX(Component.LEFT_ALIGNMENT);

        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return b;
    }
}