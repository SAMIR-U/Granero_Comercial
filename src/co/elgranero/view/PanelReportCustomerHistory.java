package co.elgranero.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import co.elgranero.controller.ReportManager;
import co.elgranero.net.reports.CustomerPurchaseHistory;
import co.elgranero.net.reports.CustomerPurchaseVolume;

public class PanelReportCustomerHistory extends PanelBase {

    private JTextField txtClientId;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private ReportManager reportManager;

    private DefaultTableModel modelVentas;
    private DefaultTableModel modelVolumen;
    private JTable tableVentas;
    private JTable tableVolumen;

    public PanelReportCustomerHistory() {
        super("👤 Historial de Compras por Cliente",
                new String[] { "ID Cliente", "Nombre", "Documento" });

        modelVentas = new DefaultTableModel(
                new String[] { "ID Venta", "Fecha", "Producto", "Cant", "Precio", "Subtotal", "Desc", "Pago" }, 0);

        modelVolumen = new DefaultTableModel(
                new String[] { "Ciudad", "Total Ventas", "Unidades Compradas", "Total Gastado" }, 0);

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

        reorganizeCentralTables();
    }

    private void applyTableStyle(JTable table) {
        table.setRowHeight(25);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(24, 69, 39));
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setFont(headerRenderer.getFont().deriveFont(Font.BOLD));

