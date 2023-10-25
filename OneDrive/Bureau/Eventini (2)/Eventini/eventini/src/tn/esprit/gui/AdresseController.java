/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entity.Adresse;
import tn.esprit.entity.Client;
import tn.esprit.services.Global;
import tn.esprit.services.ServiceAdresse;
import tn.esprit.services.SessionManager;
import static tn.esprit.services.SessionManager.getSession;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class AdresseController implements Initializable {

    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField countryField;
    @FXML
    private TextArea addressTextArea;

    private Adresse loggedInClientAddress; // Store the address of the currently logged-in client

    String sessionId = Global.sessionId;
    Client client = SessionManager.getSession(sessionId);
    @FXML
    private TableView<?> adresseTable;
    @FXML
    private Button retourbtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        if (loggedInClientAddress != null) {
            loadClientAddress();
        }
    }    
     private void loadClientAddress() {
        streetField.setText(loggedInClientAddress.getStreet());
        cityField.setText(loggedInClientAddress.getCity());
        postalCodeField.setText(loggedInClientAddress.getPostalCode());
        countryField.setText(loggedInClientAddress.getCountry());
        addressTextArea.setText(loggedInClientAddress.toString());
    }
@FXML
    private void addAddress(ActionEvent event) {
        String street = streetField.getText();
        String city = cityField.getText();
        String postalCode = postalCodeField.getText();
        String country = countryField.getText();
     
//        String sessionId = Global.sessionId;
//        Client client = SessionManager.getSession(sessionId);
    
         if (sessionId != null && !sessionId.isEmpty()) {
    
        if (client != null) {
            Adresse newAddress = new Adresse(street, city, postalCode, country);
            
            newAddress.setPersonneId(client.getId());

            // Call your service method to add the address
            ServiceAdresse serviceAdresse = new ServiceAdresse();
            serviceAdresse.ajouter(newAddress);

            clearAddressFields();
        } else {
            showAlert("Error", "Personne ID not found.", "Please log in.");
        }
         } else {
        showAlert("Error", "Session ID not found.", "Please log in.");
    }
    }
    
private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    
    private void clearAddressFields() {
        streetField.clear();
        cityField.clear();
        postalCodeField.clear();
        countryField.clear();
        addressTextArea.clear();
    }

    @FXML
    private void updateAddress(ActionEvent event) {
        String street = streetField.getText();
        String city = cityField.getText();
        String postalCode = postalCodeField.getText();
        String country = countryField.getText();
    if (sessionId != null && !sessionId.isEmpty()) {
    
    if (client != null) {
        loggedInClientAddress.setStreet(street);
        loggedInClientAddress.setCity(city);
        loggedInClientAddress.setPostalCode(postalCode);
        loggedInClientAddress.setCountry(country);

        // Call the service method to update the address in the database
        ServiceAdresse adresseService = new ServiceAdresse();
        adresseService.modifier(loggedInClientAddress);

        loadClientAddress();
    } else {
        showAlert("Error", "No address to update.", "Please add an address first.");
    }
        }
    }

    @FXML
    private void deleteAddress(ActionEvent event) {
        if (loggedInClientAddress == null) {
            showAlert("Error", "No address to delete.", "Please add an address first.");
            return;
        }

        // Create a confirmation dialog for deleting the address
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Confirmation de suppression de l'adresse");
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cette adresse ? Cette action est irréversible.");

        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = confirmationDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ServiceAdresse adresseService = new ServiceAdresse();
            adresseService.supprimer(loggedInClientAddress);
            clearAddressFields();
        }
    }

    @FXML
    private void showAddresses(ActionEvent event) {
        
        // Check if a client is logged in
        String sessionsId = Global.sessionId;
    if (sessionsId != null) {
        
        // Retrieve the client object using the session ID
        Client client = SessionManager.getSession(sessionsId);
        if (client != null) {
            // Fetch addresses associated with the client
            ServiceAdresse adresseService = new ServiceAdresse();
            List<Adresse> clientAddresses = adresseService.getByClientId(client.getId());
            
            if (!clientAddresses.isEmpty()) {
                // Create a StringBuilder to build the address information
                StringBuilder addressesInfo = new StringBuilder("Client Addresses:\n");
                
                // Append each address to the StringBuilder
                for (Adresse address : clientAddresses) {
                    addressesInfo.append(address.toString()).append("\n");
                }
                
                // Display the addresses in the addressTextArea
                addressTextArea.setText(addressesInfo.toString());
            } else {
                addressTextArea.setText("No addresses found for this client.");
            }
        } else {
            addressTextArea.setText("Client not found. Please log in.");
        }
    } else {
        addressTextArea.setText("Session ID not found. Please log in.");
    }
    }

    @FXML
    private void RetourProfil(ActionEvent event)throws IOException {
         // remove the session ID
//        Global.sessionId = null;

        // redirect the user to the register page
        Parent root = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) retourbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
}

