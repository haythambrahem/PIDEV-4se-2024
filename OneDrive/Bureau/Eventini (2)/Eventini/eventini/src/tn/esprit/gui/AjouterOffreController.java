/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import tn.esprit.entity.Offre;
import java.lang.String;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.Scene;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entity.Personne;
import tn.esprit.entity.Status;
import tn.esprit.entity.TypeOffre;
import tn.esprit.services.ServiceOffre;

/**
 * FXML Controller class
 *
 * @author imen
 */
public class AjouterOffreController implements Initializable {

    @FXML
    private TextField ajofftitre;
    @FXML
    private TextArea ajoffdescripton;
    @FXML
    private ComboBox<String> ajofftypecombobox;
    @FXML
    private ComboBox<String> ajoffstacombobox;
    @FXML
    private TextField ajoffdatefin;
    @FXML
    private TextField ajoffprix;
    @FXML
    private TextField ajoffdatedebut;
    @FXML
    private Button ajoffbuton;
    @FXML
    private Button ajoffanulerbutton;
    @FXML
    private Button modifoffbutton;
    @FXML
    private TextField ajofftitre1;
    @FXML
    private Button cheroffbutton1;
    @FXML
    private Button retourajout;
    @FXML
    private ImageView imageajout;
   

    private String imagePath; 

    @FXML
    private Button chargerimag;
    private ListView<Offre> listeView;
    @FXML
    private Button supprimeroffbutt;
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

    


// ...
//
//void afficherOffresDansListView() {
//    try {
//        ServiceOffre serviceOffre = ServiceOffre.getInstance();
//        List<Offre> offres = serviceOffre.recuperer(); 
//
//        // Créez une ObservableList pour les offres
//        ObservableList<Offre> observableOffres = FXCollections.observableArrayList(offres);
//
//        // Affichez les offres dans la ListView
//        listeView.setItems(observableOffres);
//    } catch (SQLException e) {
//        e.printStackTrace();
//        // Gérez l'exception appropriée ici
//    }
//}


   

    @FXML
    private void select(ActionEvent event) {
            String s = ajofftypecombobox.getSelectionModel().getSelectedItem().toString();
    }

    private void ajouter(ActionEvent event) {
  
  
  
        
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
         ObservableList<String> list = FXCollections.observableArrayList("Réduction", "Promotion", "Package");
        ajofftypecombobox.setItems(list);
        
         ObservableList<String> list1 = FXCollections.observableArrayList("Activé", "Expirée", "En_cours");
        ajoffstacombobox.setItems(list1);


 tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // L'utilisateur a sélectionné une offre, mettez à jour les champs du formulaire avec les détails de l'offre sélectionnée
            Offre offreSelectionnee = newSelection;
            ajofftitre.setText(offreSelectionnee.getNomOffre());
            ajoffdescripton.setText(offreSelectionnee.getDescription());
            ajofftypecombobox.setValue(offreSelectionnee.getTypeOffre().toString());
            ajoffstacombobox.setValue(offreSelectionnee.getStatus().toString());
            ajoffprix.setText(String.valueOf(offreSelectionnee.getValeurOffre()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (offreSelectionnee.getDateDebut() != null) {
                ajoffdatedebut.setText(dateFormat.format(offreSelectionnee.getDateDebut()));
            }
            if (offreSelectionnee.getDateFin() != null) {
                ajoffdatefin.setText(dateFormat.format(offreSelectionnee.getDateFin()));
            }
            String imagePath = offreSelectionnee.getImageOffre();

            // Chargez et affichez l'image de l'offre sélectionnée
            chargerImageOffre(imagePath);
        }
    });
}
 

 
        
        
     
   

    @FXML
    private void annuler(ActionEvent event) {
       
    }

    @FXML

