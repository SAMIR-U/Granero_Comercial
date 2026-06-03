package co.elgranero.net;

public class Category {
    private Integer idCategory;
    private String categoryName;
    public Category() {
    }
    public Category(Integer idCategory, String categoryName) {
        this.idCategory = idCategory;
        this.categoryName = categoryName;
    }
    public Integer getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}
