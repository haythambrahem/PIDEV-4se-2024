/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import entité.Reclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tn.esprit.tools.MyDB;

/**
 *
 * @author walid
 */

public class ReclamationService implements services.Iservice<Reclamation> {


    Statement stm;
    Connection conn;

    public ReclamationService() {
        conn=MyDB.getInstance().getCnx();
    }
    
 
    public List<Reclamation> afficheListe() {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "Select * from  `reclamation`";
            Statement st = conn.createStatement();

            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Reclamation p = new Reclamation();
                p.setId(RS.getInt("id"));
                p.setMessage(RS.getString("message"));
                p.setEtat(RS.getString("etat"));
                p.setIdUser(RS.getInt("idUser"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

  

    public void ajouter(Reclamation p) {
        try {
            String req = "INSERT INTO  `reclamation`( `message`, `etat`,`idUser`) VALUES (?,?,?)";

            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, p.getMessage());
            ps.setString(2, p.getEtat());
            ps.setInt(3, p.getIdUser());
            ps.executeUpdate();

            System.out.println("Reclamation inséré");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimer(Reclamation p) {
        try {
            String req = "DELETE FROM `reclamation` WHERE id = " + p.getId();
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("reclamation supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Reclamation p) {
        try {

            String req = "UPDATE `reclamation` SET `message` = ?, `etat` = ? WHERE `id` = ?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, p.getMessage());
            ps.setString(2, p.getEtat());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
            System.out.println("reclamation mise a jour");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Reclamation> rechercher(String msg) {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "Select * from  `reclamation` where message like '%" + msg + "%'";
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Reclamation p = new Reclamation();
                p.setId(RS.getInt("id"));
                p.setMessage(RS.getString("message"));
                p.setEtat(RS.getString("etat"));
                p.setIdUser(RS.getInt("idUser"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public int nbrRecParUser(int id) {
        try {
            String req = "Select count(*) from  `reclamation` where idUser=" + id;
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            RS.next();
            return RS.getInt("count(*)");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public Map<String, Integer> statistiquesReclamation() {
        Map<String, Integer> res = new HashMap<String, Integer>();
        try {
            String req = "Select etat,count(*) from  `reclamation` group by etat";
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                res.put(RS.getString("etat"), RS.getInt("count(*)"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

  

}