private void modifier(ActionEvent event) {
    String nomOffre = ajofftitre.getText();
    String description = ajoffdescripton.getText();
    String typeOffre = ajofftypecombobox.getValue();
    String status = ajoffstacombobox.getValue();
    String dateDebut = ajoffdatedebut.getText();
    String dateFin = ajoffdatefin.getText();
    int prix = Integer.parseInt(ajoffprix.getText());

    if (nomOffre != null && !nomOffre.isEmpty() && description != null && !description.isEmpty() &&
            typeOffre != null && status != null && dateDebut != null && !dateDebut.isEmpty() &&
            dateFin != null && !dateFin.isEmpty() && prix > 0 && imagePath != null && !imagePath.isEmpty()) {

        Offre offreModifiee = new Offre();
        offreModifiee.setNomOffre(nomOffre); // Vous pouvez modifier le nom de l'offre

        offreModifiee.setDescription(description);
        offreModifiee.setTypeOffre(TypeOffre.valueOf(typeOffre));
        offreModifiee.setStatus(Status.valueOf(status));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebutObj = null;
        Date dateFinObj = null;

        try {
            dateDebutObj = dateFormat.parse(dateDebut);
            dateFinObj = dateFormat.parse(dateFin);
        } catch (ParseException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la conversion de date", e.getMessage());
            return;
        }

        offreModifiee.setDateDebut(dateDebutObj);
        offreModifiee.setDateFin(dateFinObj);
        offreModifiee.setValeurOffre(prix);
        offreModifiee.setImageOffre(imagePath);

        try {
            ServiceOffre serviceOffre = ServiceOffre.getInstance();
            serviceOffre.modifier(nomOffre, offreModifiee); // Assurez-vous que la méthode de service est correctement implémentée

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre modifiée avec succès", "");
            Afficher();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de l'offre", e.getMessage());
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Remplissez tous les champs", "");
    }
}



    @FXML
    private void retour(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OffreA.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Obtenir la référence de la fenêtre actuelle (AfficherPanier)
        Stage currentStage = (Stage) retourajout.getScene().getWindow();

        // Fermer la fenêtre actuelle (AfficherPanier)
        currentStage.close();
    } catch (IOException e) {
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


    @FXML
private void ajouteroffre(ActionEvent event) throws SQLException {
       String titre = ajofftitre.getText();
       ServiceOffre serviceOffre = ServiceOffre.getInstance();

       if (serviceOffre.offreExisteDeja( titre)) {
        showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de l'offre existe déjà", "Veuillez choisir un nom unique.");
        return;
    }
    

        String description = ajoffdescripton.getText();
        String typeOffre = ajofftypecombobox.getValue();
        String status = ajoffstacombobox.getValue();
        String dateDebut = ajoffdatedebut.getText();
        String dateFin = ajoffdatefin.getText();
        //int prix = Integer.parseInt(ajoffprix.getText());
       int prix;

// Vérifiez si le prix est un entier valide
try {
    prix = Integer.parseInt(ajoffprix.getText());
} catch (NumberFormatException e) {
    showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un entier valide", "Veuillez entrer un prix valide.");
    return;
}
            Personne personne = new Personne();
        personne.setId(45);

        if (titre != null && !titre.isEmpty() && description != null && !description.isEmpty() &&
                typeOffre != null && status != null && dateDebut != null && !dateDebut.isEmpty() &&
                dateFin != null && !dateFin.isEmpty() && prix > 0&& imagePath != null && !imagePath.isEmpty()) {

            Offre nouvelleOffre = new Offre();
            nouvelleOffre.setNomOffre(titre);
            nouvelleOffre.setDescription(description);
            nouvelleOffre.setTypeOffre(TypeOffre.valueOf(typeOffre));
            nouvelleOffre.setStatus(Status.valueOf(status));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebutObj = null;
            Date dateFinObj = null;

            try {
                dateDebutObj = dateFormat.parse(dateDebut);
                dateFinObj = dateFormat.parse(dateFin);
            } catch (ParseException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la conversion de date", e.getMessage());
                return;
            }

            nouvelleOffre.setDateDebut(dateDebutObj);
            nouvelleOffre.setDateFin(dateFinObj);
            nouvelleOffre.setValeurOffre(prix);
            nouvelleOffre.setImageOffre(imagePath);

            try {
               // ServiceOffre serviceOffre = ServiceOffre.getInstance();
                serviceOffre.ajouter(nouvelleOffre, personne);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre ajoutée avec succès", "");
                 Afficher();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'offre", e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "", "Remplissez tous les champs", "");
        }
        
         
        Afficher(); // Appelez la fonction Afficher pour mettre à jour la liste des offres
    
   
}
    @FXML
    private void selectt(ActionEvent event) {
            String s = ajoffstacombobox.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
   private void chargerimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File selectedImage = fileChooser.showOpenDialog(new Stage());

        if (selectedImage != null) {
            imagePath = selectedImage.toURI().toString(); // Obtenez le chemin de l'image comme une URL

            // Affichez l'image sélectionnée dans l'ImageView
            Image image = new Image(imagePath); // Chargez l'image depuis le chemin du fichier
            imageajout.setImage(image); // Affichez l'image dans l'ImageView
        }
    }
   // Méthode pour charger et afficher l'image de l'offre
private void chargerImageOffre(String imagePath) {
    if (imagePath != null && !imagePath.isEmpty()) {
        try {
            Image image = new Image(imagePath);

            imageajout.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {

        Image imageParDefaut = new Image("C:\\Users\\imen\\Documents\\NetBeansProjects\\projetJavaFX\\src\\image\\image.offre.PNG");
        imageajout.setImage(imageParDefaut);

        imageajout.setImage(null);

    }
}


    @FXML
private void supprimer(ActionEvent event) {
    String nomOffre = ajofftitre.getText(); // Récupérez le nom de l'offre à supprimer

    if (nomOffre != null && !nomOffre.isEmpty()) {
        try {
            ServiceOffre serviceOffre = ServiceOffre.getInstance();
            serviceOffre.supprimer(nomOffre); // Appelez la méthode de service pour supprimer l'offre

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre supprimée avec succès", "");
           // Mettez à jour la liste des offres après la suppression
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'offre", e.getMessage());
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "", "Veuillez saisir le nom de l'offre à supprimer", "");
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
        //desctable.setCellValueFactory(new PropertyValueFactory<>("description"));
        // Affichez les offres dans le TableView
        tableView.setItems(offres);
          for (Offre offre : offres) {
            chargerImageOffre(offre.getImageOffre());
        }
    } catch (SQLException e) {
        // Gérez les exceptions si quelque chose ne se passe pas comme prévu lors de la récupération des offres depuis la base de données
        e.printStackTrace();
    }
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
  
public void chercher(ActionEvent event) {
    String nomOffre = ajofftitre1.getText(); 

    if (nomOffre != null && !nomOffre.isEmpty()) {
       
        try {
            ServiceOffre serviceOffre = ServiceOffre.getInstance();
    Offre offreTrouvee = serviceOffre.chercherOffre(nomOffre);

           if (offreTrouvee != null) {
    // Mettez à jour les champs du formulaire avec les détails de l'offre trouvée
    ajofftitre.setText(offreTrouvee.getNomOffre());
    ajoffdescripton.setText(offreTrouvee.getDescription());
    ajofftypecombobox.setValue(offreTrouvee.getTypeOffre().toString());
    ajoffstacombobox.setValue(offreTrouvee.getStatus().toString());
    ajoffprix.setText(String.valueOf(offreTrouvee.getValeurOffre()));
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if (offreTrouvee.getDateDebut() != null) {
        ajoffdatedebut.setText(dateFormat.format(offreTrouvee.getDateDebut()));
    }
    if (offreTrouvee.getDateFin() != null) {
        ajoffdatefin.setText(dateFormat.format(offreTrouvee.getDateFin()));
    }
 String imagePath = offreTrouvee.getImageOffre();
      System.out.println("Chemin de l'image : " + imagePath);

   
    Image image = new Image("file:" + imagePath); 
    imageajout.setImage(image);
    // Assurez-vous d'afficher également l'image si elle est disponible
} else {
    showAlert(Alert.AlertType.INFORMATION, "Information", "Aucune offre trouvée", "Aucune offre trouvée avec ce nom.");
}

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche de l'offre", e.getMessage());
            e.printStackTrace();
        }
    } else {
        showAlert(Alert.AlertType.INFORMATION, "Information", "Veuillez saisir le nom de l'offre à rechercher.", "");
    }
  
}

//public void show() {
//    try {
//        // Récupérez la liste des offres depuis la source de données (base de données, liste, etc.) en utilisant la méthode de récupération appropriée.
//        // Assurez-vous d'utiliser le service ou l'objet de données pour accéder à vos offres.
//
//        ServiceOffre serviceOffre = ServiceOffre.getInstance();
//        List<Offre> listeOffres = serviceOffre.recuperer();
//
//        // Convertissez la liste en une liste observable pour l'affichage dans le TableView.
//        ObservableList<Offre> offres = FXCollections.observableArrayList(listeOffres);
//
//        // Configurez le TableView pour afficher les données des offres.
//        // Assurez-vous d'adapter ces configurations aux attributs de votre entité Offre.
//         ajoffdatedebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
//         ajofftypecombobox.setCellValueFactory(new PropertyValueFactory<>("typeOffre"));
//        ajoffdescripton.setCellValueFactory(new PropertyValueFactory<>("description"));
//        prixTable.setCellValueFactory(new PropertyValueFactory<>("valeurOffre"));
//        // Configurez les autres colonnes de votre TableView comme nomRestoCol, userCol, etc.
//
//        // Affichez les offres dans le TableView.
//        tableView.setItems(offres);
//        
//        // Mettez en place la logique de filtrage similaire à celle que vous aviez pour la classe Badge.
//        FilteredList<Offre> filteredData = new FilteredList<>(offres, o -> true);
//
//        RestoNameTextfieldBadge.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(offre -> {
//                String critereNomOffre = RestoNameTextfieldBadge.getText().toLowerCase();
//                // Ajoutez d'autres critères de recherche pour les attributs de l'entité Offre.
//
//                boolean nomOffreCorrespond = offre.getNomOffre().toLowerCase().contains(critereNomOffre);
//                // Ajoutez d'autres conditions de correspondance pour les autres attributs de l'entité Offre.
//
//                return critereNomOffre.isEmpty() || nomOffreCorrespond;
//                // Ajoutez d'autres conditions de correspondance pour les autres attributs de l'entité Offre.
//            });
//        });
//
//        SortedList<Offre> sortedData = new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
//
//        tableView.setItems(sortedData);
//    } catch (SQLException e) {
//        // Gérez les exceptions si quelque chose ne se passe pas comme prévu lors de la récupération des offres depuis la source de données.
//        e.printStackTrace();
//    }
}





    






