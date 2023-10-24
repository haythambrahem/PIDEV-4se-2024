/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entity.Offre;
import tn.esprit.entity.Panier;
import tn.esprit.entity.Status;
import tn.esprit.entity.TypeOffre;
import tn.esprit.services.ServiceOffre;
import tn.esprit.services.ServicePanier;
import javafx.scene.image.Image;
import tn.esprit.entity.Personne;
import tn.esprit.entity.WeatherData;

/**
 * FXML Controller class
 *
 * @author imen
 */
public class AfficherOffreController implements Initializable {

    @FXML
    private ImageView imgaffi;
    @FXML
    private Button ajouterAuPanir;
    @FXML
    private Button affchercher;
    @FXML
    private TextField idcheraffiche;
    @FXML
    private TableView<Offre> tableView;
    @FXML
    private TableColumn<Offre,String> titretable;
    @FXML
    private TableColumn<Offre, String> desctable;
    @FXML
    private TableColumn<Offre, String> typetable1;
    @FXML
    private TableColumn<Offre, String> statustable;
    @FXML
    private TableColumn<Offre, Date> dateDebTable;
    @FXML
    private TableColumn<Offre, Date> dateDFinTable1;
    @FXML
    private TableColumn<Offre,Integer> prixTable;

    /**
     * Initializes the controller class.
     */// Déclarez les attributs pour Offre, Panier et User ici
    private Offre offre;
    private Panier panier;
    private Personne personne ;
    @FXML
    private Button meteobutton;
    @FXML
    private Label labelmeteo;
    @FXML
    private Button retourOffe;
    @FXML
    private Button supprimer;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation de Offre, Panier et User
        offre = new Offre();
        panier = new Panier();
       Personne personne = new Personne();

         offre.setNomOffre("Nom de l'offre");
         offre.setValeurOffre(50);
     offre.setTypeOffre(TypeOffre.Promotion);
     offre.setStatus(Status.Expirée);
         offre.setDescription(" ffffffffffff ");
    offre.setDateDebut(java.sql.Date.valueOf("2024-05-12"));
   offre.setDateFin(java.sql.Date.valueOf("2024-05-12"));
    offre.setImageOffre("ggggggggggg");
    panier.setDatePanier(java.sql.Date.valueOf("2024-05-10"));
   



        panier.setTotal(1);
        
        
         tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // L'utilisateur a sélectionné une offre, affichez son image
            chargerImageOffre(newSelection.getImageOffre());
        } else {
            // Aucune offre sélectionnée, vous pouvez effacer l'ImageView ou afficher une image par défaut.
            imgaffi.setImage(null); // Effacez l'image précédente
        }
    });
             
             
         }
    


    
private void handleOfferSelection(MouseEvent event) {
    Offre offreSelectionnee = tableView.getSelectionModel().getSelectedItem();
    if (offreSelectionnee != null) {
        // Initialisation des objets Panier, Offre et User ici
        Panier panier = new Panier();
        Personne personne1 = offreSelectionnee.getPersonne();
        panier.setTotal(1); // Initialisation du total à 1

        // Utilisez l'offre sélectionnée, créez un objet Offre, puis utilisez-le.
        Offre monOffre = new Offre(offreSelectionnee); // Cela suppose que votre classe Offre a un constructeur de copie.

        // Vous pouvez maintenant utiliser monOffre ou effectuer d'autres actions avec l'offre sélectionnée.
    }
}

   // Méthode pour charger et afficher l'image de l'offre
