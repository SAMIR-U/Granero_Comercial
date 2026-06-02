package co.elgranero.net;

public class Product {
    private int id;
    private int subcategoryId;
    private String name;
    private String description;
    private String expirationDate;

    public Product() {}

    public Product(int id, int subcategoryId, String name, String description, String expirationDate) {
        this.id = id;
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSubcategoryId() { return subcategoryId; }
    public void setSubcategoryId(int subcategoryId) { this.subcategoryId = subcategoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }
}
