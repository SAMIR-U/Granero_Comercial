package co.elgranero.net.reports;

public class CustomerPurchaseHistory {
    private int clientId;
    private String clientName;
    private String clientDocument;
    private int saleId;
    private String saleDate;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    private double saleDiscount;
    private String paymentMethodName;
    
    public CustomerPurchaseHistory(int clientId, String clientName, String clientDocument, int saleId, String saleDate,
            String productName, int quantity, double unitPrice, double subtotal, double saleDiscount,
            String paymentMethodName) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientDocument = clientDocument;
        this.saleId = saleId;
        this.saleDate = saleDate;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.saleDiscount = saleDiscount;
        this.paymentMethodName = paymentMethodName;
    }
    public CustomerPurchaseHistory() {
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getClientDocument() {
        return clientDocument;
    }
    public void setClientDocument(String clientDocument) {
        this.clientDocument = clientDocument;
    }
    public int getSaleId() {
        return saleId;
    }
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
    public String getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public double getSaleDiscount() {
        return saleDiscount;
    }
    public void setSaleDiscount(double saleDiscount) {
        this.saleDiscount = saleDiscount;
    }
    public String getPaymentMethodName() {
        return paymentMethodName;
    }
    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
    
}
