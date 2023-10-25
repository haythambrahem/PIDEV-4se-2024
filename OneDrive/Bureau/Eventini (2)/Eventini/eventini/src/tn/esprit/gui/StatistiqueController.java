/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import tn.esprit.entity.Offre;
import tn.esprit.services.ServiceOffre;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import tn.esprit.entity.Panier;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart.Series;
import tn.esprit.entity.Offre;
import tn.esprit.entity.TypeOffre;
import tn.esprit.services.ServicePanier;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author imen
 */
public class StatistiqueController implements Initializable {

    @FXML
    private StackedBarChart<String, Number> statPanier;
    @FXML
    private PieChart statOffre;
        @FXML
    private NumberAxis xAxis; // Ajoutez les axes x et y
    @FXML
    private CategoryAxis yAxis;
    @FXML
    private Button retourstat;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

          genererDonneesPourPieChart();   
        //genererPourcentageTypeOffresDansTousLesPaniers();
        ServicePanier servicePanier = new ServicePanier();
        
        List<TypeOffre> typesOffres = servicePanier.getTypeOffreDeTousLesOffresDansLesPaniers();

        // Comptez les occurrences de chaque type d'offre
        Map<TypeOffre, Long> occurrences = typesOffres.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Créez une série de données pour chaque type d'offre
        Series<String, Number> series = new Series<>();
        series.setName("Pourcentage d'offres par type");

        for (Map.Entry<TypeOffre, Long> entry : occurrences.entrySet()) {
            TypeOffre typeOffre = entry.getKey();
            Long count = entry.getValue();
            double pourcentage = (count * 100.0) / typesOffres.size();
            series.getData().add(new XYChart.Data<>(typeOffre.toString(), pourcentage));
        }

        // Ajoutez la série au graphique
        statPanier.getData().add(series);
    }






       
    private void genererDonneesPourPieChart() {
    ServiceOffre serviceOffre = new ServiceOffre();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    int countReduction = 0;
    int countPromotion = 0;
    int countPackage = 0;

    try {
        // Récupérez les données d'offre depuis votre service
        for (Offre offre : serviceOffre.recuperer()) {
            switch (offre.getTypeOffre()) {
                case Réduction:
                    countReduction++;
                    break;
                case Promotion:
                    countPromotion++;
                    break;
                case Package:
                    countPackage++;
                    break;
                // Ajoutez d'autres cas pour d'autres types d'offres si nécessaire
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Gérez l'exception de manière appropriée (par exemple, affichez un message d'erreur)
    }

    pieChartData.add(new PieChart.Data("Réduction", countReduction));
    pieChartData.add(new PieChart.Data("Promotion", countPromotion));
    pieChartData.add(new PieChart.Data("Package", countPackage));

    statOffre.setData(pieChartData);
}

    private void goOffreA(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OffreA.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Obtenir la référence de la fenêtre actuelle (AfficherPanier)
        Stage currentStage = (Stage) retourstat.getScene().getWindow();

        // Fermer la fenêtre actuelle (AfficherPanier)
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void retourOff(ActionEvent event) {
        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OffreA.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Obtenir la référence de la fenêtre actuelle (AfficherPanier)
        Stage currentStage = (Stage) retourstat.getScene().getWindow();

        // Fermer la fenêtre actuelle (AfficherPanier)
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}
