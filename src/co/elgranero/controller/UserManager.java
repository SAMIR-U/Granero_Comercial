package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.elgranero.controller.util.BDConnection;
import co.elgranero.net.City;
import co.elgranero.net.Country;
import co.elgranero.net.User;
import co.elgranero.persistence.SqlInstructionsReader;

public class UserManager {
    public static final int CLIENT = 0;
    public static final int SELLER = 1;
    private final String[] personTypes = {"CLIENTE","VENDEDOR"};

    private SqlInstructionsReader sir;
    private Connection conn;
    public UserManager() throws IOException{
        this.sir = SqlInstructionsReader.getInstance();
        this.conn = BDConnection.getInstance().getConnection();
    }

    public boolean registUser(int idCiudad, String nombre, int idDoc, String telnum, int personType){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "PERSONAS");
            pSt.setInt(1, idCiudad);
            pSt.setString(2, nombre);
            pSt.setInt(3, idDoc);
            pSt.setString(4, telnum);
            pSt.setString(5, personTypes[personType]);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean registCountry(String countryName){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "CIUDADES");
            pSt.setString(1, countryName);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean registCity(int idCountry, String cityName){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "CIUDADES");
            pSt.setInt(1, idCountry);
            pSt.setString(2, cityName);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    
    public ArrayList<User> obtainUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try{
            PreparedStatement pSt = sir.getConsultOf(conn, "PERSONAS");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id_cliente"),
                    rs.getInt("id_ciudad"),
                    rs.getString("nombre_cliente"),
                    rs.getString("documento_cliente"),
                    rs.getString("telefono_cliente"),
                    rs.getString("tipo_persona"),
                    rs.getString("nombre_ciudad"),
                    rs.getString("nombre_pais")
                ));
            }
            rs.close();
            pSt.close();
        }catch(SQLException|IOException e){}
        return users;
    }
    public ArrayList<Country> obtainCountries(){
        ArrayList<Country> countries = new ArrayList<Country>();
        try{
            PreparedStatement pSt = sir.getConsultOf(conn, "PAISES");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                countries.add(new Country(
                    rs.getInt("id_pais"),
                    rs.getString("nombre_pais")
                ));
            }
            rs.close();
            pSt.close();
        }catch(SQLException|IOException e){}
        return countries;
    }
    public ArrayList<City> obtainCities(int idCountry){
        ArrayList<City> cities = new ArrayList<City>();
        try{
            PreparedStatement pSt = sir.getConsultOf(conn, "CIUDADES");
            pSt.setInt(1, idCountry);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                cities.add(new City(
                    rs.getInt("id_ciudad"),
                    rs.getInt("id_pais"),
                    rs.getString("nombre_ciudad"),
                    rs.getString("nombre_pais")
                ));
            }
            rs.close();
            pSt.close();
        }catch(SQLException|IOException e){}
        return cities;
    }

    public boolean modifyUser(User user){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PERSONAS");
            pSt.setInt(1, user.getIdCity());
            pSt.setString(2, user.getUserName());
            pSt.setString(3, user.getUserDocument());
            pSt.setString(4, user.getUserPhone());
            pSt.setString(5, user.getPersonType());
            pSt.setInt(6, user.getIdUser());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean modifyCountry(Country country){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "PAISES");
            pSt.setString(1, country.getName());
            pSt.setInt(2, country.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean modifyCity(City city){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "CIUDADES");
            pSt.setString(1, city.getCityName());
            pSt.setInt(2, city.getIdCountry());
            pSt.setInt(3, city.getIdCity());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (Exception e) {}
        return result;
    }

    public boolean deleteUser(int id){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PERSONAS");
            pSt.setInt(1, id);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean deleteCountry(int id){
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PAISES");
            pSt.setInt(1, id);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
    public boolean deleteCity(int id){ 
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "CIUDADES");
            pSt.setInt(1, id);
            result = pSt.execute();
            pSt.close();
        } catch (Exception e) {}
        return result;
    }
}
