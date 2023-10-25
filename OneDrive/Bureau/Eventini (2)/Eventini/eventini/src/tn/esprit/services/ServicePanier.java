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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;
import tn.esprit.entity.Offre;
import tn.esprit.entity.Panier;
import tn.esprit.entity.Personne;
import tn.esprit.entity.Status;
import tn.esprit.entity.TypeOffre;
import tn.esprit.tools.MyDB;
//import tn.esprit.tools.DataSource;

/**
 *
 * @author imen
 */
public class ServicePanier implements IsevicePanier<Panier>{

    public static List<Offre> getListeOffres(int idPanier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //private static ServicePanier instance;
    PreparedStatement preparedStatement;
    Connection connection;
    
    public ServicePanier() {
        connection =  MyDB.getinstance().getCon();

}
//@Override
//public void ajouter(Panier p,Offre of, User us)  {
//    
//        try {
//            String req = "INSERT INTO panier (idOffre, total, datePanier, iduser) VALUES (?, ?, ?, ?)";
//            PreparedStatement ps = connection.prepareStatement(req); // Indiquez RETURN_GENERATED_KEYS
//            
//            // Définissez l'ID manuellement
//            ps.setInt(1,of.getIdOffre() );
//            ps.setInt(2, p.getTotal());
//            ps.setDate(3, new java.sql.Date(p.getDatePanier().getTime()));
//            ps.setInt(4,us.getIduser());
//            
//            ps.executeUpdate();
//            System.out.println("panier ajouté !!!!!!");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    
    
    
    
    
    
@Override
public void ajouter(Panier p, Offre of, Personne pers) {
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    try {


        String req = "INSERT INTO panier (idOffre, total,datePanier, id) VALUES (?, ?,?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, of.getIdOffre());
        ps.setInt(2, p.getTotal());
        java.util.Date javaDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
        ps.setDate(3, sqlDate);
        ps.setInt(4, pers.getId());
        ps.executeUpdate();
        System.out.println("Panier ajouté !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }


    
    
///////////////////////////   

}

    @Override
    public void modifier(int id, Panier panierModifie,int idf) throws SQLException {
          if (existePanier(id)) {
    } else {
        System.err.println("L'ID spécifié n'existe pas dans la base de données.");
        return; // Sortez de la méthode si l'ID n'existe pas
        }
    
    // Si l'ID existe, effectuez la mise à jour
   String req = "UPDATE panier SET idOffre=?,total=?, datePanier=? WHERE idPanier=?";
    PreparedStatement ps = connection.prepareStatement(req);

    // Spécifiez les valeurs pour les paramètres de la requête préparée
    ps.setInt(2, panierModifie.getTotal());
    ps.setInt(1,idf );
    ps.setDate(3, new java.sql.Date(panierModifie.getDatePanier().getTime()));
    ps.setInt(4, id);
  

    ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
  if (!existePanier(id)) {
        System.err.println("L'ID spécifié n'existe pas dans la base de données.");
        return; // Sortez de la méthode si l'ID n'existe pas
    }
    
    // Si l'ID existe, effectuez la suppression
    String req = "DELETE FROM panier WHERE idOffre=?";
    PreparedStatement ps = connection.prepareStatement(req);
    ps.setInt(1, id); 
    
    ps.executeUpdate();    }

    

    @Override
    public boolean existePanier(int id) throws SQLException {
    String req = "SELECT idPanier FROM panier WHERE idPanier=?";
    PreparedStatement ps = connection.prepareStatement(req);
    ps.setInt(1, id);
    
    ResultSet rs = ps.executeQuery();
    
    return rs.next();    }
    

    
    
    
    
    
    
    
    
    @Override
    public List<Panier> recuperer() throws SQLException {
  List<Panier> listePaniers = new ArrayList<>();

    // Écrivez la requête SQL pour récupérer les offres
    String req = "SELECT idPanier, total, datePanier, idOffre, id FROM panier";

    PreparedStatement ps = connection.prepareStatement(req);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
        
        Panier panier = new Panier();
        panier.setIdPanier(rs.getInt("idPanier"));
        panier.setDatePanier(rs.getDate("datePanier"));
        
       
         Personne personne = new Personne();
        personne.setId(rs.getInt("id")); // Remplacez le type de données approprié et la méthode d'obtention de l'ID de l'utilisateur
        panier.setPersonne(personne);
        
        Offre offre =new Offre();
        offre.setIdOffre(rs.getInt("idOffre"));
        panier.setOffre(offre);

        // Ajoutez l'offre à la liste
        listePaniers.add(panier);
    }

    return listePaniers;
}
    
    public List<Offre> getListeOffresParPanier(int idPanier) {
    List<Offre> listeOffres = new ArrayList<>(); // Créez une liste pour stocker les offres

    // Effectuez une requête SQL pour obtenir les offres associées à ce panier en utilisant l'ID du panier
    String query = "SELECT * FROM offre WHERE idPanier = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idPanier);
        ResultSet resultSet = ps.executeQuery();

       
        while (resultSet.next()) {
            int idOffre = resultSet.getInt("idOffre");
            String nomOffre = resultSet.getString("nomOffre");
            String description = resultSet.getString("description");
            Date dateDebut = resultSet.getDate("dateDebut");
            Date dateFin = resultSet.getDate("dateFin");
            String typeOffre = resultSet.getString("typeOffre");
            double valeurOffre = resultSet.getDouble("valeurOffre");
            String imageOffre = resultSet.getString("imageOffre");
            String status = resultSet.getString("status");

    Offre offre = new Offre(idOffre, nomOffre, description, dateDebut, dateFin, typeOffre, valeurOffre, imageOffre, status);
            listeOffres.add(offre);
        }
    } catch (SQLException ex) {
        ex.printStackTrace(); // Gestion de l'exception
    }

    return listeOffres;
}
///////////
    public boolean supprimerOffreDeListe(int idOffre, List<Offre> listeOffres) {
    // Parcourez la liste des offres pour trouver l'offre à supprimer en fonction de son ID
    for (Offre offre : listeOffres) {
        if (offre.getIdOffre() == idOffre) {
            // Supprimez l'offre de la liste
            listeOffres.remove(offre);
            // Renvoyez true pour indiquer que l'offre a été supprimée avec succès
            return true;
        }
    }
    // Si l'offre n'a pas été trouvée, renvoyez false pour indiquer qu'elle n'a pas été supprimée
    return false;
}
//////////
  
    
   public List<Panier> getAllPaniers() {
    List<Panier> paniers = new ArrayList<>();

    String sql = "SELECT * FROM panier";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            int idPanier = resultSet.getInt("idPanier");
            
            Panier panier = new Panier();
            panier.setIdPanier(idPanier);
            
            paniers.add(panier);
        }

    } catch (SQLException e) {
        e.printStackTrace();
       
    }

