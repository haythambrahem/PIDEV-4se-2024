package com.eventhub.services;

import com.eventhub.entities.Ami;
import com.eventhub.entities.User;

import java.util.List;

public interface AmiService {
    boolean ajouterAmi(Ami ami);

    List<Ami> listerAmis(User utilisateur);

    List<Ami> rechercherAmiParNom(String nomRecherche, User utilisateurConnecte);

    boolean supprimerInvitation(User utilisateur, Ami ami);

    void actualiserListeAmis(User utilisateurConnecte);

    boolean accepterInvitation(User utilisateur, Ami ami);

    boolean rejeterInvitation(User utilisateur, Ami ami);

    List<Ami> listerInvitations(User utilisateur);
}