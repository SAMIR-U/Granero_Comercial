package co.elgranero.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    
    public ArrayList<User> obtainUsers(){
        List<User> users = new ArrayList<User>();
        return null;
    }
    public ArrayList<Country> obtainCountries(){
        return null;
    }
    public ArrayList<City> obtainCities(int idCountry){
        return null;
    }

    public boolean modifyUser(int id, int idCiudad, String nombre, int idDoc, String telnum, String personType){
        return false;
    }
    public boolean modifyCountry(int id, String countryName){
        return false;
    }
    public boolean modifyCity(int id, String cityName){   
        return false;
    }

    public boolean deleteUser(int id){
        return false;
    }
    public boolean deleteCountry(int id){
        return false;
    }
    public boolean deleteCity(int id){   
        return false;
    }
}
