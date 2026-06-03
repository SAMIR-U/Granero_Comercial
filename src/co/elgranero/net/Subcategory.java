package co.elgranero.net;

public class Subcategory {
    private Integer idSubcategory;
    private Integer idCategory;
    private String subcategoryName;
    private String categoryName;
    public Subcategory() {
    }
    public Subcategory(Integer idSubcategory, Integer idCategory, String subcategoryName, String categoryName) {
        this.idSubcategory = idSubcategory;
        this.idCategory = idCategory;
        this.subcategoryName = subcategoryName;
        this.categoryName = categoryName;
    }
    public Integer getIdSubcategory() {
        return idSubcategory;
    }
    public void setIdSubcategory(Integer idSubcategory) {
        this.idSubcategory = idSubcategory;
    }
    public Integer getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
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
