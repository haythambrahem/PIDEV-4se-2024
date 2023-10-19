/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import services.IService;
import tools.MyDB;
import models.Client;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Adresse;
import models.Personne;

/**
 *
 * @author fadi saidi
 */
public class ServicesClient implements IService<Client> {
     Connection con; 
     Statement ste;
     PreparedStatement pre;


    public ServicesClient() {
        con = MyDB.getinstance().getCon();    }
    
    

    @Override
    public void ajouter(Client t) {
        try {
            String req = "INSERT INTO personne(nom, prenom, email, roles, password, dateNaise , ign) VALUES ( ? ,?, ?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, t.getNom());
            pre.setString(2, t.getPrenom());
            pre.setString(3, t.getEmail());
            pre.setString(4, String.join(",", t.getRoles()));
            pre.setString(5, t.getPassword());
            pre.setDate(6,java.sql.Date.valueOf (t.getDateNaise()));
//            pre.setInt(7, t.getAdresse().getId()); // car l'adresse liée à un identifiant.
            pre.setString(7, t.getIgn());

            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
       
    }

    @Override
    public void supprimer(Client t) {
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
    public void modifier(Client t) {
 try {
            String req = "UPDATE personne SET nom=?, prenom=?, email=?, roles=?, password=?, dateNaissance=?, adresse=?, ing=? WHERE id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, t.getNom());
            pre.setString(2, t.getPrenom());
            pre.setString(3, t.getEmail());
            pre.setString(4, String.join(",", t.getRoles()));
            pre.setString(5, t.getPassword());
            pre.setDate(6,java.sql.Date.valueOf (t.getDateNaise()));
            pre.setInt(7, t.getAdresse().getId());
            pre.setString(8, t.getIgn());
            pre.setInt(9, t.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }

    @Override
    public List<Client> afficher() {
        List<Client> clients = new ArrayList<>();
        String sql ="select * from personne";
        try {
            ste= con.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while(rs.next()){
                LocalDate dateNaise = rs.getDate("dateNaise").toLocalDate();
                Client c = new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("email"), rs.getString("roles").split(","), rs.getString("password"),
                        dateNaise, new Adresse(rs.getInt("adresse")), rs.getString("ign"));
                clients.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clients;
    }

    @Override
    public List<Client> rechercher(int id) {
 List<Client> Clients = new ArrayList<>();
       try {
            String query = "SELECT * FROM personne WHERE id = ?";
            pre = con.prepareStatement(query);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery("");
            while (rs.next()) {
                LocalDate dateNaise = rs.getDate("dateNaise").toLocalDate();
                 Client client = new Client(rs.getInt("id"), rs.getString("nom"),
                         rs.getString("prenom"), rs.getString("email"),
                         rs.getString("roles").split(","), rs.getString("password"),
                         dateNaise, new Adresse(rs.getInt("adresse")), rs.getString("ign")) {};
                Clients.add(client);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Clients;    }
    
    public int getClientIdByEmail(String email) throws SQLException {
        int ClientId = -1;
        String query = "SELECT id FROM personne WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ClientId = rs.getInt("id");
                }
            }
        }
        return ClientId;
    }
    public Client getClientByEmail(String email) {
        Client client = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Personne WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String emailuser = resultSet.getString("email");
                String prenom = resultSet.getString("prenom");
//                int rolez = resultSet.getInt("roleJava_joueur_id");
                //String password = resultSet.getString("password");
                String ign = resultSet.getString("ign");
//                String pprofile = resultSet.getString("pprofile");
//                LocalDate datenais = resultSet.getDate("datenai");
                java.sql.Date sqlDate = resultSet.getDate("datenaise");
                LocalDate localDate = sqlDate.toLocalDate();

//                int wins = resultSet.getInt("wins");
//                int loses = resultSet.getInt("loses");
//                Boolean is_verified = resultSet.getBoolean("is_verified");
//                Boolean is_banned = resultSet.getBoolean("is_banned");
                //String role = resultSet.getString("role");
                //int roleJava = 
                client = new Client(ign,id, nom, prenom,emailuser, localDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    
    
}