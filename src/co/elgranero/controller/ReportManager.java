package co.elgranero.controller;

import java.util.ArrayList;

import co.elgranero.net.User;
import co.elgranero.net.reports.*;

public class ReportManager {
    public ArrayList<SalesComparison> compareSalesOverTime(
        String period1Start, String period1End, 
        String period2Start, String period2End
    ) {
        return null;
    }

    public ArrayList<CustomerPurchaseHistory> obtainCustomerPurchaseHistory(int clientId) {
        return null;
    }

    public ArrayList<CustomerPurchaseVolume> obtainCustomerPurchaseVolume(int limitRows) {
        return null;
    }

    public ArrayList<SalesByCustomer> obtainSalesByCustomer(int clientId) {
        return null;
    }

    public ArrayList<SalesByProduct> obtainSalesByProduct(int productId) {
        return null;
    }

    public ArrayList<SalesByCategory> obtainSalesByProductCategory(int categoryId) {
        return null;
    }

    public ArrayList<TopSellingProduct> obtainTopSellingProducts(int limitRows) {
        return null;
    }

    public User obtainSellerByDocument(String document) {
        return null;
    }
}
