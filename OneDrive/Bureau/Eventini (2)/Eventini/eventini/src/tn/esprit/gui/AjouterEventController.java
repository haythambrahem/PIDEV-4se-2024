/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.swing.JComboBox;
import tn.esprit.entity.Evenement;
import tn.esprit.services.ServiceEvenement;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author siwar
 */
public class AjouterEventController implements Initializable {
    @FXML
    private TextField titre_aj;
    @FXML
    private TextField nomorg_aj;
    @FXML
    private TextField hd_aj;
     @FXML
    private TextField hf_aj;
    @FXML
    private TextField adresse_aj;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private TextField desc_aj;
    @FXML
    private TextField id_aj;
     @FXML
    private Button add_aj;
      @FXML
    private Button retour_menu;
 
    
     @FXML
    private DatePicker myDatePicker;
     private int idEvt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Evènement Public", "Evènement Privé");
        myChoiceBox.setItems(list);
    
    }
  
@FXML
private void addEvent(ActionEvent event) {
    String titreEvt = titre_aj.getText();
    String nomOrg = nomorg_aj.getText();
    String descEvt = desc_aj.getText();
    String hdEvtText = hd_aj.getText(); // Read the time as a String
    String hfEvtText = hf_aj.getText(); // Read the time as a String
    String adresseEvt = adresse_aj.getText();
    
    LocalDate localDate = myDatePicker.getValue(); // Get the selected date from the DatePicker

    if (isValidInput(titreEvt, nomOrg, descEvt, hdEvtText, hfEvtText, adresseEvt, localDate)) {
        Evenement ev = new Evenement();
        ev.setTitreEvt(titreEvt);
        ev.setNomOrg(nomOrg);
        ev.setDescEvt(descEvt);

        // Parse the user input Strings to LocalTime
        LocalTime hdEvt = LocalTime.parse(hdEvtText);
        LocalTime hfEvt = LocalTime.parse(hfEvtText);

        ev.setHdEvt(hdEvt);
        ev.setHfEvt(hfEvt);

        ev.setAdresseEvt(adresseEvt);

        // Convert LocalDate to Date
        Date dateEvt = java.sql.Date.valueOf(localDate);
        ev.setDateEvt(dateEvt); // Set the date using the converted Date object

        try {
            ServiceEvenement se = ServiceEvenement.getInstance();
            ev.setTypeEvt("Evènement Public");
            se.ajouterEv(ev);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement ajouté avec succès", "");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'événement", e.getMessage());
            e.printStackTrace();
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AfficherEvent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) add_aj.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Remplissez tous les champs", "");
    }
}

  
////////////SUPPRESSION
//////////done
//    @FXML
//private void supprimer(ActionEvent event) {
//    String titreEvt = titre_aj.getText();
//    if (titreEvt != null && !titreEvt.isEmpty()) {
//        try {
//            ServiceEvenement se = ServiceEvenement.getInstance();
//
//            // 1. Recherchez l'ID de l'événement par son nom
//            int idEvt = se.getIdEvenementParNom(titreEvt);
//
//            if (idEvt != -1) {
//                // 2. Créez un objet Evenement avec l'ID
//                Evenement ev = new Evenement();
//                ev.setIdEvt(idEvt);
//
//                // 3. Supprimez l'événement
//                se.supprimerEv(ev);
//
//                showAlert(Alert.AlertType.INFORMATION, "Succès", "Evenement supprimée avec succès", "");
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce nom n'existe pas", "");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'événement", e.getMessage());
//            e.printStackTrace();
//        }
//    } else {
//        showAlert(Alert.AlertType.INFORMATION, "", "Veuillez saisir le nom de l'événement à supprimer", "");
//    }
//}



    //TYPE MTAA L EVT//////
    @FXML
private void selectt(ActionEvent event) {
    String selectedItem = myChoiceBox.getSelectionModel().getSelectedItem();
}

    
    
//////////MODIFICATION 
//
//@FXML
//private void modifierEvent(ActionEvent event) {
//    String titreEvt = titre_aj.getText();
//    String nomOrg = nomorg_aj.getText();
//    String descEvt = desc_aj.getText();
//    String hdEvtText = hd_aj.getText();
//    String hfEvtText = hf_aj.getText();
//    String adresseEvt = adresse_aj.getText();
//
//
//    try {
//        idEvt = Integer.parseInt(id_aj.getText());
//    } catch (NumberFormatException e) {
//        System.err.println("Invalid ID format. Using a default value (0).");
//    }
//
//    // Initialize LocalTime variables with default values
//    LocalTime hdEvt = LocalTime.MIDNIGHT;
//    LocalTime hfEvt = LocalTime.MIDNIGHT;
//
//    try {
//        // Parse the input strings into LocalTime
//        hdEvt = LocalTime.parse(hdEvtText);
//        hfEvt = LocalTime.parse(hfEvtText);
//    } catch (DateTimeParseException e) {
//        // Handle invalid time format
//        System.err.println("Invalid time format. Using default time (00:00:00).");
//    }
//
//    System.out.println("Donner les nouvelles données :");
//    Evenement ev = new Evenement(titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, "");
//    ServiceEvenement se = ServiceEvenement.getInstance();
//    
//    // Insérez ici la logique pour vérifier si l'événement avec le titre donné existe
//    // Par exemple, en utilisant une méthode de votre service.
//
//    if (titreEvt != null && !titreEvt.isEmpty()) {
//        se.modifierEv(titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, "", ev);
//        se.afficherEv();
//        showAlert(Alert.AlertType.INFORMATION, "Succès", "Evenement modifié .", "");
//    } else {
//        showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce titre n'existe pas", "");
//    }
//}


private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);

    alert.showAndWait();
}

private boolean isValidInput(String titreEvt, String nomOrg, String descEvt, String hdEvtText, String hfEvtText, String adresseEvt, LocalDate dateEvt) {
    if (titreEvt == null || titreEvt.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "", "Le titre de l'événement est requis.", "");
        return false;
    }
    if (nomOrg == null || nomOrg.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "", "Le nom de l'organisateur est requis.", "");
        return false;
    }
    if (hdEvtText == null || hdEvtText.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "", "L'heure de début est requise.", "");
        return false;
    }
    if (hfEvtText == null || hfEvtText.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "", "L'heure de fin est requise.", "");
        return false;
    }
    if (adresseEvt == null || adresseEvt.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "", "L'adresse de l'événement est requise.", "");
        return false;
    }
    if (dateEvt == null) {
        showAlert(Alert.AlertType.WARNING, "", "La date de l'événement est requise.", "");
        return false;
    }
    if (descEvt == null || descEvt.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "", "La description de l'événement est requise.", "");
        return false;
    }
    return true;
}
 @FXML
    private void Retour(ActionEvent event)  throws IOException {
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Back1.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}

}