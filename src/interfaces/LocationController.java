/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Locataire;
import models.Location;
import models.Logement;
import services.ServiceLocataire;
import services.ServiceLocation;
import services.ServiceLogement;
import tools.MyDB;
import javafx.util.converter.IntegerStringConverter;
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
    @FXML
    private Button btn_precedent;
    @FXML
    private Button btn_suivant;
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
//----------------------------     


 private Boolean isBetween(java.sql.Date my_date, java.sql.Date my_debut,java.sql.Date my_fin ){
      
     return (my_date.equals(my_debut) || my_date.after(my_debut))&& (my_date.equals(my_fin)|| my_date.before(my_fin));
      
  }
 
//----------------------------
 
 
 public Boolean isOut(java.sql.Date dateDebut,java.sql.Date dateFin,  java.sql.Date my_debut,java.sql.Date my_fin){
      
    return (dateDebut.before(my_debut) && dateFin.after(my_fin))  ;
      
  }
   
//-----------------------------



//     String cin = txt_CIN.getText();
//    String adr = txt_adr.getText();
//
//    Locataire locataire = serviceLocataire.rechercherLocataireParCIN(cin);
//    List<Logement> logements = serviceLogement.rechercheLogement(adr);
//    Logement logement = null;
//
//    if (logements.size() > 0) {
//        logement = logements.get(0);
//    }
//
//    if (locataire != null && logement != null) {
//        LocalDate dateDebutValue = dateDebut.getValue();
//        LocalDate dateFinValue = dateFin.getValue();
//
//        if (dateDebutValue != null && dateFinValue != null) {
//            java.sql.Date dateDebut = java.sql.Date.valueOf(dateDebutValue);
//            java.sql.Date dateFin = java.sql.Date.valueOf(dateFinValue);
//
//            ServiceLocation serviceLocation = new ServiceLocation();
//
//            if (!serviceLocation.isLocationAvailable(logement.getId(), dateDebut, dateFin)) {
//                // Show an alert that location is not available
//            } else {
//                Location location = new Location();
//                location.setLogement(logement);
//                location.setLocataire(locataire);
//                location.setDateDebut(dateDebut);
//                location.setDateFin(dateFin);
//
//                if (serviceLocation.ajouterLocation(location)) {
//                    Alert alert = new Alert(AlertType.INFORMATION);
//                    alert.setTitle("Succès");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Location ajoutée avec succès.");
//                    alert.showAndWait();
//                } else {
//                    Alert alert = new Alert(AlertType.ERROR);
//                    alert.setTitle("Erreur");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Impossible d'ajouter la location.");
//                    alert.showAndWait();
//                }
//            }
//        } else {
//            // Handle the case where the DatePicker values are null
//        }
//    } else {
//        // Handle the case where locataire or logement is not found
//    }
  @FXML
    private void addLocation(MouseEvent event) throws SQLException {
    String cin = txt_CIN.getText();
    String adr = txt_adr.getText();
        textField.setTextFormatter(textFormatter);
    int locataireId = serviceLocataire.retrieveLocataireIdByCIN(cin);
    int logementId = serviceLogement.retrieveLogementIdByAdr(adr);

    if (locataireId != 0 && logementId != 0) {
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
                location.setLocataire(serviceLocataire.getLocataireById(locataireId));
                location.setDateDebut(dateDebut);
                location.setDateFin(dateFin);
               
                location.setTarif(tarif); // You should set the tarif here.

                if (serviceLocation.ajouterLocation(location)) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Location ajoutée avec succès.");
                    alert.showAndWait();
                   clearInputFields();
               //   dateDebut.setValue(null);
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
        alert.setContentText("Locataire ou logement non trouvé.");
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
    // Implement the conversion based on your date format
    // For example: return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
    // serviceLocation = new ServiceLocation();
    } 

    
    
    @FXML
    private void logementPrecedent(MouseEvent event) {
    }

    @FXML
    private void logementSuivant(MouseEvent event) {
    }

    @FXML
    private void searchLogement(ActionEvent event) {
        if (txt_searchLogementid != null) {
        String critere = txt_searchLogementid.getText().trim(); // Trim the input to remove leading/trailing whitespace
        System.out.println(critere);

        if (critere.isEmpty()) {
            // Show an error alert if the input is empty
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le champ de recherche est vide.");
            alert.showAndWait();
        } else {
            ServiceLogement serviceLogement = new ServiceLogement();
            List<Logement> resultat = serviceLogement.rechercheLogement(critere);

            if (resultat.isEmpty()) {
                // Show an information alert if no results are found
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
                        // Handle the case where the image path is empty or null (e.g., display a default image)
                    }
                });

                // Create an ObservableList from the list of results
              //  ObservableList<Logement> observableResultat = FXCollections.observableArrayList(resultat);
                // Set the ListView with the new ObservableList
             //   listView_logement.setItems(observableResultat);
            }
        }
    } else {
        // Show an error alert if txt_searchLogementid is null (empty)
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ de recherche est vide.");
        alert.showAndWait();
    }
}

    @FXML
    private void searchLocataire(ActionEvent event) {
         String cin = txt_cinSearch.getText();
    Locataire locataire = serviceLocataire.rechercherLocataireParCIN(cin);
    if (locataire != null) {
        txt_CIN.setText(locataire.getCin());
        txt_nomPrenom.setText(locataire.getNomprenom());
        txt_tele.setText(locataire.getTele());
        txt_cinSearch.setText("");
        System.out.println("Result found");
    } else {
        Alert alert = new Alert(AlertType.ERROR, "Aucun locataire avec CIN=" + cin, ButtonType.OK);
        alert.showAndWait();
    }
}
    TextField textField = new TextField();

// Créez un TextFormatter avec un filtre et un validateur
TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
    String newText = change.getControlNewText();

    if (newText.matches("\\d*")) {
        // Permet seulement les caractères numériques
        return change;
    } else {
        // Affiche une erreur si un caractère non numérique est entré
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer uniquement des chiffres.");
        alert.showAndWait();
        return null;
    }
});

// Appliquez le TextFormatter au TextField

    }

