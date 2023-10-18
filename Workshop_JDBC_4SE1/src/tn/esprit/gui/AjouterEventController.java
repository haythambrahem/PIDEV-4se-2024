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
import tn.esprit.entity.Personne;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServicePersonne;

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
    private Button delete_aj;
           @FXML
    private Button edit_aj;
    
    
    private String[] type = {"Public","Privé","Personalisé"};
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    
//    @FXML
//private void addPerson(ActionEvent event) throws SQLException {
//         String titreEvt = titre_aj.getText();
//            String nomOrg = nomorg_aj.getText();
//            String descEvt = desc_aj.getText();
//            String hdEvt = hd_aj.getText();
//            String hfEvt = hf_aj.getText();
//            String adresseEvt = adresse_aj.getText();
////            String typeEvt = (String) type_aj.getValue();
//            String typeEvt = "";
            // Créez un ComboBox
//ComboBox<Evenement> type_aj = new ComboBox<>();
//
//// Définissez un StringConverter pour afficher le nom de l'événement
//type_aj.setConverter(new StringConverter<Evenement>() {
//    @Override
//    public String toString(Evenement evenement) {
//        return evenement.getTypeEvt(); // Utilisez le nom de l'événement ici
//    }
//
//    @Override
//    public Evenement fromString(String string) {
//        // Vous pouvez ignorer cette méthode pour un ComboBox non éditable
//        return null;
//    }
//});
//List<String> myList = new ArrayList<>();
//myList.add("Element 1");
//myList.add("Element 2");
//myList.add("Element 2");
//// Add more elements as needed
//
//// Ajoutez la liste d'événements au ComboBox
//ObservableList<Evenement> evenements = FXCollections.observableArrayList();
//type_aj.setItems(evenements);
//
//    


////        if (titreEvt!= null && !titreEvt.isEmpty() && nomOrg != null && !nomOrg.isEmpty() &&
////                descEvt != null && descEvt != null && hdEvt != null && !hdEvt .isEmpty() &&
////                hfEvt  != null && !hfEvt .isEmpty() && adresseEvt != null && !adresseEvt.isEmpty() && typeEvt!= null && !typeEvt .isEmpty()  )
////        {
//////if (isValidInput(titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt))
//////{
//////                Evenement ev = new Evenement();
//////                ev.setTitreEvt(titreEvt);
//////                ev.setNomOrg(nomOrg);
//////                ev.setDescEvt(descEvt);
//////                ev.setHdEvt(hdEvt);
//////                ev.setHfEvt(hfEvt);
//////                ev.setAdresseEvt(adresseEvt);
//////                try {
//////               ServiceEvenement se = ServiceEvenement.getInstance();
//////               se.ajouterEv(ev);
//////            Parent root = null;
//////            titre_aj.getScene().setRoot(root);
//////            nomorg_aj.getScene().setRoot(root);
//////            desc_aj.getScene().setRoot(root);
//////            hd_aj.getScene().setRoot(root);
//////            hf_aj.getScene().setRoot(root);
//////            adresse_aj.getScene().setRoot(root);
////////            type_aj.getScene().setRoot(root);
//////                showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre ajoutée avec succès", "");
//////            } 
//////        catch (Exception e) {
//////                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'offre", e.getMessage());
//////                e.printStackTrace();
//////            }
//////        } else {
//////            showAlert(Alert.AlertType.INFORMATION, "", "Remplissez tous les champs", "");
//////        }
//////            System.out.println("Evènement ajouté avec succès");
//////}
//////else
//////        {
//////            System.out.println("Evènement non ajouté avec succès");
//////        }

    
    
    
    
    
//////////AJOUUUUUUUUUUT
 @FXML
    private void addEvent(ActionEvent event) {
        String titreEvt = titre_aj.getText();
        String nomOrg = nomorg_aj.getText();
        String descEvt = desc_aj.getText();
        String hdEvt = hd_aj.getText();
        String hfEvt = hf_aj.getText();
        String adresseEvt = adresse_aj.getText();
        

        if (isValidInput(titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt)) {
            Evenement ev = new Evenement();
            ev.setTitreEvt(titreEvt);
            ev.setNomOrg(nomOrg);
            ev.setDescEvt(descEvt);
            ev.setHdEvt(hdEvt);
            ev.setHfEvt(hfEvt);
            ev.setAdresseEvt(adresseEvt);

            try {
                ServiceEvenement se = ServiceEvenement.getInstance();
                ev.setTypeEvt("Evènement Public");
                se.ajouterEv(ev);

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement ajouté avec succès", "");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'événement", e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "", "Remplissez tous les champs", "");
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

////////////SUPPRESSION
//////////done
   //hedhi es7i7a 
    @FXML
private void supprimer(ActionEvent event) {
    String titreEvt = titre_aj.getText();
    if (titreEvt != null && !titreEvt.isEmpty()) {
        try {
            ServiceEvenement se = ServiceEvenement.getInstance();

            // 1. Recherchez l'ID de l'événement par son nom
            int idEvt = se.getIdEvenementParNom(titreEvt);

            if (idEvt != -1) {
                // 2. Créez un objet Evenement avec l'ID
                Evenement ev = new Evenement();
                ev.setIdEvt(idEvt);

                // 3. Supprimez l'événement
                se.supprimerEv(ev);

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Evenement supprimée avec succès", "");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce nom n'existe pas", "");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'événement", e.getMessage());
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Veuillez saisir le nom de l'événement à supprimer", "");
    }
}


//////////done
    
    
    
    
   ///////////////MODIFICATION 
@FXML
private void modifierEvent(ActionEvent event) {
    String titreEvt = titre_aj.getText();
    String nomOrg = nomorg_aj.getText();
    String descEvt = desc_aj.getText();
    String hdEvt = hd_aj.getText();
    String hfEvt = hf_aj.getText();
    String adresseEvt = adresse_aj.getText();

    int idEvt = 0;

    try {
        idEvt = Integer.parseInt(id_aj.getText());
    } catch (NumberFormatException e) {
        System.err.println("Invalid ID format. Using a default value (0).");
    }

    System.out.println("Donner les nouvelles données :");
    Evenement ev = new Evenement(titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, "");
    ServiceEvenement se = new ServiceEvenement();
    
    // Insérez ici la logique pour vérifier si l'événement avec le titre donné existe
    // Par exemple, en utilisant une méthode de votre service.

    if (titreEvt != null && !titreEvt.isEmpty()) {
        se.modifierEv(titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, "", ev);
        se.afficherEv();
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Evenement modifié .", "");
    } else {
        showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce titre n'existe pas", "");
    }
}



private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);

    alert.showAndWait();
}

}