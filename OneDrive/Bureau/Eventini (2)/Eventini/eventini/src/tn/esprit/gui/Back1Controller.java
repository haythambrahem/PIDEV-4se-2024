/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

//import java.awt.Label;
import jxl.write.Label;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Label;
import org.mindrot.jbcrypt.BCrypt;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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
public class Back1Controller implements Initializable {

    @FXML
    private AnchorPane addEmployee_form;
    @FXML
    private TableColumn<Client, String> nom;
    @FXML
    private TableColumn<Client, String> prenom;
    @FXML
    private TableColumn<Client, String> email;
    @FXML
    private TableColumn<Client, String> ign;
//    private TableColumn<Client, String> datenai;
    @FXML
    private TableColumn<Client, String> is_banned;
    @FXML
    private TableColumn<Client, String> is_verified;
    @FXML
    private TextField usersearch;
    @FXML
    private Button exporttoimg;
    @FXML
    private Button searchbutt;
    @FXML
    private Button cleareverything;
    @FXML
    private TextField nomtext;
    @FXML
    private TextField igntext;
    @FXML
    private TextField prenomtext;
    @FXML
    private TextField tfid;
    @FXML
    private Button ajouttext;
    @FXML
    private Button annulertext;
    @FXML
    private TextField emailtext;
    @FXML
    private DatePicker datenaistext;
    @FXML
    private ComboBox<String> rolescombo;
    @FXML
    private PasswordField mdptext;
    @FXML
    private Button browseimg;
    @FXML
    private ImageView viewimg;
    @FXML
    private Button excelexport;
    @FXML
    private Button importexcel;
    @FXML
    private TableView<Client> clientTable;
    private final ServicesClient serviceClient = new  ServicesClient(); 

    private final Connection cnx;
//    private TextField Adressetext;
//    private TextField Teltext;
//    private TableColumn<?, ?> Tel;

    public Back1Controller() {
        this.cnx = MyDB.getinstance().getCon();
    }
    
    private String selectedEmail;
    
    private File profileImageFile;
    
    private static final String IMAGE_FOLDER = "C:\\xampp\\htdocs\\img\\";
    
//    public static String saveImage(File imageFile) throws IOException {
//        //System.out.println("Test 3");
//        String originalFilename = imageFile.getName();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String newFilename = UUID.randomUUID().toString() + extension;
//        Path destination = Paths.get(IMAGE_FOLDER + newFilename);
//        Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
//        return newFilename;
//    }
    public static String saveImage(File imageFile) throws IOException {
    System.out.println("Original Filename: " + imageFile.getName());
    String originalFilename = imageFile.getName();
    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    String newFilename = UUID.randomUUID().toString() + extension;
    Path destination = Paths.get(IMAGE_FOLDER + newFilename);
    
    System.out.println("Destination Path: " + destination);
    
    Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("Image saved successfully to: " + destination);

    return newFilename;
}

    @FXML
    private TextField sessionname;
    @FXML
    private Button sessionlogout;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String sessionId = Global.sessionId;

        

        Client client = SessionManager.getSession(sessionId);
        sessionname.setText(client.getNom() + " " + client.getPrenom());

        rolescombo.getItems().addAll("ROLE_USER", "ROLE_ADMIN");
        rolescombo.setValue("ROLE_USER"); // Set default value
        // Initialize table columns
        //idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        email.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        ign.setCellValueFactory(
                new PropertyValueFactory<>("ign"));
        nom.setCellValueFactory(
                new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(
                new PropertyValueFactory<>("prenom"));

//       datenai.setCellValueFactory(
//                new PropertyValueFactory<>("datenai"));
//       System.out.println(datenai);
        is_banned.setCellValueFactory(
                new PropertyValueFactory<>("is_banned"));
        is_banned.setCellValueFactory(cellData
                -> {
            boolean isBanned = cellData.getValue().isIs_banned();
            String bannedStatus = isBanned ? "Banné" : "Actif";
            return new SimpleStringProperty(bannedStatus);
        }
        );
        //pprofile.setCellValueFactory(new PropertyValueFactory<>("pprofile"));
        clientTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) { // checks if the click count is 1
                handleRowClick(); // calls the handleRowClick function
            }
        });

        is_verified.setCellValueFactory(
                new PropertyValueFactory<>("is_verified"));
        is_verified.setCellValueFactory(cellData
                -> {
            boolean isVerified = cellData.getValue().isIs_verified();
            String bannedStatus = isVerified ? "✅" : "❌";
            return new SimpleStringProperty(bannedStatus);
        }
        );
