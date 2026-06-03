package co.elgranero.net;

public class User {
    private Integer idClient;
    private Integer idCity;
    private String clientName;
    private String clientDocument;
    private String clientPhone;
    private String personType;
    private String cityName;
    private String countryName;
    public User() {
    }
    public User(Integer idClient, Integer idCity, String clientName, String clientDocument, String clientPhone,
            String personType, String cityName, String countryName) {
        this.idClient = idClient;
        this.idCity = idCity;
        this.clientName = clientName;
        this.clientDocument = clientDocument;
        this.clientPhone = clientPhone;
        this.personType = personType;
        this.cityName = cityName;
        this.countryName = countryName;
    }
    public Integer getIdClient() {
        return idClient;
    }
    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }
    public Integer getIdCity() {
        return idCity;
    }
    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getClientDocument() {
        return clientDocument;
    }
    public void setClientDocument(String clientDocument) {
        this.clientDocument = clientDocument;
    }
    public String getClientPhone() {
        return clientPhone;
    }
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
    public String getPersonType() {
        return personType;
    }
    public void setPersonType(String personType) {
        this.personType = personType;
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
