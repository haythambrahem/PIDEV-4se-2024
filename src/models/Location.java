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
    private Personne personne;

    private Date dateDebut;
    private Date dateFin;
    private int tarif;

    public Location(int id, Logement logement, Personne personne, java.util.Date dateDebut, java.util.Date dateFin, int tarif) {
    }
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

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", logement=" + logement + ", personne=" + personne + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", tarif=" + tarif + '}';
    }

   
    
    
    
    
    
    
    
    
    
    
}
