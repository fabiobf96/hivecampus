package it.hivecampuscompany.hivecampus.view.utility;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import javafx.application.Application;
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
                    new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.jpeg", "*.png")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                try {
                    // Leggi l'immagine dal file selezionato
                    Image image = new Image(new FileInputStream(selectedFile));

                    // Converti l'immagine in BufferedImage
                    BufferedImage bufferedImage = convertToBufferedImage(image);

                    // Converti BufferedImage in array di byte
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    // Ottieni il formato dell'immagine
                    String imageFormat = getImageFormat(selectedFile);
                    
                    ImageIO.write(bufferedImage, imageFormat, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();

                    // Ottieni il nome e l'estensione del file selezionato
                    String imageName = selectedFile.getName();
                    String imageType = imageName.substring(imageName.lastIndexOf('.') + 1);

                    // Inserisci l'array di byte nel database insieme al nome e all'estensione dell'immagine
                    insertImageIntoDatabase(byteArray, imageName, imageType);

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

    private void insertImageIntoDatabase(byte[] byteArray, String imageName, String imageType) {
        int idHome = 3;
        String sql = "INSERT INTO hivecampus2.home_images (name, type, image, home) VALUES (?, ?, ?, ?)";
        try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, imageName);
            pstmt.setString(2, imageType);
            pstmt.setBytes(3, byteArray);
            pstmt.setInt(4, idHome);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
        }
    }


    // Metodo per convertire un'immagine JavaFX in BufferedImage
    private BufferedImage convertToBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        javafx.scene.image.PixelReader pixelReader = image.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
            }
        }

        return bufferedImage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}