/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Map;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import services.ReclamationService;


/**
 * FXML Controller class
 *
 * @author walid
 */
public class FXMLStatistiquesReclamationController implements Initializable {

    @FXML
    private SwingNode chart1;
    ReclamationService rs = new ReclamationService();

    @FXML
    private SwingNode chart2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Map<String, Integer> res2 = rs.statistiquesReclamation();
        DefaultPieDataset data1 = new DefaultPieDataset();
        DefaultPieDataset data2 = new DefaultPieDataset();
      
        res2.entrySet().forEach((i) -> {
            data2.setValue(i.getKey(), i.getValue());
        });
        JFreeChart graph1 = ChartFactory.createPieChart(
                "Repartition d'utilisateurs",
                data1,
                true,
                true,
                false
        );
        JFreeChart graph2 = ChartFactory.createPieChart(
                "Repartition des reclamations",
                data2,
                true,
                true,
                false
        );
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
        ((PiePlot) graph1.getPlot()).setLabelGenerator(labelGenerator);
        ((PiePlot) graph2.getPlot()).setLabelGenerator(labelGenerator);
        ChartPanel chartPanel1 = new ChartPanel(graph1);
        chartPanel1.setPreferredSize(new Dimension(300, 300));
        ChartPanel chartPanel2 = new ChartPanel(graph2);
        chartPanel2.setPreferredSize(new Dimension(400, 300));
        chart1.setContent(chartPanel1);
        chart2.setContent(chartPanel2);
    }

}
