package co.elgranero.view;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import co.elgranero.controller.SaleManager;
import co.elgranero.controller.UserManager;
import co.elgranero.net.Sale;
import co.elgranero.net.PaymentMethod;
import co.elgranero.net.User;

public class PanelSales extends PanelBase {

    private JComboBox<Object> cboCustomer, cboPaymentMethod;
    private JTextField txtDate, txtDiscount;
    private int selectedId = -1;

    private SaleManager saleManager;
    private UserManager userManager;

    private ArrayList<User> customerList;
    private ArrayList<PaymentMethod> paymentMethodList;

    public PanelSales() {
        super("💰  Gestión de Ventas",
                new String[] { "ID", "ID Pago", "ID Cliente", "Fecha", "Descuento", "Cant. Productos" });
        try {
            this.saleManager = new SaleManager();
            this.userManager = new UserManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    @Override
    protected void buildForm() {
        addFormTitle("Datos de la Venta");
        cboCustomer = addCombo("Cliente *");
        cboPaymentMethod = addCombo("Forma de Pago *");
        txtDate = addField("Fecha (yyyy-MM-dd) *");
        txtDiscount = addField("Descuento (%) — opcional");

        try {
            if (userManager == null) {
                this.userManager = new UserManager();
            }

            ArrayList<User> allUsers = userManager.obtainUsers();
            customerList = new ArrayList<>();

            for (User u : allUsers) {
                if ("CLIENTE".equals(u.getPersonType())) {
                    customerList.add(u);
                    cboCustomer.addItem(u.getUserName());
                }
            }

            if (saleManager == null) {
                this.saleManager = new SaleManager();
            }
            paymentMethodList = saleManager.obtainPaymentMethods();
            for (PaymentMethod p : paymentMethodList) {
                cboPaymentMethod.addItem(p.getPaymentMethodName());
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

        ArrayList<Sale> sales = saleManager.obtainSales();
        for (Sale s : sales) {
            tableModel.addRow(new Object[] {
                    s.getIdSale(),
                    s.getIdPaymentMethod(),
                    s.getIdClient(),
                    s.getSaleDate(),
                    s.getSaleDiscount(),
                    s.getProductsCount()
            });
        }
    }

    @Override
    protected void loadIntoForm(int row) {
        selectedId = (int) tableModel.getValueAt(row, 0);

        int idPaymentMethod = (int) tableModel.getValueAt(row, 1);
        int idClient = (int) tableModel.getValueAt(row, 2);

        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getIdUser() == idClient) {
                cboCustomer.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < paymentMethodList.size(); i++) {
            if (paymentMethodList.get(i).getIdPaymentMethod() == idPaymentMethod) {
                cboPaymentMethod.setSelectedIndex(i);
                break;
            }
        }

        txtDate.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        txtDiscount.setText(String.valueOf(tableModel.getValueAt(row, 4)));
    }

    @Override
    protected void clearForm() {
        selectedId = -1;
        txtDate.setText("");
        txtDiscount.setText("0");
        if (cboCustomer.getItemCount() > 0)
            cboCustomer.setSelectedIndex(0);
        if (cboPaymentMethod.getItemCount() > 0)
            cboPaymentMethod.setSelectedIndex(0);
    }

    @Override
    protected void actionSave() {
        String dateStr = txtDate.getText().trim();
        if (dateStr.isEmpty() || cboCustomer.getSelectedItem() == null || cboPaymentMethod.getSelectedItem() == null) {
            showError("Cliente, Forma de Pago y Fecha son obligatorios.");
            return;
        }

        try {
            String discountStr = txtDiscount.getText().trim();
            double discount = discountStr.isEmpty() ? 0.0 : Double.parseDouble(discountStr);

            int customerIdx = cboCustomer.getSelectedIndex();
            int paymentIdx = cboPaymentMethod.getSelectedIndex();

            int idClient = customerList.get(customerIdx).getIdUser();
            int idPaymentMethod = paymentMethodList.get(paymentIdx).getIdPaymentMethod();

            boolean success;
            if (selectedId == -1) {
                success = saleManager.registSale(idPaymentMethod, idClient, dateStr, discount);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(dateStr);
                Date sqlDate = new Date(parsedDate.getTime());

                Sale saleToUpdate = new Sale();
                saleToUpdate.setIdSale(selectedId);
                saleToUpdate.setIdPaymentMethod(idPaymentMethod);
                saleToUpdate.setIdClient(idClient);
                saleToUpdate.setSaleDate(sqlDate);
                saleToUpdate.setSaleDiscount(discount);

                success = saleManager.modifySale(saleToUpdate);
            }

            if (success) {
                loadData();
                setInitialState();
                clearForm();
            } else {
                showError("No se pudo guardar la venta en la base de datos.");
            }

        } catch (NumberFormatException e) {
            showError("El descuento debe ser un número válido.");
        } catch (ParseException e) {
            showError("La fecha debe tener el formato válido (yyyy-MM-dd).");
        }
    }

    @Override
    protected void actionDelete() {
        if (table.getSelectedRow() < 0)
            return;
        if (!confirm("¿Eliminar esta venta?"))
            return;

        boolean success = saleManager.deleteSale(selectedId);
        if (success) {
            loadData();
            setInitialState();
            clearForm();
        } else {
            showError("No se pudo eliminar la venta.");
        }
    }
}