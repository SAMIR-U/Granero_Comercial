package co.elgranero.net;

import java.sql.Date;

public class Sale {
    private Integer id;
    private Integer idPaymentMethod;
    private Integer idClient;
    private String clientName;
    private Date date;
    private Double discount;
    private Integer productsCount;

    public Sale() {
    }

    public Sale(Integer id, Integer idPaymentMethod, Integer idClient, String clientName, Date date, Double discount,
            Integer productsCount) {
        this.id = id;
        this.idPaymentMethod = idPaymentMethod;
        this.idClient = idClient;
        this.clientName = clientName;
        this.date = date;
        this.discount = discount;
        this.productsCount = productsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

}
