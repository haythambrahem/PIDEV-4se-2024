/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

import java.lang.ProcessBuilder.Redirect.Type;
import java.sql.Date;

/**
 *
 * @author fadi saidi
 */
public class Location {
     private int id;
    private Logement logement;
    private Personne personne;

    private Date dateDebut;
    private Date dateFin;
    private int tarif;

    public Location(int id, Logement logement, Personne personne, java.util.Date dateDebut, java.util.Date dateFin, int tarif) {
    }

    public Location(int aInt, int aInt0, String string, String string0, int aInt1, String string1) {
    }

//    public Location(int id, String adr, String email, java.util.Date dateDebut, java.util.Date dateFin, int tarif) {
//    }
public Logement getLogement() {
        return logement;
    }

    public void setLogement(Logement logement) {
        this.logement = logement;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebuit) {
        this.dateDebut = dateDebuit;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    public Location() {
    }

    public Location(int id, Logement logement, Personne personne, Date dateDebut, Date dateFin, int tarif) {
        this.id = id;
        this.logement = logement;
        this.personne = personne;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.tarif = tarif;
    }
public Location(int id, String adr,String region, String email, String nom, String prenom, java.util.Date dateDebut, java.util.Date dateFin, int tarif) {
  // this.logement = logement;
    this.id = id;
    this.logement = new Logement();  // You should create a new Logement instance here and set its adr.
    this.logement.setAdr(adr);        // Set the adr of the Logement instance.
    this.logement.setRegion(region);
    
      
      
    this.personne = new Personne();  // You should create a new Personne instance here and set its email.
    this.personne.setEmail(email);    // Set the email of the Personne instance.
    this.personne.setNom(nom);
    this.personne.setPrenom(prenom);
    this.dateDebut = new Date(dateDebut.getTime()); // You should convert java.util.Date to java.sql.Date.
    this.dateFin = new Date(dateFin.getTime());     // You should convert java.util.Date to java.sql.Date.
    this.tarif = tarif;
}
    @Override
    public String toString() {
        return"Information" +
           "\nLe Client est  " + personne.getNom() +" "+ personne.getPrenom()+" son Email est "+personne.getEmail()+
                "\nLe  Logement est " + logement.getAdr() + " La region de "+logement.getRegion()+
           
           "\n  Start Date: " + dateDebut +
           "\n  End Date: " + dateFin +
           "\n  Tarif a payer: " + tarif +
           "\n";
    }

   
    
    
    
    
    
    
    
    
    
    
}
