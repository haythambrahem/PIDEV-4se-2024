/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author FADI
 */
public class LocationController implements Initializable {

    @FXML
    private JFXTextField txt_searchLogementid;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_loyer;
    @FXML
    private JFXTextField txt_type;
    @FXML
    private ImageView imageLog;
    @FXML
    private JFXTextField txt_region;
    @FXML
    private JFXTextField txt_cinSearch;
    @FXML
    private JFXTextField txt_nomPrenom;
    @FXML
    private JFXTextField txt_tele;
    @FXML
    private JFXTextField txt_CIN;
    @FXML
    private JFXDatePicker dateDebut;
    @FXML
    private JFXDatePicker dateFin;
    @FXML
    private JFXTextField txt_periode;
    @FXML
    private Button btn_precedent;
    @FXML
    private Button btn_suivant;
 public TextField getAdr() {
        return txt_adr;
    }
 public TextField getLoyer() {
        return txt_loyer;
 }
 public TextField getType() {
        return txt_type;}
       
 public TextField getRegion() {
        return txt_region;
 }
    /**
     * Initializes the controller class.
     */
      

    @FXML
    private void addLocation(MouseEvent event) {
    }

    @FXML
    private void periode(ActionEvent event) {
    }

    @FXML
    private void searchLogement(MouseEvent event) {
    }

    @FXML
    private void searchLocataire(MouseEvent event) {
    }

     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    @FXML
    private void logementPrecedent(MouseEvent event) {
    }

    @FXML
    private void logementSuivant(MouseEvent event) {
    }
}
