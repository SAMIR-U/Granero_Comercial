package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;

import co.elgranero.controller.util.BDConnection;
import co.elgranero.net.Provider;
import co.elgranero.net.Purchase;
import co.elgranero.net.PurchaseProduct;
import co.elgranero.persistence.SqlInstructionsReader;

public final class InventoryManager {
    private SqlInstructionsReader sir;
    private Connection conn;
    public InventoryManager() throws IOException{
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
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}        
        return result;
    }
    public boolean registPurchase(int providerId, int paymentMethodId, String purchaseDate) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "COMPRAS");
            pSt.setInt(1, providerId);
            pSt.setInt(2, paymentMethodId);
            pSt.setString(3, purchaseDate);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (Exception e) {}
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
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    
    public ArrayList<Provider> obtainProviders() {
        ArrayList<Provider> providers = new ArrayList<Provider>();
        try{
            PreparedStatement pSt = sir.getConsultOf(conn, "PROVEEDORES");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                providers.add(new Provider(
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre_proveedor"),
                    rs.getString("documento_proveedor"),
                    rs.getString("celular_proveedor")
                ));
            }
            rs.close();
            pSt.close();
        }catch(SQLException|IOException e){}
        return providers;
    }
    public ArrayList<Purchase> obtainPurchases() {
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        try{
            PreparedStatement pSt = sir.getConsultOf(conn, "COMPRAS");
            ResultSet rs = pSt.executeQuery();
            Date tempDate = rs.getDate("fecha_compra");
            LocalDate date = LocalDate.of(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate());
            while (rs.next()) {
                purchases.add(new Purchase(
                    rs.getInt("id_compra"),
                    rs.getInt("id_proveedor"),
                    rs.getInt("id_forma_pago"),
                    date,
                    rs.getInt("numero_productos_comprados")
                ));
            }
            rs.close();
            pSt.close();
        }catch(SQLException|IOException e){}
        return purchases;
    }
    public ArrayList<PurchaseProduct> obtainPurchaseProducts(int purchaseId) {
        ArrayList<PurchaseProduct> purchaseProducts = new ArrayList<PurchaseProduct>();
        try{
            PreparedStatement pSt = sir.getConsultOf(conn, "PRODUCTOS_COMPRAS");
            pSt.setInt(1, purchaseId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                purchaseProducts.add(new PurchaseProduct(
                    rs.getInt("id_producto"),
                    rs.getInt("id_compra"),
                    rs.getInt("cantidad_prod_compra"),
                    rs.getDouble("precio_unitario_prod_compra"),
                    rs.getString("producto_compra")
                ));
            }
            rs.close();
            pSt.close();
        }catch(SQLException|IOException e){}
        return purchaseProducts;
    }

    public boolean modifyProvider(Provider provider) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PROVEEDORES");
            pSt.setInt(1, provider.getId());
            pSt.setString(2, provider.getName());
            pSt.setString(3, provider.getDocument());
            pSt.setString(4, provider.getCellphone());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean modifyPurchase(Purchase purchase) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "COMPRAS");
            pSt.setInt(1, purchase.getIdPurchase());
            pSt.setInt(2, purchase.getIdSupplier());
            pSt.setInt(3, purchase.getIdPaymentMethod());
            pSt.setString(4, purchase.getPurchaseDate().toString());
            pSt.setInt(5, purchase.getPurchasedProductsCount());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean modifyPurchaseProduct(PurchaseProduct purchaseProduct) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PRODUCTOS_COMPRAS");
            pSt.setInt(1, purchaseProduct.getIdProduct());
            pSt.setInt(2, purchaseProduct.getIdPurchase());
            pSt.setInt(3, purchaseProduct.getPurchaseProductQuantity());
            pSt.setDouble(4, purchaseProduct.getPurchaseProductUnitPrice());
            pSt.setString(5, purchaseProduct.getProductName());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    
    public boolean deleteProvider(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PROVEEDORES");
            pSt.setInt(1, id);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean deletePurchase(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "COMPRAS");
            pSt.setInt(1, id);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean deletePurchaseProduct(int productId, int purchaseId) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PRODUCTOS_COMPRAS");
            pSt.setInt(1, productId);
            pSt.setInt(2, purchaseId);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
}
