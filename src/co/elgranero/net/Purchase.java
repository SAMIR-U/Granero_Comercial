package co.elgranero.net;

import java.sql.Date;

public class Purchase {
    private Integer id;
    private Integer idSupplier;
    private Integer idPaymentMethod;
    private Date date;
    private String name;
    private String paymentMethodName;
    private Integer purchasedProductsCount;

    public Purchase() {
    }

    public Purchase(Integer idPurchase, Integer idSupplier, Integer idPaymentMethod, Date purchaseDate,
            String supplierName, String paymentMethodName, Integer purchasedProductsCount) {
        this.id = idPurchase;
        this.idSupplier = idSupplier;
        this.idPaymentMethod = idPaymentMethod;
        this.date = purchaseDate;
        this.name = supplierName;
        this.paymentMethodName = paymentMethodName;
        this.purchasedProductsCount = purchasedProductsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idPurchase) {
        this.id = idPurchase;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date purchaseDate) {
        this.date = purchaseDate;
    }

    public Integer getPurchasedProductsCount() {
        return purchasedProductsCount;
    }

    public void setPurchasedProductsCount(Integer purchasedProductsCount) {
        this.purchasedProductsCount = purchasedProductsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String supplierName) {
        this.name = supplierName;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    @Override
    public String toString() {
        return name;
    }

}
