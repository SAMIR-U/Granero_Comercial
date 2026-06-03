package co.elgranero.net;

public class SaleProduct {
    private Integer idSale;
    private Integer idProduct;
    private Integer quantity;
    private Double unitPrice;
    private String productName;
    public SaleProduct() {
    }
    public SaleProduct(Integer idSale, Integer idProduct, Integer quantity, Double unitPrice, String productName) {
        this.idSale = idSale;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productName = productName;
    }
    public Integer getIdSale() {
        return idSale;
    }
    public void setIdSale(Integer idSale) {
        this.idSale = idSale;
    }
    public Integer getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
}
