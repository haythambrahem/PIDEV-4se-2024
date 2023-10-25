/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entity.Client;
import tn.esprit.services.Global;
import tn.esprit.services.ServicesClient;
import tn.esprit.services.SessionManager;
import tn.esprit.tools.MyDB;

/**
 * FXML Controller class
 *
 * @author hayth
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane login;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
     public String sessionId;
    
    private final Connection con;

    public LoginController() {
        this.con = MyDB.getinstance().getCon();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLogin(ActionEvent event) {
        // Get the user's email and password from the text fields
        String emailLogin = email.getText();
        String pass = password.getText();

        // Check the email and password against your database
        if (checkCredentials(emailLogin, pass)) {
            ServicesClient servicesClient = new ServicesClient();
            
            Client client = servicesClient.getClientByEmail(emailLogin);
            // Debug: Print client details
System.out.println("Client email: " + client.getEmail());
System.out.println("Is Banned: " + client.isIs_banned());

            if (client.isIs_banned()) {
                try {
                //UserProfile controller = new UserProfile();
                //controller.setSessionId(sessionId);
                
                Parent page1 = FXMLLoader.load(getClass().getResource("Banned.fxml"));
                Scene scene = new Scene(page1);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeUser.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //loader.setController(controller);
                //controller.setSessionId(sessionId);
                
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            } else {
            sessionId = SessionManager.createSession(client );
            
            Global.sessionId = sessionId;
            System.out.println("Session ID in the login: " + Global.sessionId);
            System.out.println("User is not banned.");

            // If the credentials are valid, display a success message
            
            try {
//                UserProfile controller = new UserProfile();
//                controller.setSessionId(sessionId);
                if(client.getRoleJava_client_id()==2)
                {
                Parent page1 = FXMLLoader.load(getClass().getResource("HomeUser.fxml"));
                Scene scene = new Scene(page1);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeUser.fxml"));;
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //loader.setController(controller);
                //controller.setSessionId(sessionId);
                
                stage.setScene(scene);
                stage.show();
                }
                else
                {
                     Parent page1 = FXMLLoader.load(getClass().getResource("HomeUser.fxml"));//to change with the gui de siwar
                Scene scene = new Scene(page1);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeUser.fxml"));;//to change with the gui de siwar
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //loader.setController(controller);
                //controller.setSessionId(sessionId);
                
                stage.setScene(scene);
                stage.show();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            }
        } else {
            // If the credentials are invalid, display an error message
            wrongLogIn.setText("Email ou mot de passe invalide");
        }
    }
     // Check the user's credentials against your database
    private boolean checkCredentials(String emailLogin, String pass) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Personne WHERE email = ?");
            statement.setString(1, emailLogin);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPass = resultSet.getString("password");
                
                return BCrypt.checkpw(pass, hashedPass);
            } else {
                return false; // email not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
