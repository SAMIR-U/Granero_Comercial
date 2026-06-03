package co.elgranero.net;

public class User {
    private Integer idUser;
    private Integer idCity;
    private String userName;
    private String userDocument;
    private String userPhone;
    private String personType;
    private String cityName;
    private String countryName;
    public User() {
    }
    public User(Integer idClient, Integer idCity, String clientName, String clientDocument, String clientPhone,
            String personType, String cityName, String countryName) {
        this.idUser = idClient;
        this.idCity = idCity;
        this.userName = clientName;
        this.userDocument = clientDocument;
        this.userPhone = clientPhone;
        this.personType = personType;
        this.cityName = cityName;
        this.countryName = countryName;
    }
    public Integer getIdUser() {
        return idUser;
    }
    public void setIdUser(Integer idClient) {
        this.idUser = idClient;
    }
    public Integer getIdCity() {
        return idCity;
    }
    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String clientName) {
        this.userName = clientName;
    }
    public String getUserDocument() {
        return userDocument;
    }
    public void setUserDocument(String clientDocument) {
        this.userDocument = clientDocument;
    }
    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String clientPhone) {
        this.userPhone = clientPhone;
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
