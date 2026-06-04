package co.elgranero.net;

public class Provider {
    private int id;
    private String name;
    private String document;
    private String cellphone;

    public Provider() {
    }

    public Provider(int id, String name, String document, String cellphone) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.cellphone = cellphone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Override
    public String toString() {
        return name;
    }
}
