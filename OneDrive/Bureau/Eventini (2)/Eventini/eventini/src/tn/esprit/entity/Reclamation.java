/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entité;

import java.util.List;

/**
 *
 */
public class Reclamation {
    private int idReclamation,idUser;
    private String message,etat;
    private List<Commentaire> commentaire;
     public List<Commentaire> getCommentaire() {
        return commentaire ;
    }

    public void setCommentaire(List<Commentaire> commentaire) {
        this.commentaire = commentaire;
    }
    public Reclamation(int id) {
        this.idReclamation = id;
    }    
   
    public Reclamation() {
    }
   
    public Reclamation(int id, String message, String etat,int idUser) {
        this.idReclamation = id;
        this.message = message;
        this.etat = etat;
        this.idUser = idUser;
    }

    public Reclamation(int idUser, String message, String etat) {
        this.idUser = idUser;
        this.message = message;
        this.etat = etat;
    }



    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

 

    public int getId() {
        return idReclamation;
    }

    public void setId(int id) {
        this.idReclamation = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + idReclamation +  ", idUser=" + idUser + ", message=" + message + ", etat=" + etat + '}';
    }

 
   
}