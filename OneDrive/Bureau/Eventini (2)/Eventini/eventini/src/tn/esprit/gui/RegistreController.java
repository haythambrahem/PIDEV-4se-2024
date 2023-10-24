/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import tn.esprit.entity.Personne;
import tn.esprit.services.ServicePersonne;
import tn.esprit.services.ServicesClient;
import org.mindrot.jbcrypt.BCrypt;

import tn.esprit.entity.Client;


/**
 * FXML Controller class
 *
 * @author hayth
 */
public class RegistreController implements Initializable {

    @FXML
    private AnchorPane register;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField ign;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button browseBtn;
    @FXML
    private Button registerBtn;
    @FXML
    private DatePicker datenaistext;
    @FXML
    private ImageView pprofileRegister;
    
     private File profileImageFile;

//   private static final String IMAGE_FOLDER = "C:\\Users\\hayth\\Downloads\\Eventini\\eventini\\src\\tn\\esprit\\img";//bech twali pointuer 3al htdocs
     private static final String IMAGE_FOLDER = "C:\\xampp\\htdocs\\img\\";//bech twali pointuer 3al htdocs

//    private final String appId = "1384965872358291";
//    private final String appSecret = "f5ffe8cdc19629d65deaed095c02a6dc";
//    private final String redirectUrl = "https://vocalremover.org/";
//    private final String ACCESS_TOKEN_REGEX = "access_token=(.+)&";
//    private final String AUTH_URL = "https://www.facebook.com/v12.0/dialog/oauth?client_id=" + appId + "&redirect_uri=" + redirectUrl + "&response_type=token&scope=email";
//    ////////////////
//    // TWILIO
//    private final String ACCOUNT_SID = "ACb3f09a2d40b2e60759af29b509ae1441";
//    private final String AUTH_TOKEN = "c2e9ba52ea5a7ee1c0ab309af2812d4a";
//    private final static String TWILIO_PHONE_NUMBER = "+21658101754";
    ////////////////
    public static String saveImage(File imageFile) throws IOException {
        //System.out.println("Test 3");
        String originalFilename = imageFile.getName();
        System.out.println(originalFilename);
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;
        System.out.println(newFilename);
        Path destination = Paths.get(IMAGE_FOLDER + newFilename);
        Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFilename;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir l'image du profil");
        // pprofileRegister
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        profileImageFile = file;
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                System.out.println("Test 1");
                //ImageView imageView = new ImageView(image);
                //profileImageFile = file;
                pprofileRegister.setImage(image);
                //System.out.println("Test 2");
            } catch (Exception e) {
                System.out.println("Error loading image");
            }
        }
    }

    public boolean validateInputs(String nom, String prenom, String email, String ign, String password, String datenaistext) {
        if (datenaistext.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide!");
            alert.showAndWait();

            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date2 = LocalDate.parse(datenaistext, formatter);
            LocalDate now = LocalDate.now();
            int age = Period.between(date2, now).getYears();
            if (age < 16) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Date de naissance invalide!");
                alert.setContentText("L'âge doit être supérieur ou égal à 16 ans.");
                alert.showAndWait();
                return false;

            }
        } catch (DateTimeParseException e) {
            //showAlert("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            alert.showAndWait();
            return false;
        }
        // Validate nom
        if (!nom.matches("^[a-zA-Z]{1,15}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid nom");
            alert.setContentText("Le nom doit être entièrement alphabétique et ne peut excéder 15 caractères.");
            alert.showAndWait();
            return false;
        }

        // Validate prenom
        if (!prenom.matches("^[a-zA-Z]{1,15}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid prenom");
            alert.setContentText("Le prenom doit être entièrement alphabétique et ne peut excéder 15 caractères.");
            alert.showAndWait();
            return false;
        }

        // Validate email
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid email");
            alert.setContentText("L'adresse électronique doit être valide.");
            alert.showAndWait();
            return false;
        }

        // Validate ign
        if (ign.length() > 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid IGN");
            alert.setContentText("L'IGN est soit pris, soit dépasse 15 caractères.");
            alert.showAndWait();
            return false;
        }

