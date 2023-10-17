/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

import java.time.LocalDate;

 
/**
 *
 * @author hayth
 */
public class Personne {
    private int id; 
    private String nom , prenom ;
    private String email;
    private String[] roles;
    private String password;
    private LocalDate dateNaise;
    private Adresse adresse;
    private String pprofile;
    private boolean is_banned;
    private int roleJava_client_id;

    public int getRoleJava_client_id() {
        return roleJava_client_id;
    }

    public void setRoleJava_client_id(int roleJava_client_id) {
        this.roleJava_client_id = roleJava_client_id;
    }

    
    
    public String getPprofile() {
        return pprofile;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setPprofile(String pprofile) {
        this.pprofile = pprofile;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    
    public String getEmail() {
        return email;
    }

    public String[] getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    

    public LocalDate getDateNaise() {
        return dateNaise;
    }

    public void setDateNaise(LocalDate dateNaise) {
        this.dateNaise = dateNaise;
    }

    public Personne(int id, String nom, String prenom, String email, String[] roles, String password, LocalDate dateNaise, Adresse adresse ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.dateNaise = dateNaise;
        this.adresse = new Adresse(id, " street", " city","postalCode","country");;
    }

public Personne(String nom, String prenom, String email, String password, LocalDate dateNaise) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.dateNaise = dateNaise;
    }    

public Personne(String nom, String prenom, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    } 

    public Personne(int id, String nom, String prenom, LocalDate dateNaise) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaise = dateNaise;
    }

    public Personne(int id, String nom, String prenom, String email, LocalDate dateNaise) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaise = dateNaise;
    }
    
    
    

    public Personne(int id, String nom, String prenom ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;

    }

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        
    }

    public Personne() {
    }


    @Override
    public String toString() {
        return "Personne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" 
                + email + ", roles=" + roles + ", password=" + password + ", dateNaise=" 
                + dateNaise + ", adresse=" + adresse + '}';
    }
    
    
    
    
    
    
}
