/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

import java.time.LocalDate;

/**
 *
 * @author siwar
 */
public class Evenement {
     private int idEvt;
    private String titreEvt;
    private String nomOrg;
    private String descEvt;
    private String hdEvt; //heure du début de l'évènement//
    private String hfEvt; //heure de la fin de l'évènement//
    private String adresseEvt;
    private String typeEvt;
//    private LocalDate dateEvt;

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

    public String getHdEvt() {
        return hdEvt;
    }

    public String getHfEvt() {
        return hfEvt;
    }

    public String getAdresseEvt() {
        return adresseEvt;
    }

    public String getTypeEvt() {
        return typeEvt;
    }

////    public LocalDate getDateEvt() {
////        return dateEvt;
////    }

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

    public void setHdEvt(String hdEvt) {
        this.hdEvt = hdEvt;
    }

    public void setHfEvt(String hfEvt) {
        this.hfEvt = hfEvt;
    }

    public void setAdresseEvt(String adresseEvt) {
        this.adresseEvt = adresseEvt;
    }

    public void setTypeEvt(String typeEvt) {
        this.typeEvt = typeEvt;
    }

////    public void setDateEvt(LocalDate dateEvt) {
////        this.dateEvt = dateEvt;
////    }

    public Evenement(int idEvt, String titreEvt, String nomOrg, String descEvt, String hdEvt, String hfEvt, String adresseEvt, String typeEvt) {
        this.idEvt = idEvt;
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
        this.typeEvt = typeEvt;
     
    }

    public Evenement(String titreEvt, String nomOrg, String descEvt, String hdEvt, String hfEvt, String adresseEvt, String typeEvt) {
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

    public Evenement(String titreEvt, String nomOrg, String descEvt, String hdEvt, String hfEvt, String adresseEvt) {
        this.titreEvt = titreEvt;
        this.nomOrg = nomOrg;
        this.descEvt = descEvt;
        this.hdEvt = hdEvt;
        this.hfEvt = hfEvt;
        this.adresseEvt = adresseEvt;
    }
   

    @Override
    public String toString() {
        return "Evenement{" + "idEvt=" + idEvt + ", titreEvt=" + titreEvt + ", nomOrg=" + nomOrg + ", descEvt=" + descEvt + ", hdEvt=" + hdEvt + ", hfEvt=" + hfEvt + ", adresseEvt=" + adresseEvt + ", typeEvt=" + typeEvt + '}';
    }
    
    

    
}
