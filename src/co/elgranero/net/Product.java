package co.elgranero.net;

import java.sql.Date;

public class Product {
    private Integer idProduct;
    private Integer idSubcategory;
    private String productName;
    private String productDescription;
    private Date productExpirationDate;
    private String subcategoryName; 
    private String categoryName;
    public Product() {
    }
    public Product(Integer idProduct, Integer idSubcategory, String productName, String productDescription,
            Date productExpirationDate, String subcategoryName, String categoryName) {
        this.idProduct = idProduct;
        this.idSubcategory = idSubcategory;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productExpirationDate = productExpirationDate;
        this.subcategoryName = subcategoryName;
        this.categoryName = categoryName;
    }
    public Integer getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }
    public Integer getIdSubcategory() {
        return idSubcategory;
    }
    public void setIdSubcategory(Integer idSubcategory) {
        this.idSubcategory = idSubcategory;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public Date getProductExpirationDate() {
        return productExpirationDate;
    }
    public void setProductExpirationDate(Date productExpirationDate) {
        this.productExpirationDate = productExpirationDate;
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
    
}
