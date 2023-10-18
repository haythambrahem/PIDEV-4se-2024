/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import tn.esprit.entity.Personne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.tools.MyDB;

/**
 *
 * @author Fayechi
 */
public class ServicePersonne implements IService<Personne> {
    
    Connection con; 
    Statement ste;

    public ServicePersonne() {
        con = MyDB.getinstance().getCon();    }
    
    
//
//    @Override
//    public void ajouter(Personne t) {
//        try {
//            String req = "INSERT INTO personne(nom,prenom)values(?,?)";
//            PreparedStatement pre = con.prepareStatement(req);
//            pre.setString(1,t.getNom() );
//            pre.setString(2,t.getPrenom());
//            pre.executeUpdate();
//            
//            
//            
//            } catch (SQLException ex) {
//                System.out.println("ajout avec succès");
//            
//        }
//       
//    }
//
//    @Override
//    public void supprimer(Personne t) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void modifier(Personne t) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<Personne> affihcer() {
//        List<Personne> personnes = new ArrayList<>();
//        String sql ="select * from personne";
//        try {
//            ste= con.createStatement();
//            ResultSet rs = ste.executeQuery(sql);
//            while(rs.next()){
//                Personne p = new Personne(rs.getInt("id"),
//                        rs.getString("nom"), rs.getString(2));
//                personnes.add(p);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return personnes;
//    }
//    
}
