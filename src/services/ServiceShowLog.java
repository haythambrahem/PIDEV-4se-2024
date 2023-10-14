/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import interfaces.AccueilController;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.LogementDetails;
import tools.MyDB;

/**
 *
 * @author FADI
 */
public class ServiceShowLog {
  private  Connection con;
    Statement ste;
    private FileInputStream fs;


    public ServiceShowLog() {
       con = MyDB.getinstance().getCon(); 
    }
    
     public int getAvailableLogementCount() {
        int count = 0;
        try {
            // Perform a database query to get the count of available logements.
            String sql = "SELECT COUNT(*) FROM logement";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                count = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
     

    public LogementDetails getAvailableLogementDetails() {
        LogementDetails details = new LogementDetails();
        try {
            // Perform a database query to get logement details.
            String sql = "SELECT loyer, superfice, region, adrL FROM logement WHERE idLogement = 1";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                details.setLoyer(result.getInt("loyer"));
                details.setSuperfice(result.getInt("superfice"));
                details.setRegion(result.getString("region"));
                details.setAdrL(result.getString("adrL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }
     public  int getPositionByAdr(String adr) throws SQLException {
        String sql = "select idLogement from logement where adrL = ?";
       
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, adr);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    return result.getInt("idLogement");
                }
            }
      
        return 0;
    }

    public  void loadNextLogementData(int position, AccueilController controller) throws SQLException {
        String sql = "select loyer, superfice, region, adrL, image " +
                     "from logement " +
                     "where idLogement not in (select logement from location) " +
                     "and idLogement > ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, position);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    int loyer = result.getInt("loyer");
                    String region = result.getString("region");
                    String adrL = result.getString("adrL");
                     int superfice = result.getInt("superfice");
                    Blob blob = result.getBlob("image");
                    byte[] byteImg = blob.getBytes(1, (int) blob.length());

                    controller.updateUI(loyer, region, adrL, superfice, byteImg);
                }
            }
        }
    }
    public void loadPreviousLogementData(int position, AccueilController controller) {
    String sql = "select loyer, superfice, region, adrL, image " +
                 "from logement " +
                 "where idLogement not in (select logement from location) " +
                 "and idLogement < ?";
    int loyer = 0;
    int superfice;
    byte byteImg[];
    Blob blob;

    try (PreparedStatement st = con.prepareStatement(sql)) {
        st.setInt(1, position);
        try (ResultSet result = st.executeQuery()) {
            if (result.next()) {
                loyer = result.getInt("loyer");
                String region = result.getString("region");
                String adrL = result.getString("adrL");
                superfice = result.getInt("superfice");
                blob = result.getBlob("image");
                byteImg = blob.getBytes(1, (int) blob.length());
                controller.updateUI(loyer, region, adrL, superfice, byteImg);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
}