//        // Validate password
//        if (password.length() < 5 || password.length() > 10 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Invalid password");
//            alert.setContentText("Le mot de passe doit comporter entre 5 et 10 caractères et contenir au moins une lettre majuscule et un chiffre.");
//            alert.showAndWait();
//            return false;
//        }

        // If all inputs are valid, return true
        return true;
    }    
    @FXML
    private void registerUser(ActionEvent event) throws javax.mail.MessagingException {
         // Retrieve user registration information and handle registration logic
        String userNom = nom.getText();
        String userPrenom = prenom.getText();
        String userIgn = ign.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();
            
        //LocalDate dob = dobPicker.getValue();
        LocalDate datee = datenaistext.getValue();
        DateTimeFormatter formatter69 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = datee.format(formatter69);
        Date date3 = Date.valueOf(formattedDate);
        String datenaistext2 = datee.toString();
        //System.out.println("\u001B[31mError: Test4.\u001B[0m");
        // Check if all fields are filled
        if (userEmail.isEmpty() || userPassword.isEmpty() || userNom.isEmpty() || userPrenom.isEmpty() || userIgn.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Verifier que tout is valide");
            alert.setContentText("Verifier que tout is valide");
            alert.showAndWait();
            return;
        }
        if ((validateInputs(userNom, userPrenom, userEmail, userIgn, userPassword, datenaistext2)) && (profileImageFile != null)) {
            ServicesClient serviceClient = new ServicesClient();
            // System.out.println("\u001B[31mError: Test 5.\u001B[0m");
            String profilepic = null;
            try {
                //System.out.println("\u001B[31mError: Test 6.\u001B[0m");
                profilepic = saveImage(profileImageFile);
            } catch (IOException e) {
                System.out.println("Error saving image file"+ e.getMessage());
            }
            String salt = BCrypt.gensalt(12);
            String hashedPassword = BCrypt.hashpw(userPassword, salt);
            Client client = new Client( userIgn ,userNom, userPrenom,userEmail, hashedPassword);
            //joueur.setDob(Date.valueOf(dob));
            //System.out.println("\u001B[31mError: Test 7.\u001B[0m");
//            client.setVerified(false);
            client.setIs_banned(false);
            client.setPprofile(profilepic);
            client.setDateNaise(datee);
            String role = "ROLE_USER";
            String[] roles = new String[]{"\"" + role + "\""};
            client.setRoles(roles);
            serviceClient.ajouter(client);
           
            try {
                sendVerificationEmail(client.getEmail());
            } catch (MessagingException e) {
                System.out.println("Error sending verification email: " + e.getMessage());
            }
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("EmailHasBeenSent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            /*if (verificationMethod.equals("email")) {
                try {
                    sendVerificationEmail(joueur.getEmail());
                } catch (MessagingException e) {
                    System.out.println("Error sending verification email: " + e.getMessage());
                }
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("EmailHasBeenSent.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (verificationMethod.equals("sms")) {
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("SMSMethod.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }*/

        }

        // Get the file path of the selected image
        //String imagePath = imageFile != null ? imageFile.getAbsolutePath() : null;
        // Insert the user into the database
        /*JoueurDAO joueurDAO = new JoueurDAO();
        Joueur joueur = new Joueur(userEmail, userPassword, userNom, userPrenom, userIgn);
        //joueur.setDob(Date.valueOf(dob));
        joueur.setVerified(false);
        joueur.setBanned(false);
        joueurDAO.insertTest(joueur);

        // Show success message
        Alert registerAlert = new Alert(AlertType.CONFIRMATION);
        registerAlert.setHeaderText("Done!");
        registerAlert.showAndWait();*/
    }
    public static void sendVerificationEmail(String recipientEmail) throws MessagingException, javax.mail.MessagingException {
        System.out.println("Test email");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("eventini.pidev@gmail.com", "qhthepftqwmczqpx");
            }
        };
        // create session and authenticate
        Session session = Session.getInstance(props, auth);

        // create message
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("Eventininotifier@gmail.com"));
        } catch (AddressException e) {
            System.out.println("Invalid email address: " + e.getMessage());
        }
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject("Confirmation email");

        // set content and add link to verify
        try {
            ServicesClient servicesClient = new ServicesClient();
            int ClientId = servicesClient.getClientIdByEmail(recipientEmail);
            String ClientIdStr = String.valueOf(ClientId);
            System.out.println(ClientId);
            String content = "Cher utilisateur,<br><br>Veuillez cliquer sur le lien ci-dessous pour confirmer votre inscription:<br><br>"
                    + "<a href=http://127.0.0.1:8000/verify/java/" + ClientIdStr + ">Confirmer l'inscription</a><br><br>"
                    + "Merci de votre attention,<br>Eventini.";
            System.out.println(content);
            message.setContent(content, "text/html");

            // send message
            Transport.send(message);
            // TODO fix roles
            System.out.println("Confirmation email sent successfully.");
        } catch (SQLException e) {
            // handle the exception
            e.printStackTrace();
        }
        //System.out.println("Error sending confirmation email: " + e.getMessage());
    }
    
}
