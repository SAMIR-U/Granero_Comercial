package co.elgranero.net;

public class City {
    private Integer idCity;
    private Integer idCountry;
    private String cityName;
    private String countryName;
    
    public City() {
    }
    public City(Integer idCity, Integer idCountry, String cityName, String countryName) {
        this.idCity = idCity;
        this.idCountry = idCountry;
        this.cityName = cityName;
        this.countryName = countryName;
    }
    public Integer getIdCity() {
        return idCity;
    }
    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }
    public Integer getIdCountry() {
        return idCountry;
    }
    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
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
