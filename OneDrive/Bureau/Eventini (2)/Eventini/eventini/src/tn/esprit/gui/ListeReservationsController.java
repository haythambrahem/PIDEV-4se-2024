/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import tn.esprit.entity.Reservation;
import tn.esprit.services.ServiceReservation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siwar
 */
public class ListeReservationsController implements Initializable {

   
    @FXML
    private Button annuler_res;

    @FXML
    private TextField recherche_res;

    @FXML
    private TableColumn<?, ?> res_idbillet;

    @FXML
    private TableColumn<?, ?> res_idevt;

    @FXML
    private TableColumn<?, ?> res_prixbillet;

    @FXML
    private TableColumn<?, ?> res_titreevt;

    @FXML
    private TableView<Reservation> reservation_list;

    @FXML
    private Button retour_res;
      @FXML
    private Button ajouterres;
    
    private int idEvt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the TableView columns
        res_idbillet.setCellValueFactory(new PropertyValueFactory<>("idBillet"));
        res_idevt.setCellValueFactory(new PropertyValueFactory<>("idEvt"));
        res_titreevt.setCellValueFactory(new PropertyValueFactory<>("titreEvt"));
        res_prixbillet.setCellValueFactory(new PropertyValueFactory<>("prixBillet"));
        loadReservations(idEvt);
    }

  public void loadReservations(int idEvt) {
    ServiceReservation sr = ServiceReservation.getInstance();
    ObservableList<Reservation> reservations = sr.getReservationsWithEventDetails();

    reservation_list.setItems(reservations);
}



    public void setIdEvt(int idEvt) {
        this.idEvt = idEvt;
    }

    public void setTextFields(int eventId) {
        setIdEvt(idEvt);
        loadReservations(idEvt);
    }
    
      @FXML
    private void AnnulerRes(ActionEvent event)  throws IOException {
   
     FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierReservation.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}
    @FXML
    private void Retour(ActionEvent event)  throws IOException {
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherEvent.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}
    
        @FXML
    private void ajoutt(ActionEvent event)  throws IOException {
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReservation.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}
    
}
