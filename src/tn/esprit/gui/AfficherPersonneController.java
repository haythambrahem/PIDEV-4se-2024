/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import tn.esprit.services.ServicePersonne;
import tn.esprit.entity.Personne;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.util.List;


/**
 * FXML Controller class
 *
 * @author Fayechi
 */
public class AfficherPersonneController implements Initializable {

    @FXML
    private TextField recNom;
    @FXML
    private TextField recPrenom;
    @FXML
    private TextField recList;
    @FXML
    private ListView<String> Vlist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Initialize the ListView
        Vlist.setItems(FXCollections.observableArrayList());

        // You can retrieve the list of Personne objects and populate the ListView
        List<Personne> personnes = retrievePersonnesFromDatabase(); // Implement this method to retrieve the data

        // Populate the ListView with person information
        for (Personne personne : personnes) {
            String personInfo = "ID: " + personne.getId() + ", Nom: " + personne.getNom() + ", Prenom: " + personne.getPrenom();
            Vlist.getItems().add(personInfo);
        }
    }
     // Implement a method to retrieve the list of Personne objects from your database
    private List<Personne> retrievePersonnesFromDatabase() {
        // Implement database retrieval logic here
        // Return a list of Personne objects
        ServicePersonne ps = new ServicePersonne();
        return ps.afficher();
    }

    public TextField getRecNom() {
        return recNom;
    }

    public void setRecNom(String recNom) {
        this.recNom.setText(recNom);
    }

    public TextField getRecPrenom() {
        return recPrenom;
    }

    public void setRecPrenom(String recPrenom) {
        this.recPrenom.setText(recPrenom);
    }

    public TextField getRecList() {
        return recList;
    }

    public void setRecList(String recList) {
        this.recList.setText(recList);
    }
    
    
}
