/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import tn.esprit.entity.Personne;
import tn.esprit.entity.Location;
import tn.esprit.entity.Logement;
import tn.esprit.tools.MyDB;

/**
 *
 * @author fadi saidi
 */
public class ServiceLocation {
    private  Connection con ;
    Statement ste;
    private FileInputStream fs;
    
    
    
    public ServiceLocation() {
     con = MyDB.getinstance().getCon();
     
}
    
    
    
    
    public boolean ajouterLocation(Location location) {
    String sql = "INSERT INTO location (logement, personne, dateDebut, dateFin, tarif) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement insertStatement = con.prepareStatement(sql)) {
        insertStatement.setInt(1, location.getLogement().getId());
        insertStatement.setInt(2, location.getPersonne().getId());
        insertStatement.setDate(3, location.getDateDebut());
        insertStatement.setDate(4, location.getDateFin());
        insertStatement.setInt(5, location.getTarif());
        
        return insertStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public Location getLocationById(int id) {
        Location location = null;
        try {
            String sql = "SELECT * FROM location WHERE idLocation = ?";
            PreparedStatement selectStatement = con.prepareStatement(sql);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int idLogement = resultSet.getInt("logement");
                int idPersonne = resultSet.getInt("personne");
                Date dateDebut = resultSet.getDate("dateDebut");
                Date dateFin = resultSet.getDate("dateFin");
                int tarif = resultSet.getInt("tarif");
  ServiceLogement serviceLogement = new ServiceLogement();
            ServicePersonne servicePersonne = new ServicePersonne();
            Logement logement = serviceLogement.getLogementById(idLogement);
            Personne personne = servicePersonne.getPersonneById(idPersonne);
                // Vous devrez récupérer le Logement et le Locataire correspondant à partir de leur ID ici
                location = new Location(id, logement, personne, dateDebut, dateFin, tarif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    public void modifierLocation(Location location) throws SQLException {
        ServiceLogement serviceLogement = new ServiceLogement();
ServicePersonne servicePersonne = new ServicePersonne();
         Logement logement = serviceLogement.getLogementById(location.getLogement().getId());
Personne personne = servicePersonne.getPersonneById(location.getPersonne().getId());
        try {
            String sql = "UPDATE location SET logement = ?, personne = ?, dateDebut = ?, dateFin = ?, tarif = ? WHERE idLocation = ?";
            PreparedStatement updateStatement = con.prepareStatement(sql);
            updateStatement.setInt(1, location.getLogement().getId());
            updateStatement.setInt(2, location.getPersonne().getId());
            updateStatement.setDate(3, new java.sql.Date(location.getDateDebut().getTime()));
            updateStatement.setDate(4, new java.sql.Date(location.getDateFin().getTime()));
            updateStatement.setInt(5, location.getTarif());
            updateStatement.setInt(6, location.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerLocation(int id) {
        try {
            String sql = "DELETE FROM location WHERE idLocation = ?";
            PreparedStatement deleteStatement = con.prepareStatement(sql);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Location> getAllLocations() {
       List<Location> locations = new ArrayList<>();

    try {
        String sql = "SELECT l.idLocation, l.logement, l.personne, l.dateDebut, l.dateFin, l.tarif, lg.adrL,lg.region, p.email, p.nom, p.prenom " +
                     "FROM location l " +
                     "JOIN logement lg ON l.logement = lg.idLogement " +
                     "JOIN personne p ON l.personne = p.id";

        PreparedStatement selectAllStatement = con.prepareStatement(sql);
        ResultSet resultSet = selectAllStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("idLocation");
            String adr = resultSet.getString("adrL");
             String region = resultSet.getString("region");
            String email = resultSet.getString("email");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            Date dateDebut = resultSet.getDate("dateDebut");
            Date dateFin = resultSet.getDate("dateFin");
            int tarif = resultSet.getInt("tarif");

            // Create Location object and add it to the list
            Location location = new Location(id, adr,region, email,nom,prenom, dateDebut, dateFin, tarif);
            locations.add(location);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return locations;
}
    
   public boolean isLocationAvailable(int logementId, Date dateDebut, Date dateFin) {
     try {
        String sql = "SELECT idLocation FROM location " +
                     "WHERE logement = ? " +
                     "AND ((dateDebut <= ? AND dateFin >= ?) " +
                     "OR (dateDebut <= ? AND dateFin >= ?))";

        PreparedStatement checkStatement = con.prepareStatement(sql);
        checkStatement.setInt(1, logementId);
        checkStatement.setDate(2, (java.sql.Date) dateDebut);
        checkStatement.setDate(3, (java.sql.Date) dateDebut);
        checkStatement.setDate(4, (java.sql.Date) dateFin);
        checkStatement.setDate(5, (java.sql.Date) dateFin);

        ResultSet resultSet = checkStatement.executeQuery();

        return !resultSet.next();
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
  public List<Location> searchLocationsByCriteria(String criteria) {
    List<Location> searchResults = new ArrayList<>();
    try {
        String sql = "SELECT l.idLocation, l.logement, l.personne, l.dateDebut, l.dateFin, l.tarif, lg.adrL, lg.region, p.email, p.nom, p.prenom " +
                     "FROM location l " +
                     "JOIN logement lg ON l.logement = lg.idLogement " +
                     "JOIN personne p ON l.personne = p.id " +
                     "WHERE  lg.adrL LIKE ? OR lg.region LIKE ? OR p.email = ? OR p.nom = ? OR p.prenom = ?";
        PreparedStatement searchStatement = con.prepareStatement(sql);
        searchStatement.setString(1,  "%" + criteria + "%");
        searchStatement.setString(2, "%" + criteria + "%");
        searchStatement.setString(3, criteria);
        searchStatement.setString(4, criteria);
        searchStatement.setString(5, criteria);
      //  searchStatement.setString(6, criteria);

        ResultSet resultSet = searchStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("idLocation");
            String adr = resultSet.getString("adrL");
            String region = resultSet.getString("region");
            String email = resultSet.getString("email");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            Date dateDebut = resultSet.getDate("dateDebut");
            Date dateFin = resultSet.getDate("dateFin");
            int tarif = resultSet.getInt("tarif");

            Location location = new Location(id, adr, region, email, nom, prenom, dateDebut, dateFin, tarif);
            searchResults.add(location);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return searchResults;
}
   public List<Location> searchLocationsByEmail(String criteria) {
    List<Location> searchResults = new ArrayList<>();
    try {
        String sql = "SELECT l.idLocation, l.logement, l.personne, l.dateDebut, l.dateFin, l.tarif, lg.adrL, lg.region, p.email, p.nom, p.prenom " +
                     "FROM location l " +
                     "JOIN logement lg ON l.logement = lg.idLogement " +
                     "JOIN personne p ON l.personne = p.id " +
                     "WHERE p.email = ?";
        PreparedStatement searchStatement = con.prepareStatement(sql);
        searchStatement.setString(1, criteria); // You don't need wildcards (%) for exact email match

        ResultSet resultSet = searchStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("idLocation");
            String adr = resultSet.getString("adrL");
            String region = resultSet.getString("region");
            String email = resultSet.getString("email");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            Date dateDebut = resultSet.getDate("dateDebut");
            Date dateFin = resultSet.getDate("dateFin");
            int tarif = resultSet.getInt("tarif");

            Location location = new Location(id, adr, region, email, nom, prenom, dateDebut, dateFin, tarif);
            searchResults.add(location);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return searchResults;
}
   
}
