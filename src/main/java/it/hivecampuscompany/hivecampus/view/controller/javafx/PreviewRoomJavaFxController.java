package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.bean.AdBean;
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

    private AdBean adBean;

    public PreviewRoomJavaFxController() {
        // Default constructor
    }

    public void setAdBean(AdBean bean){
        // set the bean
        this.adBean = bean;
    }

    public void initializePreviewFeatures() {
        lblTitle.setText(properties.getProperty("ROOM_TYPE_MSG") + adBean.adTitle() + properties.getProperty("MONTHLY_PRICE_MSG"));
        lblFeatures.setText(properties.getProperty("ROOM_FEATURES_MSG"));
        lblSurface.setText(properties.getProperty("SURFACE_MSG"));
        lblPrivateBath.setText(properties.getProperty("PRIVATE_BATH_MSG"));
        lblBalcony.setText(properties.getProperty("BALCONY_MSG"));
        lblConditioner.setText(properties.getProperty("CONDITIONER_MSG"));
        lblTVConnection.setText(properties.getProperty("TV_CONNECTION_MSG"));

        lblArea.setText(String.valueOf(adBean.getRoomBean().getSurface()));
        lblBath.setText(String.valueOf(adBean.getRoomBean().getBathroom()));
        lblBalc.setText(String.valueOf(adBean.getRoomBean().getBalcony()));
        lblAirCond.setText(String.valueOf(adBean.getRoomBean().getConditioner()));
        lblTV.setText(String.valueOf(adBean.getRoomBean().getTV()));
    }

    public void initializePreviewDistance() {
        lblDist.setText(properties.getProperty("DISTANCE_MSG"));
        lblDistUni.setText(properties.getProperty("UNIVERSITY_MSG"));
        lblAvl.setText(properties.getProperty("AVAILABILITY_MSG"));

        lblUniversity.setText(adBean.getUniversity());
        lblDistance.setText(String.valueOf(adBean.getDistance()));
        lblAvailability.setText(String.valueOf(adBean.getAdStart()));
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
