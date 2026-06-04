package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.elgranero.net.PaymentMethod;
import co.elgranero.net.Sale;
import co.elgranero.net.SaleProduct;
import co.elgranero.controller.util.BDConnection;
import co.elgranero.persistence.SqlInstructionsReader;

public class SaleManager {

    private SqlInstructionsReader sir;
    private Connection conn;

    public SaleManager() throws IOException {
        this.sir = SqlInstructionsReader.getInstance();
        this.conn = BDConnection.getInstance().getConnection();
    }

    public boolean registPaymentMethod(String paymentMethodName) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "FORMAS_PAGOS");
            pSt.setString(1, paymentMethodName);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean registSale(int paymentMethodId, int clientId, String saleDate, double saleDiscount) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "VENTAS");
            pSt.setInt(1, paymentMethodId);
            pSt.setInt(2, clientId);
            pSt.setString(3, saleDate);
            pSt.setDouble(4, saleDiscount);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean registSaleProduct(int saleId, int productId, int quantity, double unitPrice) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "VENTAS_PRODUCTOS");
            pSt.setInt(1, saleId);
            pSt.setInt(2, productId);
            pSt.setInt(3, quantity);
            pSt.setDouble(4, unitPrice);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<PaymentMethod> obtainPaymentMethods() {
        ArrayList<PaymentMethod> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "FORMAS_PAGOS");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new PaymentMethod(
                        rs.getInt("id_forma_pago"),
                        rs.getString("nombre_forma_pago")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Sale> obtainSales() {
        ArrayList<Sale> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "VENTAS");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new Sale(
                        rs.getInt("id_venta"),
                        rs.getInt("id_forma_pago"),
                        rs.getInt("per_id_cliente"),
                        rs.getString("nombre_cliente"),
                        rs.getDate("fecha_venta"),
                        rs.getDouble("descuento_venta"),
                        rs.getInt("numero_de_productos")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<SaleProduct> obtainSaleProducts(int saleId) {
        ArrayList<SaleProduct> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "VENTAS_PRODUCTOS");
            pSt.setInt(1, saleId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new SaleProduct(
                        rs.getInt("id_venta"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getString("nombre_producto")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean modifyPaymentMethod(PaymentMethod paymentMethod) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "FORMAS_PAGOS");
            pSt.setString(1, paymentMethod.getName());
            pSt.setInt(2, paymentMethod.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean modifySale(Sale sale) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "VENTAS");
            pSt.setInt(1, sale.getIdPaymentMethod());
            pSt.setInt(2, sale.getIdClient());
            pSt.setDate(3, sale.getDate());
            pSt.setDouble(4, sale.getDiscount());
            pSt.setInt(5, sale.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean modifySaleProduct(SaleProduct saleProduct) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "VENTAS_PRODUCTOS");
            pSt.setInt(1, saleProduct.getQuantity());
            pSt.setDouble(2, saleProduct.getUnitPrice());
            pSt.setInt(3, saleProduct.getIdSale());
            pSt.setInt(4, saleProduct.getIdProduct());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deletePaymentMethod(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "FORMAS_PAGOS");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteSale(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "VENTAS");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteSaleProduct(int saleId, int productId) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "VENTAS_PRODUCTOS");
            pSt.setInt(1, saleId);
            pSt.setInt(2, productId);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}