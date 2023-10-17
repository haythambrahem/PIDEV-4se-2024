/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import models.Logement;
import models.type;
import services.ServiceLogement;

/**
 * FXML Controller class
 *
 * @author FADI
 */
public class LogementController implements Initializable {

    @FXML
    private JFXTextField txt_searchid;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_superfice;
    @FXML
    private JFXComboBox<type> cb_type;
    @FXML
    private JFXTextField txt_loyer;
    @FXML
    private Label lab_url;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private JFXButton btn_delete;
    @FXML
    private JFXButton icon_importer;
    @FXML
    private ImageView image_logement;
    @FXML
    private JFXTextField txt_region;
private ComboBox<String> cbLogementType;
 Connection con;
    Statement ste;
                 Logement loge = new Logement();

    
    @FXML
    private JFXListView<Logement> listView_logement;
    @FXML
    private Button LogAccueil;
    @FXML
    private Button refreshButton;

 
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // cb_type.setItems(FXCollections.observableArrayList("maison","studio","appartement"));
        // TODO
        ObservableList<type> enumValues = FXCollections.observableArrayList(type.values());
cb_type.setItems(enumValues);
ServiceLogement serviceLogement = new ServiceLogement();
        List<Logement> logements = serviceLogement.affihcerLogement();
ObservableList<Logement> logementList = FXCollections.observableArrayList(logements);
listView_logement.setItems(logementList);
 
        
    }    



ObservableList<type> types = FXCollections.observableArrayList(type.values());

    @FXML
    private void ajouterLogement(ActionEvent event) {
      Logement selectedLogement = listView_logement.getSelectionModel().getSelectedItem();
    if (selectedLogement != null) {
        // Show an alert to inform the user to clear the selection before adding a new logement
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez désélectionner le logement existant avant d'en ajouter un nouveau.");
        alert.showAndWait();
         txt_adr.clear();
        txt_superfice.clear();
        txt_loyer.clear();
        cb_type.getSelectionModel().clearSelection(); // Clear the selected value in cb_type
        txt_region.clear();
        image_logement.setImage(null);
        listView_logement.getSelectionModel().clearSelection();
        return; // Exit the method
    }

    String adr = txt_adr.getText();
    String superf = txt_superfice.getText();
    String loy = txt_loyer.getText();
    type logementType = cb_type.getValue();
    String reg = txt_region.getText();
    File image = new File(lab_url.getText());
    

    if (adr.isEmpty() || reg.isEmpty() || logementType == null || superf == null || loy == null || !image.exists()) {
        // Show an error message if any required field is missing
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez remplir tous les champs obligatoires.");
        errorAlert.showAndWait();
    } else {
        int superfice = Integer.parseInt(superf);
        int loyer = Integer.parseInt(loy);

        Logement logement = new Logement();
        logement.setAdr(adr);
        logement.setSuperfice(superfice);
        logement.setLoyer(loyer);
        logement.setType(logementType);
        logement.setRegion(reg);
        logement.setImage(lab_url.getText());

        // Call the ajouterLogement method from your ServiceLogement
        ServiceLogement serviceLogement = new ServiceLogement();
        serviceLogement.ajouterLogement(logement);

        // Show a success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Le logement a été ajouté avec succès.");
        successAlert.showAndWait();

        List<Logement> logements = serviceLogement.affihcerLogement();
        ObservableList<Logement> logementList = FXCollections.observableArrayList(logements);
        listView_logement.setItems(logementList);
        
        // Clear input fields and reset ComboBox selections
        txt_adr.clear();
        txt_superfice.clear();
        txt_loyer.clear();
        cb_type.getSelectionModel().clearSelection(); // Clear the selected value in cb_type
        txt_region.clear();
         image_logement.setImage(null);
    }
}

