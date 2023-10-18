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
import tn.esprit.services.ServiceReservation;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
@FXML
private void reserver(ActionEvent event) {
    String titreEvt = titre_res.getText();
     
    if (isValidInput(titreEvt)) {
        try {
            ServiceReservation sr = ServiceReservation.getInstance();

            // 1. Recherchez l'ID de l'événement par son nom
            int idEvt = sr.getIdEvenementParNom(titreEvt);

            if (idEvt != -1) {
                // 2. Créez un objet Reservation avec l'ID
                Reservation reservation = new Reservation();
                reservation.setIdEvt(idEvt);

                // 3. Effectuez la réservation (ajoutez votre logique ici)
                // Par exemple, vous pouvez avoir une méthode dans votre service pour effectuer la réservation

                // En supposant que vous avez une méthode nommée "ajouterRes" dans votre ServiceReservation
                boolean reservationReussie = sr.effectuerRes(reservation);

                if (reservationReussie) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation effectuée avec succès", "");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la réservation", "");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce nom n'existe pas", "");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la réservation de l'événement", e.getMessage());
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Veuillez saisir le nom de l'événement à réserver", "");
    }
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

    
}
