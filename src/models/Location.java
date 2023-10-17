/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author fadi saidi
 */
public class Location {
     private int id;
    private Logement logement;
    private Locataire locataire;

    private Date dateDebut;
    private Date dateFin;
    private int tarif;

    public Location(int id, Logement logement, Locataire locataire, java.util.Date dateDebut, java.util.Date dateFin, int tarif) {
    }
public Logement getLogement() {
        return logement;
    }

    public void setLogement(Logement logement) {
        this.logement = logement;
    }

    public Locataire getLocataire() {
        return locataire;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
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

    public Location(int id, Logement logement, Locataire locataire, Date dateDebut, Date dateFin, int tarif) {
        this.id = id;
        this.logement = logement;
        this.locataire = locataire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.tarif = tarif;
    }

   
    
    
    
    
    
    
    
    
    
    
}
