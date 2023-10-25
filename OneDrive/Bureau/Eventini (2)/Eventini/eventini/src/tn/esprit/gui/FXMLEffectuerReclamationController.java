/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import entité.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import services.ReclamationService;

public class FXMLEffectuerReclamationController implements Initializable {

    @FXML
    private ChoiceBox choix_type;

    private String[] types = {"Sport", "Soirée", "Festival"};
    @FXML
    private TextArea ta_msg;
    @FXML
    private AnchorPane sidepane;

    ReclamationService rs = new ReclamationService();
    @FXML
    private Label msg_err;
    @FXML
    private Button btn_effectuer;

    private static final Logger LOGGER = Logger.getLogger(FXMLEffectuerReclamationController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choix_type.getItems().addAll(types);
        choix_type.setValue("Sport");
    }

    @FXML
   

private void effectuerReclamation(ActionEvent event) {
    String message = ta_msg.getText().trim().toLowerCase();

    // Vérifie si le message contient le mot "fuck"
    if (message.contains("fuck")) {
        // Affichez l'alerte d'erreur pour un langage inapproprié
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Le message contient un langage inapproprié.");
        alert.show();
    } else if (message.isEmpty() || message.length() > 200) {
        // Vérifie si le message est vide ou dépasse la limite de 200 caractères
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez vérifier votre message.");
        alert.show();
    } else {
        // Le message est valide, réinitialisez le label d'erreur
        msg_err.setText("");

        // Créez l'objet Reclamation avec le message, l'état et l'ID utilisateur
        Reclamation r = new Reclamation(13, message, "Ouvert");

        // Ajoutez la réclamation à la base de données
        rs.ajouter(r);
        
        // Affichez l'alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText("Réclamation ajoutée avec succès");
        alert.show();

        try {
            // Chargez la scène FXMLConsulterReclamation après l'ajout de la réclamation
            setSceneContent("FXMLConsulterReclamation");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de la scène FXMLConsulterReclamation", ex);
        }
    }
}




    @FXML
    private void Retour(ActionEvent event) {
        try {
            setSceneContent("FXMLConsulterReclamation");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de la scène FXMLConsulterReclamation", ex);
        }
    }

    private void setSceneContent(String sceneName) throws IOException {
        // Code pour charger la scène spécifiée
        // ...
    }
}
