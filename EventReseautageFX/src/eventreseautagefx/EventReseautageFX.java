package eventreseautagefx;

import com.eventhub.entities.User;
import com.eventhub.services.AmiService;
import com.eventhub.services.ServiceAmi;
import com.eventhub.gui.AmiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EventReseautageFX extends Application {

    private static AmiService amiService;
    private static User loggedInUser;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Créer une instance de la classe ServiceAmi (vous pouvez utiliser votre propre implémentation ici)
            amiService = new ServiceAmi();

            // Création d'un utilisateur factice pour la démonstration
            loggedInUser = new User(1, "John", "Doe", "johndoe@example.com", "password");
           


            // Charger le fichier FXML de votre interface utilisateur (assurez-vous que le chemin du fichier est correct)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eventhub/gui/Freind.fxml"));
            BorderPane root = loader.load();

            // Obtenir le contrôleur de l'interface utilisateur
            AmiController amiController = loader.getController();
            amiController.initialize(); // Vous pouvez appeler cette méthode pour effectuer toute initialisation

            // Définir l'utilisateur connecté dans le contrôleur
            amiController.setLoggedInUser(loggedInUser);

            // Définir le service Ami dans le contrôleur
            amiController.setAmiService(amiService);

            // Créer une scène et afficher l'interface utilisateur
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EventReseautageFX");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}