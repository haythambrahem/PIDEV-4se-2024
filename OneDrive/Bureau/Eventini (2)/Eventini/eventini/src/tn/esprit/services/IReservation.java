/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.List;

/**
 *
 * @author siwar
 */
public interface IReservation<Evenement> {
    void ajouterRes(Evenement ev);
    void modifierRes(Evenement ev);
    void supprimerRes(Evenement ev);
    List<Evenement> afficherRes();
    
    
}