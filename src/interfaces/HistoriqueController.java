/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import models.Location;
import models.Logement;
import services.ServiceLocation;
import services.ServiceLogement;

/**
 * FXML Controller class
 *
 * @author fadi saidi
 */
public class HistoriqueController implements Initializable {

    @FXML
    private JFXTextField txt_search;
    @FXML
    private JFXTextField txt_tarif;
    @FXML
    private JFXTextField txt_type;
    @FXML
    private Label txt_region;
    @FXML
    private JFXTextField txt_nomPrenom;
    @FXML
    private JFXTextField txt_tele;
    @FXML
    private JFXTextField txt_cin;
    @FXML
    private JFXTextField txt_dateDebut;
    @FXML
    private JFXTextField txt_dateFin;
    @FXML
    private JFXButton search;
    @FXML
    private JFXListView<Location> listview_historique;
    @FXML
    private JFXTextField txt_event;
Connection con;
    Statement ste;
    @FXML
    private JFXTextField txt_adr;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceLocation serviceLocation = new ServiceLocation();
         List<Location> locations = serviceLocation.getAllLocations();
         ObservableList<Location> locationList = FXCollections.observableArrayList(locations);
         listview_historique.setItems(locationList);
    }    

    @FXML
    private void searchHistorique(ActionEvent event) {
      String critere = txt_search.getText().trim();

    if (critere.isEmpty()) {
        // Show an error alert if the input is empty
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ de recherche est vide.");
        alert.showAndWait();
    } else {
        ServiceLocation serviceLocation = new ServiceLocation();
        List<Location> searchResults = serviceLocation.searchLocationsByCriteria(critere);

        if (searchResults.isEmpty()) {
            // Show an information alert if no results are found
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Aucun résultat trouvé pour le critère de recherche.");
            alert.showAndWait();
        } else {
            // Populate the listview_historique with the search results
            listview_historique.getItems().setAll(searchResults);
        }
    }
}

    @FXML
    private void listLocation(MouseEvent event) {
        Location selectedLocation = listview_historique.getSelectionModel().getSelectedItem();

    if (selectedLocation != null) {
        // Get the associated Logement
        Logement logement = selectedLocation.getLogement();

        // Populate the fields with Logement details
//        txt_adr.setText(logement.getAdr());
//        txt_tarif.setText(String.valueOf(selectedLocation.getTarif()));
//        txt_region.setText(logement.getRegion());

        // Update other fields as needed
    }
}
    
}
