/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Period;

import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entity.Locataire;
import tn.esprit.entity.Personne;
import tn.esprit.entity.Location;
import tn.esprit.entity.Logement;
import tn.esprit.services.ServiceLocataire;
import tn.esprit.services.ServicePersonne;
import tn.esprit.services.ServiceLocation;
import tn.esprit.services.ServiceLogement;
import tn.esprit.tools.MyDB;
import javafx.util.converter.IntegerStringConverter;
import tn.esprit.entity.Example;
/**
 * FXML Controller class
 *
 * @author FADI
 */
public class LocationController implements Initializable {

    @FXML
    private JFXTextField txt_searchLogementid;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_loyer;
    @FXML
    private JFXTextField txt_type;
    @FXML
    private ImageView imageLog;
    @FXML
    private JFXTextField txt_region;
    @FXML
    private JFXTextField txt_cinSearch;
    @FXML
    private JFXTextField txt_nomPrenom;
    @FXML
    private JFXTextField txt_tele;
    @FXML
    private JFXTextField txt_CIN;
    @FXML
    private JFXDatePicker dateDebut;
    @FXML
    private JFXDatePicker dateFin;
    @FXML
    private JFXTextField txt_periode;
    Connection con;
    Statement ste;
                 Logement loge = new Logement();

    
 public TextField getAdr() {
        return txt_adr;
    }
 public TextField getLoyer() {
        return txt_loyer;
 }
 public TextField getType() {
        return txt_type;}
       
 public TextField getRegion() {
        return txt_region;
 }
    /**
     * Initializes the controller class.
     */
 
private ServiceLogement serviceLogement;
private ServiceLocataire serviceLocataire;
private ServicePersonne servicePersonne;
//----------------------------     


 private Boolean isBetween(java.sql.Date my_date, java.sql.Date my_debut,java.sql.Date my_fin ){
      
     return (my_date.equals(my_debut) || my_date.after(my_debut))&& (my_date.equals(my_fin)|| my_date.before(my_fin));
      
  }
 
//----------------------------
 
 
 public Boolean isOut(java.sql.Date dateDebut,java.sql.Date dateFin,  java.sql.Date my_debut,java.sql.Date my_fin){
      
    return (dateDebut.before(my_debut) && dateFin.after(my_fin))  ;
      
  }
   
//-----------------------------



//     
  @FXML
    private void addLocation(MouseEvent event) throws SQLException {
    String email = txt_CIN.getText();
    String adr = txt_adr.getText();
        textField.setTextFormatter(textFormatter);
    int personneId = servicePersonne.retrievePersonneIdByEMAIL(email);
    int logementId = serviceLogement.retrieveLogementIdByAdr(adr);

    if (personneId != 0 && logementId != 0) {
        LocalDate dateDebutValue = dateDebut.getValue();
        LocalDate dateFinValue = dateFin.getValue();

        if (dateDebutValue != null && dateFinValue != null) {
            java.sql.Date dateDebut = java.sql.Date.valueOf(dateDebutValue);
            java.sql.Date dateFin = java.sql.Date.valueOf(dateFinValue);

            ServiceLocation serviceLocation = new ServiceLocation();

            if (!serviceLocation.isLocationAvailable(logementId, dateDebut, dateFin)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("La location n'est pas disponible pour les dates sélectionnées.");
                alert.showAndWait();
            } else {
                 int loyer = serviceLogement.getLogementById(logementId).getLoyer();
                int days =Integer.parseInt(txt_periode.getText()); // Calculate the number of days (e.g., from txt_periode)
                int tarif = days * loyer;
                Location location = new Location();
                location.setLogement(serviceLogement.getLogementById(logementId));
                location.setPersonne(servicePersonne.getPersonneById(personneId));
                location.setDateDebut(dateDebut);
                location.setDateFin(dateFin);
               
                location.setTarif(tarif);

                if (serviceLocation.ajouterLocation(location)) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Location ajoutée avec succès.");
                    alert.showAndWait();
                   clearInputFields();
      //Twilo API              //Twilo API
        /**/      String recipientPhoneNumber = "+21650227451"; // Replace with the recipient's phone number
        /**/  String messageText = "Votre Location est ajoutée avec succès"; // Provide your message
        /**/ Example.sendTwilioSMS(recipientPhoneNumber, messageText);
         //Twilo API       //   dateDebut.setValue(null);
               //     dateFin.setValue(null);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Impossible d'ajouter la location.");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner des dates valides.");
            alert.showAndWait();
        }
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Client ou logement non trouvé.");
        alert.showAndWait();
    }
    
}

private Logement searchLogement(String adr) {
    List<Logement> logements = serviceLogement.rechercheLogement(adr);

    if (logements != null && !logements.isEmpty()) {
        return logements.get(0);
    } else {
        return null;
    }
}

private Date convertLocalDateToDate(LocalDate localDate) {
    return null;
}

private void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}

