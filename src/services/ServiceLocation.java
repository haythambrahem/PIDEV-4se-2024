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
import java.util.Date;
import java.util.List;
import models.Personne;
import models.Location;
import models.Logement;
import tools.MyDB;

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
    ServiceLogement serviceLogement = new ServiceLogement();
    ServicePersonne servicePersonne = new ServicePersonne();
    
    try {
        String sql = "SELECT * FROM location";
        PreparedStatement selectAllStatement = con.prepareStatement(sql);
        ResultSet resultSet = selectAllStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("idLocation");
            int idLogement = resultSet.getInt("Logement");
            int idPersonne = resultSet.getInt("personne");
            Date dateDebut = resultSet.getDate("dateDebut");
            Date dateFin = resultSet.getDate("dateFin");
            int tarif = resultSet.getInt("tarif");

            Logement logement = serviceLogement.getLogementById(idLogement);
            Personne personne = servicePersonne.getPersonneById(idPersonne);

            Location location = new Location(id, logement, personne, dateDebut, dateFin, tarif);
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
   public List<Location> searchLocationsByCriteria(String critere) {
      String selectQuery = "SELECT * FROM location WHERE logement LIKE ?";

    try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
        preparedStatement.setString(1, "%" + critere + "%"); // Utilize % for partial search

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Location> locations = new ArrayList<>();

        while (resultSet.next()) {
            // Retrieve location details from the result set and create Location objects
            int id = resultSet.getInt("idLocation");
            int logementId = resultSet.getInt("logement");
            int personneId = resultSet.getInt("personne");
            Date dateDebut = resultSet.getDate("dateDebut");
            Date dateFin = resultSet.getDate("dateFin");
            int tarif = resultSet.getInt("tarif");

            // Use the service classes to get Logement and Locataire objects by their IDs
            ServiceLogement serviceLogement = new ServiceLogement();
            ServicePersonne servicePersonne = new ServicePersonne();

            Logement logement = serviceLogement.getLogementById(logementId);
            Personne personne = servicePersonne.getPersonneById(personneId);

            // Create Location object and add it to the list
            Location location = new Location(id, logement, personne, dateDebut, dateFin, tarif);
            locations.add(location);
        }

        return locations;
    } catch (SQLException e) {
        e.printStackTrace();
        return Collections.emptyList(); 
    }
}
}
