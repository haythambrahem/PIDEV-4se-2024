/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import tn.esprit.entity.Evenement;
import tn.esprit.services.ServiceEvenement;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siwar
 */
public class ModifierReservationController implements Initializable {
@FXML
    private Button annuler;

    @FXML
    private Button consulter;

    @FXML
    private ImageView exit;

    @FXML
    private Button supprimer_res;

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

   private void handleDeleteReservation(ActionEvent event) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Suppression de réservation");
    dialog.setHeaderText("Veuillez entrer le titre de l'événement pour supprimer la réservation.");
    dialog.setContentText("Titre de l'événement:");

    Optional<String> result = dialog.showAndWait();
    
    result.ifPresent(titreEvt -> {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Voulez-vous vraiment supprimer la réservation pour l'événement : " + titreEvt + " ?");
        confirmation.setContentText("Cette action est irréversible.");

        Optional<ButtonType> confirmationResult = confirmation.showAndWait();

        if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
            // L'utilisateur a cliqué sur OK dans la boîte de dialogue de confirmation
            // Placez le code de suppression ici

            try {
                ServiceEvenement se = ServiceEvenement.getInstance();
                int idEvt = se.getIdEvenementParNom(titreEvt);

                if (idEvt != -1) {
                    Evenement ev = new Evenement();
                    ev.setIdEvt(idEvt);
                    se.supprimerEv(ev);

                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation supprimée avec succès", "");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce nom n'existe pas", "");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la réservation", e.getMessage());
                e.printStackTrace();
            }
        }
    });
}
   private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);

    alert.showAndWait();
}
   
     @FXML
    private void Retour(ActionEvent event)  throws IOException {
   
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
}

