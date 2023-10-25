/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entity.Evenement;
import tn.esprit.tools.MyDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    
public void ajouterEv(Evenement ev) {
    try {
        String req = "INSERT INTO evenement(idEvt, titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, typeEvt, dateEvt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, ev.getIdEvt());
        pre.setString(2, ev.getTitreEvt());
        pre.setString(3, ev.getNomOrg());
        pre.setString(4, ev.getDescEvt());
        pre.setString(5, ev.getHdEvt().toString());
        pre.setString(6, ev.getHfEvt().toString());
        pre.setString(7, ev.getAdresseEvt());
        pre.setString(8, ev.getTypeEvt());

        java.util.Date dateEvt = ev.getDateEvt();
        if (dateEvt != null) {
            pre.setDate(9, new java.sql.Date(dateEvt.getTime()));
        } else {
            // cas ou dateEvt is null
            pre.setDate(9, new java.sql.Date(System.currentTimeMillis())); 
        }

        System.out.println("Ajouté avec succès.");
        pre.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex);
    }
}

    
//    public List<Evenement> afficherEv() {
//        List<Evenement> evenements = new ArrayList<>();
//        String sql ="select * from evenement";
//        try {
//            ste= con.createStatement();
//            ResultSet rs = ste.executeQuery(sql);
//            while(rs.next()){
//                Evenement e = new Evenement(rs.getInt("idEvt"),
//                        rs.getString("titreEvt") , rs.getString("nomOrg"),
//                        rs.getString("descEvt") ,rs.getString("hdEvt") ,
//                        rs.getString("hfEvt"),rs.getString("adresseEvt"),
//                        rs.getString("typeEvt"), rs.getDate("dateEvt"));
//                evenements.add(e);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return evenements;
//    }
//    
////public List<Evenement> afficherEv() {
////    List<Evenement> evenements = new ArrayList<>();
////    String sql ="select * from evenement";
////    try {
////        ste = con.createStatement();
////        ResultSet rs = ste.executeQuery(sql);
////        while (rs.next()) {
////            Date dateEvt = rs.getDate("dateEvt").toDate();
////            
////            // Handle parsing errors using a try-catch block
////            LocalTime hdEvt = null;
////            try {
////                hdEvt = LocalTime.parse(rs.getString("hdEvt"));
////            } catch (DateTimeParseException ex) {
////                System.err.println("Error parsing hdEvt: " + ex.getMessage());
////            }
////            
////            LocalTime hfEvt = null;
////            try {
////                hfEvt = LocalTime.parse(rs.getString("hfEvt"));
////            } catch (DateTimeParseException ex) {
////                System.err.println("Error parsing hfEvt: " + ex.getMessage());
////            }
////            
////            Evenement e = new Evenement(rs.getInt("idEvt"),
////                    rs.getString("titreEvt"), rs.getString("nomOrg"),
////                    rs.getString("descEvt"), hdEvt, hfEvt, rs.getString("adresseEvt"),
////                    rs.getString("typeEvt"), dateEvt);
////            evenements.add(e);
////        }
////    } catch (SQLException ex) {
////        System.out.println(ex.getMessage());
////    }
////    return evenements;
////}


