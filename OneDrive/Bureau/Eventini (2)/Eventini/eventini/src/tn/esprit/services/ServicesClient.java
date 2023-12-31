/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import tn.esprit.services.IService;
import tn.esprit.tools.MyDB;
import tn.esprit.entity.Client;
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
import tn.esprit.entity.Adresse;
import tn.esprit.entity.Personne;

/**
 *
 * @author hayth
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
            String req = "INSERT INTO personne(nom, prenom, email, roles, password, dateNaise , ign , is_banned, pprofile,roleJava_client_id) VALUES ( ?,?,? ,?,?, ?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, t.getNom());
            pre.setString(2, t.getPrenom());
            pre.setString(3, t.getEmail());
            pre.setString(4, String.join(",", t.getRoles()));
            pre.setString(5, t.getPassword());
            pre.setDate(6,java.sql.Date.valueOf (t.getDateNaise()));
//            pre.setInt(7, t.getAdresse().getId()); // car l'adresse liée à un identifiant.
            pre.setString(7, t.getIgn());
            pre.setBoolean(8, t.isIs_banned());
            pre.setString(9, t.getPprofile());
            pre.setInt(10, 1);

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
            String req = "UPDATE personne SET nom=?, prenom=?, email=?, roles=?, password=?, dateNaissance=?, adresse=?, ing=? ,pprofile=?, WHERE id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, t.getNom());
            pre.setString(2, t.getPrenom());
            pre.setString(3, t.getEmail());
            pre.setString(4, String.join(",", t.getRoles()));
            pre.setString(5, t.getPassword());
            pre.setDate(6,java.sql.Date.valueOf (t.getDateNaise()));
            pre.setInt(7, t.getAdresse().getId());
            pre.setString(8, t.getIgn());
            pre.setString(5, t.getPprofile());
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
                         dateNaise, new Adresse(rs.getInt("adresse")), rs.getString("ign"));
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
    
//    public String getEmailBySessionId(String id) {
//    try {
//        // Query your database to retrieve the email associated with the session ID
//        String sql = "SELECT email FROM personne WHERE id = ?";
//        PreparedStatement preparedStatement = con.prepareStatement(sql);
//        preparedStatement.setString(1, id);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        
//        if (resultSet.next()) {
//            return resultSet.getString("email");
//        }
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//    }
//    
//    return null; // Return null if no email is found
//}

    
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
                int rolez = resultSet.getInt("roleJava_client_id");
                //String password = resultSet.getString("password");
                String ign = resultSet.getString("ign");
                String pprofile = resultSet.getString("pprofile");
//                LocalDate datenais = resultSet.getDate("datenai");
                java.sql.Date sqlDate = resultSet.getDate("datenaise");
                LocalDate localDate = sqlDate.toLocalDate();
                Boolean is_banned = resultSet.getBoolean("is_banned");
                Boolean is_verified = resultSet.getBoolean("is_verified");
                
                //String role = resultSet.getString("role");
                //int roleJava = 
                client = new Client( ign,  id,  nom,  prenom,  email ,  rolez,  localDate,  pprofile,is_banned,is_verified);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    
    
    
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Personne ORDER BY id DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setEmail(rs.getString("email"));
                client.setPassword(rs.getString("password"));
                client.setIs_verified(rs.getBoolean("is_verified"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                
                // Assuming rs is your ResultSet
                java.sql.Date dateSql = rs.getDate("dateNaise"); // Retrieve the date from the result set

                // Convert java.sql.Date to LocalDate
                LocalDate dateLocal = dateSql.toLocalDate();
                client.setDateNaise(dateLocal);
                /*
                LocalDate localDate =client.getDateNaise();
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringDate =localDate.format(formatter);
        profiledate.setText(stringDate);*/
                //client.setDateNaise(rs.getDate("datenai")); //because i change the date to localdate so i change this ligne 
                client.setPprofile(rs.getString("pprofile"));

                client.setIs_banned(rs.getBoolean("is_banned"));
                client.setIgn(rs.getString("ign"));
                
                //joueur.setCreatedAt(rs.getDate("created_at"));
                //System.out.println(joueur);
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clients;
    }
    
    public List<Client> searchClient(String searchTerm) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM personne WHERE nom LIKE ? OR prenom LIKE ? OR email LIKE ? OR ign LIKE ?");

            String likeSearchTerm = "%" + searchTerm + "%";
            statement.setString(1, likeSearchTerm);
            statement.setString(2, likeSearchTerm);
            statement.setString(3, likeSearchTerm);
            statement.setString(4, likeSearchTerm);
            

            ResultSet rs = statement.executeQuery();

            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setEmail(rs.getString("email"));
                client.setPassword(rs.getString("password"));
                client.setIs_verified(rs.getBoolean("is_verified"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                Date dateFormDatabase =rs.getDate("dateNaise");
                LocalDate localDate =dateFormDatabase.toLocalDate();
                client.setDateNaise(localDate);
                client.setPprofile(rs.getString("pprofile"));
                client.setIs_banned(rs.getBoolean("is_banned"));
                client.setIgn(rs.getString("ign"));
                
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            System.err.println("Error while searching for joueurs: " + e.getMessage());
            return null;
        }
    }
    
}
