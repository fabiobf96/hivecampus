package it.hivecampuscompany.hivecampus.view.utility;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.logging.Logger;

public class DisplayImage extends Application {

    private final Connection connection = ConnectionManager.getConnection();
    private final Logger LOGGER = Logger.getLogger(DisplayImage.class.getName());

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Display Image");
        // DEVO RENDERLO GENERICO NEL SENSO CHE GLI PASSO L'ID DA RECUPARE

        byte [] byteArray = getImageBytesFromDatabase(3);  // <--  DB
        //byte [] byteArray = new SaveRoomIntoCSV().retrieveRoom(1);  // <--  CSV

        // Creazione di un flusso di input dai byte
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);

        // Creazione di un'immagine dall'array di byte
        Image image = new Image(byteArrayInputStream);
        //Image image = retrieveImageFromDatabase(2);
        VBox vbox = new VBox();

        // Creazione di un'ImageView per visualizzare l'immagine
        ImageView imageView = new ImageView(image);
        vbox.getChildren().add(imageView);

        // Creazione di una nuova scena con l'immagine visualizzata
        Scene scene = new Scene(vbox, 500, 500);

        // Impostazione della scena sulla finestra principale
        primaryStage.setScene(scene);

        // Mostra la finestra
        primaryStage.show();
    }

    private byte[] getImageBytesFromDatabase(int roomId) {
        String sql = "SELECT image FROM room WHERE idRoom = ?";
        // Implementazione per recuperare l'array di byte dell'immagine dal database
        try(java.sql.PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("image");
            }
        } catch (java.sql.SQLException e) {
            LOGGER.severe("Errore durante il recupero dell'immagine dal database: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
