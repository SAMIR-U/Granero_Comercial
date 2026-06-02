package co.elgranero.net;

public class Sale {
    private int id;
    private int paymentMethodId;
    private int clientId;
    private String saleDate;
    private double discount;

    public Sale() {}

    public Sale(int id, int paymentMethodId, int clientId, String saleDate, double discount) {
        this.id = id;
        this.paymentMethodId = paymentMethodId;
        this.clientId = clientId;
        this.saleDate = saleDate;
        this.discount = discount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(int paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public String getSaleDate() { return saleDate; }
    public void setSaleDate(String saleDate) { this.saleDate = saleDate; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
}
