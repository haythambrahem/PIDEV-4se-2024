/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

/**
 *
 * @author hayth
 */
public class Client extends Personne {
    private String ign;



    public Client(String ign, String nom, String prenom, String email, String password) {
        super(nom, prenom, email, password);
        this.ign = ign;
    }

    public Client(String ign, int id, String nom, String prenom, String email, LocalDate dateNaise) {
        super(id, nom, prenom, email, dateNaise);
        this.ign = ign;
    }
    
    

    public String getIgn() {
        return ign;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

  

    public Client() {
    }

    public Client( int id, String nom, String prenom,String email,String[] roles,String password, LocalDate dateNaise, Adresse adresse,String ing) {
        super(id, nom, prenom, email, roles, password, dateNaise, adresse);
        this.ign = ign;
    }

    public Client(String ign, int id, String nom, String prenom, String email, String[] roles, String password, LocalDate dateNaise, String pprofile, boolean is_banned, int roleJava_client_id) {
        super(id, nom, prenom, email, roles, password, dateNaise, pprofile, is_banned, roleJava_client_id);
        this.ign = ign;
    }

    public Client(String ign, int id, String nom, String prenom, String email, String[] roles, String password, LocalDate dateNaise, Adresse adresse, String pprofile, boolean is_banned, int roleJava_client_id) {
        super(id, nom, prenom, email, roles, password, dateNaise, adresse, pprofile, is_banned, roleJava_client_id);
        this.ign = ign;
        this.setRoleJava_client_id(roleJava_client_id);
    }

    public Client(String ign, int id, String nom, String prenom, String email, LocalDate dateNaise, String pprofile, int roleJava_client_id) {
        super(id, nom, prenom, email, dateNaise, pprofile, roleJava_client_id);
        this.ign = ign;
    }

//    public Client(String ign, String nom, String prenom, String email, String[] roles, String password, LocalDate dateNaise, String pprofile, boolean is_banned, boolean is_verified) {
//        super(nom, prenom, email, roles, password, dateNaise, pprofile, is_banned, is_verified);
//        this.ign = ign;
//    }
    public Client(String ign, String nom, String prenom, String email, String[] roles, String password, LocalDate datee, String profilepic, boolean is_banned, boolean is_verified) {
        super(nom, prenom, email, roles, password, datee, profilepic, is_banned, is_verified);
        this.ign = ign;
    }

    public Client(String ign, int id, String nom, String prenom, String email, int roleJava_client_id, LocalDate dateNaise, String pprofile, boolean is_banned, boolean is_verified) {
        super(id, nom, prenom, email, roleJava_client_id, dateNaise, pprofile, is_banned, is_verified);
        this.ign = ign;
    }
    
    

   public String toString() {
    return "Client{" +
            "id=" + getId() + 
            ", nom='" + getNom() + '\'' +
            ", prenom='" + getPrenom() + '\'' + 
            ", email='" + getEmail() + '\'' + 
            ", roles=" + Arrays.toString(getRoles()) + 
            ", password='" + getPassword() + '\'' + 
            ", dateNaissance=" + getDateNaise() + 
            ", adresse=" + getAdresse() + 
            ", ign='" + ign + '\'' +
            '}';
} 
}

