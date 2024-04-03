package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.logging.Logger;

public class PreviewRoomJavaFxController extends JavaFxController {

    private final Connection connection = ConnectionManager.getConnection();  // solo per la prova delle immagini
    private static final Logger LOGGER = Logger.getLogger(PreviewRoomJavaFxController.class.getName());

    @FXML
    private ImageView imgRoom;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblFeatures;
    @FXML
    private Label lblSurface;
    @FXML
    private Label lblPrivateBath;
    @FXML
    private Label lblBalcony;
    @FXML
    private Label lblConditioner;
    @FXML
    private Label lblTVConnection;

    @FXML
    private Label lblArea;
    @FXML
    private Label lblBath;
    @FXML
    private Label lblBalc;
    @FXML
    private Label lblAirCond;
    @FXML
    private Label lblTV;

    @FXML
    private Label lblDist;
    @FXML
    private Label lblUniversity;
    @FXML
    private Label lblDistUni;
    @FXML
    private Label lblDistance;
    @FXML
    private Label lblAvl;
    @FXML
    private Label lblAvailability;

    public PreviewRoomJavaFxController() {
        // Default constructor
    }

    public void initializePreviewFeatures() {
        lblTitle.setText("ROOM_TITLE");  // bean
        lblFeatures.setText(properties.getProperty("ROOM_FEATURES_MSG"));
        lblSurface.setText(properties.getProperty("SURFACE_MSG"));
        lblPrivateBath.setText(properties.getProperty("PRIVATE_BATH_MSG"));
        lblBalcony.setText(properties.getProperty("BALCONY_MSG"));
        lblConditioner.setText(properties.getProperty("CONDITIONER_MSG"));
        lblTVConnection.setText(properties.getProperty("TV_CONNECTION_MSG"));

        lblArea.setText("AREA"); // bean
        lblBath.setText("BATH"); // bean
        lblBalc.setText("BALCONY"); // bean
        lblAirCond.setText("AIR CONDITIONER"); // bean
        lblTV.setText("TV"); // bean

        // Recupera l'array di byte dell'immagine dal database
        byte[] imageBytes = getImageBytesFromDatabase(1);
        if (imageBytes != null) {
            // Crea un'istanza di Image da bytearray
            Image image = new Image(new ByteArrayInputStream(imageBytes));
            // Imposta l'imageview per visualizzare l'immagine
            imgRoom.setImage(image);
            imgRoom.setPreserveRatio(false);
        }
    }

    public void initializePreviewDistance() {
        lblDist.setText(properties.getProperty("DISTANCE_MSG"));
        lblDistUni.setText(properties.getProperty("UNIVERSITY_MSG"));
        lblAvl.setText(properties.getProperty("AVAILABILITY_MSG"));

        lblUniversity.setText("UNIVERSITY"); // bean
        lblDistance.setText("DISTANCE"); // bean
        lblAvailability.setText("AVAILABILITY"); // bean
    }

    // Recupera l'array di byte dell'immagine dal database (metodo di prova)
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
}