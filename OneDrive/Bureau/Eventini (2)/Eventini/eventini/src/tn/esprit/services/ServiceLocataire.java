/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.List;
import tn.esprit.entity.Locataire;
import tn.esprit.services.IService;
import tn.esprit.tools.MyDB;
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

/**
 *
 * @author hayth
 */
public class ServiceLocataire implements IService<Locataire> {

    private Connection con;
    private PreparedStatement pre;
    @Override
    public void ajouter(Locataire l) {
       try {
            String query = "INSERT INTO Locataire (nom, prenom, dateNaise, tele, cin) VALUES (?, ?, ?, ?,?)";
            pre = con.prepareStatement(query);
            pre.setString(1, l.getNom());
            pre.setString(2, l.getPrenom());
            pre.setDate(3,java.sql.Date.valueOf (l.getDateNaise()));
            pre.setString(4, l.getTele());
            pre.setString(5, l.getCin());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Locataire l) {
 try {
            String query = "DELETE FROM Locataire WHERE id=?";
            pre = con.prepareStatement(query);
            pre.setInt(1, l.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    }

    @Override
    public void modifier(Locataire l) {
 try {
            String query = "UPDATE Locataire SET nom=?, prenom=? , dateNaise=?, tele=?, cin=? WHERE id=?";
            pre = con.prepareStatement(query);
            pre.setString(1, l.getNom());
            pre.setString(2, l.getPrenom());
            pre.setDate(3, java.sql.Date.valueOf (l.getDateNaise()));
            pre.setString(4, l.getTele());
            pre.setString(5, l.getCin());
            pre.setInt(6, l.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    }

    @Override
    public List<Locataire> afficher() {
 List<Locataire> locataires = new ArrayList<>();
        try {
            String query = "SELECT * FROM Locataire";
            pre = con.prepareStatement(query);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                LocalDate dateNaise = rs.getDate("dateNaise").toLocalDate();
                Locataire locataire = new Locataire(rs.getInt("id"), rs.getString("nom"),rs.getString("prenom"), dateNaise, rs.getString("tele"), rs.getString("cin"));
                locataires.add(locataire);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return locataires;    }

    @Override
    public List<Locataire> rechercher(int id) {
 List<Locataire> locataires = new ArrayList<>();
        try {
            String query = "SELECT * FROM Locataire WHERE id = ?";
            pre = con.prepareStatement(query);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                LocalDate dateNaise = rs.getDate("dateNaise").toLocalDate();
                Locataire locataire = new Locataire(rs.getInt("id"), rs.getString("nom"),rs.getString("prenom"), dateNaise, rs.getString("tele"), rs.getString("cin"));
                locataires.add(locataire);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return locataires;    }
 }

    
    
