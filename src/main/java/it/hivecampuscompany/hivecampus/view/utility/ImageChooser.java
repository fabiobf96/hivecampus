package it.hivecampuscompany.hivecampus.view.utility;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import javafx.application.Application;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.File;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Arrays;
import java.util.logging.Logger;


public class ImageChooser extends Application {

    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(ImageChooser.class.getName());

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Seleziona un'immagine");

        // Creazione del pulsante per selezionare l'immagine
        Button selectImageButton = new Button("Seleziona Immagine");

        // Azione quando viene cliccato il pulsante
        selectImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleziona un'immagine");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.jpeg", "*.png" , "*.pdf")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                try {
                    // Leggi l'immagine come array di byte
                    byte[] imageArray = Files.readAllBytes(selectedFile.toPath());  // <-- funziona per entrambi

                    // Ottieni il formato dell'immagine
                    //String imageFormat = getImageFormat(selectedFile);

                    // Ottieni il nome e l'estensione del file selezionato
                    String imageName = selectedFile.getName();
                    String imageType = imageName.substring(imageName.lastIndexOf('.') + 1);

                    // Inserisci l'array di byte nel database insieme al nome e all'estensione dell'immagine

                    insertImageIntoDatabase(imageArray);  // <-- lato database
                    //new SaveRoomIntoCSV().saveRoom(imageArray, imageName, imageType);

                    // Ora puoi utilizzare byteArray come desideri
                } catch (IOException ex) {
                    LOGGER.severe(Arrays.toString(ex.getStackTrace()));
                }
            }
        });

        VBox vbox = new VBox(selectImageButton);
        Scene scene = new Scene(vbox, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getImageFormat(File file) {
        String format = null;
        try {
            format = ImageIO.getImageReadersBySuffix(getExtension(file)).next().getFormatName();
        } catch (IOException e) {
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
        }
        return format;
    }

    private String getExtension(File file) {
        String name = file.getName();
        int lastIndexOfDot = name.lastIndexOf('.');
        if (lastIndexOfDot == -1 || lastIndexOfDot == name.length() - 1) {
            return "";
        }
        return name.substring(lastIndexOfDot + 1);
    }

    private void insertImageIntoDatabase(byte[] byteArray) { // byte[] byteArray, String imageName, String imageType nella version hivecampus
        int idImage = 3;
        String sql = "INSERT INTO hivecampus_db.room (idRoom, image) VALUES (?, ?)";
        try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idImage);
            pstmt.setBytes(2, byteArray);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}