        private void clearInputFields() {
            txt_CIN.clear();
        txt_adr.clear();
        txt_loyer.clear();
        txt_type.clear();
        txt_region.clear();
        txt_cinSearch.clear();
        txt_nomPrenom.clear();
        txt_tele.clear();
        txt_periode.clear();
        dateDebut.setValue(null);
        dateFin.setValue(null);
        imageLog.setImage(null);
}
    
//------------------------------------------------------
   
    
    @FXML
    private void periode(ActionEvent event) {
       LocalDate startDate = dateDebut.getValue();
LocalDate endDate = dateFin.getValue();
if (startDate != null && endDate != null) {
    Period period = Period.between(startDate, endDate);
    int days = period.getDays();
    int months = period.getMonths();
    txt_periode.setText(String.valueOf(days));
    //txt_periode.setText(String.valueOf(months));
}
        
    }
    //---------------------
    
    
 public int daysBetween (java.sql.Date dl, java.sql.Date d2){
        return (int) ((d2.getTime()-dl.getTime())/(1000*60*60*24)); 
       
    }
//-----------------------
 
 
    //--------------------


@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = MyDB.getinstance().getCon();
    serviceLocataire = new ServiceLocataire();
     serviceLogement = new ServiceLogement();
     servicePersonne = new ServicePersonne();
    // serviceLocation = new ServiceLocation();
    
    } 

    
    
    private void logementPrecedent(MouseEvent event) {
         try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Logement.fxml"));
            Parent root = loader.load();

            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logementSuivant(MouseEvent event) {
     try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Historique.fxml"));
            Parent root = loader.load();

            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchLogement(ActionEvent event) {
        if (txt_searchLogementid != null) {
        String critere = txt_searchLogementid.getText().trim(); // Trim the input to remove leading/trailing whitespace
        System.out.println(critere);

        if (critere.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le champ de recherche est vide.");
            alert.showAndWait();
        } else {
            ServiceLogement serviceLogement = new ServiceLogement();
            List<Logement> resultat = serviceLogement.rechercheLogement(critere);

            if (resultat.isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Aucun résultat trouvé pour le critère de recherche.");
                alert.showAndWait();
            } else {
                resultat.forEach(logement -> {
                    txt_adr.setText(logement.getAdr());
                    // txt_superfice.setText(String.valueOf(logement.getSuperfice()));
                    txt_loyer.setText(String.valueOf(logement.getLoyer()));
                    txt_region.setText(logement.getRegion());
                    txt_type.setText(logement.getType().toString());

                    System.out.println(logement.getImage());

                    if (logement.getImage() != null && !logement.getImage().isEmpty()) {
                        String fileUrl = new File(logement.getImage()).toURI().toString();
                        System.out.println(fileUrl);
                        Image image = new Image(fileUrl);
                        imageLog.setImage(image);
                    } else {
                    }
                });

              //  ObservableList<Logement> observableResultat = FXCollections.observableArrayList(resultat);
             //   listView_logement.setItems(observableResultat);
            }
        }
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ de recherche est vide.");
        alert.showAndWait();
    }
}

    @FXML
    private void searchLocataire(ActionEvent event) {
         String email = txt_cinSearch.getText();
    Personne personne = servicePersonne.rechercherPersonneParEmail(email);
    if (personne != null) {
        txt_CIN.setText(personne.getEmail());
        txt_nomPrenom.setText(personne.getNom());
        txt_tele.setText(personne.getPrenom());
        txt_cinSearch.setText("");
        System.out.println("Result found");
    } else {
        Alert alert = new Alert(AlertType.ERROR, "Aucun locataire avec email=" + email, ButtonType.OK);
        alert.showAndWait();
    }
    
}
    TextField textField = new TextField();

TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
    String newText = change.getControlNewText();

    if (newText.matches("\\d*")) {
        return change;
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer uniquement des chiffres.");
        alert.showAndWait();
        return null;
    }
});
 private Parent fxml;
    @FXML
    private void goToAccueil(ActionEvent event) {
           try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeUser.fxml"));
            Parent root = loader.load();

            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
// try {
//            fxml= FXMLLoader.load(getClass().getResource("Accueil.fxml"));
//            root.getChildren().removeAll();
//            root.getChildren().setAll(fxml);
//        } catch (IOException ex) {
//        }
    }
    

    @FXML
    private void goToMyLocation(ActionEvent event) {
           try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MyLocation.fxml"));
            Parent root = loader.load();

            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

