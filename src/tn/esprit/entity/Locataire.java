/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

import java.sql.Date;
import java.time.LocalDate;
//import java.util.Date;


/**
 *
 * @author hayth
 */
public class Locataire extends Personne{
    
    String tele;
    String cin ;


    public String getTele() {
        return tele;
    }

    public String getCin() {
        return cin;
    }
    

    public void setTele(String tele) {
        this.tele = tele;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Locataire() {
    }

     public Locataire(int id, String nom, String prenom, LocalDate dateNaise, String tele, String cin) {
        super(id, nom, prenom,dateNaise);
        this.tele = tele;
        this.cin = cin;
    }

    @Override
    public String toString() {
        return "Locataire{" + "id=" + getId() + ", nom=" + getNom() + ", prenom=" + getPrenom()+  ", dateNaise=" + getDateNaise() + ", tele=" + tele + ", cin=" + cin + '}';
    }

    
    
    
}
