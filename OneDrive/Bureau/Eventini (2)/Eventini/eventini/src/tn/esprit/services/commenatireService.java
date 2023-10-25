/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import entité.Commentaire;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;

/**
 *
 * @author walid
 */
public class commenatireService implements tn.esprit.services.Iservice<Commentaire> {

    Statement stm;
    Connection conn;

    public commenatireService() {
        conn = MyDB.getInstance().getCnx();
    }

    public List<Commentaire> afficheListe() {
        List<Commentaire> list = new ArrayList<>();
        try {
            String req = "Select * from  `commentaire`";
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Commentaire p = new Commentaire();
                p.setId1(RS.getInt("id1_id"));
                p.setId2(RS.getInt("id2_id"));
                p.setMessage(RS.getString("message"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public void ajouter(Commentaire p) {
        try {
            String req = "INSERT INTO  `commentaire`( `id2_id`, `message`) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, p.getId1());
            ps.setInt(2, p.getId2());
            ps.setString(3, p.getMessage());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(commenatireService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void supprimer(Commentaire p) {
        try {
            String req = "DELETE FROM `commentaire` WHERE id1_id = ? AND id2_id= ?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, p.getId1());
            ps.setInt(2, p.getId2());
            ps.executeUpdate(req);
            System.out.println("Commentaire supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Commentaire p) {
        try {
            String req = "UPDATE `commentaire` SET message=?, WHERE id1_id = ? AND id2_id= ?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, p.getMessage());
            ps.setInt(2, p.getId1());
            ps.setInt(3, p.getId2());
            ps.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(commenatireService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}