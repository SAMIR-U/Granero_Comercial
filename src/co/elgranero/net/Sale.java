package co.elgranero.net;

import java.sql.Date;

public class Sale {
    private Integer idSale;
    private Integer idPaymentMethod;
    private Integer idClient;
    private Date saleDate;
    private Double saleDiscount;
    private Integer productsCount;
    public Sale() {
    }
    public Sale(Integer idSale, Integer idPaymentMethod, Integer idClient, Date saleDate, Double saleDiscount,
            Integer productsCount) {
        this.idSale = idSale;
        this.idPaymentMethod = idPaymentMethod;
        this.idClient = idClient;
        this.saleDate = saleDate;
        this.saleDiscount = saleDiscount;
        this.productsCount = productsCount;
    }
    public Integer getIdSale() {
        return idSale;
    }
    public void setIdSale(Integer idSale) {
        this.idSale = idSale;
    }
    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }
    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }
    public Integer getIdClient() {
        return idClient;
    }
    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }
    public Date getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
    public Double getSaleDiscount() {
        return saleDiscount;
    }
    public void setSaleDiscount(Double saleDiscount) {
        this.saleDiscount = saleDiscount;
    }
    public Integer getProductsCount() {
        return productsCount;
    }
    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }
    
}
