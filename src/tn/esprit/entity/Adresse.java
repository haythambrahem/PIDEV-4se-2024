/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

/**
 *
 * @author hayth
 */

public class Adresse {
    private int id;
    private String street;
    private String city;
    private String postalCode;
    private String country;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Adresse(int id) {
        this.id = id;
    }

    
    
    public Adresse() {
    }

    public Adresse(int id, String street, String city, String postalCode, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    
    
    public Adresse(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    
}