// Read image data from the image file

    @FXML
    private void modifierLogement(ActionEvent event) throws SQLException {
       Logement selectedLogement = listView_logement.getSelectionModel().getSelectedItem();

    if (selectedLogement == null) {
        // Show an alert to inform the user to select a logement to modify
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un logement à modifier.");
        alert.showAndWait();
        return; // Exit the method since there's nothing to modify
    }

    // Now you have a selected logement to modify
    String adr = txt_adr.getText();
    String superf = txt_superfice.getText();
    String loy = txt_loyer.getText();
    type logementType = cb_type.getValue(); // Assuming you have cb_type for the logementType
    String reg = txt_region.getText();
File image = new File(lab_url.getText());
    if (adr.isEmpty() || reg.isEmpty() || logementType == null || superf.isEmpty() || loy.isEmpty() || !image.exists()) {
        // Show an error message if any required field is missing
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez remplir tous les champs obligatoires.");
        errorAlert.showAndWait();
    } else {
        int superfice = Integer.parseInt(superf);
        int loyer = Integer.parseInt(loy);

        // Modify the selected logement
        selectedLogement.setAdr(adr);
        selectedLogement.setSuperfice(superfice);
        selectedLogement.setLoyer(loyer);
        selectedLogement.setType(logementType);
        selectedLogement.setRegion(reg);
         selectedLogement.setImage(lab_url.getText());
        // You might want to update the image URL as well, but this depends on your requirements.

        // Update the logement using your service
        ServiceLogement serviceLogement = new ServiceLogement();
        serviceLogement.modifierLogement(selectedLogement);
//        List<Logement> logements = serviceLogement.affihcerLogement();
//        ObservableList<Logement> logementList = FXCollections.observableArrayList(logements);
//        listView_logement.setItems(logementList);
  // Remove the selected logement from the list
    listView_logement.getItems().remove(selectedLogement);

    // Add the updated logement back to the list
    listView_logement.getItems().add(selectedLogement);
      //   listView_logement.getSelectionModel().clearSelection();
       // Clear input fields and reset ComboBox selections
        txt_adr.clear();
        txt_superfice.clear();
        txt_loyer.clear();
        cb_type.getSelectionModel().clearSelection();
        txt_region.clear();
         image_logement.setImage(null);
         listView_logement.getSelectionModel().clearSelection();
      // Show a success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Le logement a été modifié avec succès.");
        successAlert.showAndWait();
  // Refresh the list of logements in the ListView
       
       
       
    }
}
    

    @FXML
    private void supprimerLogement(ActionEvent event) throws SQLException {
        Logement selectedLogement = listView_logement.getSelectionModel().getSelectedItem();

    if (selectedLogement == null) {
         
        // Show an alert to inform the user to select a logement to modify
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un logement à modifier.");
        alert.showAndWait();
        return; // Exit the method since there's nothing to modify
    } else{

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer le logement ?");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce logement ?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int logementId = selectedLogement.getId();
            ServiceLogement serviceLogement = new ServiceLogement();
            Logement logement = serviceLogement.getLogementById(logementId);

            serviceLogement.supprimerLogement(logement);
            List<Logement> logements = serviceLogement.affihcerLogement();
            ObservableList<Logement> logementList = FXCollections.observableArrayList(logements);
            listView_logement.setItems(logementList);
        }
    }
}
    
    

    @FXML
    private void importerImage(MouseEvent event) {
         FileChooser fc=new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files","*.png","*jpg"));
        File f=fc.showOpenDialog(null);
        if(f!=null){
            lab_url.setText(f.getAbsolutePath());
            Image image= new Image(f.toURI().toString(),image_logement.getFitWidth(),image_logement.getFitHeight(),true,true);
            image_logement.setImage(image);
            
        }
        
    
   
    }
   private final ObservableList<String> typeNames = FXCollections.observableArrayList();

    public void initialize() {
        
        // Populate the typeNames list with enum names
        for (type t : type.values()) {
            typeNames.add(t.toString());
        }

        // Set the type names as items for the ComboBox
        cbLogementType.setItems(typeNames);

        // Create a StringConverter to convert between String and enum
        cbLogementType.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        });
    }
   


    @FXML
    private void listLogEvn(MouseEvent event) throws SQLException {
                Logement selectedLogement = listView_logement.getSelectionModel().getSelectedItem();
    if (selectedLogement != null) {
        int logementId = selectedLogement.getId();
        ServiceLogement serviceLogement = new ServiceLogement();
        Logement logement = serviceLogement.getLogementById(logementId);

        // Populate the input fields with the selected Logement's data
        txt_adr.setText(logement.getAdr());
        txt_superfice.setText(String.valueOf(logement.getSuperfice()));
        txt_loyer.setText(String.valueOf(logement.getLoyer()));
        cb_type.setValue(logement.getType());
        txt_region.setText(logement.getRegion());
        // Update the lab_url based on the Logement's image path
        lab_url.setText(logement.getImage());
        File imageFile = new File(logement.getImage());
        Image image = new Image(imageFile.toURI().toString());
        image_logement.setImage(image);
                
                
    }

    }

    @FXML
    private void searchLogement(ActionEvent event) {
   if (txt_searchid != null) {
        String critere = txt_searchid.getText().trim(); // Trim the input to remove leading/trailing whitespace
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
                    txt_superfice.setText(String.valueOf(logement.getSuperfice()));
                    txt_loyer.setText(String.valueOf(logement.getLoyer()));
                    txt_region.setText(logement.getRegion());
                    cb_type.setValue(logement.getType());

                    System.out.println(logement.getImage());

                    if (logement.getImage() != null && !logement.getImage().isEmpty()) {
                        String fileUrl = new File(logement.getImage()).toURI().toString();
                        System.out.println(fileUrl);
                        Image image = new Image(fileUrl);
                        image_logement.setImage(image);
                    } else {
                        // Handle the case where the image path is empty or null (e.g., display a default image)
                    }
                });

                // Create an ObservableList from the list of results
                ObservableList<Logement> observableResultat = FXCollections.observableArrayList(resultat);
                // Set the ListView with the new ObservableList
                listView_logement.setItems(observableResultat);
            }
        }
    } else {
        // Show an error alert if txt_searchid is null (empty)
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ de recherche est vide.");
        alert.showAndWait();
    }
}

    @FXML
    private void logementTOaccueil(ActionEvent event) throws IOException {
           try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
            Parent root = loader.load();

            // Create the new scene
            Scene newScene = new Scene(root);

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(newScene);

            // Show the new scene
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    TextField textField = new TextField();
    TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
    String newText = change.getControlNewText();

    if (newText.matches("\\d*")) {
        // Permet seulement les caractères numériques
        return change;
    } else {
        // Affiche une erreur si un caractère non numérique est entré
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez entrer uniquement des chiffres.");
        alert.showAndWait();
        return null;
    }
});

    @FXML
    private void handleRefreshButton(ActionEvent event) {
          ServiceLogement serviceLogement = new ServiceLogement();
    List<Logement> logements = serviceLogement.affihcerLogement();
    ObservableList<Logement> logementList = FXCollections.observableArrayList(logements);
    listView_logement.setItems(logementList);
    }

    }
    


