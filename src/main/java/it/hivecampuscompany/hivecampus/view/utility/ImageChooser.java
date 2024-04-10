package it.hivecampuscompany.hivecampus.view.utility;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.Arrays;
import java.util.logging.Logger;


public class ImageChooser extends Application {

    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(ImageChooser.class.getName());

    File selectedFile;

    @Override
    public void start(Stage primaryStage) {

        // Creazione del pulsante per selezionare l'immagine
        Button btnSelectImage = new Button("Seleziona Immagine");
        // Creazione di un textfield per specificare il nome dell'immagine caricata
        TextField txfImageName = new TextField();

        // Creazione di un textfield per specificare l'id della stanza
        TextField txfIdRoom = new TextField();
        txfIdRoom.setPromptText("Inserisci l'id della stanza");

        // Creazione di un textfield per specificare l'id della casa
        TextField txfIdHome = new TextField();
        txfIdHome.setPromptText("Inserisci l'id della casa");

        // Creazione di un bottone submit
        Button btnSubmit = new Button("Submit");

        // Azione quando viene cliccato il pulsante
        btnSelectImage.setOnAction(e -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Seleziona un'immagine");
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.jpeg", "*.png"));
                    selectedFile = fileChooser.showOpenDialog(primaryStage);

                    // Mostra il nome del file selezionato nel textfield
                    txfImageName.setText(selectedFile.getName());
                });

        // Azione quando viene cliccato il pulsante submit
        btnSubmit.setOnAction(e -> {
            // Recupera l'id della stanza e della casa
            int idRoom = Integer.parseInt(txfIdRoom.getText());
            int idHome = Integer.parseInt(txfIdHome.getText());

            // Se è stata selezionata un'immagine e sono stati inseriti l'id della stanza e della casa
            if (selectedFile != null && txfIdRoom.getText() != null && txfIdHome.getText() != null) {
                try {
                    // Leggi l'immagine come array di byte
                    byte[] imageArray = Files.readAllBytes(selectedFile.toPath());  // <-- funziona per entrambi

                    // Ottieni il nome e l'estensione del file selezionato
                    String imageName = selectedFile.getName();
                    String imageType = imageName.substring(imageName.lastIndexOf('.') + 1);

                    // insertImageIntoDatabase(imageName, imageType, imageArray, idRoom, idHome);  // <-- lato database

                    new InsertImageIntoCSV().saveRoom(imageName, imageType, imageArray, idRoom, idHome); // <-- lato CSV

                    // Ora puoi utilizzare byteArray come desideri
                } catch (IOException ex) {
                    LOGGER.severe(Arrays.toString(ex.getStackTrace()));
                }
            }
        });

        VBox vbox = new VBox(btnSelectImage, txfIdRoom, txfIdHome, txfImageName, btnSubmit);

        vbox.setPadding(new javafx.geometry.Insets(20));
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertImageIntoDatabase(String imgName, String imgType, byte[] byteArray, int idRoom, int idHome) {
        String sql = "INSERT INTO room_images (name, type, image, idRoom, idHome) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, imgName);
            pstmt.setString(2, imgType);
            pstmt.setBytes(3, byteArray);
            pstmt.setInt(4, idRoom);
            pstmt.setInt(5, idHome);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
        }
    }
}