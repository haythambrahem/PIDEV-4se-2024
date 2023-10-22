/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entity.LogementDetails;
import tn.esprit.entity.SharedDataModel;
import tn.esprit.services.ServiceLogement;
import tn.esprit.services.ServiceShowLog;
//import projet.ConnexionMysql;

/**
 *
 * @author FADI
 */
public class AccueilController implements Initializable {
    private Connection cnx;
    private PreparedStatement st;
    private ResultSet result;

    @FXML
    private Label lab_nbr;
    @FXML
    private ImageView imageLog;
    @FXML
    private JFXTextField txt_loyer;
    @FXML
    private JFXTextField txt_superfice;
    @FXML
    private JFXTextField txt_region;
    @FXML
    private Button precedant;
    @FXML
    private Button suivant;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private Button LIKE_btn;

   private ServiceShowLog serviceShowLog;
    @FXML
    private Label adr;
   
   
   
    @FXML
    private void ShowPrecedant() {
          String adr = txt_adr.getText();
    try {
        ServiceShowLog service = new ServiceShowLog();
        int position = service.getPositionByAdr(adr);
        //position--;
        service.loadPreviousLogementData(position, this);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @FXML
    private void ShowSuivant() {
             String adr = txt_adr.getText();
       try {
           int count = serviceShowLog.getAvailableLogementCount();
        lab_nbr.setText(Integer.toString(count));
        ServiceShowLog service = new ServiceShowLog(); // Create an instance
        int position = service.getPositionByAdr(adr); // Call the non-static method
        service.loadNextLogementData(position, this);
        // Call the non-static method
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
   
    
    }
    public AccueilController() {
        serviceShowLog = new ServiceShowLog();
    }
    
public void showLogement(){
    int count = serviceShowLog.getAvailableLogementCount();
        lab_nbr.setText(Integer.toString(count));

        LogementDetails details = serviceShowLog.getAvailableLogementDetails();
        txt_loyer.setText(Integer.toString(details.getLoyer()));
        txt_superfice.setText(Integer.toString(details.getSuperfice()));
        txt_region.setText(details.getRegion());
        txt_adr.setText(details.getAdrL());
        // imageLog.setImage(details.getImage());
        
    
    }

        
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       
        
             ShowSuivant();
      
       String labelValue = adr.getText(); 
    }

    @FXML
    private void gotolocation(ActionEvent event) {
     String adr=txt_adr.getText();
        String region = txt_region.getText();
        //int id = Integer.parseInt( this.id.getText());
        String loyer = txt_loyer.getText();
        String superfice = txt_superfice.getText();
        ServiceLogement ps = new ServiceLogement();
     
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

    public void updateUI(int loyer, String region, String adrL, String Img) {

    txt_loyer.setText(Integer.toString(loyer)); 
    txt_region.setText(region);
    txt_adr.setText(adrL); 

    // Convert the byte array to an Image and set it to an ImageView
  
   String fileUrl = new File(Img).toURI().toString();
Image image = new Image(fileUrl);
imageLog.setImage(image);
    }

    public void updateUI(int loyer, String region, String adrL, int superfice, String Img) {
//Assuming you have JavaFX components in your controller class
    txt_loyer.setText(Integer.toString(loyer)); // Update a text field for rent
    txt_region.setText(region); // Update a text field for region
    txt_adr.setText(adrL); // Update a text field for address
    txt_superfice.setText(Integer.toString(superfice));
    // Convert the byte array to an Image and set it to an ImageView
    
    String fileUrl = new File(Img).toURI().toString();
Image image = new Image(fileUrl);
imageLog.setImage(image);

    }   

    
}
