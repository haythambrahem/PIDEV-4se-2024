/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author FADI
 */

    public class Logement {
    private int id;
    private String adr;
    private int superfice;
    private int loyer;
    type type;
    private String region;
   private String image;

    // Constructors

    // Default constructor
    public Logement() {
    }

    public Logement(int id, int superfice, int loyer, String region) {
        this.id = id;
        this.superfice = superfice;
        this.loyer = loyer;
        this.region = region;
    }

   

    public String getImage() {
        return image;
    }

    public Logement(int id, String adr, int superfice, int loyer, type type, String region, String image) {
        this.id = id;
        this.adr = adr;
        this.superfice = superfice;
        this.loyer = loyer;
        this.type = type;
        this.region = region;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Logement(int id, String adr, int superfice, int loyer, type type) {
        this.id = id;
        this.adr = adr;
        this.superfice = superfice;
        this.loyer = loyer;
        this.type = type;
    }

    
   

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public int getSuperfice() {
        return superfice;
    }

    public void setSuperfice(int superfice) {
        this.superfice = superfice;
    }

    public int getLoyer() {
        return loyer;
    }

    public void setLoyer(int loyer) {
        this.loyer = loyer;
    }

    public type getType() {
        return type;
    }

    public void setType(type type) {
        this.type = type;
    }

   

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Logement logement = (Logement) o;
    return id == logement.id &&
           superfice == logement.superfice &&
           loyer == logement.loyer &&
           Objects.equals(adr, logement.adr) &&
           Objects.equals(type, logement.type) &&
           Objects.equals(region, logement.region);
}

    public Logement(int id, String adr, int superfice, String region) {
        this.id = id;
        this.adr = adr;
        this.superfice = superfice;
        this.region = region;
    }
    
@Override
public String toString() {
    return " l'adresse est " + adr + 
           ", avec une superfice de " + superfice +
           ", son loyer est " + loyer +
           ", de type " + type + 
           ", dans region " + region ;
}
// public byte[] getImageData() {
//        if (image == null) {
//            return null; // Return null if no image data is present
//        }
//
//        try {
//            // Convert the Blob to a byte array
//            InputStream inputStream = image.getBinaryStream();
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[4096];
//            int bytesRead = -1;
//
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//
//            return outputStream.toByteArray();
//        } catch (SQLException | IOException e) {
//            e.printStackTrace(); // Handle exceptions as needed
//            return null; // Return null in case of an error
//        }
//    }
    // Additional methods if needed
}
