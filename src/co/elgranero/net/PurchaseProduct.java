package co.elgranero.net;

public class PurchaseProduct {
    private Integer idProduct;
    private Integer idPurchase;
    private Integer purchaseProductQuantity;
    private Double purchaseProductUnitPrice;
    private String productName;

    public PurchaseProduct() {
    }

    public PurchaseProduct(Integer idProduct, Integer idPurchase, Integer purchaseProductQuantity,
            Double purchaseProductUnitPrice, String productName) {
        this.idProduct = idProduct;
        this.idPurchase = idPurchase;
        this.purchaseProductQuantity = purchaseProductQuantity;
        this.purchaseProductUnitPrice = purchaseProductUnitPrice;
        this.productName = productName;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(Integer idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Integer getPurchaseProductQuantity() {
        return purchaseProductQuantity;
    }

    public void setPurchaseProductQuantity(Integer purchaseProductQuantity) {
        this.purchaseProductQuantity = purchaseProductQuantity;
    }

    public Double getPurchaseProductUnitPrice() {
        return purchaseProductUnitPrice;
    }

    public void setPurchaseProductUnitPrice(Double purchaseProductUnitPrice) {
        this.purchaseProductUnitPrice = purchaseProductUnitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return productName;
    }

}
