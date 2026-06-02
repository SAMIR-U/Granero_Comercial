package co.elgranero.net;

public class Purchase {
    private int id;
    private int providerId;
    private int paymentMethodId;
    private String purchaseDate;

    public Purchase() {}

    public Purchase(int id, int providerId, int paymentMethodId, String purchaseDate) {
        this.id = id;
        this.providerId = providerId;
        this.paymentMethodId = paymentMethodId;
        this.purchaseDate = purchaseDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProviderId() { return providerId; }
    public void setProviderId(int providerId) { this.providerId = providerId; }

    public int getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(int paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
}
