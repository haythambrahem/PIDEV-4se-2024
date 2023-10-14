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
import java.sql.SQLException;
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
                 Logement loge = new Logement();

    
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
        System.out.println(logement.toString());
        txt_adr.setText(logement.getAdr());
        txt_superfice.setText(String.valueOf(logement.getSuperfice()));
        txt_loyer.setText(String.valueOf(logement.getLoyer()));
        txt_region.setText(logement.getRegion());
        cb_type.setValue(logement.getType());
        
        
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
   
//private void searchLogement(MouseEvent event) {
//     if (txt_searchid != null) {
//    String critere = txt_searchid.getText(); 
//         System.out.println(critere);
//         
//         List<Logement> resultat = sl.rechercheLogement(critere);
//
//    // Créez une ObservableList à partir de la liste de résultats
//    ObservableList<Logement> observableResultat = FXCollections.observableArrayList(resultat);
//
//    // Configurez votre ListView avec la nouvelle ObservableList
//    listView_logement.setItems(observableResultat);
//       } else {
//        // Gérez la situation où txt_searchid est null
//        System.out.println("txt_searchid est null");
//    }}

    @FXML
    private void listLogEvn(MouseEvent event) {

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
    

    
}
