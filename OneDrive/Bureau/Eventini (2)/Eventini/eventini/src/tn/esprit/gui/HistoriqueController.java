/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entity.Locataire;
import tn.esprit.entity.Location;
import tn.esprit.entity.Logement;
import tn.esprit.services.ServiceLocation;
import tn.esprit.services.ServiceLogement;

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
    private JFXTextField txt_region;
    @FXML
    private JFXTextField txt_dateDebut;
    @FXML
    private JFXTextField txt_dateFin;
    @FXML
    private JFXButton search;
    @FXML
    private JFXListView<Location> listview_historique;
Connection con;
    Statement ste;
    public ResultSet result;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_nom;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_prenom;
    @FXML
    private Button move;
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
    public ObservableList<Location> data = FXCollections.observableArrayList();

    @FXML
    private void searchHistorique(ActionEvent event) {
      String critere = txt_search.getText().trim();

    if (critere.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ de recherche est vide.");
        alert.showAndWait();
    } else {
        ServiceLocation serviceLocation = new ServiceLocation();
        List<Location> searchResults = serviceLocation.searchLocationsByCriteria(critere);

        if (searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Aucun résultat trouvé pour le critère de recherche.");
            alert.showAndWait();
        } else {
            listview_historique.getItems().setAll(searchResults);
        }
    }
}

    @FXML
    private void listLocation(MouseEvent event) throws SQLException {
//   
 Location selectedLocation = listview_historique.getSelectionModel().getSelectedItem();

    if (selectedLocation != null) {
        // Populate other fields based on the selected location, if needed
        txt_adr.setText(selectedLocation.getLogement().getAdr());
        txt_tarif.setText(String.valueOf(selectedLocation.getTarif()));
        txt_region.setText(selectedLocation.getLogement().getRegion());
        txt_email.setText(selectedLocation.getPersonne().getEmail());
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define your date format
String formattedDate = dateFormat.format(selectedLocation.getDateDebut()); // Format the Date
txt_dateDebut.setText(formattedDate); // Set the formatted date in the TextField
        txt_nom.setText(selectedLocation.getPersonne().getNom());
        String formattedDateF = dateFormat.format(selectedLocation.getDateFin()); // Format the Date
txt_dateFin.setText(formattedDateF);
         txt_prenom.setText(selectedLocation.getPersonne().getPrenom());
    }
}


    public void remplirTable(){
//        
//         table.getItems().clear();
//        String sql="select * from location";
//        try {
//            ste=con.prepareStatement(sql);
//            result=ste.executeQuery(sql);
//            while(result.next()) {
//                 data.add(new Location(result.getInt("idLocation"),result.getInt("logement"), result.getString("email"), result.getString("dateDebut"), result.getInt("dateFin"), result.getString("tarif")));
//                
//              //  data.add(new Locataire(result.getInt("id"),result.getString("nomprenomL"),result.getDate("datenaiseL");result.getString("teleL"),result.getString("CIN")));
//            //col_cin.setCellValueFactory(new PropertyValueFactory<Locataire,String>("CIN"))
//                
//               
//            }
//            
//        } catch (SQLException ex) {
//        }
//        cln_client.setCellValueFactory(new PropertyValueFactory<Location, String>("personne"));
//        cln_debut.setCellValueFactory(new PropertyValueFactory<Location, String>("dateDebut"));
//        cln_fin.setCellValueFactory(new PropertyValueFactory<Location, String>("dateFin"));
//        cln_logement.setCellValueFactory(new PropertyValueFactory<Location, Integer>("logement"));
//        cln_tarif.setCellValueFactory(new PropertyValueFactory<Location, Integer>("tarif"));
//         table.setItems(data);
//        
   }

    @FXML
    private void OnKeyPressedSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
        // This block of code will be executed when the Enter key is pressed
        String searchCriteria = txt_search.getText(); // Get the search criteria from your text field
        ServiceLocation serviceLocation = new ServiceLocation();
        // Call your search function with the search criteria
        List<Location> searchResults = serviceLocation.searchLocationsByCriteria(searchCriteria);

        // Update your ListView or any other UI element with the search results
        ObservableList<Location> searchResultsList = FXCollections.observableArrayList(searchResults);
        listview_historique.setItems(searchResultsList);
    } 
        
    }

    @FXML
    private void OnKeyTypedSearch(KeyEvent event) {
        String searchCriteria = txt_search.getText(); // Get the search criteria from your text field
    ServiceLocation serviceLocation = new ServiceLocation();
    // Call your search function with the search criteria
    List<Location> searchResults = serviceLocation.searchLocationsByCriteria(searchCriteria);

    // Update your ListView or any other UI element with the search results
    ObservableList<Location> searchResultsList = FXCollections.observableArrayList(searchResults);
    listview_historique.setItems(searchResultsList);
    }

    @FXML
    private void move(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Back1.fxml"));
            Parent root = loader.load();

            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogement(ActionEvent event) {
           try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Logement.fxml"));
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
    


