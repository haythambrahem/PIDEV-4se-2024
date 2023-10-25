/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entity.Client;
import tn.esprit.services.Global;
import tn.esprit.services.ServicesClient;
import tn.esprit.services.SessionManager;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class UserProfileController implements Initializable {

    @FXML
    private AnchorPane login;
    @FXML
    private Label profilenom;
    @FXML
    private Button logout;
    @FXML
    private Label wrongLogIn;
    @FXML
    private Label profileprenom;
    @FXML
    private Label profileemail;
    @FXML
    private Label profileign;
    @FXML
    private Label profilewins;
    @FXML
    private Label profileloses;
    @FXML
    private ImageView profileimg;
    @FXML
    private Button modifier;
    @FXML
    private Label profilenom1;
    @FXML
    private Label profilenom11;
    @FXML
    private Label profilenom111;
    @FXML
    private Label profilenom1111;
    @FXML
    private Label profilenom11111;
    @FXML
    private Label profiledate;
    @FXML
    private Button gotoback;
    @FXML
    private Button delet;
    @FXML
    private Button adresse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         String sessionId = Global.sessionId;

        System.out.println("Session ID: " + sessionId);

        Client client = SessionManager.getSession(sessionId);
        System.out.println("Test 1");
        System.out.println("Role Java Client ID: " + client.getRoleJava_client_id());
        if (client.getRoleJava_client_id() == 2) {
                // Show the dashboard button
                gotoback.setVisible(true);
                System.out.println("Test 2");
            } else {
                // Hide the dashboard button
                gotoback.setVisible(false);
                System.out.println("Test 3");
            }
        String imageFilename = client.getPprofile();

// Construct a File object pointing to the location of the image file
        File imageFile = new File("C:\\xampp\\htdocs\\img\\" + imageFilename);
//C:\\Users\\hayth\\Downloads\\Eventini\\eventini\\src\\tn\\esprit\\img
//File imageFile = new File("C:\\xamp\\htdocs\\img\\" + imageFilename);
                                      //C:\\\\xamppppppp\\\\htdocs\\img\\
// Create an Image object from the File
        Image image = new Image(imageFile.toURI().toString());

// Set the Image object as the image of the ImageView
        
        profilenom.setText(client.getNom());
        profileprenom.setText(client.getPrenom());
        profileemail.setText(client.getEmail());
        profileimg.setImage(image);
        profileign.setText(client.getIgn());
//        java.util.Date date = new java.util.Date(client.getDateNaise().getTime());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String stringDate = dateFormat.format(date);
     LocalDate localDate =client.getDateNaise();
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringDate =localDate.format(formatter);
        profiledate.setText(stringDate);
    }    

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
         // remove the session ID
        Global.sessionId = null;

        // redirect the user to the register page
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void GotoModifier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ModifyProfile.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleback(ActionEvent event)  throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Back1.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    @FXML
private void deletCompte(ActionEvent event) throws SQLException {
    // Create a confirmation dialog
    Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
    confirmationDialog.setTitle("Confirmation");
    confirmationDialog.setHeaderText("Confirmation de suppression de compte");
    confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.");

    // Show the confirmation dialog and wait for user input
    Optional<ButtonType> result = confirmationDialog.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        ServicesClient serviceClient = new ServicesClient();
        Client client = new Client();
        String sessionId = Global.sessionId;

        // Check if the session ID is set
        if (sessionId != null) {
            // Get the email of the currently logged-in user
            String clientEmail = SessionManager.getSession(sessionId).getEmail();

            // Get the client ID based on the email
            int clientIdToDelete = serviceClient.getClientIdByEmail(clientEmail);

            if (clientIdToDelete > 0) {
                // User confirmed the deletion, proceed with the account deletion
                // Set the client's ID for deletion
                client.setId(clientIdToDelete);

                // You can now call your service method to delete the client's account
                serviceClient.supprimer(client);

                // After the account is deleted, you can redirect the user to the home page
                Global.sessionId = null;
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) logout.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle the case where the client ID is not found
                System.out.println("Client ID not found for the email: " + clientEmail);
            }
        } else {
            // Handle the case where the session ID is not set
            System.out.println("Session ID is not set");
        }
    } else {
        // User canceled the deletion, do nothing
    }
}

    @FXML
    private void GotoAddAdresse(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Adresse.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }}
