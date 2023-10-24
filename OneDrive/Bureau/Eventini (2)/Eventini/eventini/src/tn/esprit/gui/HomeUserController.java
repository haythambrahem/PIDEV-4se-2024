/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.services.Global;

/**
 * FXML Controller class
 *
 * @author hayth
 */

public class HomeUserController implements Initializable {
    private Parent fxml;

    @FXML
    private AnchorPane HomeAll;
    @FXML
    private TextField sessionname;
    @FXML
    private Button sessionlogout;
    private Button goToProfile;
    @FXML
    private AnchorPane root;
    @FXML
    private Button Acceuil;
    @FXML
    private Button Evenement;
    @FXML
    private Button Offre;
    @FXML
    private Button heberge;
    @FXML
    private Button Resautage;
    @FXML
    private Button Reclamation;
    @FXML
    private Button Profilee;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logoutback(ActionEvent event)throws IOException {
        // remove the session ID
        Global.sessionId = null;

        // redirect the user to the register page
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) sessionlogout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    private void goToProfileBTN(MouseEvent event)  throws IOException {
//        // redirect the user to the register page
//        Parent root = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) goToProfile.getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//    }

    @FXML
    private void goToAcceuil(ActionEvent event) {
//        try {
//            fxml= FXMLLoader.load(getClass().getResource("AcceuilHome.fxml"));
//            root.getChildren().removeAll();
//            root.getChildren().setAll(fxml);
//        } catch (IOException ex) {
//        }
        try {
            fxml= FXMLLoader.load(getClass().getResource("AcceuilHome.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    private void goToEvenement(ActionEvent event) {
        try {
            fxml= FXMLLoader.load(getClass().getResource(".fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    private void goToOffre(ActionEvent event) {
        try {
            fxml= FXMLLoader.load(getClass().getResource(".fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    private void goToHeberge(ActionEvent event) {
        try {
            fxml= FXMLLoader.load(getClass().getResource("Accueil.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    private void goToResautage(ActionEvent event) {
        try {
            fxml= FXMLLoader.load(getClass().getResource(".fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    private void goToReclamation(ActionEvent event) {
        try {
            fxml= FXMLLoader.load(getClass().getResource(".fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    

    @FXML
    private void goToProfileBTN(MouseEvent event){
//            throws IOException {
//        // remove the session ID
//        Global.sessionId = null;
//
//        // redirect the user to the register page
//        Parent root = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) goToProfile.getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
    
    }

    

    @FXML
    private void goToProfilee(ActionEvent event) {
        try {
            fxml= FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }
    
}
