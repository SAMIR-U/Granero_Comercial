package co.elgranero.net;

public class ProductPresentation {
    private int presentationId;
    private int productId;
    private double price;

    public ProductPresentation() {}

    public ProductPresentation(int presentationId, int productId, double price) {
        this.presentationId = presentationId;
        this.productId = productId;
        this.price = price;
    }

    public int getPresentationId() { return presentationId; }
    public void setPresentationId(int presentationId) { this.presentationId = presentationId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
