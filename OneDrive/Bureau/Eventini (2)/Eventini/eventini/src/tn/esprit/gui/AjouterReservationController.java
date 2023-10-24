/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.entity.Reservation;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceReservation;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siwar
 */
public class AjouterReservationController implements Initializable {
    
    @FXML
    private Button reserve_res;

    @FXML
    private TextField titre_res;
    @FXML
    private Button consulter_res;
@FXML
    private Button qr_code;
    @FXML
    private Button annuler_res;
    @FXML
    private Button retour_acc;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
private void reserver(ActionEvent event) {
    String nomEvenement = titre_res.getText(); // Obtenez le nom de l'événement depuis le champ de texte

    if (nomEvenement != null && !nomEvenement.isEmpty()) {
        try {
            ServiceEvenement se = ServiceEvenement.getInstance();
            int idEvenement = se.getIdEvenementParNom(nomEvenement);

            if (idEvenement != -1) {
                // Créez un objet Reservation
                Reservation reservation = new Reservation();
                reservation.setIdEvt(idEvenement);
                reservation.setTitreEvt(nomEvenement);
                reservation.setPrixBillet(11.33f); // Remplacez 0 par le prix du billet réel

                // Ajoutez la réservation à la base de données
                ServiceReservation sr = ServiceReservation.getInstance();
                sr.ajouterRes(reservation);

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation effectuée avec succès.", "");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce nom n'existe pas.", "");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la réservation de l'événement.", e.getMessage());
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Veuillez saisir le nom de l'événement à réserver.", "");
    }
}


  @FXML
    private void AnnuleRes(ActionEvent event)  throws IOException {
   
     FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeReservations.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}
     @FXML
    private void ConsulterEv(ActionEvent event)  throws IOException {
   
     FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherEvent.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}


private boolean isValidInput(String... values) {
    for (String value : values) {
        if (value == null || value.isEmpty()) {
            return false;
        }
    }
    return true;
}

private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);

    alert.showAndWait();
}
      @FXML
    private void generer(ActionEvent event)  throws IOException {
   
     FXMLLoader loader = new FXMLLoader(getClass().getResource("QRevent.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
  
}

    @FXML
    private void retourAcc(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeUser.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
        
    }

    
}
