/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import models.Logement;
import models.type;
import tools.MyDB;

/**
 *
 * @author FADI
 */
public class ServiceLogement implements LogService<Logement> {
 private  Connection con ;
    Statement ste;
    private FileInputStream fs;




    public ServiceLogement() {
       con = MyDB.getinstance().getCon();
    }

    @Override
    public void ajouterLogement(Logement t) {
     String sql = "INSERT INTO logement (adrL, superfice, loyer, type, region, image) VALUES (?, ?, ?, ?, ?, ?)";
        try {
       
        PreparedStatement ps = con.prepareStatement(sql);
String type = t.getType().toString();
        // Set the values for the placeholders
        ps.setString(1, t.getAdr());
        ps.setInt(2, t.getSuperfice());
        ps.setInt(3, t.getLoyer());
        ps.setString(4, type);
        ps.setString(5, t.getRegion());

    
        ps.setString(6, t.getImage());
        ps.executeUpdate();

    } catch (SQLException ex) {
        System.out.println(ex);
        
    }
}


    
public List<Logement> rechercheLogement(String critere) {
    String selectQuery = "SELECT * FROM logement WHERE adrL LIKE ?";

    try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
        preparedStatement.setString(1, "%" + critere + "%"); // Utilisez % pour une recherche partielle

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Logement> logements = new ArrayList<>();

        while (resultSet.next()) {
            Logement logement = new Logement();
            logement.setId(resultSet.getInt("idLogement"));
            logement.setAdr(resultSet.getString("adrL"));
            logement.setSuperfice(resultSet.getInt("superfice"));
            logement.setLoyer(resultSet.getInt("loyer"));
            logement.setRegion(resultSet.getString("region"));
            logement.setType(type.valueOf(resultSet.getString("type")));
            logement.setImage(resultSet.getString("image"));

            logements.add(logement);

        }

        return logements;
    } catch (SQLException e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}
public List<Logement> rechercheLogement2(String adrCritere, String regionCritere) {
    String selectQuery = "SELECT * FROM logement WHERE adrL LIKE ? AND region LIKE ?";

    try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
        preparedStatement.setString(1, "%" + adrCritere + "%"); // Utilize % for partial search
        preparedStatement.setString(2, "%" + regionCritere + "%"); // Utilize % for partial search

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Logement> logements = new ArrayList<>();

        while (resultSet.next()) {
            Logement logement = new Logement();
            logement.setId(resultSet.getInt("idLogement"));
            logement.setAdr(resultSet.getString("adrL"));
            logement.setSuperfice(resultSet.getInt("superfice"));
            logement.setLoyer(resultSet.getInt("loyer"));
            logement.setRegion(resultSet.getString("region"));
            logement.setType(type.valueOf(resultSet.getString("type")));
            logement.setImage(resultSet.getString("image"));
           

            logements.add(logement);
        }

        return logements;
    } catch (SQLException e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}

   @Override
   public void modifierLogement(Logement t) {
          
         try {
        String sql = "UPDATE logement SET adrL=?, superfice=?, loyer=?, type=?, region=?, image=? WHERE idLogement=?" ;

        PreparedStatement updateStatement ;
        String type = t.getType().toString();
        updateStatement = con.prepareStatement(sql);
        updateStatement.setString(1, t.getAdr());
        updateStatement.setInt(2, t.getSuperfice());
        updateStatement.setInt(3, t.getLoyer());
        updateStatement.setString(4, type);
        updateStatement.setString(5, t.getRegion());

      
        
        updateStatement.setString(6,  t.getImage());
        updateStatement.setInt(7, t.getId());

       
        updateStatement.executeUpdate();

        int rowsUpdated = updateStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("La modification de logement : " + t.getId() + " a été effectuée avec succès.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    

    @Override
    public List<Logement> affihcerLogement() {
        List<Logement> logements = new ArrayList<>();

    String sql = "SELECT * FROM logement";

    try {
       if (con == null) {
            System.out.println("Database connection is not initialized.");
            return logements; // Return an empty list or handle this condition accordingly.
       }

        ste = con.createStatement();
        ResultSet resultSet = ste.executeQuery(sql);

        while (resultSet.next()) {
            Logement logement = new Logement();
            logement.setId(resultSet.getInt("idLogement"));
            logement.setAdr(resultSet.getString("adrL"));
            logement.setSuperfice(resultSet.getInt("superfice"));
            logement.setLoyer(resultSet.getInt("loyer"));
            String typeString = resultSet.getString("type");
            logement.setType(type.valueOf(typeString));
            logement.setRegion(resultSet.getString("region"));
            logement.setImage(resultSet.getString("image"));

            // Add the logement to the list
            logements.add(logement);
        }
    } catch (SQLException ex) {
        System.out.println("An SQL exception occurred: " + ex.getMessage());
    } finally {
    }

    return logements;

    }
     public List<Logement> getAllLogements() {
        List<Logement> logements = new ArrayList<>();
        String sql = "SELECT * FROM logement";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery(); 

            while (resultSet.next()) {
                Logement logement = new Logement();
                logement.setId(resultSet.getInt("id"));
                logement.setAdr(resultSet.getString("adr"));
                logement.setSuperfice(resultSet.getInt("superfice"));
                logement.setLoyer(resultSet.getInt("loyer"));

String typeString = resultSet.getString("type");
logement.setType(type.valueOf(typeString));


                logement.setRegion(resultSet.getString("region"));
                // Handle Blob image data

                logements.add(logement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., log or throw it
        }

        return logements;
    }

 
    @Override
   public void supprimerLogement(Logement t) {
         

        
     try {
         String sql = "DELETE FROM logement WHERE idLogement = ?";
         PreparedStatement ps = con.prepareStatement(sql);
         ps.setInt(1, t.getId());
         
         ps.executeUpdate();
         System.out.println("element supprimer");
        
     } catch (SQLException ex) {
     }
            
    }
     public Logement getLogementById(int id) throws SQLException {
        String selectQuery = "SELECT * FROM logement WHERE idLogement = ?";
        Logement logement = null;

        try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                logement = new Logement();
                logement.setId(resultSet.getInt("idLogement"));
                logement.setAdr(resultSet.getString("adrL"));
                logement.setSuperfice(resultSet.getInt("superfice"));
                logement.setLoyer(resultSet.getInt("loyer"));
                String typeString = resultSet.getString("type");
                logement.setType(type.valueOf(typeString));
                logement.setRegion(resultSet.getString("region"));
                logement.setImage(resultSet.getString("image"));
            }
        }

        return logement;
    }
   
   public int retrieveLogementIdByAdr(String address) {
    try {
        String sql = "SELECT idLogement FROM logement WHERE adrL = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, address);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getInt("idLogement");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
   
   
    }
    
    

