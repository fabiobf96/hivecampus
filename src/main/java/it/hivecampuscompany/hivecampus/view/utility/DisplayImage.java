package it.hivecampuscompany.hivecampus.view.utility;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.csv.CSVUtility;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisplayImage extends Application {

    private final Connection connection = ConnectionManager.getConnection();

    private File fd;
    private static final Logger LOGGER = Logger.getLogger(DisplayImage.class.getName());
    private Properties properties;
    static final String ERROR_ACCESS = "ERR_ACCESS";

    public DisplayImage() {
        try (InputStream input = new FileInputStream("properties/csv.properties")){
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("ROOM_IMAGES_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Display Image");

        // DEVO RENDERLO GENERICO NEL SENSO CHE GLI PASSO L'ID DA RECUPARE
        /*
        if (mode.equals("DB")) {
            byteArray = getImageBytesFromDatabase(idImage);  // <--  DB
        } else if (mode.equals("CSV")) {
            byteArray = getImageBytesFromCSV(idImage);  // <--  CSV
        } else {
            LOGGER.severe("Errore: modalità non supportata");
            System.exit(1);
        }
         */

        // Creazione del pulsante per selezionare l'immagine
        Button btnSearch= new Button("Visualizza immagine");

        // Creazione di un textfield per specificare l'id della stanza
        TextField txfIdImage = new TextField();
        txfIdImage.setPromptText("Inserisci l'id dell'immagine");

        // Creazione di un VBox per image
        VBox imageVbox = new VBox();
        imageVbox.setPrefSize(200, 200);
        imageVbox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        VBox vbox = new VBox();

        // Azione quando viene cliccato il pulsante
        btnSearch.setOnAction(e -> {

            // Recupera l'id dell'immagine
            int idImage = Integer.parseInt(txfIdImage.getText());

            byte [] byteArray = getImageBytesFromDB(idImage); // <--  funziona
           // byte [] byteArray = getImageBytesFromCSV(idImage); // <--  funziona

            // Creazione di un'immagine dall'array di byte
            Image image =  new Image(new ByteArrayInputStream(byteArray));

            // Creazione di un'ImageView per visualizzare l'immagine
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);

            imageVbox.setAlignment(javafx.geometry.Pos.CENTER);
            imageVbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            imageVbox.getChildren().clear();
            imageVbox.getChildren().add(imageView);
        });

        vbox.setPadding(new javafx.geometry.Insets(20));
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        vbox.setSpacing(20);

        vbox.getChildren().addAll(txfIdImage, imageVbox, btnSearch);
        // Creazione di una nuova scena con l'immagine visualizzata
        Scene scene = new Scene(vbox);
        // Impostazione della scena sulla finestra principale
        primaryStage.setScene(scene);
        // Mostra la finestra
        primaryStage.show();
    }

    public byte[] getImageBytesFromDB(int idImage) {
        String sql = "SELECT image FROM hivecampus2.room_images WHERE id = ?";
        //String sql = "SELECT image FROM hivecampus_db.room_images WHERE id = ?"; // <--  funziona
        //String sql = "SELECT image FROM hivecampus_db.room WHERE idRoom = ?"; // <--  funziona
        // Implementazione per recuperare l'array di byte dell'immagine dal database
        try(java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idImage);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("image");
            }
        } catch (java.sql.SQLException e) {
            LOGGER.severe("Errore durante il recupero dell'immagine dal database: " + e.getMessage());
        }
        return null;
    }

    public byte[] getImageBytesFromCSV (int idImage) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> imageTable = reader.readAll();
            String[] imageRecord = imageTable.get(idImage);
            return CSVUtility.decodeBase64ToBytes(imageRecord[5]);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), fd), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERR_PARSER"), fd), e);
            System.exit(3);
        }
        return new byte[0];
    }
}
