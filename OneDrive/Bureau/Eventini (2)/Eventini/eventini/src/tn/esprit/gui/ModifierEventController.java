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
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration; // Import the correct Duration class

/**
 * FXML Controller class
 *
 * @author siwar
 */
public class ModifierEventController implements Initializable {

    
       @FXML
    private TextField adresse_modif;

    @FXML
    private Button confirmer_modif;
    @FXML
    private Button  retour_mdf;

    @FXML
    private TextField desc_modif;

    @FXML
    private TextField hd_modif;

    @FXML
    private TextField hf_modif;

    @FXML
    private TextField id_modif;
        @FXML
    private TextField id_aj;


    @FXML
    private ChoiceBox<String> myChoiceBox_modif;

    @FXML
    private DatePicker myDatePicker_modif;

    @FXML
    private TextField nomorg_modif;

    @FXML
    private TextField titre_modif;
       @FXML
    private ImageView exit;

    @FXML
    private ImageView exit1;
     @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;
    @FXML
private Pane opacityPane;
    @FXML
private ImageView drawerImage;

  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ObservableList<String> list = FXCollections.observableArrayList("Evènement Public", "Evènement Privé");
        myChoiceBox_modif.setItems(list);
    
    }

        // TODO
      
    
    
@FXML
private void modifier(ActionEvent event) {
    String titreEvt = titre_modif.getText();
    String nomOrg = nomorg_modif.getText();
    String descEvt = desc_modif.getText();
    String hdEvtText = hd_modif.getText();
    String hfEvtText = hf_modif.getText();
    String adresseEvt = adresse_modif.getText();
    String typeEvt = myChoiceBox_modif.getValue(); // Use myChoiceBox_modif to get the selected value
    LocalDate dateEvt = myDatePicker_modif.getValue();

    if (titreEvt != null && !titreEvt.isEmpty() && nomOrg != null && !nomOrg.isEmpty() &&
            descEvt != null && !descEvt.isEmpty() && hdEvtText != null &&
            hfEvtText != null && adresseEvt != null && !adresseEvt.isEmpty() && typeEvt != null && dateEvt != null)
    {
        LocalTime hdEvt = LocalTime.parse(hdEvtText);
        LocalTime hfEvt = LocalTime.parse(hfEvtText);

        Evenement eventModifie = new Evenement(titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, typeEvt, Date.valueOf(dateEvt));

        try {
            ServiceEvenement se = ServiceEvenement.getInstance();
            se.modifierEv(titreEvt,eventModifie );

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement modifié avec succès", "");
            se.afficherEv();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de l'événement", e.getMessage());
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Remplissez tous les champs", "");
    }
}
 
    @FXML
private void selectt(ActionEvent event) {
    String selectedItem = (String) myChoiceBox_modif.getSelectionModel().getSelectedItem();
}


//
//
//
//
//
private boolean isValidInput(String titreEvt, String nomOrg, String descEvt, String hdEvtText, String hfEvtText, String adresseEvt, Date dateEvt) {
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("La date saisie invalide!");
            alert.setContentText("Date invalide!");
            alert.showAndWait();

            return false;}
    try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date2 = LocalDate.parse((CharSequence) dateEvt, formatter);
            LocalDate now = LocalDate.now();
            int age = Period.between(date2, now).getYears();
            if (age < 16) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Date de naissance invalide!");
                alert.setContentText("L'âge doit être supérieur ou égal à 16 ans.");
                alert.showAndWait();
                return false;

            }
        } catch (DateTimeParseException e) {
            //showAlert("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Date invalide!");
            alert.setContentText("Date invalide! Veuillez saisir une date valide (format: jj/mm/aaaa).");
            alert.showAndWait();
            return false;
        }
     if (descEvt == null || descEvt.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "", "La description de l'événement est requise.", "");
        return false;
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
    private void Retour(ActionEvent event)  throws IOException {
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Back1.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}
}
