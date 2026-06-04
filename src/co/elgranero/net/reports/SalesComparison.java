package co.elgranero.net.reports;

public class SalesComparison {
    private String period;
    private int totalSales;
    private int totalUnitsSold;
    private double totalRevenue;

    public SalesComparison(String period, int totalSales, int totalUnitsSold, double totalRevenue) {
        this.period = period;
        this.totalSales = totalSales;
        this.totalUnitsSold = totalUnitsSold;
        this.totalRevenue = totalRevenue;
    }

    public SalesComparison() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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
