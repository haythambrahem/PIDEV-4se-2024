/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author siwar
 */
public class Evenement {
    private int idEvt;
    private String titreEvt;
    private String nomOrg;
    private String descEvt;
    private LocalTime hdEvt;
    private LocalTime hfEvt;
    private String adresseEvt;
    private String typeEvt;
    private Date dateEvt;
    private int vote;

    public Evenement(String danse, String oklm, String boissons, String h, String minuit, String rafraf, String evènement_Public, Date dateEvt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdEvt() {
        return idEvt;
    }

    public String getTitreEvt() {
        return titreEvt;
    }

    public String getNomOrg() {
        return nomOrg;
    }

    public String getDescEvt() {
        return descEvt;
    }

    public LocalTime getHdEvt() {
        return hdEvt;
    }

    public LocalTime getHfEvt() {
        return hfEvt;
    }

    public String getAdresseEvt() {
        return adresseEvt;
    }

    public String getTypeEvt() {
        return typeEvt;
    }

    public Date getDateEvt() {
        return dateEvt;
    }

    public int getVote() {
        return vote;
    }

    

    public void setIdEvt(int idEvt) {
        this.idEvt = idEvt;
    }

    public void setTitreEvt(String titreEvt) {
        this.titreEvt = titreEvt;
    }

    public void setNomOrg(String nomOrg) {
        this.nomOrg = nomOrg;
    }

    public void setDescEvt(String descEvt) {
        this.descEvt = descEvt;
    }

 public void setHdEvt(LocalTime hdEvt) {
    this.hdEvt = hdEvt;
}

    public void setHfEvt(LocalTime hfEvt) {
    this.hfEvt = hfEvt;
}


    public void setAdresseEvt(String adresseEvt) {
        this.adresseEvt = adresseEvt;
    }

    public void setTypeEvt(String typeEvt) {
        this.typeEvt = typeEvt;
    }

    public void setDateEvt(Date dateEvt) {
        this.dateEvt = dateEvt;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

   

    public Evenement(int idEvt, String titreEvt, String nomOrg, String descEvt, LocalTime hdEvt, LocalTime hfEvt, String adresseEvt, String typeEvt) {
        this.idEvt = idEvt;
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
        this.typeEvt = typeEvt;
    }

    public Evenement(int idEvt, String titreEvt, String nomOrg, String descEvt, LocalTime hdEvt, LocalTime hfEvt, String adresseEvt, String typeEvt, Date dateEvt, int vote) {
        this.idEvt = idEvt;
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
        this.typeEvt = typeEvt;
        this.dateEvt = dateEvt;
        this.vote = vote;
    }

    public Evenement(String titreEvt, String nomOrg, String descEvt, LocalTime hdEvt, LocalTime hfEvt, String adresseEvt, String typeEvt, Date dateEvt, int vote) {
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
        this.typeEvt = typeEvt;
        this.dateEvt = dateEvt;
        this.vote = vote;
    }
    

    public Evenement(String titreEvt, String nomOrg, String descEvt, LocalTime hdEvt, LocalTime hfEvt, String adresseEvt, String typeEvt) {
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
        this.typeEvt = typeEvt;
    }

    public Evenement() {
    }

    public Evenement(int idEvt, String titreEvt, String nomOrg, String descEvt, LocalTime hdEvt, LocalTime hfEvt, String adresseEvt, String typeEvt, Date dateEvt) {
        this.idEvt = idEvt;
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
        this.typeEvt = typeEvt;
        this.dateEvt = dateEvt;
    }

    public Evenement(String titreEvt, String nomOrg, String descEvt, LocalTime hdEvt, LocalTime hfEvt, String adresseEvt, String typeEvt, Date dateEvt) {
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
        this.typeEvt = typeEvt;
        this.dateEvt = dateEvt;
    }
    

    @Override
    public String toString() {
        return "Evenement{" + "idEvt=" + idEvt + ", titreEvt=" + titreEvt + ", nomOrg=" + nomOrg + ", descEvt=" + descEvt + ", hdEvt=" + hdEvt + ", hfEvt=" + hfEvt + ", adresseEvt=" + adresseEvt + ", typeEvt=" + typeEvt + ", dateEvt=" + dateEvt + '}';
    }
    
}
