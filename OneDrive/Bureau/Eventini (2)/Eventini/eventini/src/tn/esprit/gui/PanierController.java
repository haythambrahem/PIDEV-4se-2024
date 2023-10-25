/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.entity.Offre;
import tn.esprit.entity.Panier;
import tn.esprit.entity.Personne;
import tn.esprit.services.ServicePanier;
import tn.esprit.services.EmailService;
//import org.controlsfx.control.Notifications;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//

/**
 * FXML Controller class
 *
 * @author imen
 */
public class PanierController implements Initializable {
    private int idPanier; // Déclaration de la variable idPanier

    @FXML
    private ImageView imgaffi;
    @FXML
    private Button affchercher;
    @FXML
    private ListView<String> listview;
    @FXML
    private Button confirmerPanier;
    @FXML
    private TextField totalPrix;
    @FXML
    private TextField idUsercheraffiche;
       private Panier panier;
       private List<Offre> listeOffres; // Assurez-vous de déclarer et d'initialiser cette liste


    @FXML
    private Button retourPanier1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panier = new Panier();
        
        // TODO
    }    






 @FXML
   private void confirmer(ActionEvent event) {
    // ... votre code précédent pour afficher la boîte de dialogue de confirmation

    // Récupérez les informations de la personne (vous devrez définir la manière de le faire dans votre application)
    Personne personne = new Personne();
    personne.setPrenom("ltifi");
    personne.setEmail("nasriamin300@gmail.com");

    // Utilisez la classe EmailService pour envoyer l'e-mail de confirmation
    EmailService.sendConfirmationEmail(personne);

    // Affichez un message de confirmation (vous pouvez personnaliser ce message)
    Alert confirmationAlert = new Alert(AlertType.INFORMATION);
    confirmationAlert.setTitle("Confirmation");
    confirmationAlert.setHeaderText("C'est confirmé.");
    confirmationAlert.setContentText("Vous allez recevoir les détails par e-mail.");
    confirmationAlert.showAndWait();
    Notifications.create()
    .title("Confirmation").text("votre commande a été enregistré"
            + "Vous allez recevoir les détails par e-mail").showWarning();
}




public void calculerTotal(Panier panier, TextField totalPrix) {
    int total = 0; // Initialisation du total à zéro

    // Parcourez la liste des offres dans le panier
    for (Offre offre : panier.getListeOffres()) {
        total += offre.getValeurOffre(); // Ajoute la valeur de chaque offre au total
    }

    // Affichez le total dans le TextField
    totalPrix.setText(Integer.toString(total));
}

//    @FXML
//    private void chercher(ActionEvent event) {
//
//    String idPanierText = idcheraffiche.getText();
//    if (!idPanierText.isEmpty()) {
//        try {
//            ServicePanier servicePanier = new ServicePanier(); // Créez une instance de ServicePanier
//
//            int idPanier = Integer.parseInt(idPanierText);
//            Panier panier = servicePanier.getPanierParId(idPanier);
//
//            if (panier != null) {
//                // Vous pouvez maintenant afficher les détails du panier dans le ListView
//                listview.getItems().clear();
//                listview.getItems().add("ID Panier : " + panier.getIdPanier());
//                listview.getItems().add("Total : " + panier.getTotal());
//                listview.getItems().add("Date du Panier : " + panier.getDatePanier());
//                // Ajoutez d'autres détails si nécessaire
//            } else {
//                // Aucun panier trouvé avec l'ID spécifié
//                listview.getItems().clear();
//                listview.getItems().add("Aucun panier trouvé avec cet ID.");
//            }
//        } catch (NumberFormatException e) {
//            // Gestion de l'exception si l'ID entré n'est pas un entier
//            listview.getItems().clear();
//            listview.getItems().add("Veuillez entrer un ID de panier valide.");
//        }
//      
//        
//             double total = Panier.calculerTotalPanier(idPanier);
//                    totalPrix.setText(String.valueOf(total));
//
//
//   
//    }  
//    }
//    public List<Offre> getOffresPanier(int idPanier) {
//    List<Offre> offres = new ArrayList<>();
//    // Remplacez ce bloc par la logique pour récupérer les offres du panier
//    try {
//        // Supposons que vous avez un service pour gérer les paniers
//        ServicePanier servicePanier = new ServicePanier();
//
//        // Obtenez le panier en fonction de l'ID du panier
//        Panier panier = servicePanier.getPanierById(idPanier);
//
//        if (panier != null) {
//            // Obtenez la liste des offres dans le panier
//            offres = panier.getListeOffres();
//        } else {
//            System.out.println("Panier non trouvé.");
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        System.out.println("Une erreur s'est produite lors de la récupération des offres du panier.");
//    }
//
//    return offres;
//}
////////////////////////////*****te5dem tafichi list shiha **********************************************************************
 @FXML
private void chercher(ActionEvent event) {
    int sommeValeurOffre = 0; // Initialisation de la somme à zéro

    String idUserText = idUsercheraffiche.getText();
    if (!idUserText.isEmpty()) {
        try {
            ServicePanier servicePanier = new ServicePanier(); // Créez une instance de ServicePanier

            int id = Integer.parseInt(idUserText);
            Panier panier = servicePanier.getPanierParId(id);

            listview.getItems().clear(); // Effacez les éléments précédents du ListView

            if (panier != null) {
                // Ajoutez les détails du panier au ListView
                listview.getItems().add("ID Panier : " + panier.getIdPanier());
                listview.getItems().add("Total : " + panier.getTotal());
                listview.getItems().add("Date du Panier : " + panier.getDatePanier());
                listview.getItems().add("Offres : " );
                for(Offre offre : panier.getListeOffres()){
                    
                 listview.getItems().add("nom Offre : "  + offre.getNomOffre());
                  listview.getItems().add("valeurOffre : "  + offre.getValeurOffre());
                    sommeValeurOffre += offre.getValeurOffre(); // Ajoute la valeur de l'offre à la somme

                    System.out.println(" " + sommeValeurOffre);
                }
                totalPrix.setText(String.valueOf(sommeValeurOffre));

            } else {
                // Aucun panier trouvé avec l'ID spécifié
                listview.getItems().add("Aucun panier trouvé avec cet ID.");
            }
        } catch (NumberFormatException e) {
            // Gestion de l'exception si l'ID entré n'est pas un entier
            listview.getItems().clear();
            listview.getItems().add("Veuillez entrer un ID de panier valide.");
        }
    }
  

}




    @FXML
    private void retourPanier(ActionEvent event) {
        
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Offre.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Obtenir la référence de la fenêtre actuelle (AfficherPanier)
        Stage currentStage = (Stage) retourPanier1.getScene().getWindow();

        // Fermer la fenêtre actuelle (AfficherPanier)
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
   







    
    }
