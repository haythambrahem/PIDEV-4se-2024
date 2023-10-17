/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entity.Client;
import tn.esprit.services.Global;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         String sessionId = Global.sessionId;

        System.out.println("Session ID: " + sessionId);

        Client client = SessionManager.getSession(sessionId);
        if (client.getRoleJava_client_id() == 2) {
            // Show the dashboard button
            gotoback.setVisible(true);
        } else {
            // Hide the dashboard button
            gotoback.setVisible(false);
        }
        String imageFilename = client.getPprofile();

// Construct a File object pointing to the location of the image file
        File imageFile = new File("C:\\User\\hayth\\Downloads\\Eventini\\eventini\\src\\tn\\esprit\\img\\" + imageFilename);

// Create an Image object from the File
        Image image = new Image(imageFile.toURI().toString());

// Set the Image object as the image of the ImageView
        profileimg.setImage(image);
        profilenom.setText(client.getNom());
        profileprenom.setText(client.getPrenom());
        profileemail.setText(client.getEmail());
        profileimg.setImage(image);
//        profilewins.setText(String.valueOf(joueur.getWins()));
//        profileloses.setText(String.valueOf(joueur.getLoses()));
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
    private void handleback(ActionEvent event) {
    }
    
}
