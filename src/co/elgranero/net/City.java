package co.elgranero.net;

public class City {
    private Integer id;
    private Integer idCountry;
    private String name;
    private String countryName;

    public City() {
    }

    public City(Integer idCity, Integer idCountry, String cityName, String countryName) {
        this.id = idCity;
        this.idCountry = idCountry;
        this.name = cityName;
        this.countryName = countryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idCity) {
        this.id = idCity;
    }

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String cityName) {
        this.name = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return name;
    }
}