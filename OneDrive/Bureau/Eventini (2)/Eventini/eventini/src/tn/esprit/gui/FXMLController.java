/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author imen
 */
public class FXMLController implements Initializable {

    @FXML
    private Button buttonOffre;
    @FXML
    private ImageView imageFixe;
    @FXML
    private Button butonPanier;
    @FXML
    private Button retourAccc2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }
    @FXML
    private void goPAnier(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Panier.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
             Stage currentStage = (Stage) butonPanier.getScene().getWindow();

        // Fermer la fenêtre actuelle (AfficherPanier)
        currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   

    @FXML
    private void goOffre(ActionEvent event)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherOffre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
             AfficherOffreController controller = loader.getController();
             controller.Afficher();
              Stage currentStage = (Stage) buttonOffre.getScene().getWindow();

        // Fermer la fenêtre actuelle (AfficherPanier)
        currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void retourAcc2(ActionEvent event) {
        
         try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeUser.fxml"));
            Parent root = loader.load();

            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(newScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    
    }
 

