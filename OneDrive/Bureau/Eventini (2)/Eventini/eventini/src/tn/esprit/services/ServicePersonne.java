/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import tn.esprit.services.IService;
import tn.esprit.tools.MyDB;
import tn.esprit.entity.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.entity.Adresse;
import tn.esprit.entity.Client;

/**
 *
 * @author Fayechi
 */
public class ServicePersonne implements IService<Personne> {
    
    Connection con; 
    Statement ste;
    PreparedStatement pre;


    public ServicePersonne() {
        con = MyDB.getinstance().getCon();    }
    
    

    @Override
    public void ajouter(Personne t) {
        try {
            String req = "INSERT INTO personne(nom, prenom, email, roles, password, dateNaissance, adresse) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, t.getNom());
            pre.setString(2, t.getPrenom());
            pre.setString(3, t.getEmail());
            pre.setString(4, String.join(",", t.getRoles()));
            pre.setString(5, t.getPassword());
            pre.setDate(6,java.sql.Date.valueOf (t.getDateNaise()));
            pre.setInt(7, t.getAdresse().getId()); // car l'adresse liée à un identifiant.
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void supprimer(Personne t) {
 try {
            String req = "DELETE FROM personne WHERE id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, t.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void modifier(Personne t) {
 try {
            String req = "UPDATE personne SET nom=?, prenom=?, email=?, roles=?, password=?, dateNaissance=?, adresse=? WHERE id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, t.getNom());
            pre.setString(2, t.getPrenom());
            pre.setString(3, t.getEmail());
            pre.setString(4, String.join(",", t.getRoles()));
            pre.setString(5, t.getPassword());
            pre.setDate(6, java.sql.Date.valueOf (t.getDateNaise()));
            pre.setInt(7, t.getAdresse().getId());
            pre.setInt(8, t.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Personne> afficher() {
        List<Personne> personnes = new ArrayList<>();
        String sql ="select * from personne";
        try {
            ste= con.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while(rs.next()){
                LocalDate dateNaise = rs.getDate("dateNaise").toLocalDate();
                Personne p = new Personne(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("email"), rs.getString("roles").split(","),
                        rs.getString("password"),dateNaise , new Adresse(rs.getInt("adresse")));
                personnes.add(p);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return personnes;
    }

    @Override
    public List<Personne> rechercher(int id) {
    
        List<Personne> personne = new ArrayList<>();
        try {
            String query = "SELECT * FROM Utilisateur WHERE id = ?";
            pre = con.prepareStatement(query);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery("");
            while (rs.next()) {
                LocalDate dateNaise = rs.getDate("dateNaise").toLocalDate();
                Personne personnes = new Personne(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("email"), rs.getString("roles").split(","),
                        rs.getString("password"), dateNaise, new Adresse(rs.getInt("adresse")));
                personne.add(personnes);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return personne;    }

     public Personne getPersonneById(int id) throws SQLException {
    String selectQuery = "SELECT * FROM personne WHERE id = ?";
    Personne personne = null;

    try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            personne = new Personne();
            personne.setId(resultSet.getInt("id"));
            personne.setNom(resultSet.getString("nom"));
            personne.setPrenom(resultSet.getString("prenom"));
            personne.setEmail(resultSet.getString("email"));
        //    personne.setTele(resultSet.getString("tele"));
          //  personne.setDateNaise(resultSet.getDate("datenaissL"));
          //  personne.setCin(resultSet.getString("CIN"));
            // Vous pouvez continuer à définir d'autres propriétés du locataire ici
        }
    }

    return personne;
    }
 public int retrievePersonneIdByEMAIL(String email) {
    try {
        String sql = "SELECT * FROM personne WHERE email = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0; // Return 0 if no matching locataire is found.
}
     public Personne rechercherPersonneParEmail(String email) {
      String sql = "SELECT * FROM personne WHERE email = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Personne personne = new Personne();
                personne.setEmail(resultSet.getString("email"));
                personne.setNom(resultSet.getString("nom"));
                personne.setPrenom(resultSet.getString("prenom"));
              //  locataire.setTele(resultSet.getString("teleL"));
                return personne;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
   

    
}
