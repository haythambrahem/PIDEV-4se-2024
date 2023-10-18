/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

/**
 *
 * @author siwar
 */
public class Reservation {
    private int idBillet ,idEvt; 
    private String titreEvt;
    private Float prixBillet;

    public int getIdBillet() {
        return idBillet;
    }

    public int getIdEvt() {
        return idEvt;
    }

    public String getTitreEvt() {
        return titreEvt;
    }

    public Float getPrixBillet() {
        return prixBillet;
    }

    public void setIdBillet(int idBillet) {
        this.idBillet = idBillet;
    }

    public void setIdEvt(int idEvt) {
        this.idEvt = idEvt;
    }

    public void setTitreEvt(String titreEvt) {
        this.titreEvt = titreEvt;
    }

    public void setPrixBillet(Float prixBillet) {
        this.prixBillet = prixBillet;
    }

    public Reservation() {
    }

    public Reservation(int idBillet, int idEvt, String titreEvt, Float prixBillet) {
        this.idBillet = idBillet;
        this.idEvt = idEvt;
        this.titreEvt = titreEvt;
        this.prixBillet = prixBillet;
    }

    public Reservation(int idEvt, String titreEvt, Float prixBillet) {
        this.idEvt = idEvt;
        this.titreEvt = titreEvt;
        this.prixBillet = prixBillet;
    }
    
    
  
    
}
