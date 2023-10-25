/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import entité.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import services.ReclamationService;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class FXMLGererReclamationController implements Initializable {

    @FXML
    private TableView<Reclamation> reclamationTable;
    @FXML
    private TableColumn<?, ?> id_reclamationCol;
    @FXML
    private TableColumn<?, ?> etatCol;
    @FXML
    private TableColumn<?, ?> messageCol;
    @FXML
    private TableColumn<?, ?> IdUsrCol;
    ReclamationService rs = new ReclamationService();
    @FXML
    private TextField idRec;
    @FXML
    private ChoiceBox choix_type;
   

    private String[] etats = {"Ouvert", "En cours", "Traite"};
    @FXML
    private TextField recherche;
    @FXML
    private TableColumn<?, ?> IdUsrCol1;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherReclamation(rs.afficheListe());
     choix_type.getItems().addAll(etats);
        choix_type.setValue("Traite");
    }

    public void afficherReclamation(List<Reclamation> l) {
        id_reclamationCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        IdUsrCol1.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        ObservableList<Reclamation> L = FXCollections.observableArrayList(l);
        reclamationTable.setItems(L);
    }

    @FXML
    private void supprimerReclamation(ActionEvent event) {
        Reclamation r = new Reclamation(reclamationTable.getSelectionModel().getSelectedItem().getId());
        rs.supprimer(r);
        afficherReclamation(rs.afficheListe());
    }

    @FXML
    private void modifierReclamation(ActionEvent event) {
        Reclamation c = reclamationTable.getSelectionModel().getSelectedItem();
        c.setEtat(choix_type.getValue().toString());
        rs.modifier(c);
        afficherReclamation(rs.afficheListe());
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Reclamation c = reclamationTable.getSelectionModel().getSelectedItem();
        idRec.setText("Id: " + c.getId());
        choix_type.setValue(c.getEtat());
    }
    @FXML
    private void rechercherMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            afficherReclamation(rs.rechercher(recherche.getText()));
        }
    }

    @FXML
    private void detailsReclamation(ActionEvent event) {
         Reclamation c = reclamationTable.getSelectionModel().getSelectedItem();
        try {
              setSceneContent("FXMLDetailReclamation");
        } catch (IOException ex) {
            Logger.getLogger(FXMLGererReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       private void setSceneContent(String sceneName) throws IOException {
        // Code pour charger la scène spécifiée
        // ...
    }
    }

    




