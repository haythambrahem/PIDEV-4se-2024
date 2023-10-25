/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entité.Commentaire;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import services.commenatireService;



/**
 * FXML Controller class
 *
 * @author walid
 */
public class FXMLAvisSurConducteurController implements Initializable {

    @FXML
    private Button eval1;
    @FXML
    private Button eval2;
    @FXML
    private Button eval3;
    @FXML
    private Button eval4;
    @FXML
    private Button eval5;
    @FXML
    private TextArea tf_comment;
    @FXML
    private ImageView etoile1;
    @FXML
    private ImageView etoile2;
    @FXML
    private ImageView etoile3;
    @FXML
    private ImageView etoile4;
    @FXML
    private ImageView etoile5;
    private int rating = 0;
    ArrayList<ImageView> etoiles = new ArrayList<>();
   
    @FXML
    private Button soumBtn;
    @FXML
    private Label msgErreur;
    File file = new File("src/Mots Innaproprie.txt");
    UtilisateurService us = new UtilisateurService();
    @FXML
    private Label nomC;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    
        etoiles.add(etoile1);
        etoiles.add(etoile2);
        etoiles.add(etoile3);
        etoiles.add(etoile4);
        etoiles.add(etoile5);
    }

    @FXML
    private void evaluation(ActionEvent event) {
        switch (((Node) event.getSource()).getId()) {
            case "eval1":
                etoiles.forEach((i) -> {
                    i.setImage(new Image("/images/star.png/"));
                });
                etoile1.setImage(new Image("/images/star (1).png/"));
                rating = 1;
                break;
            case "eval2":
                etoiles.forEach((i) -> {
                    i.setImage(new Image("/images/star.png/"));
                });
                etoile1.setImage(new Image("/images/star (1).png/"));
                etoile2.setImage(new Image("/images/star (1).png/"));
                rating = 2;
                break;
            case "eval3":
                etoiles.forEach((i) -> {
                    i.setImage(new Image("/images/star.png/"));
                });
                etoile1.setImage(new Image("/images/star (1).png/"));
                etoile2.setImage(new Image("/images/star (1).png/"));
                etoile3.setImage(new Image("/images/star (1).png/"));
                rating = 3;
                break;
            case "eval4":
                etoiles.forEach((i) -> {
                    i.setImage(new Image("/images/star.png/"));
                });
                etoile1.setImage(new Image("/images/star (1).png/"));
                etoile2.setImage(new Image("/images/star (1).png/"));
                etoile3.setImage(new Image("/images/star (1).png/"));
                etoile4.setImage(new Image("/images/star (1).png/"));
                rating = 4;
                break;
            case "eval5":
                etoile1.setImage(new Image("/images/star (1).png/"));
                etoile2.setImage(new Image("/images/star (1).png/"));
                etoile3.setImage(new Image("/images/star (1).png/"));
                etoile4.setImage(new Image("/images/star (1).png/"));
                etoile5.setImage(new Image("/images/star (1).png/"));
                rating = 5;
                break;
        }
        soumBtn.setDisable(false);
    }

    @FXML
    private void soummetreEval(ActionEvent event) {
        Commentaire c = new Commentaire();
        if (!tf_comment.getText().isEmpty()) {
            c.setMessage(tf_comment.getText());
            c.setId1(11);
            c.setId2(12);
            
            
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("Votre evaluation a été enregistrée");
        alert.show();
        
    }

    @FXML
    private void verifMsg(KeyEvent event) {
        if (isInapproprie(tf_comment.getText())) {
            msgErreur.setVisible(true);
            soumBtn.setDisable(true);
        } else {
            msgErreur.setVisible(false);
            soumBtn.setDisable(false);
        }
    }

    private boolean isInapproprie(String text) {
        try {
            Scanner scanner = new Scanner(file);
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                if (text.contains(line)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
