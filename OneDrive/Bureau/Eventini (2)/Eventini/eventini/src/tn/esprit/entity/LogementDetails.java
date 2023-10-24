/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 *
 * @author FADI
 */
public class LogementDetails {
     private int loyer;
    private int superfice;
    private String region;
    private String adrL;
private String image;

    public LogementDetails() {
    }
    public int getLoyer() {
        return loyer;
    }

    public LogementDetails(int loyer, int superfice, String region, String adrL, String image) {
        this.loyer = loyer;
        this.superfice = superfice;
        this.region = region;
        this.adrL = adrL;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLoyer(int loyer) {
        this.loyer = loyer;
    }

    public int getSuperfice() {
        return superfice;
    }

    public void setSuperfice(int superfice) {
        this.superfice = superfice;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAdrL() {
        return adrL;
    }

    public void setAdrL(String adrL) {
        this.adrL = adrL;
    }
//     public byte[] getImageData() {
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
}