    return paniers;
}

    
//////////////////hethi eli te5dem affichi panier ta3 kol had 
    @Override
    public Panier getPanierParId(int id)  {
        ResultSet resultSet ;
        Panier panier = null;

        try {

           String sql = "SELECT panier.*, offre.* " +
                    "FROM panier " +
                    "JOIN offre ON panier.idoffre = offre.idoffre " +
                    "WHERE panier.id = ?";


            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            List<Offre> listeOffres = new ArrayList<Offre>();
          panier = new Panier();

            while (resultSet.next()) {
                  int idPanier = resultSet.getInt("idPanier");
                int total = resultSet.getInt("total");
                Date datePanier = resultSet.getDate("datePanier");

                panier.setIdPanier(idPanier);
                panier.setTotal(total);
                panier.setDatePanier(datePanier);

                int idOffre = resultSet.getInt("idOffre");
                String nomOffre = resultSet.getString("nomOffre");
                  TypeOffre typeOffre = TypeOffre.valueOf(resultSet.getString("typeOffre"));
                   int valeurOffre = resultSet.getInt("valeurOffre");
                    Date dateDebut = resultSet.getDate("dateDebut");
                Date dateFin = resultSet.getDate("dateFin");
                
               
                Offre offre = new Offre();
                offre.setIdOffre(idOffre);
                offre.setNomOffre(nomOffre);
                offre.setDateDebut(dateDebut);
                offre.setDateFin(dateFin);
                offre.setTypeOffre(typeOffre);
                offre.setValeurOffre(valeurOffre);
                listeOffres.add(offre);
               
                
                panier.setListeOffres(listeOffres);

            }

        
        
        }  catch( SQLException aaa) {   
        aaa.printStackTrace();}
       
        

        return panier;
    }
       ////stat
    public List<TypeOffre> getTypeOffreDeTousLesOffresDansLesPaniers() {
    List<TypeOffre> typesOffres = new ArrayList<>(); // Liste pour stocker les types d'offres de toutes les offres

    try {
        // Établissez la connexion à la base de données ici

        // Créez et exécutez la requête SQL pour récupérer les types d'offres
        String sql = "SELECT offre.typeOffre FROM panier " +
                     "INNER JOIN offre ON panier.idoffre = offre.idoffre";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Parcourez les résultats de la requête et ajoutez les types d'offres à la liste
        while (resultSet.next()) {
            TypeOffre typeOffre = TypeOffre.valueOf(resultSet.getString("typeOffre"));
            typesOffres.add(typeOffre);
        }

        // Fermez la connexion et les ressources ici

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return typesOffres;
}



     public List<Panier> getListeOffres() {
        // Implémentez la logique pour obtenir la liste de paniers
        // Cette méthode ne prend pas d'arguments
        return null;
    }
    
}
    
    
    
    

    

    
    
    
    
   



