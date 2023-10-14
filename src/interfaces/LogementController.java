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
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
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
    private TableView<?> table_logement;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_adr;
    @FXML
    private TableColumn<?, ?> col_superfice;
    @FXML
    private TableColumn<?, ?> col_loyer;
    @FXML
    private TableColumn<?, ?> col_type;
    @FXML
    private TableColumn<?, ?> col_region;
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
    @FXML
    private JFXListView<Logement> listView_logement;
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

    @FXML
    private void searchLogement(KeyEvent event) {
    }


    @FXML
    private void tableLogEvent(MouseEvent event) {
    }
ObservableList<type> types = FXCollections.observableArrayList(type.values());

    @FXML
    private void ajouterLogement(ActionEvent event) {
     String adr = txt_adr.getText();
    String superf = txt_superfice.getText();
    int superfice = Integer.parseInt(superf);
    String loy = txt_loyer.getText();
    int loyer = Integer.parseInt(loy);
    type logementType = cb_type.getValue(); // Assuming you have cb_type for the logementType
    String reg = txt_region.getText();
    File image = new File(lab_url.getText());
    
    if (adr.isEmpty() || reg.isEmpty() || logementType == null || superf == null || loy == null) {
        // Show an error message if any required field is missing
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez remplir tous les champs obligatoires.");
        errorAlert.showAndWait();
    } else {
        Logement logement = new Logement();
        logement.setAdr(adr);
        logement.setSuperfice(superfice);
        logement.setLoyer(loyer);
        logement.setType(logementType);
        logement.setRegion(reg);

        // Set the Blob image data in logement using setImageBlob method (if applicable)
        // logement.setImageBlob(blob);

        // Call the ajouterLogement method from your ServiceLogement
        ServiceLogement serviceLogement = new ServiceLogement();
        serviceLogement.ajouterLogement(logement);

        // Show a success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Le logement a été ajouté avec succès.");
        successAlert.showAndWait();

        // Clear input fields and reset ComboBox selections
        txt_adr.clear();
        txt_superfice.clear();
        txt_loyer.clear();
        cb_type.getSelectionModel().clearSelection(); // Clear the selected value in cb_type
        txt_region.clear();

        // Rest of your button click logic
    }
}

// Read image data from the image file

    @FXML
    private void modifierLogement(ActionEvent event) {
        String adr = txt_adr.getText();
    String superf = txt_superfice.getText();
    int superfice = 0;
    
    if (!superf.isEmpty()) {
        try {
            superfice = Integer.parseInt(superf);
        } catch (NumberFormatException e) {
            // Gérez le cas où l'entrée n'est pas un entier valide
            // Vous pouvez afficher un message d'erreur ou prendre des mesures appropriées ici
        }
    }
    
    String loy = txt_loyer.getText();
    int loyer = Integer.parseInt(loy);
 //   String typ = cb_type.getValue();
    String reg = txt_region.getText();
    
    Logement logement = new Logement();
    logement.setAdr(adr);
    logement.setSuperfice(superfice);
    logement.setLoyer(loyer);
 //   logement.setType(typ);
    logement.setRegion(reg);
    
    // Remarque : vous devrez définir les autres propriétés du logement en fonction de votre logique
    
    ServiceLogement serviceLogement = new ServiceLogement(); // Initialisez avec votre connexion à la base de données
    logement.setId(Integer.parseInt(txt_searchid.getText())); // Définissez l'ID du logement que vous souhaitez modifier
    
    //boolean modificationReussie = serviceLogement.modifierLogement(logement);
serviceLogement.modifierLogement(logement);
    //if (modificationReussie) {
   //     showLogement();
        lab_url.setText("aucune sélectionnée");
        txt_adr.setText("");
        txt_superfice.setText("");
        txt_loyer.setText("");
        txt_searchid.setText("");
    //    cb_commune.setValue("commune");
       // cb_province.setValue("province");
       // cb_type.setValue("type");
        txt_region.setText("region");
        image_logement.setImage(null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le logement a été modifié avec succès.");
        alert.showAndWait();
  /*  } else {
        // Gérez le cas où la modification a échoué
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("La modification du logement a échoué.");
        alert.showAndWait();
    }*/
  /* String adr = txt_adr.getText();
        int superficie = Integer.parseInt(txt_superfice.getText());
        int loyer = Integer.parseInt(txt_loyer.getText());
        String type = cbLogementType.getValue();
        String region = txt_region.getText();

        // Create a Logement object with the updated data
        Logement logement = new Logement();
        logement.setAdr(adr);
        logement.setSuperfice(superficie);
        logement.setLoyer(loyer);
        logement.setType(type);
        logement.setRegion(region);

        // Get the Logement ID you want to update (for example, from a table or user selection)
        int logementId = 1; // Replace with the actual ID

        // Call the service to update the Logement
        ServiceLogement logementService = new ServiceLogement(con);
        logement.setId(logementId); // Set the ID for the Logement
        Logement modifierLogement = logementService.modifierLogement(logement);

        if (modifierLogement != null) {
            // Logement was successfully updated
            // You can also update the UI to reflect the changes
        } else {
            // Handle the case where the update failed, e.g., show an error message
        }*/
    
}
    

    @FXML
    private void supprimerLogement(ActionEvent event) {
        String adresse = txt_adr.getText(); // Obtenez l'adresse à partir de votre champ texte
    ServiceLogement serviceLogement = new ServiceLogement(); // Initialisez avec votre connexion à la base de données
    Logement logement = new Logement();
    logement.setAdr(adresse);
    serviceLogement.supprimerLogement(logement);
      String adr = txt_adr.getText();
        int superficie = Integer.parseInt(txt_superfice.getText());
        int loyer = Integer.parseInt(txt_loyer.getText());
        String type = cbLogementType.getValue();
        String region = txt_region.getText();

        // Call the LogementService to delete Logement based on the criteria
        ServiceLogement logementService = new ServiceLogement();
     //   logementService.supprimerLogement(adr, superficie, loyer, type, region);

        // Handle the deletion result
        // You can update the UI or display a message
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
    private void searchLogement(MouseEvent event) {
    }
    
}