        table.getTableHeader().setDefaultRenderer(headerRenderer);
        table.getTableHeader().setOpaque(false);
    }

    private void reorganizeCentralTables() {
        java.awt.Component centerComp = ((java.awt.BorderLayout) getLayout())
                .getLayoutComponent(java.awt.BorderLayout.CENTER);

        if (centerComp instanceof JSplitPane) {
            JSplitPane mainSplit = (JSplitPane) centerComp;
            java.awt.Component leftComp = mainSplit.getLeftComponent();

            JScrollPane scrollInfoCliente = null;
            java.awt.Container parentOfScroll = null;

            if (leftComp instanceof JScrollPane) {
                scrollInfoCliente = (JScrollPane) leftComp;
                parentOfScroll = mainSplit;
            } else if (leftComp instanceof JPanel) {
                JPanel leftPanel = (JPanel) leftComp;
                java.awt.Component centerOfLeft = ((java.awt.BorderLayout) leftPanel.getLayout())
                        .getLayoutComponent(java.awt.BorderLayout.CENTER);
                if (centerOfLeft instanceof JScrollPane) {
                    scrollInfoCliente = (JScrollPane) centerOfLeft;
                    parentOfScroll = leftPanel;
                }
            }

            if (scrollInfoCliente != null) {
                scrollInfoCliente.setBorder(BorderFactory.createTitledBorder("👤 Información General del Cliente"));

                tableVentas = new JTable(modelVentas);
                applyTableStyle(tableVentas);
                JScrollPane scrollVentas = new JScrollPane(tableVentas);
                scrollVentas.setBorder(BorderFactory.createTitledBorder("📦 Desglose de Ventas y Productos"));

                tableVolumen = new JTable(modelVolumen);
                applyTableStyle(tableVolumen);
                JScrollPane scrollVolumen = new JScrollPane(tableVolumen);
                scrollVolumen.setBorder(BorderFactory.createTitledBorder("📊 Resumen de Volumen de Compra"));

                JPanel panelTablasVerticales = new JPanel(new java.awt.GridLayout(3, 1, 0, 10));
                panelTablasVerticales.add(scrollInfoCliente);
                panelTablasVerticales.add(scrollVentas);
                panelTablasVerticales.add(scrollVolumen);

                if (parentOfScroll instanceof JSplitPane) {
                    ((JSplitPane) parentOfScroll).setLeftComponent(panelTablasVerticales);
                } else if (parentOfScroll instanceof JPanel) {
                    parentOfScroll.remove(scrollInfoCliente);
                    parentOfScroll.add(panelTablasVerticales, java.awt.BorderLayout.CENTER);
                }

                parentOfScroll.revalidate();
                parentOfScroll.repaint();
            }
        }
    }

    @Override
    protected void buildForm() {
        addFormTitle("Filtro por Cliente");
        txtClientId = addField("ID del Cliente *");
        txtStartDate = addField("Fecha Inicio (DD/MM/YYYY)");
        txtEndDate = addField("Fecha Fin (DD/MM/YYYY)");

        JButton btnConsult = new JButton("🔍 Buscar Historial");
        btnConsult.addActionListener(e -> {
            // Validación estricta solo al hacer click
            if (txtClientId.getText().trim().isEmpty()) {
                showError("El ID del cliente es obligatorio.");
                return;
            }
            loadData();
        });
        formPanel.add(btnConsult);
        formPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void loadData() {
        if (reportManager == null || tableModel == null || modelVentas == null || modelVolumen == null
                || txtClientId == null)
            return;

        String idStr = txtClientId.getText().trim();

        // Si está vacío en la carga inicial, simplemente abortamos sin mostrar error
        if (idStr.isEmpty()) {
            return;
        }

        tableModel.setRowCount(0);
        modelVentas.setRowCount(0);
        modelVolumen.setRowCount(0);

        String startStr = txtStartDate.getText().trim();
        String endStr = txtEndDate.getText().trim();

        try {
            int clientId = Integer.parseInt(idStr);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Date startDate = startStr.isEmpty()
                    ? Date.valueOf("1970-01-01")
                    : Date.valueOf(LocalDate.parse(startStr, formatter));

            Date endDate = endStr.isEmpty()
                    ? Date.valueOf("2099-12-31")
                    : Date.valueOf(LocalDate.parse(endStr, formatter));

            ArrayList<CustomerPurchaseHistory> listHistory = reportManager.obtainCustomerPurchaseHistory(
                    clientId, startDate, endDate);

            if (!listHistory.isEmpty()) {
                CustomerPurchaseHistory first = listHistory.get(0);
                tableModel.addRow(new Object[] {
                        first.getClientId(),
                        first.getClientName(),
                        first.getClientDocument()
                });

                for (CustomerPurchaseHistory h : listHistory) {
                    modelVentas.addRow(new Object[] {
                            h.getSaleId(),
                            h.getSaleDate(),
                            h.getProductName(),
                            h.getQuantity(),
                            h.getUnitPrice(),
                            h.getSubtotal(),
                            h.getSaleDiscount(),
                            h.getPaymentMethodName()
                    });
                }
            }

            ArrayList<CustomerPurchaseVolume> listVolume = reportManager.obtainCustomerPurchaseVolume(
                    clientId, startDate, endDate);

            for (CustomerPurchaseVolume v : listVolume) {
                modelVolumen.addRow(new Object[] {
                        v.getCityName(),
                        v.getTotalSales(),
                        v.getTotalUnitsPurchased(),
                        v.getTotalSpent()
                });
            }

        } catch (NumberFormatException e) {
            showError("El ID del cliente debe ser un número entero.");
        } catch (DateTimeParseException e) {
            showError("Formato de fecha inválido. Por favor use el formato DD/MM/YYYY (Ejemplo: 31/12/2023).");
        }
    }

    @Override
    protected void loadIntoForm(int row) {
    }

    @Override
    protected void clearForm() {
        txtClientId.setText("");
        if (txtStartDate != null)
            txtStartDate.setText("");
        if (txtEndDate != null)
            txtEndDate.setText("");

        if (tableModel != null)
            tableModel.setRowCount(0);
        if (modelVentas != null)
            modelVentas.setRowCount(0);
        if (modelVolumen != null)
            modelVolumen.setRowCount(0);
    }

    @Override
    protected void actionSave() {
    }

    @Override
    protected void actionDelete() {
    }
}