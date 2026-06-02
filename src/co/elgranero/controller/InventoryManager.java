package co.elgranero.controller;

import java.util.ArrayList;

import co.elgranero.net.Provider;
import co.elgranero.net.Purchase;
import co.elgranero.net.PurchaseProduct;

public final class InventoryManager {
    public boolean registProvider(String name, String document, String cellphone) {
        return false;
    }
    public boolean registPurchase(int providerId, int paymentMethodId, String purchaseDate) {
        return false;
    }
    public boolean registPurchaseProduct(int productId, int purchaseId, int quantity, double unitPrice) {
        return false;
    }
    
    public ArrayList<Provider> obtainProviders() {
        return null;
    }
    public ArrayList<Purchase> obtainPurchases() {
        return null;
    }
    public ArrayList<PurchaseProduct> obtainPurchaseProducts(int purchaseId) {
        return null;
    }

    public boolean modifyProvider(Provider provider) {
        return false;
    }
    public boolean modifyPurchase(Purchase purchase) {
        return false;
    }
    public boolean modifyPurchaseProduct(PurchaseProduct purchaseProduct) {
        return false;
    }
    
    
    
    public boolean deleteProvider(int id) {
        return false;
    }
    public boolean deletePurchase(int id) {
        return false;
    }
    public boolean deletePurchaseProduct(int productId, int purchaseId) {
        return false;
    }
}
