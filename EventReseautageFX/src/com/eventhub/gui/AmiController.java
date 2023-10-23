package com.eventhub.gui;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import com.eventhub.entities.Ami;
import com.eventhub.entities.User;
import com.eventhub.services.AmiService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;

import java.util.List;

public class AmiController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Ami> suggestedFriendsList;
    @FXML
    private ListView<Ami> currentFriendsList;
    @FXML
    private ListView<Ami> mutualFriendsList;
    @FXML
    private ListView<Ami> invitationsList;
    @FXML
    private Button addFriendButton;
    @FXML
    private Button removeFriendButton;
    @FXML
    private Button acceptInvitationButton;
    @FXML
    private Button rejectInvitationButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;

    @FXML
    private Button refreshButton;
    private AmiService amiService;
    private User loggedInUser; // Ajout d'une référence à l'utilisateur connecté

    public AmiController() {
    }

    public AmiController(AmiService amiService, User loggedInUser) {
        this.amiService = amiService;
        this.loggedInUser = loggedInUser;
    }

    @FXML
    private void handleSearchAction() {
        String searchQuery = searchField.getText();
        List<Ami> foundFriends = amiService.rechercherAmiParNom(searchQuery, loggedInUser);
        suggestedFriendsList.getItems().setAll(foundFriends);
    }
   @FXML
private void handleAddFriendAction() {
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();

    // Vérifiez si les champs de saisie ne sont pas vides
    if (firstName.isEmpty() || lastName.isEmpty()) {
        // Affichez un message d'erreur à l'utilisateur en utilisant une fenêtre de dialogue
        showError("Veuillez remplir tous les champs.");
    } else {
        // Générez un identifiant unique pour le nouvel ami en appelant la méthode generateUniqueFriendId
        long uniqueFriendId = generateUniqueFriendId();

        // Créez un nouvel ami en utilisant l'identifiant unique
        User friendUser = new User(uniqueFriendId, firstName, lastName, "", ""); // Laissez les champs email et mot de passe vides

        // Créez un nouvel ami en utilisant l'ID généré et l'objet User
        Ami newFriend = new Ami(uniqueFriendId, friendUser);

        // Ajoutez une vérification pour éviter les valeurs nulles
        if (newFriend != null) {
            boolean ajoutReussi = amiService.ajouterAmi(newFriend);
            if (ajoutReussi) {
                // Effacez les champs de saisie après l'ajout
                firstNameField.clear();
                lastNameField.clear();
                suggestedFriendsList.getItems().remove(newFriend);
                currentFriendsList.getItems().add(newFriend);
            } else {
                // Gérez le cas où l'ajout a échoué en affichant un message d'erreur
                showError("L'ajout de l'ami a échoué. Veuillez réessayer plus tard.");
            }
        }
    }
}

// Méthode de génération d'identifiant unique (exemple simple)
private long uniqueFriendIdCounter = 1; // Initialisation du compteur

// Méthode de génération d'identifiant unique (exemple simple)
private long generateUniqueFriendId() {
    // Incrémente le compteur pour chaque nouvel ami
    return uniqueFriendIdCounter++;
}


// Méthode pour afficher une fenêtre de dialogue d'erreur
private void showError(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

    @FXML
    private void handleRemoveFriendAction() {
        Ami selectedFriend = currentFriendsList.getSelectionModel().getSelectedItem();
        boolean suppressionReussie = amiService.supprimerInvitation(loggedInUser, selectedFriend);
        if (suppressionReussie) {
            currentFriendsList.getItems().remove(selectedFriend);
        }
    }

    @FXML
    private void handleAcceptInvitationAction() {
        Ami selectedInvitation = invitationsList.getSelectionModel().getSelectedItem();
        boolean acceptationReussie = amiService.accepterInvitation(loggedInUser, selectedInvitation);
        if (acceptationReussie) {
            invitationsList.getItems().remove(selectedInvitation);
            currentFriendsList.getItems().add(selectedInvitation);
        }
    }

    @FXML
private void handleRejectInvitationAction() {
    Ami selectedInvitation = invitationsList.getSelectionModel().getSelectedItem();
    boolean rejetReussi = amiService.rejeterInvitation(loggedInUser, selectedInvitation);
    if (rejetReussi) {
        invitationsList.getItems().remove(selectedInvitation);
    }
}

    // Dans la méthode handleRefreshAction
@FXML
private void handleRefreshAction() {
    if (amiService != null && invitationsList != null) {
        List<Ami> amis = amiService.listerAmis(loggedInUser);
        List<Ami> invitations = amiService.listerInvitations(loggedInUser);
        
        // Assurez-vous que les listes sont initialisées avant de les utiliser
        if (amis != null && invitations != null) {
            invitationsList.getItems().setAll(invitations);
        }

        currentFriendsList.getItems().setAll(amis);
    }
}


    public void initialize() {
         if (suggestedFriendsList != null) {
        // Configure cell factories for the ListViews
        configureCellFactory(suggestedFriendsList);
    }
        // Configure cell factories for the ListViews
        configureCellFactory(suggestedFriendsList);
        configureCellFactory(currentFriendsList);
        configureCellFactory(mutualFriendsList);
        configureCellFactory(invitationsList);
    }

private void configureCellFactory(ListView<Ami> listView) {
    System.out.println("Creating ListCell for Ami");

    if (listView == null) {
        System.out.println("ListView is null");
        return; // Sortir de la méthode si listView est null
    }

    listView.setCellFactory(param -> new ListCell<Ami>() {
        @Override
        protected void updateItem(Ami item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.getUser().getFirstName() + " " + item.getUser().getLastName());
            }
        }
    });
}





    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
     public void setAmiService(AmiService amiService) {
        this.amiService = amiService;
    }

    // Other methods and logic for the friend interface
}