private void chargerImageOffre(String imagePath) {
    if (imagePath != null && !imagePath.isEmpty()) {
        try {
            Image image = new Image(imagePath);

            imgaffi.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {

        Image imageParDefaut = new Image("C:\\Users\\imen\\Documents\\NetBeansProjects\\projetJavaFX\\src\\image\\image.offre.PNG");
        imgaffi.setImage(imageParDefaut);

        imgaffi.setImage(null);

    }
}

@FXML
private void ajouterAuPanier(ActionEvent event) {
    Panier panier = new Panier();
    Offre offreSelectionnee = tableView.getSelectionModel().getSelectedItem();
    if (offreSelectionnee == null) {
        // Gérez le cas où aucune offre n'est sélectionnée
        System.out.println("Aucune offre sélectionnée.");
        return; // Sortez de la méthode pour éviter les erreurs
    }
   
        Personne personneActuelle = offreSelectionnee.getPersonne();
    // Obtenez l'utilisateur associé à l'offre sélectionnée

    ServicePanier servicePanier = new ServicePanier();
      try {
        servicePanier.ajouter(panier, offreSelectionnee, personneActuelle);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre ajoutée au panier avec succès", "");
    } catch (Exception e) {
        showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'offre au panier", e.getMessage());
        e.printStackTrace();
    }
}


void Afficher() {
    try {
        // Récupérez la liste des offres depuis la base de données en utilisant la méthode recuperer()
        ServiceOffre ps = new ServiceOffre();
        List<Offre> listeOffres = ps.recuperer();

        // Convertissez la liste en une liste observable pour l'affichage dans le TableView
        ObservableList<Offre> offres = FXCollections.observableArrayList(listeOffres);

        // Configurez le TableView pour afficher les données des offres
        titretable.setCellValueFactory(new PropertyValueFactory<>("nomOffre"));
        desctable.setCellValueFactory(new PropertyValueFactory<>("description"));
        typetable1.setCellValueFactory(new PropertyValueFactory<>("typeOffre"));
        statustable.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateDebTable.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateDFinTable1.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        prixTable.setCellValueFactory(new PropertyValueFactory<>("valeurOffre"));

        // Affichez les offres dans le TableView
        tableView.setItems(offres);
    } catch (SQLException e) {
        // Gérez les exceptions si quelque chose ne se passe pas comme prévu lors de la récupération des offres depuis la base de données
        e.printStackTrace();
    }

}



 private void showAlert(Alert.AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}
     void Affiche(ActionEvent event) {
//    try {
//        // Récupérez la liste des offres depuis la base de données en utilisant la méthode recuperer()
//        ServiceOffre ps = new ServiceOffre();
//        List<Offre> listeOffres = ps.recuperer();
//
//        // Convertissez la liste en une liste observable pour l'affichage dans le TableView
//        ObservableList<Offre> offres = FXCollections.observableArrayList(listeOffres);
//
//        // Configurez le TableView pour afficher les données des offres
//        titretable.setCellValueFactory(new PropertyValueFactory<>("nomOffre"));
//        desctable.setCellValueFactory(new PropertyValueFactory<>("description"));
//        typetable1.setCellValueFactory(new PropertyValueFactory<>("typeOffre"));
//        statustable.setCellValueFactory(new PropertyValueFactory<>("status"));
//        dateDebTable.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
//        dateDFinTable1.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
//        prixTable.setCellValueFactory(new PropertyValueFactory<>("valeurOffre"));
//
//        // Affichez les offres dans le TableView
//        tableView.setItems(offres);
//    } catch (SQLException e) {
//        // Gérez les exceptions si quelque chose ne se passe pas comme prévu lors de la récupération des offres depuis la base de données
//        e.printStackTrace();
//    }
}

@FXML
private void chercherAffiche(ActionEvent event) {
    String nomOffreRecherche = idcheraffiche.getText(); // Récupérez le texte saisi par l'utilisateur

    try {
        // Utilisez la méthode chercherOffre pour rechercher l'offre par son nom
        ServiceOffre ps = new ServiceOffre();
        Offre offreTrouvee = ps.chercherOffre(nomOffreRecherche);

        if (offreTrouvee != null) {
            // Effacez le contenu actuel du TableView
            tableView.getItems().clear();

            // Ajoutez l'offre trouvée au TableView
            ObservableList<Offre> offres = FXCollections.observableArrayList(offreTrouvee);
            tableView.setItems(offres);
        }
    } catch (SQLException e) {
        // Gérez les exceptions si quelque chose ne se passe pas comme prévu lors de la recherche de l'offre
        e.printStackTrace();
    }

   }
    @FXML
   private void meteo(ActionEvent event) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=36.805301&lon=10.169022&appid=a1459670604cd9b095f5ebf6fb765c49";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                // Use Jackson to map the JSON response to the WeatherData class
                ObjectMapper objectMapper = new ObjectMapper();
                WeatherData weatherData = objectMapper.readValue(response.toString(), WeatherData.class);

                // Create a StringBuilder to store all details
                StringBuilder details = new StringBuilder();
                details.append("City: ").append(weatherData.getName()).append("\n");
                details.append("Temperature: ").append(weatherData.getMain().getTemp()).append(" K\n");
                details.append("Feels Like: ").append(weatherData.getMain().getFeelsLike()).append(" K\n");
                details.append("Min Temperature: ").append(weatherData.getMain().getTempMin()).append(" K\n");
                details.append("Max Temperature: ").append(weatherData.getMain().getTempMax()).append(" K\n");
                details.append("Pressure: ").append(weatherData.getMain().getPressure()).append(" hPa\n");
                details.append("Humidity: ").append(weatherData.getMain().getHumidity()).append(" %\n");
                details.append("Visibility: ").append(weatherData.getVisibility()).append(" meters\n");
                details.append("Wind Speed: ").append(weatherData.getWind().getSpeed()).append(" m/s\n");
                details.append("Wind Degree: ").append(weatherData.getWind().getDeg()).append("\n");
                details.append("Cloudiness: ").append(weatherData.getClouds().getAll()).append("%\n");
                details.append("Timestamp: ").append(weatherData.getDt()).append("\n");
                details.append("Country: ").append(weatherData.getSys().getCountry()).append("\n");
                details.append("Sunrise: ").append(weatherData.getSys().getSunrise()).append("\n");
                details.append("Sunset: ").append(weatherData.getSys().getSunset()).append("\n");

                // Update the label with all details
                labelmeteo.setText(details.toString());
            } else {
                labelmeteo.setText("HTTP GET request failed with response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            labelmeteo.setText("An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void retourOffre(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Offre.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Obtenir la référence de la fenêtre actuelle (AfficherPanier)
        Stage currentStage = (Stage) retourOffe.getScene().getWindow();

        // Fermer la fenêtre actuelle (AfficherPanier)
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void supprimerAff(ActionEvent event) throws SQLException {
        Offre offreSelectionnee = tableView.getSelectionModel().getSelectedItem();

    if (offreSelectionnee != null) {
        ServiceOffre serviceOffre = ServiceOffre.getInstance();
        serviceOffre.supprimer(offreSelectionnee.getNomOffre()); // Appelez la méthode de service pour supprimer l'offre sélectionnée
        // Mettez à jour la liste des offres dans votre TableView
        tableView.getItems().remove(offreSelectionnee);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre supprimée avec succès", "");
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Veuillez sélectionner une offre à supprimer", "");
    }
}
    
}
   

  

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
  
    
    
    
    
 
