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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
     String adr = txt_adr.getText();
    String superf = txt_superfice.getText();
   
    String loy = txt_loyer.getText();
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
         int superfice = Integer.parseInt(superf);
             int loyer = Integer.parseInt(loy);

        Logement logement = new Logement();
        logement.setAdr(adr);
        logement.setSuperfice(superfice);
        logement.setLoyer(loyer);
        logement.setType(logementType);
        logement.setRegion(reg);
        logement.setImage(lab_url.getText());
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
         List<Logement> logements = serviceLogement.affihcerLogement();
        ObservableList<Logement> logementList = FXCollections.observableArrayList(logements);
        listView_logement.setItems(logementList);
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
    private void modifierLogement(ActionEvent event) throws SQLException {
                
        Logement selectedLogement = listView_logement.getSelectionModel().getSelectedItem();

    if (selectedLogement != null) {
        int logementId = selectedLogement.getId();
        ServiceLogement serviceLogement = new ServiceLogement();
        Logement logement = serviceLogement.getLogementById(logementId);
        
        String adr = txt_adr.getText();
        String superf = txt_superfice.getText();
        
        String loy = txt_loyer.getText();
        
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
         int superfice = Integer.parseInt(superf);
         int loyer = Integer.parseInt(loy);
        logement.setAdr(adr);
        logement.setSuperfice(superfice);
        logement.setLoyer(loyer);
        logement.setType(logementType);
        logement.setRegion(reg);
        logement.setImage(lab_url.getText());
        
             System.out.println(serviceLogement.getLogementById(logementId).toString());
        serviceLogement.modifierLogement(logement);
        // Show a success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Le logement a été modifier avec succès.");
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
       // image_logement.clear();  
        // Rest of your button click logic
    }
        // Faites ce que vous devez avec l'ID, par exemple, affichez-le ou effectuez d'autres opérations
        System.out.println("ID du logement sélectionné : " + logementId);
        
    }
    
   
}
    

    @FXML
    private void supprimerLogement(ActionEvent event) throws SQLException {
        Logement selectedLogement = listView_logement.getSelectionModel().getSelectedItem();

    if (selectedLogement != null) {
        int logementId = selectedLogement.getId();
        ServiceLogement serviceLogement = new ServiceLogement();
        Logement logement = serviceLogement.getLogementById(logementId);

        serviceLogement.supprimerLogement(logement);
         List<Logement> logements = serviceLogement.affihcerLogement();
        ObservableList<Logement> logementList = FXCollections.observableArrayList(logements);
        listView_logement.setItems(logementList);

      
    }}

    

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
             
    String critere = txt_searchid.getText(); 
         System.out.println(critere);
     ServiceLogement serviceLogement = new ServiceLogement(); // Créez une instance de ServiceLogement
List<Logement> resultat = serviceLogement.rechercheLogement(critere); // Appelez la méthode sur l'instance

resultat.forEach(logement -> {
    txt_adr.setText(logement.getAdr());
    txt_superfice.setText(String.valueOf(logement.getSuperfice()));
    txt_loyer.setText(String.valueOf(logement.getLoyer()));
    txt_region.setText(logement.getRegion());
    cb_type.setValue(logement.getType());
    
            System.out.println(logement.getImage());

    // Vérifiez si le chemin du fichier image n'est pas vide avant de créer l'objet Image
    if (logement.getImage() != null && !logement.getImage().isEmpty()) {
        String fileUrl = new File(logement.getImage()).toURI().toString();
        System.out.println(fileUrl);
        Image image = new Image(fileUrl);
        image_logement.setImage(image);
    } else {
        // Gérez le cas où le chemin du fichier image est vide ou null
        // Peut-être afficher une image par défaut
    }
});
    // Créez une ObservableList à partir de la liste de résultats
    ObservableList<Logement> observableResultat = FXCollections.observableArrayList(resultat);
    // Configurez votre ListView avec la nouvelle ObservableList
    listView_logement.setItems(observableResultat);
       } else {
        // Gérez la situation où txt_searchid est null
        System.out.println("txt_searchid est null");
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
    }
    


