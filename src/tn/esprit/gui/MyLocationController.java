/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entity.Location;
import tn.esprit.entity.SharedDataModel;
import tn.esprit.services.ServiceLocation;

/**
 * FXML Controller class
 *
 * @author fadi saidi
 */
public class MyLocationController implements Initializable {

    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_nom;
    @FXML
    private JFXTextField txt_prenom;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_dateD;
    @FXML
    private JFXTextField txt_dateF;
    @FXML
    private JFXTextField txt_tarif;
    @FXML
    private ListView<Location> MesLocation;
    @FXML
    private Button search_email;
    @FXML
    private Button contrat;
    Connection con;
    Statement ste;
    @FXML
    private Button move;
    @FXML
    private Label yourEmail;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          ServiceLocation serviceLocation = new ServiceLocation();
       // List<Location> searchResults = serviceLocation.searchLocationsByEmail(critere);
         List<Location> locations = serviceLocation.getAllLocations();
         ObservableList<Location> locationList = FXCollections.observableArrayList(locations);
         MesLocation.setItems(locationList);
 String userEmail = yourEmail.getText().trim();

        // Fetch and display the data
        populateListView(userEmail);
         
    }  //display email
     public void displayEmail(String email){
        yourEmail.setText(email);
    }
     private void populateListView(String email) {
      ServiceLocation serviceLocation = new ServiceLocation();

    // Fetch all locations
    List<Location> allLocations = serviceLocation.getAllLocations();

    // Filter locations based on the provided email
    List<Location> filteredLocations = allLocations.stream()
            .filter(location -> location.getPersonne().getEmail().equals(email))
            .collect(Collectors.toList());

    // Check if data is available
    if (!filteredLocations.isEmpty()) {
        MesLocation.getItems().setAll(filteredLocations);
    } else {
        // Clear the ListView if no matching locations are found
        MesLocation.getItems().clear();
    }
}

    @FXML
    private void listMesLocation(MouseEvent event) {
        Location selectedLocation = MesLocation.getSelectionModel().getSelectedItem();

    if (selectedLocation != null) {
        // Populate other fields based on the selected location, if needed
        txt_adr.setText(selectedLocation.getLogement().getAdr());
        txt_tarif.setText(String.valueOf(selectedLocation.getTarif()));
      //  txt_region.setText(selectedLocation.getLogement().getRegion());
       // txt_email.setText(selectedLocation.getPersonne().getEmail());
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define your date format
        String formattedDate = dateFormat.format(selectedLocation.getDateDebut()); // Format the Date
        txt_dateD.setText(formattedDate); // Set the formatted date in the TextField
        txt_nom.setText(selectedLocation.getPersonne().getNom());
        String formattedDateF = dateFormat.format(selectedLocation.getDateFin()); // Format the Date
        txt_dateF.setText(formattedDateF);
         txt_prenom.setText(selectedLocation.getPersonne().getPrenom());
    }
    }

    @FXML
    private void rechercherEmail(ActionEvent event) {
      //  String critere = txt_email.getText().trim();
         String critere = yourEmail.getText().trim();
    if (critere.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le champ de recherche est vide.");
        alert.showAndWait();
    } else {
        ServiceLocation serviceLocation = new ServiceLocation();
        List<Location> searchResults = serviceLocation.searchLocationsByEmail(critere);

        if (searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Aucun résultat trouvé pour le critère de recherche.");
            alert.showAndWait();
        } else {
            MesLocation.getItems().setAll(searchResults);
        }
    }
}


    @FXML
    private void move(ActionEvent event) {
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Location.fxml"));
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
    private void GetContrat(ActionEvent event) {
    Document doc = new Document();
    try {
        PdfWriter.getInstance(doc, new FileOutputStream("contrat.pdf"));
        doc.open();
        
        String format = "dd/MM/yy HH:mm"; // Correct date format
        
        SimpleDateFormat formater = new SimpleDateFormat(format);
        Date date = new Date();
        
        com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("C:\\Users\\111fa\\OneDrive\\Documents\\NetBeansProjects\\PIdev\\src\\tn\\esprit\\img\\entete2.JPG");
        img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
        doc.add(img);
        doc.add(new Paragraph("Entre : FADI SAIDI"
                + "\nDemeurant à : Tunis"
                +"\nde Nationalité Tunisienne, CIN :07220722"
                +"\nD'une part"
                +"\nEt "+txt_nom.getText()+" "+txt_prenom.getText()+""
                +"\nDemurent a : Tunis"
                +"\nde Nationalité Tunisienne, CIN :XXXXX"
                +"\nD'autre part"
                +"\n\nIl a été convenu et arrété ce qui suit"
                +"\nM.Fadi Saidi donne loyer a "+txt_nom.getText()+" "+txt_prenom.getText()+""
                +"\nPour une durée allant de :"+txt_dateD.getText()+" jusqu'a "+txt_dateF.getText()+""
                +"\nUn Logement située a :"+txt_adr.getText()+""
               + "\nTel que le tout se poursuit et comporte sans exeption ni réserve et sans "
                +"  plus ample description, le Client s'engage à payer un loyer de : "+txt_tarif.getText()+"DT"
                 + "\nUn état des lieux sera établi avant la prise de possession du bien et à la fin de la location. Tout dommage constaté sera à la charge du Locataire.\n\n"
                +"\n"
                +"\n"
                +"\n"
                +"\n"
                +"\n"
                 +"\n"
                 +"\n"
                +"\nFait a Tunis le :"+ formater.format(date)+""
                +"\nSignature :..............."
                , FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLACK)));
        doc.close();
        
        // Open the PDF file with the default PDF viewer
        File pdfFile = new File("contrat.pdf");
        if (pdfFile.exists()) {
            Desktop.getDesktop().open(pdfFile);
        }
    } catch (DocumentException | IOException ex) {
        Logger.getLogger(MyLocationController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
   
    }

