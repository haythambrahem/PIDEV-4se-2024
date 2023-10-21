/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Location;
import services.ServiceLocation;

/**
 * FXML Controller class
 *
 * @author fadi saidi
 */
public class MyLocationController implements Initializable {

    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_nom;
    @FXML
    private JFXTextField txt_prenom;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_dateD;
    @FXML
    private JFXTextField txt_dateF;
    @FXML
    private JFXTextField txt_tarif;
    @FXML
    private ListView<Location> MesLocation;
    @FXML
    private Button search_email;
    @FXML
    private Button contrat;
    Connection con;
    Statement ste;
    @FXML
    private Button move;
    @FXML
    private Label yourEmail;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          ServiceLocation serviceLocation = new ServiceLocation();
       // List<Location> searchResults = serviceLocation.searchLocationsByEmail(critere);
//         List<Location> locations = serviceLocation.getAllLocations();
//         ObservableList<Location> locationList = FXCollections.observableArrayList(locations);
//         MesLocation.setItems(locationList);
 String userEmail = yourEmail.getText().trim();

        // Fetch and display the data
        populateListView(userEmail);
    }  
     private void populateListView(String email) {
        ServiceLocation serviceLocation = new ServiceLocation();

        // Fetch data based on the extracted email
        List<Location> initialData = serviceLocation.searchLocationsByEmail(email);

        // Check if data is available
        if (!initialData.isEmpty()) {
            MesLocation.getItems().setAll(initialData);
        }
    }

    @FXML
    private void listMesLocation(MouseEvent event) {
    }

    @FXML
    private void rechercherEmail(ActionEvent event) {
      //  String critere = txt_email.getText().trim();
         String critere = yourEmail.getText().trim();
    if (critere.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ de recherche est vide.");
        alert.showAndWait();
    } else {
        ServiceLocation serviceLocation = new ServiceLocation();
        List<Location> searchResults = serviceLocation.searchLocationsByEmail(critere);

        if (searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Aucun résultat trouvé pour le critère de recherche.");
            alert.showAndWait();
        } else {
            MesLocation.getItems().setAll(searchResults);
        }
    }
}

    @FXML
    private void GetContrat(ActionEvent event) {
    }

    @FXML
    private void move(ActionEvent event) {
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Location.fxml"));
            Parent root = loader.load();

            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
