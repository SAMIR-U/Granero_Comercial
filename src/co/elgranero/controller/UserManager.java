package co.elgranero.controller;

import java.util.ArrayList;

import co.elgranero.net.City;
import co.elgranero.net.Country;
import co.elgranero.net.User;

public class UserManager {
    public boolean registUser(int idCiudad, String nombre, int idDoc, String telnum, String personType){
        return false;
    }
    public boolean registCountry(String countryName){
        return false;
    }
    public boolean registCity(String cityName){   
        return false;
    }
    
    public ArrayList<User> obtainUsers(){
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
}
