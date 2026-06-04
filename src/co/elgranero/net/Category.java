package co.elgranero.net;

public class Category {
    private Integer id;
    private String name;

    public Category() {
    }

    public Category(Integer idCategory, String categoryName) {
        this.id = idCategory;
        this.name = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idCategory) {
        this.id = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }

    @Override
    public String toString() {
        return name;
    }
}