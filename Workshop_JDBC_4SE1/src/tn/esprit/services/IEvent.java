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
public interface IEvent<Evenement> {
    void ajouterEv(Evenement ev);
    void modifierEv(Evenement ev);
    void supprimerEv(Evenement ev);
    List<Evenement> afficherEv();
}
