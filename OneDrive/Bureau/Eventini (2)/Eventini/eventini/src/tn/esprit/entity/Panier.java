
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

/**
 *
 * @author imen
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tn.esprit.services.ServicePanier;

public class Panier {
    private int idPanier;
    private List<Offre> listeOffres;
    private int total;
    private Date datePanier;
    private Offre Offre;
    private int iduser;
     private Personne personne;
    private int id;

    public Panier(int idPanier, List<Offre> listeOffres, int total, Date datePanier, Offre Offre,  int iduser, Personne personne, int id) {
        this.idPanier = idPanier;
        this.listeOffres = listeOffres;
        this.total = total;
        this.datePanier = datePanier;
        this.Offre = Offre;
        this.iduser = iduser;
        this.personne = personne;
        this.id = id;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Panier(int idPanier, List<Offre> listeOffres, int total, Date datePanier, Offre Offre,  int iduser, Personne personne) {
        this.idPanier = idPanier;
        this.listeOffres = listeOffres;
        this.total = total;
        this.datePanier = datePanier;
        this.Offre = Offre;
        this.iduser = iduser;
        this.personne = personne;
    }
    
 
    public Panier() {
        
        this.listeOffres= new ArrayList<Offre>();
    }
    
    

    public Panier(int idPanier, int iduser, List<Offre> listeOffres, int total, Date datePanier) {
        this.idPanier = idPanier;
        this.iduser = iduser;
        this.listeOffres = listeOffres;
        this.total = total;
        this.datePanier = datePanier;
    } 
    public Panier(int idPanier, List<Offre> listeOffres, int total, Date datePanier, Offre Offre) {
        this.idPanier = idPanier;
        this.listeOffres = listeOffres;
        this.total = total;
        this.datePanier = datePanier;
        this.Offre = Offre;
    }

    public Panier(int idPanier, List<Offre> listeOffres, int total, Date datePanier) {
        this.idPanier = idPanier;
        this.listeOffres = listeOffres;
        this.total = total;
        this.datePanier = datePanier;
    }
     public Panier(int idPanier, int total, Date datePanier) {
        this.idPanier = idPanier;
        this.total = total;
        this.datePanier = datePanier;}

//    public Panier(int idPanier, List<Offre> listeOffres, int total, Date datePanier, Offre Offre, User user) {
//        this.idPanier = idPanier;
//        this.listeOffres = listeOffres;
//        this.total = total;
//        this.datePanier = datePanier;
//        this.Offre = Offre;
//        this.user = user;
//    }
//    
// public static int calculerTotalPanier(List<Offre> offres) {
//    int total = 0;
//    for (Offre offre : offres) {
//        total += offre.getValeurOffre();
//    }
//    return total;
//}


    public Panier(int idPanier, int iduser, List<Offre> listeOffres, int total, Date datePanier, String nomOff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Panier(int idPanier, int iduser, int total, Date datePanier, List<Offre> listeOffres) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }






    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

public static int calculerTotalPanier(int idPanier) {
    // Utilisez votre méthode pour obtenir la liste des offres associées au panier
  List<Offre> offres = ServicePanier.getListeOffres(idPanier);

    int total = 0;
    for (Offre offre : offres) {
        total += offre.getValeurOffre();
    }
    return total;
}
    public List<Offre> getListeOffres() {
        return listeOffres;
    }

    public void setListeOffres(List<Offre> listeOffres) {
        this.listeOffres = listeOffres;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getDatePanier() {
        return datePanier;
    }

    public void setDatePanier(Date datePanier) {
        this.datePanier = datePanier;
    }

    public Offre getOffre() {
        return Offre;
    }

    public void setOffre(Offre Offre) {
        this.Offre = Offre;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

  
    
}
