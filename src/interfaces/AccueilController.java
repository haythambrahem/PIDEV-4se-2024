/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

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
import models.LogementDetails;
import services.ServiceLogement;
import services.ServiceShowLog;
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
    /*    String adr = txt_adr.getText();
          String sql3="select idLogement from logement where adrL ='"+adr+"'"; 
        int position=0;
          try {
            st=cnx.prepareStatement(sql3);
            result=st.executeQuery();
            if(result.next()){
                position=result.getInt("idLogement");
                
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       // System.out.println(position);
        
        String sql4="select loyer, superfice, region,adrL,image from logement where  idLogement not in (select logement from location)and idLogement >'"+position+"'";
        int loyer=0;
      int superfice;
      
      byte byteImg[];
      
              Blob blob;
        try {
            st= cnx.prepareStatement(sql4);
            result=st.executeQuery();
             if(result.next()) {
                 
                loyer=result.getInt("loyer");
                txt_loyer.setText(Integer.toString(loyer));
                superfice=result.getInt("superfice");
                txt_superfice.setText(Integer.toString(superfice));
                txt_region.setText(result.getString("region"));
                txt_adr.setText(result.getString("adrL"));
                blob=result.getBlob("image");
                byteImg=blob.getBytes(1,(int) blob.length());
              //  Image img= new Image(new ByteArryInputStream(byte Img),imageLog.getFitWidth(),imageLog.getFitHeight(),true,true);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteImg);
        Image img = new Image(inputStream);
        imageLog.setImage(img); 
            }
            
            
        } catch (SQLException ex) {
             ex.printStackTrace();
        }*/
    
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
        // You should set the image if your UI allows image display.
        // imageLog.setImage(details.getImage());
        
    
    }

        
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       
        
             ShowSuivant();
             
       
           
        
        
        
       
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
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Location.fxml"));
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

    public void updateUI(int loyer, String region, String adrL, String Img) {
// Assuming you have JavaFX components in your controller class
    txt_loyer.setText(Integer.toString(loyer)); // Update a text field for rent
    txt_region.setText(region); // Update a text field for region
    txt_adr.setText(adrL); // Update a text field for address

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
