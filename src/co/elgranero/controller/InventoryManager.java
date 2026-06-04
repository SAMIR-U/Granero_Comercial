package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import co.elgranero.controller.util.BDConnection;
import co.elgranero.net.Provider;
import co.elgranero.net.Purchase;
import co.elgranero.net.PurchaseProduct;
import co.elgranero.persistence.SqlInstructionsReader;

public final class InventoryManager {
    private SqlInstructionsReader sir;
    private Connection conn;

    public InventoryManager() throws IOException {
        this.sir = SqlInstructionsReader.getInstance();
        this.conn = BDConnection.getInstance().getConnection();
    }

    public boolean registProvider(String name, String document, String cellphone) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "PROVEEDORES");
            pSt.setString(1, name);
            pSt.setString(2, document);
            pSt.setString(3, cellphone);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en registProvider:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean registPurchase(int providerId, int paymentMethodId, Date purchaseDate) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "COMPRAS");
            pSt.setInt(1, providerId);
            pSt.setInt(2, paymentMethodId);
            pSt.setDate(3, purchaseDate);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en registPurchase:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean registPurchaseProduct(int productId, int purchaseId, int quantity, double unitPrice) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "PRODUCTOS_COMPRAS");
            pSt.setInt(1, productId);
            pSt.setInt(2, purchaseId);
            pSt.setInt(3, quantity);
            pSt.setDouble(4, unitPrice);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en registPurchaseProduct:");
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Provider> obtainProviders() {
        ArrayList<Provider> providers = new ArrayList<Provider>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PROVEEDORES");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                providers.add(new Provider(
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre_proveedor"),
                        rs.getString("documento_proveedor"),
                        rs.getString("celular_proveedor")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en obtainProviders:");
            e.printStackTrace();
        }
        return providers;
    }

    public ArrayList<Purchase> obtainPurchases() {
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "COMPRAS");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("fecha_compra");
                purchases.add(new Purchase(
                        rs.getInt("id_compra"),
                        rs.getInt("id_proveedor"),
                        rs.getInt("id_forma_pago"),
                        date,
                        rs.getString("nombre_proveedor"),
                        rs.getString("nombre_forma_pago"),
                        rs.getInt("numero_productos_comprados")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en obtainPurchases (¡Probablemente aquí está el problema!):");
            e.printStackTrace();
        }
        return purchases;
    }

    public ArrayList<PurchaseProduct> obtainPurchaseProducts(int purchaseId) {
        ArrayList<PurchaseProduct> purchaseProducts = new ArrayList<PurchaseProduct>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PRODUCTOS_COMPRAS");
            pSt.setInt(1, purchaseId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                purchaseProducts.add(new PurchaseProduct(
                        rs.getInt("id_producto"),
                        rs.getInt("id_compra"),
                        rs.getInt("cantidad_prod_compra"),
                        rs.getDouble("precio_unitario_prod_compra"),
                        rs.getString("nombre_producto")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en obtainPurchaseProducts:");
            e.printStackTrace();
        }
        return purchaseProducts;
    }

    public boolean modifyProvider(Provider provider) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "PROVEEDORES");
            pSt.setString(1, provider.getName());
            pSt.setString(2, provider.getDocument());
            pSt.setString(3, provider.getCellphone());
            pSt.setInt(4, provider.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en modifyProvider:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean modifyPurchase(Purchase purchase) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "COMPRAS");
            pSt.setInt(1, purchase.getIdSupplier());
            pSt.setInt(2, purchase.getIdPaymentMethod());
            pSt.setDate(3, purchase.getDate());
            pSt.setInt(4, purchase.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en modifyPurchase:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean modifyPurchaseProduct(PurchaseProduct purchaseProduct) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "PRODUCTOS_COMPRAS");
            pSt.setInt(1, purchaseProduct.getPurchaseProductQuantity());
            pSt.setDouble(2, purchaseProduct.getPurchaseProductUnitPrice());
            pSt.setInt(3, purchaseProduct.getIdProduct());
            pSt.setInt(4, purchaseProduct.getIdPurchase());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en modifyPurchaseProduct:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteProvider(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PROVEEDORES");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en deleteProvider:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean deletePurchase(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "COMPRAS");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en deletePurchase:");
            e.printStackTrace();
        }
        return result;
    }

    public boolean deletePurchaseProduct(int productId, int purchaseId) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PRODUCTOS_COMPRAS");
            pSt.setInt(1, productId);
            pSt.setInt(2, purchaseId);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            System.err.println("Error en deletePurchaseProduct:");
            e.printStackTrace();
        }
        return result;
    }
}