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

    // Constructor to initialize the database connection
 /*   public ServiceLogement(Connection con) {
        this.con = con;
    }*/

    public ServiceLogement() {
       con = MyDB.getinstance().getCon();//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ajouterLogement(Logement t) {
     String sql = "INSERT INTO logement (adrL, superfice, loyer, type, region, image) VALUES (?, ?, ?, ?, ?, ?)";
        try {
        // You can use PreparedStatement for better security against SQL injection
       
        PreparedStatement ps = con.prepareStatement(sql);
String type = t.getType().toString();
        // Set the values for the placeholders
        ps.setString(1, t.getAdr());
        ps.setInt(2, t.getSuperfice());
        ps.setInt(3, t.getLoyer());
        ps.setString(4, type);
        ps.setString(5, t.getRegion());

        // Assuming that the image data is stored as a Blob
        Blob imageBlob = t.getImage();
        ps.setBlob(6, imageBlob);

        // Execute the query
        ps.executeUpdate();

        // Close the PreparedStatement
     //   ps.close();
    } catch (SQLException ex) {
        System.out.println(ex);
        // Handle the exception as needed
    }
}

/*
    @Override
    public void supprimerLogement(String adr, int superficie, int loyer, String type, String region) {
       /*   String sql = "DELETE FROM logement WHERE idLogement = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, t.getId());
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Logement supprimé avec succès.");
        } else {
            System.out.println("Logement non trouvé avec l'adresse : " + t.getAdr());
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }*/
        // Call rechercheLogement to get the list of Logement entities that match the criteria
      /*  List<Logement> logementsToDelete = rechercheLogement(adr, superficie, loyer, type, region);

        for (Logement logement : logementsToDelete) {
            // Delete each matching Logement entity
            supprimerLogement(logement.getAdr(),logement.getSuperfice(),logement.getLoyer(),logement.getType(),logement.getRegion());
        }
    }*/
    
    
 public List<Logement> rechercheLogement(String adr, int superficie, int loyer, String type, String region) {
         String selectQuery = "SELECT * FROM logement WHERE " +
        "adr LIKE ? AND " +
        "superficie >= ? AND " +
        "loyer <= ? AND " +
        "type = ? AND " +
        "region = ?";

    try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
        preparedStatement.setString(1, "%" + adr + "%"); // Use % for a partial match
        preparedStatement.setInt(2, superficie);
        preparedStatement.setInt(3, loyer);
        preparedStatement.setString(4, type);
        preparedStatement.setString(5, region);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Logement> logements = new ArrayList<>();

        while (resultSet.next()) {
            Logement logement = new Logement();
            logement.setId(resultSet.getInt("id"));
            logement.setAdr(resultSet.getString("adr"));
            logement.setSuperfice(resultSet.getInt("superfice"));
            logement.setLoyer(resultSet.getInt("loyer"));
          //  String typeString = resultSet.getString("type");
            
            // Convert the string to the enum value
         //   String typeString = resultSet.getString("type");
//logement.setType(String.valueOf(typeString));

            
            logement.setRegion(resultSet.getString("region"));
            // Handle Blob image data
            // logement.setImage(resultSet.getBlob("image"));

            logements.add(logement);
        }

        return logements;
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., log or throw it
        return Collections.emptyList();
    }
}
    @Override
    public void modifierLogement(Logement t) {
          
         try {
        String sql = "UPDATE logement SET adrL=?, superfice=?, loyer=?, type=?, region=?, image=? WHERE idLogement=" + t.getId();

        // Use a PreparedStatement to avoid SQL injection
        PreparedStatement updateStatement;
        String type = t.getType().toString();
        updateStatement = con.prepareStatement(sql);
        updateStatement.setString(1, t.getAdr());
        updateStatement.setInt(2, t.getSuperfice());
        updateStatement.setInt(3, t.getLoyer());
        updateStatement.setString(4, type);
        updateStatement.setString(5, t.getRegion());

        // Handle updating Blob image data
        InputStream imageStream = t.getImage().getBinaryStream();
        updateStatement.setBinaryStream(6, imageStream, t.getImage().length());

        // Use a WHERE clause to specify the record to update
        updateStatement.executeUpdate();

        // Handle exceptions as needed
        int rowsUpdated = updateStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("La modification de logement : " + t.getId() + " a été effectuée avec succès.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., log or throw it
    }
    }

    

    @Override
    public List<Logement> affihcerLogement() {
        List<Logement> logements = new ArrayList<>();

    // Écrivez une requête SQL SELECT pour récupérer les logements depuis la base de données
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

            // Add the logement to the list
            logements.add(logement);
        }
    } catch (SQLException ex) {
        System.out.println("An SQL exception occurred: " + ex.getMessage());
        // Handle the exception further or rethrow it if necessary.
    } finally {
        // Close the ResultSet, Statement, and handle any exceptions here.
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
// Assuming you have a ResultSet object named resultSet

// Retrieve the string representation of type from the result set
String typeString = resultSet.getString("type");
logement.setType(type.valueOf(typeString));

// Set the enum value to the Logement object

                //logement.setType(resultSet.getString("type"));
                logement.setRegion(resultSet.getString("region"));
                // Handle Blob image data
                // logement.setImage(resultSet.getBlob("image"));

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
         String sql = "DELETE FROM logement WHERE id = ?";
         PreparedStatement ps = con.prepareStatement(sql);
         ps.setInt(1, t.getId());
         
         ps.executeUpdate();
         System.out.println("element supprimer");
        
     } catch (SQLException ex) {
     }
            
    }
   /*private int getTypeId(String typeName) {
int type = 0;
    String sql = "select idType from type where nomType=?";
    try {
        PreparedStatement ste = con.prepareStatement(sql); // Use PreparedStatement here
        ste.setString(1, typeName);
        ResultSet result = ste.executeQuery();
        if (result.next()) {
            type = result.getInt("idType");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return type;
}*/
    }
    
    

