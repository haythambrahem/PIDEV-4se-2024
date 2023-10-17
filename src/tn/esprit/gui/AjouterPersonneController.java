/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entity.Personne;
import tn.esprit.services.ServicePersonne;

/**
 * FXML Controller class
 *
 * @author Fayechi
 */
public class AjouterPersonneController implements Initializable {

    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtprenom;
//    @FXML
//    private TextField id;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addPerson(ActionEvent event) {
        String nom=txtnom.getText();
        String prenom = txtprenom.getText();
//        int id = Integer.parseInt( this.id.getText());
        
        ServicePersonne ps = new ServicePersonne();
        Personne p = new Personne(nom, prenom);
        ps.ajouter(p);
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("AfficherPersonne.fxml"));
            Parent root = loader.load();
        AfficherPersonneController ac = loader.getController();
        ac.setRecNom(txtnom.getText());
        ac.setRecPrenom(txtprenom.getText());
        ac.setRecList(ps.afficher().toString());
        txtprenom.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }

    @FXML
    private void gotoAccueil(ActionEvent event) {
         try {

            Parent page1
                    = FXMLLoader.load(getClass().getResource("HomePage.fxml"
                    ));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }
    }
    
}
