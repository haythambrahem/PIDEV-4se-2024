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
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entity.Adresse;
import tn.esprit.tools.MyDB;

/**
 *
 * @author hayth
 */
public class ServiceAdresse implements IService<Adresse>{
    
    Connection con;
    PreparedStatement pre;

    public ServiceAdresse() {
        con = MyDB.getinstance().getCon();
    }

    @Override
    public void ajouter(Adresse adresse) {
        try {
            String query = "INSERT INTO adresse(street, city, postalCode, country,personneId) VALUES (?,?, ?, ?,?)";
            PreparedStatement pre = con.prepareStatement(query);
            pre.setString(1, adresse.getStreet());
            pre.setString(2, adresse.getCity());
            pre.setString(3, adresse.getPostalCode());
            pre.setString(4, adresse.getCountry());
            pre.setInt(5, adresse.getPersonneId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void supprimer(Adresse adresse) {
        try {
            String query = "DELETE FROM adresse WHERE id=?";
            PreparedStatement pre = con.prepareStatement(query);
            pre.setInt(1, adresse.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void modifier(Adresse adresse) {
        try {
            String query = "UPDATE adresse SET street=?, city=?, postalCode=?, country=? WHERE id=?";
            PreparedStatement pre = con.prepareStatement(query);
            pre.setString(1, adresse.getStreet());
            pre.setString(2, adresse.getCity());
            pre.setString(3, adresse.getPostalCode());
            pre.setString(4, adresse.getCountry());
            pre.setInt(5, adresse.getId());
//            pre.setInt(6, adresse.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Adresse> afficher() {
        List<Adresse> adresses = new ArrayList<>();
        String sql = "SELECT * FROM adresse";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Adresse adresse = new Adresse(
                    rs.getInt("id"),
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("postalCode"),
                    rs.getString("country")
                    ,rs.getInt("personneId")
                );
                adresses.add(adresse);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return adresses;
    }

    @Override
    public List<Adresse> rechercher(int id) {
        List<Adresse> adresses = new ArrayList<>();
        try {
            String query = "SELECT * FROM adresse WHERE id = ?";
            PreparedStatement pre = con.prepareStatement(query);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Adresse adresse = new Adresse(
                    rs.getInt("id"),
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("postalCode"),
                    rs.getString("country")
                    ,rs.getInt("personneId")
                );
                adresses.add(adresse);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return adresses;
    }
    public List<Adresse> getByClientId(int clientId) {
    List<Adresse> adresses = new ArrayList<>();
    try {
        String query = "SELECT * FROM adresse WHERE personneId = ?";
        PreparedStatement pre = con.prepareStatement(query);
        pre.setInt(1, clientId);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            Adresse adresse = new Adresse(
                rs.getInt("id"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("postalCode"),
                rs.getString("country"),
                rs.getInt("personneId")   
            );
            adresses.add(adresse);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return adresses;
}
public void deleteByClientId(int clientId) {
    try {
        String query = "DELETE FROM adresse WHERE clientId = ?";
        PreparedStatement pre = con.prepareStatement(query);
        pre.setInt(1, clientId);
        pre.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex);
    }
}

}
