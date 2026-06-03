package co.elgranero.net;

import java.sql.Date;

public class Purchase {
    private Integer idPurchase;
    private Integer idSupplier;
    private Integer idPaymentMethod;
    private Date purchaseDate;
    private String supplierName;
    private String paymentMethodName;
    private Integer purchasedProductsCount;

    public Purchase() {
    }
    public Purchase(Integer idPurchase, Integer idSupplier, Integer idPaymentMethod, Date purchaseDate,
            String supplierName, String paymentMethodName, Integer purchasedProductsCount) {
        this.idPurchase = idPurchase;
        this.idSupplier = idSupplier;
        this.idPaymentMethod = idPaymentMethod;
        this.purchaseDate = purchaseDate;
        this.supplierName = supplierName;
        this.paymentMethodName = paymentMethodName;
        this.purchasedProductsCount = purchasedProductsCount;
    }

    public Integer getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(Integer idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Integer getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Integer idSupplier) {
        this.idSupplier = idSupplier;
    }

    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getPurchasedProductsCount() {
        return purchasedProductsCount;
    }

    public void setPurchasedProductsCount(Integer purchasedProductsCount) {
        this.purchasedProductsCount = purchasedProductsCount;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

}
