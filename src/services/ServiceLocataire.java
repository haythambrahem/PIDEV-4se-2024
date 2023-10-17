/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.Locataire;
import models.Logement;
import models.type;
import tools.MyDB;

/**
 *
 * @author fadi saidi
 */
public class ServiceLocataire {
     private  Connection con;
    Statement ste;
    private FileInputStream fs;
    
    public ServiceLocataire() {
       con = MyDB.getinstance().getCon();//To change body of generated methods, choose Tools | Templates.
    }
    
     public Locataire rechercherLocataireParCIN(String cin) {
      String sql = "SELECT nomprenomL, teleL, CIN FROM locataire WHERE CIN = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, cin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Locataire locataire = new Locataire();
                locataire.setCin(resultSet.getString("CIN"));
                locataire.setNomprenom(resultSet.getString("nomprenomL"));
                locataire.setTele(resultSet.getString("teleL"));
                return locataire;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
     
    public Locataire getLocataireById(int id) throws SQLException {
    String selectQuery = "SELECT * FROM locataire WHERE idL = ?";
    Locataire locataire = null;

    try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            locataire = new Locataire();
            locataire.setId(resultSet.getInt("idL"));
            locataire.setNomprenom(resultSet.getString("nomprenomL"));
            locataire.setTele(resultSet.getString("teleL"));
            locataire.setDateNaise(resultSet.getDate("datenaissL"));
            locataire.setCin(resultSet.getString("CIN"));
            // Vous pouvez continuer à définir d'autres propriétés du locataire ici
        }
    }

    return locataire;
}
    public int retrieveLocataireIdByCIN(String cin) {
    try {
        String sql = "SELECT idL FROM locataire WHERE CIN = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, cin);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getInt("idL");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0; // Return 0 if no matching locataire is found.
}
}
