package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
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

    public ArrayList<SalesComparison> compareSalesOverTime(String period1Start, String period1End, String period2Start,
            String period2End) {
        ArrayList<SalesComparison> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "COMPARACION_VENTAS");
            pSt.setString(1, period1Start);
            pSt.setString(2, period1End);
            pSt.setString(3, period2Start);
            pSt.setString(4, period2End);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new SalesComparison(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDouble(4)));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<CustomerPurchaseHistory> obtainCustomerPurchaseHistory(int clientId) {
        ArrayList<CustomerPurchaseHistory> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "HISTORIAL_COMPRAS_CLIENTE");
            pSt.setInt(1, clientId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new CustomerPurchaseHistory(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getString(11)));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<CustomerPurchaseVolume> obtainCustomerPurchaseVolume(int limitRows) {
        ArrayList<CustomerPurchaseVolume> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "VOLUMEN_COMPRAS_CLIENTE");
            pSt.setInt(1, limitRows);
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

    public ArrayList<SalesByCustomer> obtainSalesByCustomer(int clientId) {
        ArrayList<SalesByCustomer> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "VENTAS_POR_CLIENTE");
            pSt.setInt(1, clientId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new SalesByCustomer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDouble(6)));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<SalesByProduct> obtainSalesByProduct(int productId) {
        ArrayList<SalesByProduct> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "VENTAS_POR_PRODUCTO");
            pSt.setInt(1, productId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new SalesByProduct(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDouble(6)));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<SalesByCategory> obtainSalesByProductCategory(int categoryId) {
        ArrayList<SalesByCategory> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "VENTAS_POR_CATEGORIA");
            pSt.setInt(1, categoryId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new SalesByCategory(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDouble(5)));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<TopSellingProduct> obtainTopSellingProducts(int limitRows) {
        ArrayList<TopSellingProduct> list = new ArrayList<>();
        try {
            PreparedStatement pSt = sir.getReportOf(conn, "PRODUCTOS_MAS_VENDIDOS");
            pSt.setInt(1, limitRows);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                list.add(new TopSellingProduct(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDouble(6)));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User obtainSellerByDocument(String document) {
        User seller = null;
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "VENDEDOR_POR_DOCUMENTO");
            pSt.setString(1, document);
            ResultSet rs = pSt.executeQuery();
            if (rs.next()) {
                seller = new User(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return seller;
    }
}