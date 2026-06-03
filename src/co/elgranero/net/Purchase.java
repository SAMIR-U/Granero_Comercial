package co.elgranero.net;

import java.time.LocalDate;

public class Purchase {
    private Integer idPurchase;
    private Integer idSupplier;
    private Integer idPaymentMethod;
    private LocalDate purchaseDate;
    private Integer purchasedProductsCount;
    public Purchase() {
    }
    public Purchase(Integer idPurchase, Integer idSupplier, Integer idPaymentMethod, LocalDate purchaseDate, Integer purchasedProductsCount) {
        this.idPurchase = idPurchase;
        this.idSupplier = idSupplier;
        this.idPaymentMethod = idPaymentMethod;
        this.purchaseDate = purchaseDate;
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
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public Integer getPurchasedProductsCount() {
        return purchasedProductsCount;
    }
    public void setPurchasedProductsCount(Integer purchasedProductsCount) {
        this.purchasedProductsCount = purchasedProductsCount;
    }
    
}
