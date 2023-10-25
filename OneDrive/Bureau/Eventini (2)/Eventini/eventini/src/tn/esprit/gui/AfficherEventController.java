/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;
import tn.esprit.entity.Evenement;
import tn.esprit.services.ServiceEvenement;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siwar
 */  

public class AfficherEventController implements Initializable {
    @FXML
    private TableView<Evenement> table_see;
    
    @FXML
    private TableColumn<Evenement, Integer> afficher_id;
    
    @FXML
    private TableColumn<Evenement, String> afficher_titre;
    
    @FXML
    private TableColumn<Evenement, String> afficher_nomorg;
    
    @FXML
    private TableColumn<Evenement, String> afficher_description;
    
    @FXML
    private TableColumn<Evenement, LocalTime> afficher_hd;
    
    @FXML
    private TableColumn<Evenement, LocalTime> afficher_hf;
    
    @FXML
    private TableColumn<Evenement, String> afficher_adresse;
    
    @FXML
    private TableColumn<Evenement, String> afficher_type;
    
    @FXML
    private TableColumn<Evenement, LocalDate> afficher_date;
        @FXML
    private TextField recherche_text_par;
    @FXML
    private Button handleDeleteEvent;

    @FXML
    private Button handleModifyEvent;
     @FXML
    private TextField nb_like_dislike;
    @FXML
    private Button btn_like;
    
    @FXML
    private Button ajouter;
        @FXML
    private Button affich_res;
        @FXML
    private Button retourf;
    @FXML
    private Button retour8;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set cell value factories to map columns to properties
        afficher_id.setCellValueFactory(new PropertyValueFactory<>("idEvt"));
        afficher_titre.setCellValueFactory(new PropertyValueFactory<>("titreEvt"));
        afficher_nomorg.setCellValueFactory(new PropertyValueFactory<>("nomOrg"));
        afficher_description.setCellValueFactory(new PropertyValueFactory<>("descEvt"));
        afficher_hd.setCellValueFactory(new PropertyValueFactory<>("hdEvt"));
        afficher_hf.setCellValueFactory(new PropertyValueFactory<>("hfEvt"));
        afficher_adresse.setCellValueFactory(new PropertyValueFactory<>("adresseEvt"));
        afficher_type.setCellValueFactory(new PropertyValueFactory<>("typeEvt"));
        afficher_date.setCellValueFactory(new PropertyValueFactory<>("dateEvt"));
        

        // Initialize the TableView
        table_see.setItems(FXCollections.observableArrayList());
         
        // You can retrieve the list of Evenement objects and populate the TableView
        List<Evenement> evenements = retrieveEvenementsFromDatabase();

        // Populate the TableView with data
        table_see.getItems().addAll(evenements);
        
recherche_text_par.textProperty().addListener((observable, oldValue, newValue) -> {
    // Récupérer la liste des événements affichée dans la TableView

    // Créer une nouvelle liste pour stocker les résultats de la recherche
    ObservableList<Evenement> evenementsRecherche = FXCollections.observableArrayList();

    // Parcourir la liste des événements et ajouter les événements correspondant au titre de recherche
    for (Evenement evenement : evenements) {
        if (evenement.getTitreEvt().toLowerCase().contains(newValue.toLowerCase())) {
            evenementsRecherche.add(evenement);
        }
    }

    // Afficher les résultats de la recherche dans la TableView
    table_see.setItems(evenementsRecherche);

    if (evenementsRecherche.isEmpty()) {
        // Afficher un message d'erreur si aucune participation n'a été trouvée
        System.out.println("Aucune participation trouvée pour l'événement avec le titre: " + newValue);
    }
});
    }


    private List<Evenement> retrieveEvenementsFromDatabase() {
        // Return a list of Evenement objects
        ServiceEvenement se = new ServiceEvenement();
        return se.afficherEv();
    }
    
    public String retrieveFormattedDate(Connection con) throws SQLException {
    String formattedDate = null;
    String sql = "SELECT idEvt, titreEvt, nomOrg, descEvt, hdEvt, hfEvt, adresseEvt, typeEvt, DATE_FORMAT(dateEvt, '%m/%d/%Y') AS formattedDate FROM evenement";

    try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                formattedDate = resultSet.getString("formatted_date");
            }
        }
    }

    return formattedDate;
}
    
  

