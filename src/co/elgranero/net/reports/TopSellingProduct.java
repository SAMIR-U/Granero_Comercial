package co.elgranero.net.reports;

public class TopSellingProduct {
    private int productId;
    private String productName;
    private String subcategoryName;
    private String categoryName;
    private int totalUnitsSold;
    private double totalRevenue;
    public TopSellingProduct() {
    }
    public TopSellingProduct(int productId, String productName, String subcategoryName, String categoryName,
            int totalUnitsSold, double totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.subcategoryName = subcategoryName;
        this.categoryName = categoryName;
        this.totalUnitsSold = totalUnitsSold;
        this.totalRevenue = totalRevenue;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getSubcategoryName() {
        return subcategoryName;
    }
    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