public List<Evenement> afficherEv() {
    List<Evenement> evenements = new ArrayList<>();
    String sql = "select * from evenement";
    try {
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery(sql);
        while (rs.next()) {
            Date dateEvt = null;

            try {
                java.sql.Date sqlDate = rs.getDate("dateEvt");
                if (sqlDate != null) {
                    dateEvt = new Date(sqlDate.getTime());
                }
            } catch (SQLException ex) {
                System.err.println("Error parsing dateEvt: " + ex.getMessage());
            }

            LocalTime hdEvt = null;
            try {
                hdEvt = LocalTime.parse(rs.getString("hdEvt"));
            } catch (DateTimeParseException ex) {
                System.err.println("Error parsing hdEvt: " + ex.getMessage());
            }

            LocalTime hfEvt = null;
            try {
                hfEvt = LocalTime.parse(rs.getString("hfEvt"));
            } catch (DateTimeParseException ex) {
                System.err.println("Error parsing hfEvt: " + ex.getMessage());
            }

            Evenement e = new Evenement(rs.getInt("idEvt"),
                    rs.getString("titreEvt"), rs.getString("nomOrg"),
                    rs.getString("descEvt"), hdEvt, hfEvt, rs.getString("adresseEvt"),
                    rs.getString("typeEvt"), dateEvt);
            evenements.add(e);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return evenements;
}






     public void modifierEv(String titreEvt, Evenement eventModifie){
         try {
             if (existeEvent(titreEvt)) {
                 // Si l'événement avec le titre spécifié existe, effectuer la mise à jour
                 
                 // Construire la requête SQL de mise à jour
                 String sql = "update evenement set nomOrg=?, descEvt=?, hdEvt=?, hfEvt=?, adresseEvt=?, typeEvt=?, dateEvt=? where titreEvt=?";
                 PreparedStatement pre = con.prepareStatement(sql);
                 
                 pre.setString(1, eventModifie.getNomOrg());
                 pre.setString(2, eventModifie.getDescEvt());
                 
                 // Formatter les objets LocalTime en chaînes de caractères au format HH:MM:SS
                 pre.setString(3, eventModifie.getHdEvt().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                 pre.setString(4, eventModifie.getHfEvt().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                 
                 pre.setString(5, eventModifie.getAdresseEvt());
                 pre.setString(6, eventModifie.getTypeEvt());
                 pre.setDate(7, new java.sql.Date(eventModifie.getDateEvt().getTime()));
                 pre.setString(8, titreEvt);
                 
                 pre.executeUpdate();
             } else {
                 System.err.println("Aucun événement trouvé avec le titre spécifié dans la base de données.");
             }    } catch (SQLException ex) {
             Logger.getLogger(ServiceEvenement.class.getName()).log(Level.SEVERE, null, ex);
         }
}

     public boolean existeEvent(String titreEvt) throws SQLException {
    // Construire la requête SQL de recherche
    String sql = "SELECT COUNT(*) FROM evenement WHERE titreEvt = ?";
    PreparedStatement pre = con.prepareStatement(sql);
    pre.setString(1, titreEvt);

    // Exécuter la requête
    ResultSet resultSet = pre.executeQuery();
    
    // Vérifier si un enregistrement avec le titre spécifié existe
    if (resultSet.next()) {
        int rowCount = resultSet.getInt(1);
        return rowCount > 0; // Si rowCount est supérieur à zéro, l'événement existe
    }

    // Aucun enregistrement trouvé avec le titre spécifié
    return false;
}


     @Override
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

 public ObservableList<Evenement> getAllById(int userId) {
    ObservableList<Evenement> evenements = FXCollections.observableArrayList();
    try {
        String sql = "SELECT * FROM evenement WHERE id_user = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Evenement ev = new Evenement(
                resultSet.getInt(1),
                resultSet.getString("titreEvt"),
                resultSet.getString("nomOrg"),
                resultSet.getString("descEvt"),
                LocalTime.parse(resultSet.getString("hdEvt")),
                LocalTime.parse(resultSet.getString("hfEvt")),
                resultSet.getString("adresseEvt"),
                resultSet.getString("typeEvt"),
                resultSet.getDate("dateEvt")
            );

            evenements.add(ev);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Handle the exception as needed
    }

    return evenements;
}
     public boolean evenementExists(int idEvt) throws SQLException {
    Evenement evenement = new Evenement(); // Create an instance of the Personne class
    
    String query = "SELECT * FROM evenement WHERE idEvt = ?";
    PreparedStatement statement = con.prepareStatement(query);
    statement.setInt(1, idEvt);
    ResultSet result = statement.executeQuery();
    
    return result.next();
}

 
 public void likeEvent(Evenement ev) {
    try {
        Evenement evenement = new Evenement();
        System.out.println(evenement.getIdEvt() + "id ::");  // Use the instance to call getId()
        if (evenementExists(ev.getIdEvt()))
        {
            String sql = "update evenement set vote = " + 1 + " where idEvt = ?";
            PreparedStatement ste = con.prepareStatement(sql);
            // ste.setInt(1, p.getVote());
            ste.setInt(1, ev.getIdEvt());
            ste.executeUpdate();
            System.out.println("like ajouté");
        } else {
            System.out.println("Vous n'avez pas encore participé");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

  
      public int[] getLikesAndDislikesCount(Evenement ev) {
    int[] counts = new int[2];
    try {
        // Préparer la requête SQL pour sélectionner tous les votes pour l'événement donné
        String sql = "SELECT vote FROM evenement WHERE idEvt = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, ev.getIdEvt());
        
        // Exécuter la requête SQL et parcourir les résultats pour compter les votes positifs et négatifs
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int vote = rs.getInt("vote");
            if (vote == 1) {
                counts[0]++;
            } else if (vote == 2) {
                counts[1]++;
            }
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors du calcul du nombre de likes et dislikes : " + ex.getMessage());
    }
    return counts;
}
      
      
     private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
}
    
    private int likesCount = 0;
    private int dislikesCount = 0;
    
    public void updateLikesAndDislikes(Evenement ev) {
        int[] newLikesAndDislikes = getLikesAndDislikesCount(ev);
        int[] oldLikesAndDislikes = { likesCount, dislikesCount };
        likesCount = newLikesAndDislikes[0];
        dislikesCount = newLikesAndDislikes[1];
        pcs.firePropertyChange("likesAndDislikes", oldLikesAndDislikes, newLikesAndDislikes);
    }

}