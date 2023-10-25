/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import tn.esprit.entity.Personne;
import java.sql.SQLException;
import java.util.List;
import tn.esprit.entity.Offre;

/**
 *
 * @author imen
 */
public interface IserviceOffre {
    public void ajouter(Offre of ,Personne  pers) throws SQLException ;

   // public void modifier(int id, Offre offreModifie) throws SQLException ;
  public void modifier(String nomOffre, Offre offreModifie) throws SQLException ;


    public void supprimer(String nomOffre) throws SQLException ;

    List<Offre> recuperer() throws SQLException ;
    public boolean existeOffre(int id) throws SQLException;
     public boolean offreExisteDeja(String titre) ;
     public Offre chercherOffre(String nomOffre) throws SQLException;
    
}
