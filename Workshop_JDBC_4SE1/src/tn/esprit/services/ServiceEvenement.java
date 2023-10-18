/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import static com.mysql.cj.protocol.x.XProtocolDecoder.instance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entity.Evenement;
import tn.esprit.tools.MyDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author siwar
 */
public class ServiceEvenement implements IEvent<Evenement>  {

     private static ServiceEvenement instance;

    // Public method to get the instance
    public static ServiceEvenement getInstance() {
        if (instance == null) {
            instance = new ServiceEvenement();
        }
        return instance;
    }
    
     Connection con; 
     Statement ste;

    public ServiceEvenement() {
        con = MyDB.getinstance().getCon(); 
    }
    //Ajouter
     
     public void ajouterEv(Evenement ev) {
        try {
            String req = "INSERT INTO evenement(idEvt, titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, typeEvt)values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1,ev.getIdEvt() );
            pre.setString(2,ev.getTitreEvt());
            pre.setString(3,ev.getNomOrg() );
            pre.setString(4,ev.getDescEvt() );
            pre.setString(5,ev.getHdEvt() );
            pre.setString(6,ev.getHfEvt() );
            pre.setString(7,ev.getAdresseEvt() );
            pre.setString(8,ev.getTypeEvt() );
            //pre.setDate(9,valueOf(ev.getDateEvt()));
            System.out.println("Ajouté avec succès.");
            pre.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex);
            
        }
   
     }

    public List<Evenement> afficherEv() {
        List<Evenement> evenements = new ArrayList<>();
        String sql ="select * from evenement";
        try {
            ste= con.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while(rs.next()){
                Evenement e = new Evenement(rs.getInt("idEvt"),
                        rs.getString("titreEvt") , rs.getString("nomOrg"),
                        rs.getString("descEvt") ,rs.getString("hdEvt") ,
                        rs.getString("hfEvt"),rs.getString("adresseEvt"),
                        rs.getString("typeEvt"));
                evenements.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return evenements;
    }
    
 public void modifierEv(String titreEvt, String nomOrg, String descEvt, String hdEvt, String hfEvt, String adresseEvt, String typeEvt, Evenement ev) {
    String sql = "update evenement set titreEvt=?, nomOrg=?, descEvt=?, hdEvt=?, hfEvt=?, adresseEvt=?, typeEvt=? where idEvt=?";
    try {
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1, titreEvt);
        pre.setString(2, nomOrg);
        pre.setString(3, descEvt);
        pre.setString(4, hdEvt);
        pre.setString(5, hfEvt);
        pre.setString(6, adresseEvt);
        pre.setString(7, typeEvt);
        pre.setInt(8, ev.getIdEvt()); // Set the value of idEvt from the provided Evenement object
        int rowsUpdated = pre.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Evènement modifié avec succès.");
        } else {
            System.out.println("Aucune modification effectuée.");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

    public void supprimerEv(Evenement ev) {
          String sql = "delete from evenement where idEvt=?";
        try {
            PreparedStatement ste = con.prepareStatement(sql);
            ste.setInt(1, ev.getIdEvt());
            ste.executeUpdate();
            System.out.println("Evenement supprimé avec succès .");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
public List<Evenement> afficher() {
    List<Evenement> evenement = new ArrayList<>();
    String sql = "SELECT * FROM evenement";
    try {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery(sql);
        while (rs.next()) {
            Evenement ev = new Evenement(rs.getInt("idEvt"), rs.getString("titreEvt"), rs.getString("nomOrg"), rs.getString("descEvt"), rs.getString("hdEvt"), rs.getString("hfEvt"), rs.getString("adresseEvt"), rs.getString("typeEvt"));
            evenement.add(ev);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return evenement;
}

    @Override
    public void modifierEv(Evenement ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void modifierEv(int idEvt, String titreEvt, String nomOrg, String descEvt, String hdEvt, String hfEvt, String adresseEvt, String string, Evenement ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}