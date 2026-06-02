package co.elgranero.net.reports;

public class SalesByCustomer {
    private int clientId;
    private String clientName;
    private String clientDocument;
    private int totalSales;
    private int totalUnitsPurchased;
    private double totalSpent;
    public SalesByCustomer() {
    }
    public SalesByCustomer(int clientId, String clientName, String clientDocument, int totalSales,
            int totalUnitsPurchased, double totalSpent) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientDocument = clientDocument;
        this.totalSales = totalSales;
        this.totalUnitsPurchased = totalUnitsPurchased;
        this.totalSpent = totalSpent;
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
    public int getTotalSales() {
        return totalSales;
    }
    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }
    public int getTotalUnitsPurchased() {
        return totalUnitsPurchased;
    }
    public void setTotalUnitsPurchased(int totalUnitsPurchased) {
        this.totalUnitsPurchased = totalUnitsPurchased;
    }
    public double getTotalSpent() {
        return totalSpent;
    }
    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }
    
}
