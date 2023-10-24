/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.List;

/**
 *
 * @author hayth
 */
public interface IServiceAdersse <T>{
     // Create (Ajouter) a new entity
    void ajouter(T t);

    // Delete (Supprimer) an entity
    void supprimer(T t);

    // Update (Modifier) an entity
    void modifier(T t);

    // Retrieve (Afficher) all entities
    List<T> afficher();

    // Search (Rechercher) for entities by ID
    List<T> rechercher(int id);
}
