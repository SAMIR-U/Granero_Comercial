package co.elgranero.net;

public class User {
    private Integer id;
    private Integer idCity;
    private String name;
    private String document;
    private String phone;
    private String type;
    private String cityName;
    private String countryName;

    public User() {
    }

    public User(Integer idClient, Integer idCity, String clientName, String clientDocument, String clientPhone,
            String personType, String cityName, String countryName) {
        this.id = idClient;
        this.idCity = idCity;
        this.name = clientName;
        this.document = clientDocument;
        this.phone = clientPhone;
        this.type = personType;
        this.cityName = cityName;
        this.countryName = countryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idClient) {
        this.id = idClient;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String clientName) {
        this.name = clientName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String clientDocument) {
        this.document = clientDocument;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String clientPhone) {
        this.phone = clientPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String personType) {
        this.type = personType;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
