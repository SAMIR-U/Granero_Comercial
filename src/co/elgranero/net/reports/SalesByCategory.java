package co.elgranero.net.reports;

public class SalesByCategory {
    private int categoryId;
    private String categoryName;
    private int totalSales;
    private int totalUnitsSold;
    private double totalRevenue;
    public SalesByCategory() {
    }
    public SalesByCategory(int categoryId, String categoryName, int totalSales, int totalUnitsSold,
            double totalRevenue) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalSales = totalSales;
        this.totalUnitsSold = totalUnitsSold;
        this.totalRevenue = totalRevenue;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public int getTotalSales() {
        return totalSales;
    }
    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }
    public int getTotalUnitsSold() {
        return totalUnitsSold;
    }
    public void setTotalUnitsSold(int totalUnitsSold) {
        this.totalUnitsSold = totalUnitsSold;
    }
    public double getTotalRevenue() {
        return totalRevenue;
    }
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
}
