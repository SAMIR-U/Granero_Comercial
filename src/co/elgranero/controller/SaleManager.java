package co.elgranero.controller;

import java.util.ArrayList;
import co.elgranero.net.PaymentMethod;
import co.elgranero.net.Sale;
import co.elgranero.net.SaleProduct;

public class SaleManager {
    public boolean registPaymentMethod(String paymentMethodName) {
        return false;
    }
    public boolean registSale(int paymentMethodId, int clientId, String saleDate, double saleDiscount) {
        return false;
    }
    public boolean registSaleProduct(int saleId, int productId, int quantity, double unitPrice) {
        return false;
    }
    
    public ArrayList<PaymentMethod> obtainPaymentMethods() {
        return null;
    }
    public ArrayList<Sale> obtainSales() {
        return null;
    }
    public ArrayList<SaleProduct> obtainSaleProducts(int saleId) {
        return null;
    }
    
    public boolean modifyPaymentMethod(PaymentMethod paymentMethod) {
        return false;
    }
    public boolean modifySale(Sale sale) {
        return false;
    }
    public boolean modifySaleProduct(SaleProduct saleProduct) {
        return false;
    }

    public boolean deletePaymentMethod(int id) {
        return false;
    }
    public boolean deleteSale(int id) {
        return false;
    }
    public boolean deleteSaleProduct(int saleId, int productId) {
        return false;
    }
}
