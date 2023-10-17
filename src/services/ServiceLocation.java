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
import java.util.Date;
import java.util.List;
import models.Locataire;
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
    String sql = "INSERT INTO location (logement, locataire, dateDebut, dateFin, tarif) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement insertStatement = con.prepareStatement(sql)) {
        insertStatement.setInt(1, location.getLogement().getId());
        insertStatement.setInt(2, location.getLocataire().getId());
        insertStatement.setDate(3, location.getDateDebut());
        insertStatement.setDate(4, location.getDateFin());
        insertStatement.setInt(5, location.getTarif());
        
        return insertStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // Opération Read (récupérer une réservation par son ID)
    public Location getLocationById(int id) {
        Location location = null;
        try {
            String sql = "SELECT * FROM location WHERE idLocation = ?";
            PreparedStatement selectStatement = con.prepareStatement(sql);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int idLogement = resultSet.getInt("logement");
                int idLocataire = resultSet.getInt("locataire");
                Date dateDebut = resultSet.getDate("dateDebut");
                Date dateFin = resultSet.getDate("dateFin");
                int tarif = resultSet.getInt("tarif");
  ServiceLogement serviceLogement = new ServiceLogement();
            ServiceLocataire serviceLocataire = new ServiceLocataire();
            Logement logement = serviceLogement.getLogementById(idLogement);
            Locataire locataire = serviceLocataire.getLocataireById(idLocataire);
                // Vous devrez récupérer le Logement et le Locataire correspondant à partir de leur ID ici
                location = new Location(id, logement, locataire, dateDebut, dateFin, tarif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    // Opération Update (mettre à jour une réservation existante)
    public void modifierLocation(Location location) throws SQLException {
        ServiceLogement serviceLogement = new ServiceLogement();
ServiceLocataire serviceLocataire = new ServiceLocataire();
         Logement logement = serviceLogement.getLogementById(location.getLogement().getId());
Locataire locataire = serviceLocataire.getLocataireById(location.getLocataire().getId());
        try {
            String sql = "UPDATE location SET logement = ?, locataire = ?, dateDebut = ?, dateFin = ?, tarif = ? WHERE idLocation = ?";
            PreparedStatement updateStatement = con.prepareStatement(sql);
            updateStatement.setInt(1, location.getLogement().getId());
            updateStatement.setInt(2, location.getLocataire().getId());
            updateStatement.setDate(3, new java.sql.Date(location.getDateDebut().getTime()));
            updateStatement.setDate(4, new java.sql.Date(location.getDateFin().getTime()));
            updateStatement.setInt(5, location.getTarif());
            updateStatement.setInt(6, location.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Opération Delete (supprimer une réservation existante)
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

    // Opération Read (récupérer toutes les réservations)
    public List<Location> getAllLocations() {
         List<Location> locations = new ArrayList<>();
    ServiceLogement serviceLogement = new ServiceLogement();
    ServiceLocataire serviceLocataire = new ServiceLocataire();
    
    try {
        String sql = "SELECT * FROM location";
        PreparedStatement selectAllStatement = con.prepareStatement(sql);
        ResultSet resultSet = selectAllStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("idLocation");
            int idLogement = resultSet.getInt("Logement");
            int idLocataire = resultSet.getInt("locataire");
            Date dateDebut = resultSet.getDate("dateDebut");
            Date dateFin = resultSet.getDate("dateFin");
            int tarif = resultSet.getInt("tarif");

            // Vous devrez récupérer le Logement et le Locataire correspondant à partir de leur ID ici
            Logement logement = serviceLogement.getLogementById(idLogement);
            Locataire locataire = serviceLocataire.getLocataireById(idLocataire);

            Location location = new Location(id, logement, locataire, dateDebut, dateFin, tarif);
            locations.add(location);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return locations;
}
    
   public boolean isLocationAvailable(int logementId, Date dateDebut, Date dateFin) {
     try {
        // Query the database to check for overlapping bookings
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

        // If the query returns any results, it means the location is not available
        return !resultSet.next();
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
