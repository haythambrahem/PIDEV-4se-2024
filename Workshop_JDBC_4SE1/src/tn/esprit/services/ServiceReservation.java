/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import tn.esprit.entity.Reservation;
import tn.esprit.tools.MyDB;

/**
 *
 * @author siwar
 */
public class ServiceReservation implements IReservation<Reservation>  {

     private static ServiceReservation instance;

    // Public method to get the instance
    public static ServiceReservation getInstance() {
        if (instance == null) {
            instance = new ServiceReservation();
        }
        return instance;
    }
    
     Connection con; 
     Statement ste;
      public ServiceReservation() {
        con = MyDB.getinstance().getCon(); 
    }

   public void ajouterRes(Reservation res) {
        try {
            String req = "INSERT INTO reservation(idEvt, titreEvt, prixBillet )values(?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1,res.getIdEvt());
            pre.setString(2,res.getTitreEvt());
            pre.setFloat(3,res.getPrixBillet());
            
            System.out.println("Merci pour la réservation de cet évènement !");
            pre.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
   
     }
   public boolean effectuerRes(Reservation reservation) {
         boolean reservationSuccessfullyAdded = false;
    // Your logic to add the reservation goes here.

    // If the reservation was successfully added, return true.
    // If there was an error, return false.
    
    // For example:
    if (reservationSuccessfullyAdded) {
        return true;
    } else {
        return false;
    }
}

   public int getIdEvenementParNom(String titreEvt) throws SQLException {
    // Établissez la connexion à la base de données (assurez-vous que votre connexion fonctionne)

     con = MyDB.getinstance().getCon(); // Obtenez votre connexion à la base de données ici

    String query = "SELECT idEvt FROM evenement WHERE titreEvt = ?";
    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
        preparedStatement.setString(1, titreEvt);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // L'événement a été trouvé, renvoyez son ID
            return resultSet.getInt("idEvt");
        } else {
            // L'événement n'a pas été trouvé, renvoyez -1 ou lancez une exception
            // return -1;
            throw new SQLException("L'événement avec le nom " + titreEvt + " n'a pas été trouvé.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Vous pouvez également gérer l'exception ici ou la relancer
    }
}

    @Override
    public void modifierRes(Reservation ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerRes(Reservation ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> afficherRes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