//        Addresse.setCellValueFactory(
//                new PropertyValueFactory<>("Addresse"));
//        Tel.setCellValueFactory(
//                new PropertyValueFactory<>("Tel"));
        
        //this is code to add a ban button that changes color based on the state of the user, disgusting amount of filthy code to do sucha  simple task, why are they teaching us this when technology has advanced beyond this.
        // I hate java so much so much so much
        //////////////////
        TableColumn<Client, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setCellFactory(column -> {
            return new TableCell<Client, Void>() {
                private final Button banButton = new Button();

                {
                    banButton.setOnAction(event -> {
                        Client client = getTableView().getItems().get(getIndex());
                        boolean isBanned = client.isIs_banned();
                        if (isBanned) {
                            // Unban the client
                            client.setIs_banned(false);
                            banButton.setText("Bannir");
                            banButton.setStyle("-fx-background-color: red;");
                        } else {
                            // Ban the client
                            client.setIs_banned(true);
                            banButton.setText("Débannir");
                            banButton.setStyle("-fx-background-color: green;");
                        }
                        String updateQuery = "UPDATE personne SET is_banned = ? WHERE id = ?";
                        try {
                            PreparedStatement preparedStatement = cnx.prepareStatement(updateQuery);
                            preparedStatement.setBoolean(1, client.isIs_banned());
                            preparedStatement.setInt(2, client.getId());
                            preparedStatement.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("Update failed: " + e.getMessage());
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Client client = getTableView().getItems().get(getIndex());
                        boolean isBanned = client.isIs_banned();
                        if (isBanned) {
                            banButton.setText("Débannir");
                            banButton.setStyle("-fx-background-color: green;");
                        } else {
                            banButton.setText("Bannir");
                            banButton.setStyle("-fx-background-color: red;");
                        }
                        setGraphic(banButton);
                        //updateData();

                    }
                }
            };
        });
        //cleareverything
        cleareverything.setOnAction(this::clear);
        ajouttext.setOnAction(this::handleAddUserButtonAction);
        clientTable.getColumns().add(buttonColumn);

        /////////////////
        //nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        // Load data from database and add to table
        List<Client> clients = serviceClient.getAllClients();

        //System.out.println(clients);
        clientTable.getItems()
                .addAll(clients);
        searchbutt.setOnAction(this::setupSearchButton);
    }  
    
    void handleRowClick() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();

        if (selectedClient != null) {
            nomtext.setText(selectedClient.getNom());
            prenomtext.setText(selectedClient.getPrenom());
            igntext.setText(selectedClient.getIgn());
            emailtext.setText(selectedClient.getEmail());
            
            mdptext.clear();
            selectedEmail = selectedClient.getEmail();
            System.out.println("Selected email: " + selectedEmail);

            // and so on for all text fields you want to populate
        }
    }
    
     //Functinos concerning the search
    private void updateTableView(String searchTerm) {
        List<Client> searchResults = serviceClient.searchClient(searchTerm); // Call your search function
        clientTable.getItems().clear(); // Clear existing items in the table view

        clientTable.setItems(FXCollections.observableArrayList(searchResults)); // Add the search results to the table view
    }

    private void showExportedImage(Image image) {
        Stage stage = new Stage();
        stage.setTitle("Exported Image");
        ImageView imageView = new ImageView(image);
        ScrollPane scrollPane = new ScrollPane(imageView);
        Scene scene = new Scene(scrollPane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void exportimg(ActionEvent event) {
        if (selectedEmail != null && !selectedEmail.isEmpty()) {
            // Retrieve the user from the database using the selectedEmail
            // You can use your existing code or any database access method of your choice

            // Create the image with user info and profile picture
            Client client = serviceClient.getClientByEmail(selectedEmail);
            if (client != null) {
                // Create the image with user data and profile picture
                //TODO
                Image userImage = createExportedImage(client);
                showExportedImage(userImage);

                // Failed code.
                // Send the image through Bluetooth
                // sendImageThroughBluetooth(userImage);
                // Using imgur API
                // Client ID:3d02f319c246baf
                // Client Secret:e625f1081d77fc21fd8d9fa1daa59ac93d700962
               /* try {
                    String link = uploadImage(userImage);
                    System.out.println("Imgur uploaded image: " + link);
                    BufferedImage QrCode = generateQRCode(link);
                    showExportedQr(QrCode);

                } catch (IOException e) {
                    // handle the exception here
                    System.out.println("An I/O error occurred: " + e.getMessage());
                    e.printStackTrace();
                }*/

            }

            // Use exportedImage for whatever purpose you need (e.g. transfer through Bluetooth)
        }
    }
    
    
    private Image createExportedImage(Client user) {
        // Create an empty image with the desired dimensions and background color
        int imageWidth = 800;
        int imageHeight = 800;
        Color backgroundColor = Color.WHITE;
        WritableImage image = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                pixelWriter.setColor(x, y, backgroundColor);
            }
        }
        String imgpath = IMAGE_FOLDER + user.getPprofile();
        System.out.println("Image path: " + imgpath);
        File imageFile = new File(imgpath);
        Image profilePicture = new Image(imageFile.toURI().toString());
        // Load user profile picture from file or database
        //Image profilePicture = new Image("file:" + user.getProfile());

        // Add user info and profile picture to the image
        Canvas canvas = new Canvas(imageWidth, imageHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //// IMAGES MODIFICATIONS
        //// RADIOACTIVE ZONE!!!!!!!!!
        /*
    
                                | \
                                | |
                                | |
           |\                   | |
          /, ~\                / /
         X     `-.....-------./ /
          ~-. ~  ~              |
             \             /    |
              \  /_     ___\   /
              | /\ ~~~~~   \ |
              | | \        || |
              | |\ \       || )
             (_/ (_/      ((_/
    
    
         */
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText(user.getNom() + " " + user.getPrenom(), 500, 80);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        gc.fillText(user.getEmail(), 500, 120);
        gc.fillText(user.getIgn(), 500, 150);
        gc.fillText("Banned: " + user.isIs_banned(), 500, 180);
        gc.fillText("Verified: " + user.isIs_verified(), 500, 210);
        double imageWidthRatio = profilePicture.getWidth() / profilePicture.getHeight();
        double imageHeightRatio = profilePicture.getHeight() / profilePicture.getWidth();
        double scaledImageWidth = imageWidth / 2;
        double scaledImageHeight = scaledImageWidth * imageHeightRatio;
        gc.drawImage(profilePicture, 20, 20, scaledImageWidth, scaledImageHeight);

        /////////////////////////
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.WHITE);
        // Save the image as a JPEG file
        Image imageResult = canvas.snapshot(params, null);

        return imageResult;
    }
    
        
    
    @FXML
    private void setupSearchButton(ActionEvent event) {
        Button searchButton = searchbutt;

        String searchTerm = usersearch.getText();
        updateTableView(searchTerm);

        // Add an event listener that changes the background color of the search button when the mouse hovers over it
        searchButton.setOnMouseEntered(e -> {

            searchButton.setStyle("-fx-background-color: yellow;");

        });

        // Add an event listener that resets the background color of the search button when the mouse exits
        searchButton.setOnMouseExited(e -> {

            searchButton.setStyle("-fx-background-color: transparent;");

        });
    }

    @FXML
    private void handleAddUserButtonAction(ActionEvent event) {
        String nom = nomtext.getText();
        String prenom = prenomtext.getText();
        String ign = igntext.getText();
        String password = mdptext.getText();
        String email = emailtext.getText();
        //String datanai = datenaistext.getText();
        LocalDate datee = datenaistext.getValue();
//        LocalDate datee = LocalDate.now();
        DateTimeFormatter formatter69 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = datee.format(formatter69);
        Date date3 = Date.valueOf(formattedDate);
        String datenaistext2 = datee.toString();

        String selectedValue = rolescombo.getValue();
        String[] roles = selectedValue.split(",");

        boolean isVerified = false; // set to true if the user has been verified

        //String profile = "test";
        String profilepic = null;
        try {
            //System.out.println("\u001B[31mError: Test 6.\u001B[0m");
            profilepic = saveImage(profileImageFile);
        } catch (IOException e) {
            System.out.println("Error saving image file");
        }
        boolean isBanned = false; // set to true if the user has been banned

        //Date createdAt = new Date(client.getCreatedAt().getTime());
        // validate input fields here, e.g. check if required fields are filled
        //////////////////////VALIDATION
        // validate nom
        if (nom.isEmpty() || nom.length() > 15 || !nom.matches("[a-zA-Z]+")) {
            //showAlert("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nom invalide!");
            alert.setContentText("Nom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;
        }

// validate prenom
        if (prenom.isEmpty() || prenom.length() > 15 || !prenom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Prenom invalide!");
            alert.setContentText("Prenom invalide! Il doit être composé de lettres seulement et ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;

        }

// validate ign
        if (ign.isEmpty() || ign.length() > 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("IGN invalide!");
            alert.setContentText("IGN invalide! Il ne doit pas dépasser 15 caractères.");
            alert.showAndWait();
            return;
        }

// validate password
        if (password.isEmpty() || password.length() > 10 || !password.matches("^(?=.*[0-9])(?=.*[A-Z]).{1,}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Mot de passe invalide!");
            alert.setContentText("Mot de passe invalide! Il doit contenir au moins 1 chiffre, 1 lettre majuscule et ne doit pas dépasser 10 caractères.");
            alert.showAndWait();
            return;
        }

// validate email
        if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Email invalide!");
            alert.setContentText("Email invalide! Veuillez saisir une adresse email valide.");
            alert.showAndWait();
            return;
        }

//// validate wins
//        try {
//            wins = Integer.parseInt(winsString);
//        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Erreur de saisie");
//            alert.setContentText("Victoires invalide! Veuillez saisir un entier.");
//            alert.showAndWait();
//            return;
//        }
//
//// validate losses
//        try {
//            losses = Integer.parseInt(winsString);
//        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Erreur de saisie");
//            alert.setContentText("Defaites invalide! Veuillez saisir un entier.");
//            alert.showAndWait();
//            return;
//        }

// validate datanai
        if (datenaistext2.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide!");
            alert.showAndWait();

            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date2 = LocalDate.parse(datenaistext2, formatter);
            LocalDate now = LocalDate.now();
            int age = Period.between(date2, now).getYears();
            if (age < 16) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Date de naissance invalide!");
                alert.setContentText("L'âge doit être supérieur ou égal à 16 ans.");
                alert.showAndWait();
                return;

            }
        } catch (DateTimeParseException e) {
            //showAlert("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Date de naissance invalide!");
            alert.setContentText("Date de naissance invalide! Veuillez saisir une date de naissance valide (format: jj/mm/aaaa).");
            alert.showAndWait();
            return;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                                    
        /* String salt = BCrypt.gensalt(12);
            String hashedPassword = BCrypt.hashpw(userPassword, salt);*/
        Client client = new Client( ign,  nom,  prenom,  email, roles,  hashedPassword,  datee,  profilepic,  isBanned,  isVerified);

        try {
            // Insert the new Client into the database using the DAO
            serviceClient.ajouter(client);
            System.out.println("Client added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding clint: " + e.getMessage());

            //I hate java with the deepest roots of my heart, I hope however invented javaFX roots in hell for his sins.
        }

        // clear input fields
        nomtext.clear();
        prenomtext.clear();
        datenaistext.setValue(null);
        mdptext.clear();
        igntext.clear();
        emailtext.clear();
//        Adressetext.clear();
//        Teltext.clear();
        updateData();
        
    }

    @FXML
    private void clear(ActionEvent event) {
        nomtext.clear();
        prenomtext.clear();
        datenaistext.setValue(null);
        mdptext.clear();
        igntext.clear();
        emailtext.clear();
//        Adressetext.clear();
//        Teltext.clear();
        usersearch.clear();
        updateData();
    }
    public void updateData() {
        // Get the current data from the database or any other source
        List<Client> data = serviceClient.getAllClients();

        // Clear the current data in the table view
        clientTable.getItems().clear();

        // Add the new data to the table view
        clientTable.getItems().addAll(data);

        // Refresh the table view to show the new data
        clientTable.refresh();
    }
    
    @FXML
    private void browseImage(ActionEvent event) {
        //System.out.println("Test 0");
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
                //System.out.println("Test 1");
                //ImageView imageView = new ImageView(image);
                //profileImageFile = file;
                viewimg.setImage(image);
                //System.out.println("Test 2");
            } catch (Exception e) {
                System.out.println("Error loading image");
            }
        }
    }

    @FXML
    private void exportExcel(ActionEvent event) {
        // create a file chooser
        FileChooser fileChooser = new FileChooser();

        // set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);

        // show save dialog
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // create a writable workbook
                WritableWorkbook workbook = Workbook.createWorkbook(file);

                // create a writable sheet
                WritableSheet sheet = workbook.createSheet("Clients", 0);

                // add column headers
                sheet.addCell(new Label(0, 0, "id"));
                sheet.addCell(new Label(1, 0, "email"));
                sheet.addCell(new Label(2, 0, "password"));
                sheet.addCell(new Label(3, 0, "is_verified"));
                sheet.addCell(new Label(4, 0, "nom"));
                sheet.addCell(new Label(5, 0, "prenom"));

                sheet.addCell(new Label(6, 0, "is_banned"));
                sheet.addCell(new Label(7, 0, "ign"));
                

                // get all the clients from the database
                List<Client> clients = serviceClient.getAllClients();

                // add clients data to the sheet
                int row = 1;
                for (Client client : clients) {
                    sheet.addCell(new Label(0, row, Integer.toString(client.getId())));
                    sheet.addCell(new Label(1, row, client.getEmail()));
                    sheet.addCell(new Label(2, row, client.getPassword()));
                    sheet.addCell(new Label(3, row, client.isIs_verified()== false ? "❌" : "✅"));

                    sheet.addCell(new Label(4, row, client.getNom()));
                    sheet.addCell(new Label(5, row, client.getPrenom()));

                    sheet.addCell(new Label(6, row, client.isIs_banned()== false ? "Not banned" : "Banned"));
                    sheet.addCell(new Label(7, row, client.getIgn()));
                    

                    row++;
                }

                // write and close the workbook
                workbook.write();
                workbook.close();

                // show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export to Excel");
                alert.setHeaderText(null);
                alert.setContentText("Data exported to " + file.getAbsolutePath());
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                // show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export to Excel");
                alert.setHeaderText(null);
                alert.setContentText("Error: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void importExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Excel File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                Workbook workbook = Workbook.getWorkbook(selectedFile);
                Sheet sheet = workbook.getSheet(0);

                ServicesClient serviceClient = new ServicesClient();

                // Start reading from row 1, since row 0 contains the column headers
                for (int i = 1; i < sheet.getRows(); i++) {
                    String email = sheet.getCell(0, i).getContents();
                    String password = sheet.getCell(1, i).getContents();
                    String isVerifiedString = sheet.getCell(2, i).getContents();
                    boolean isVerified = isVerifiedString.equalsIgnoreCase("true");
                    String nom = sheet.getCell(3, i).getContents();
                    String prenom = sheet.getCell(4, i).getContents();
                    String isBannedString = sheet.getCell(5, i).getContents();
                    boolean isBanned = isBannedString.equalsIgnoreCase("true");
                    String ign = sheet.getCell(6, i).getContents();
                    
                    Date dateNaissance = Date.valueOf("1999-12-12");

                    String salt = BCrypt.gensalt(12);
                    String hashedPassword = BCrypt.hashpw(password, salt);
                    // Add user to database with ROLE_USER
                    Client client = new Client(email, hashedPassword, nom, prenom, ign);
                    client.setIs_verified(isVerified);
                    
                    Instant instant =dateNaissance.toInstant();
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDate localDate =instant.atZone(zoneId).toLocalDate();
                    client.setDateNaise(localDate);
                    client.setIs_banned(isBanned);

                    String role = "ROLE_USER";
                    String[] roles = new String[]{"\"" + role + "\""};
                    client.setRoles(roles);
                    serviceClient.ajouter(client);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Import successfull!");
                    alert.setHeaderText("Client imported.");

                    alert.showAndWait();

                }

                workbook.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void logoutback(ActionEvent event) throws IOException {
        // remove the session ID
        Global.sessionId = null;

        // redirect the user to the register page
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) sessionlogout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
}
