package co.elgranero.net;

import java.sql.Date;

public class Product {
    private Integer id;
    private Integer idSubcategory;
    private String name;
    private String description;
    private Date expirationDate;
    private String subcategoryName;
    private String categoryName;

    public Product() {
    }

    public Product(Integer idProduct, Integer idSubcategory, String productName, String productDescription,
            Date productExpirationDate, String subcategoryName, String categoryName) {
        this.id = idProduct;
        this.idSubcategory = idSubcategory;
        this.name = productName;
        this.description = productDescription;
        this.expirationDate = productExpirationDate;
        this.subcategoryName = subcategoryName;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idProduct) {
        this.id = idProduct;
    }

    public Integer getIdSubcategory() {
        return idSubcategory;
    }

    public void setIdSubcategory(Integer idSubcategory) {
        this.idSubcategory = idSubcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String productName) {
        this.name = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String productDescription) {
        this.description = productDescription;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date productExpirationDate) {
        this.expirationDate = productExpirationDate;
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

    @Override
    public String toString() {
        return name;
    }
}