/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.List;

/**
 *
 * @author FADI
 */
public interface LogService<T> {
     void ajouterLogement(T t);
    void supprimerLogement(T t);
    void modifierLogement(T t);
    List<T> affihcerLogement();
}
