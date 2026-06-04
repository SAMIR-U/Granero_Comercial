package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.elgranero.net.User;
import co.elgranero.net.reports.*;
import co.elgranero.controller.util.BDConnection;
import co.elgranero.persistence.SqlInstructionsReader;

public class ReportManager {

    private SqlInstructionsReader sir;
    private Connection conn;

    public ReportManager() throws IOException {
        this.sir = SqlInstructionsReader.getInstance();
        this.conn = BDConnection.getInstance().getConnection();
    }

    public ArrayList<SalesComparison> compareSalesOverTime(Date period1Start, Date period1End, Date period2Start,
            Date period2End) {
        ArrayList<SalesComparison> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "COMPARE_SALES_OVER_TIME");
            pSt.setDate(1, period1Start);
            pSt.setDate(2, period1End);
            pSt.setDate(3, period2Start);
            pSt.setDate(4, period2End);
            pSt.setDate(5, period1Start);
            pSt.setDate(6, period1End);
            pSt.setDate(7, period2Start);
            pSt.setDate(8, period2End);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new SalesComparison(
                        rs.getString("periodo"),
                        rs.getInt("total_ventas"),
                        rs.getInt("total_unidades_vendidas"),
                        rs.getDouble("total_ingresos")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<CustomerPurchaseHistory> obtainCustomerPurchaseHistory(int clientId, Date startDate,
            Date endDate) {
        ArrayList<CustomerPurchaseHistory> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "CUSTOMER_PURCHASE_HISTORY");
            pSt.setInt(1, clientId);
            pSt.setDate(2, startDate);
            pSt.setDate(3, endDate);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new CustomerPurchaseHistory(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre_cliente"),
                        rs.getString("documento_cliente"),
                        rs.getInt("id_venta"),
                        rs.getDate("fecha_venta"),
                        rs.getString("nombre_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("descuento_venta"),
                        rs.getString("nombre_forma_pago")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<CustomerPurchaseVolume> obtainCustomerPurchaseVolume(int limitRows, Date startDate, Date endDate) {
        ArrayList<CustomerPurchaseVolume> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "CUSTOMER_PURCHASE_VOLUME");
            pSt.setDate(1, startDate);
            pSt.setDate(2, endDate);
            pSt.setInt(3, limitRows);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new CustomerPurchaseVolume(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7)));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<TopSellingProduct> obtainTopSellingProducts(int limitRows, Date startDate, Date endDate) {
        ArrayList<TopSellingProduct> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "TOP_SELLING_PRODUCTS");
            pSt.setDate(1, startDate);
            pSt.setDate(2, endDate);
            pSt.setInt(3, limitRows);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new TopSellingProduct(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("nombre_subcategoria"),
                        rs.getString("nombre_categoria"),
                        rs.getInt("total_unidades_vendidas"),
                        rs.getDouble("total_ingresos")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}