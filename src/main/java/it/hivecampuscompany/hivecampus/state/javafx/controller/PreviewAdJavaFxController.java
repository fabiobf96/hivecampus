package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.logging.Logger;

public class PreviewAdJavaFxController extends JavaFxController {

    private final Connection connection = ConnectionManager.getConnection();  // solo per la prova delle immagini
    private static final Logger LOGGER = Logger.getLogger(PreviewAdJavaFxController.class.getName());

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

    private AdBean adBean;

    public PreviewAdJavaFxController() {
        // Default constructor
    }

    public void setAdBean(AdBean bean){
        // set the bean
        this.adBean = bean;
    }

    public void initializePreviewFeatures() {
        // Imposta il colore a nero e il testo per tutte le label
        setLabelText(lblTitle, properties.getProperty("ROOM_TYPE_MSG") + adBean.adTitle() + properties.getProperty("MONTHLY_PRICE_MSG"));
        setLabelText(lblFeatures, properties.getProperty("ROOM_FEATURES_MSG"));
        setLabelText(lblSurface, properties.getProperty("SURFACE_MSG"));
        setLabelText(lblPrivateBath, properties.getProperty("PRIVATE_BATH_MSG"));
        setLabelText(lblBalcony, properties.getProperty("BALCONY_MSG"));
        setLabelText(lblConditioner, properties.getProperty("CONDITIONER_MSG"));
        setLabelText(lblTVConnection, properties.getProperty("TV_CONNECTION_MSG"));
        setLabelText(lblArea, String.valueOf(adBean.getRoomBean().getSurface()));
        setLabelText(lblBath, String.valueOf(adBean.getRoomBean().getBathroom()));
        setLabelText(lblBalc, String.valueOf(adBean.getRoomBean().getBalcony()));
        setLabelText(lblAirCond, String.valueOf(adBean.getRoomBean().getConditioner()));
        setLabelText(lblTV, String.valueOf(adBean.getRoomBean().getTV()));

        byte[] imageBytes = getImageBytesFromDatabase(1);
        if (imageBytes != null) {
            imgRoom.setImage(new Image(new ByteArrayInputStream(imageBytes))); // solo per la prova delle immagini
        }

    }

    public void initializePreviewDistance() {
        // Imposta il colore a nero e il testo per tutte le label
        setLabelText(lblDist, properties.getProperty("DISTANCE_MSG"));
        setLabelText(lblDistUni, properties.getProperty("UNIVERSITY_MSG"));
        setLabelText(lblAvl, properties.getProperty("AVAILABILITY_MSG"));
        setLabelText(lblUniversity, adBean.getUniversity());
        setLabelText(lblDistance, (adBean.getDistance()) + " km");
        setLabelText(lblAvailability, String.valueOf(adBean.getAdStart()));
    }

    // Recupera l'array di byte dell'immagine dal database (metodo di prova)
    public byte[] getImageBytesFromDatabase(int roomId) {
        String sql = "SELECT image FROM hivecampus2.room_images WHERE id = ?"; // hivecampus_db.room WHERE idRoom = ? (funziona)
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
        return new byte[0];
    }
}
