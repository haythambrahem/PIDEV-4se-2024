/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;



/**
 * FXML Controller class
 *
 * @author siwar
 */


public class QReventController implements Initializable {

    
     @FXML
    public ImageView qr;
      @FXML
    private Button download;
      private String pdfFilePath;
        @FXML
    private Button retourqr;
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        pdfFilePath = "C:/Users/Siwar/Documents/GitHub/PIDEV-4se-2024/Event_sapp/evenement.pdf";
        try {
            generateQRCode("file:///" + pdfFilePath, qr);
        } catch (WriterException ex) {
            Logger.getLogger(QReventController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
   
   
   

    private void generateQRCode(String pdfFilePath, ImageView qr) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(pdfFilePath, BarcodeFormat.QR_CODE, 200, 200);
        WritableImage qrCodeImage = SwingFXUtils.toFXImage(MatrixToImageWriter.toBufferedImage(bitMatrix), null);
        qr.setImage(qrCodeImage);
    }
 
   
    @FXML
    private void downloadqr(ActionEvent event) {
       
       
       
       
        FileChooser fileChooser = new FileChooser();

    // Set extension filter to PNG files
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
    fileChooser.getExtensionFilters().add(extFilter);

    // Show save file dialog
    File file = fileChooser.showSaveDialog(download.getScene().getWindow());

    if (file != null) {
        try {
            // Convert the image to a buffered image
            BufferedImage bImage = SwingFXUtils.fromFXImage(qr.getImage(), null);

            // Save the buffered image to disk as a PNG
            ImageIO.write(bImage, "png", file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }}

         @FXML
    private void retourqr(ActionEvent event)  throws IOException {
   
    FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeUser.fxml"));
         Stage stage = new Stage();
         
         stage.setScene(new Scene(loader.load()));
         stage.show();
    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    currentStage.hide();
   
   
   
}

}
    
   