@FXML
private void rechercheEvenement(ActionEvent event) {
    // Récupérer le titre de l'événement à rechercher depuis le champ de texte
    String titreEvenement = recherche_text_par.getText();

    // Récupérer la liste des événements affichée dans la TableView
    ObservableList<Evenement> evenements = table_see.getItems();

    // Créer une nouvelle liste pour stocker les résultats de la recherche
    ObservableList<Evenement> evenementsRecherche = FXCollections.observableArrayList();

    // Parcourir la liste des événements et ajouter les événements correspondant au titre de recherche
    for (Evenement evenement : evenements) {
        if (evenement.getTitreEvt().toLowerCase().contains(titreEvenement.toLowerCase())) {
            evenementsRecherche.add(evenement);
        }
    }

    if (!evenementsRecherche.isEmpty()) {
        // Afficher les résultats de la recherche dans la TableView
        table_see.setItems(evenementsRecherche);
    } else {
        // Afficher un message d'erreur si aucun événement n'a été trouvé
        System.out.println("Aucun événement trouvé pour le titre " + titreEvenement);
    }
}
private void handleModifyEvent(ActionEvent event) {
    // Get the selected event from the TableView
    Evenement selectedEvent = table_see.getSelectionModel().getSelectedItem();

    if (selectedEvent != null) {
        // Open a new window or dialog for event modification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEvent.fxml"));
            Parent root = loader.load();

            // Pass the selected event to the controller of the modification window
            ModifierEventController modificationController = loader.getController();

            Stage modificationStage = new Stage();
            modificationStage.setScene(new Scene(root));
            modificationStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    } else {
        System.out.println("No event selected for modification.");
    }
}
@FXML
private void handleDeleteEvent(ActionEvent event) {
    Evenement selectedEvent = table_see.getSelectionModel().getSelectedItem();

    if (selectedEvent != null) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Voulez-vous vraiment supprimer cet événement ?");
        confirmation.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // L'utilisateur a cliqué sur OK dans la boîte de dialogue de confirmation
            // Placez le code de suppression ici

            String titreEvt = selectedEvent.getTitreEvt();

            try {
                ServiceEvenement se = ServiceEvenement.getInstance();
                int idEvt = se.getIdEvenementParNom(titreEvt);

                if (idEvt != -1) {
                    Evenement ev = new Evenement();
                    ev.setIdEvt(idEvt);
                    se.supprimerEv(ev);

                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement supprimé avec succès", "");

                    table_see.getItems().remove(selectedEvent);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'événement avec ce nom n'existe pas", "");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'événement", e.getMessage());
                e.printStackTrace();
            }
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Veuillez sélectionner un événement à supprimer", "");
    }
}

  @FXML
    private void ajouter(ActionEvent event)  throws IOException {
   
     FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterEvent.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}


@FXML
private void LikeE(ActionEvent event) {
    // Get the selected event participant
    Evenement selectedEV = table_see.getSelectionModel().getSelectedItem();
    
    if (selectedEV != null) {
        System.out.println("id_e::" + selectedEV.getIdEvt());
        Evenement ev = new Evenement();
        System.out.println("test1");

        ServiceEvenement se = new ServiceEvenement();
        
        try {
            // Attempt to like the event
            se.likeEvent(ev);
            se.updateLikesAndDislikes(ev);
            System.out.println("test2");

            // Update the display of likes and dislikes
            ServiceEvenement se2 = new ServiceEvenement();
            int[] likesAndDislikes = se2.getLikesAndDislikesCount(ev);
            nb_like_dislike.setText("Likes: " + likesAndDislikes[0] + " Dislikes: " + likesAndDislikes[1]);
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update likes and dislikes.", e.getMessage());
        }
    } else {
        showAlert(Alert.AlertType.ERROR, "Error", "No event participant selected.", "Please select an event participant to like.");
    }
}
private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);

    alert.showAndWait();
}

    @FXML
    private void Dabble(ActionEvent event)  throws IOException {
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeReservations.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}
        @FXML
    private void ModifInterf(ActionEvent event)  throws IOException {
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEvent.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}
    private void AccueilPer(ActionEvent event)  throws IOException {
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEvent.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}

    @FXML
    private void retourF(ActionEvent event) {
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
        
    }

    @FXML
    private void retourF1(ActionEvent event) {
        
         
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Back1.fxml"));
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
    private void LikeE(MouseEvent event) {
    }


}
