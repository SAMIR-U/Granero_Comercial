package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import co.elgranero.controller.ReportManager;
import co.elgranero.net.reports.TopSellingProduct;

public class PanelReportTopProducts extends PanelBase {

    private JTextField txtLimit;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private ReportManager reportManager;

    // Bandera para bloquear las recargas automáticas al iniciar el panel
    private boolean explicitSearch = false;

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
        if (this.btnEdit != null)
            this.btnEdit.setVisible(false);

        if (this.btnNew != null) {
            this.btnNew.setText("🧹 Limpiar Filtros");
        }
    }

    @Override
    protected void buildForm() {
        addFormTitle("Filtros de Búsqueda");

        txtLimit = addField("Límite de Filas a Mostrar *");
        txtLimit.setText("10"); // Lo dejamos en 10 por comodidad visual

        txtStartDate = addField("Fecha Inicio (DD/MM/YYYY)");
        txtEndDate = addField("Fecha Fin (DD/MM/YYYY)");

        JButton btnConsult = new JButton("🔍 Consultar");
        btnConsult.addActionListener(e -> {
            if (txtLimit.getText().trim().isEmpty()) {
                showError("El límite de filas es obligatorio.");
                return;
            }
            // Activamos la bandera indicando que el usuario SÍ quiere buscar
            explicitSearch = true;
            loadData();
        });
        formPanel.add(btnConsult);
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        // Si la orden de carga no viene de pulsar el botón, la ignoramos y no mostramos
        // nada
        if (!explicitSearch) {
            return;
        }

        if (reportManager == null || tableModel == null || txtLimit == null)
            return;

        tableModel.setRowCount(0);
        String limitStr = txtLimit.getText().trim();
        String startStr = txtStartDate.getText().trim();
        String endStr = txtEndDate.getText().trim();

        try {
            int limit = Integer.parseInt(limitStr);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Date startDate = startStr.isEmpty()
                    ? Date.valueOf("1970-01-01")
                    : Date.valueOf(LocalDate.parse(startStr, formatter));

            Date endDate = endStr.isEmpty()
                    ? Date.valueOf("2099-12-31")
                    : Date.valueOf(LocalDate.parse(endStr, formatter));

            ArrayList<TopSellingProduct> list = reportManager.obtainTopSellingProducts(limit, startDate, endDate);

            for (TopSellingProduct p : list) {
                tableModel.addRow(new Object[] {
                        p.getProductId(),
                        p.getProductName(),
                        p.getSubcategoryName(),
                        p.getCategoryName(),
                        p.getTotalUnitsSold(),
                        p.getTotalRevenue()
                });
            }
        } catch (NumberFormatException e) {
            showError("Por favor ingrese un número entero válido para el límite.");
        } catch (DateTimeParseException e) {
            showError("Formato de fecha inválido. Por favor use el formato DD/MM/YYYY (Ejemplo: 31/12/2023).");
        }
    }

    @Override
    protected void loadIntoForm(int row) {
    }

    @Override
    protected void clearForm() {
        if (txtLimit != null)
            txtLimit.setText("10");
        if (txtStartDate != null)
            txtStartDate.setText("");
        if (txtEndDate != null)
            txtEndDate.setText("");

        if (tableModel != null) {
            tableModel.setRowCount(0);
        }

        // Al limpiar los filtros, reiniciamos la bandera para evitar recargas fantasma
        explicitSearch = false;
    }

    @Override
    protected void actionSave() {
    }

    @Override
    protected void actionDelete() {
    }
}