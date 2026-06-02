package co.elgranero.net;

public class PurchaseProduct {
    private int productId;
    private int purchaseId;
    private int quantity;
    private double unitPrice;

    public PurchaseProduct() {}

    public PurchaseProduct(int productId, int purchaseId, int quantity, double unitPrice) {
        this.productId = productId;
        this.purchaseId = purchaseId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getPurchaseId() { return purchaseId; }
    public void setPurchaseId(int purchaseId) { this.purchaseId = purchaseId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}
