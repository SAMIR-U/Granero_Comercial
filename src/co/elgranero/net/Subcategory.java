package co.elgranero.net;

public class Subcategory {
    private Integer id;
    private Integer idCategory;
    private String name;
    private String categoryName;

    public Subcategory() {
    }

    public Subcategory(Integer idSubcategory, Integer idCategory, String subcategoryName, String categoryName) {
        this.id = idSubcategory;
        this.idCategory = idCategory;
        this.name = subcategoryName;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idSubcategory) {
        this.id = idSubcategory;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String subcategoryName) {
        this.name = subcategoryName;
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