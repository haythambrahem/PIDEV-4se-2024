/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.swing.JComboBox;
import tn.esprit.entity.Evenement;
import tn.esprit.services.ServiceEvenement;

/**
 * FXML Controller class
 *
 * @author siwar
 */
public class AfficherEventController implements Initializable {
    @FXML
    private TextField titre_af;
    @FXML
    private TextField nomorg_af;
    @FXML
    private TextField hd_af;
     @FXML
    private TextField hf_af;
    @FXML
    private TextField adresse_af;
    @FXML
    private ComboBox type_af;
    @FXML
    private TextField desc_af;
    @FXML
    private ListView liste_af;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public TextField getTitre_af() {
        return titre_af;
    }
     public void setTitre_af(String titre_af) {
        this.titre_af.setText(titre_af);
    }
    
    public TextField getNomorg_af() {
        return nomorg_af;
    }
     public void setNomorg_af(String nomorg_af) {
        this.nomorg_af.setText(nomorg_af);
    }

    public TextField getHd_af() {
        return hd_af;
    }
    public void setHd_af(String hd_af) {
        this.hd_af.setText(hd_af);
    }
    
    public TextField getHf_af() {
        return hf_af;
    }
    public void setHf_af(String hf_af) {
        this.hf_af.setText(hf_af);
    }

    public TextField getAdresse_af() {
        return adresse_af;
    }
    public void setAdresse_af(String adresse_af) {
        this.adresse_af.setText(adresse_af);
    }

    public ComboBox getType_af() {
        return type_af;
    }
//    public void setType_af(String type_af) {
//        this.type_af.setText(type_af);
//    }

    public TextField getDesc_af() {
        return desc_af;
    }
    public void setDesc_af(String desc_af) {
        this.desc_af.setText(desc_af);
    }
    public ListView getListe_af() {
        return liste_af;
    }
public void setListe_af(ObservableList<Evenement> liste_af) {
    // Assuming that liste_af is a ListView<Evenement>
    this.liste_af.setItems(liste_af);
}

private void loadInitialDataFromDatabase() {
    ServiceEvenement se = new ServiceEvenement();
    List<Evenement> initialEvenement = se.afficherEv();
   
    // Create an ObservableList from the initial data
    ObservableList<Evenement> evenementObservableList = FXCollections.observableArrayList(initialEvenement);
    
    // Set the items in your ListView
    liste_af.setItems(evenementObservableList);
}

}
