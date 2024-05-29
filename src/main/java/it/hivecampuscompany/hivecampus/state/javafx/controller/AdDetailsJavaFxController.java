package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.AdSearchJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.TenantHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.state.utility.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The AdDetailsJavaFxController class represents a controller for the ad details page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the ad details view and handling user interactions.
 */

public class AdDetailsJavaFxController extends JavaFxController{

    private AdSearchPage adSearchPage;
    private static final Logger LOGGER = Logger.getLogger(AdDetailsJavaFxController.class.getName());

    @FXML
    private Button btnBack;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblUniversity;
    @FXML
    private Label lblDistance;
    @FXML
    private Label lblHType;
    @FXML
    private Label lblHSurface;
    @FXML
    private Label lblNumRooms;
    @FXML
    private Label lblNumBathrooms;
    @FXML
    private Label lblFloor;
    @FXML
    private Label lblElevator;
    @FXML
    private Text txfHDescription;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblTelephone;
    @FXML
    private Label lblRSurface;
    @FXML
    private Label lblBath;
    @FXML
    private Label lblBalcony;
    @FXML
    private Label lblConditioner;
    @FXML
    private Label lblTV;
    @FXML
    private Text txfRDescription;
    @FXML
    private Label lblAvailability;
    @FXML
    private Label lblHInfo;
    @FXML
    private Label lblDFrom;
    @FXML
    private Label lblUni;
    @FXML
    private Label lblHFeatures;
    @FXML
    private Label lblHomeType;
    @FXML
    private Label lblHomeSurf;
    @FXML
    private Label lblNRooms;
    @FXML
    private Label lblNBaths;
    @FXML
    private Label lblHFloor;
    @FXML
    private Label lblHLift;
    @FXML
    private Label lblContacts;
    @FXML
    private Label lblOwnerEmail;
    @FXML
    private Label lblOwnerTel;
    @FXML
    private Label lblHDescription;
    @FXML
    private Label lblRInfo;
    @FXML
    private Label lblRFeatures;
    @FXML
    private Label lblRoomSurf;
    @FXML
    private Label lblPrivateBath;
    @FXML
    private Label lblTVConnection;
    @FXML
    private Label lblBalc;
    @FXML
    private Label lblAirCond;
    @FXML
    private Label lblRDescription;
    @FXML
    private Label lblAvl;
    @FXML
    private Label lblMap;
    @FXML
    private Button btnRequest;
    @FXML
    private ImageView imvHome;
    @FXML
    private ImageView imvRoom;
    @FXML
    private ImageView imvMap;
    @FXML
    private Label lblAddress;

    public AdDetailsJavaFxController() {
        // Default constructor
    }

    /**
     * Initializes the ad details view with the ad information.
     * It sets the labels and text fields with the ad detail and the images of the home, room, and map.
     * It also sets the button text for going back and requesting the lease.
     *
     * @param context The context object for the ad details page.
     * @param adSearchPage The ad search page object.
     * @param adBean The ad bean object.
     * @author Marina Sotiropoulos
     */

    public void initialize(Context context, AdSearchPage adSearchPage, AdBean adBean) {
        this.context = context;
        this.adSearchPage = adSearchPage;

        btnBack.setText(properties.getProperty("GO_BACK_MSG"));

        lblTitle.setText(properties.getProperty("ROOM_TYPE_MSG") + adBean.adTitle() + properties.getProperty("MONTHLY_PRICE_MSG"));
        lblHInfo.setText(properties.getProperty("HOME_INFO_MSG"));
        lblDFrom.setText(properties.getProperty("DISTANCE_MSG"));
        lblDistance.setText((adBean.getDistance()) + " Km");
        lblUni.setText(properties.getProperty("UNIVERSITY_MSG"));
        lblUniversity.setText(adBean.getUniversity());

        lblHFeatures.setText(properties.getProperty("HOME_FEATURES_MSG"));
        setHomeDetails(adBean);

        lblRInfo.setText(properties.getProperty("ROOM_INFO_MSG"));
        lblRFeatures.setText(properties.getProperty("ROOM_FEATURES_MSG"));
        setRoomDetails(adBean);

        btnRequest.setText(properties.getProperty("LEASE_REQUEST_MSG"));
        lblMap.setText(properties.getProperty("MAP_MSG"));

        setImage(imvHome, adBean, "home");
        setImage(imvRoom, adBean, "room");

        setMap(adBean.getMap(), adBean.getHomeBean().getAddress());

        btnBack.setOnAction(event -> handleBack());
        btnRequest.setOnAction(event -> handleRequest(adBean));
    }

    /**
     * Sets the home data in the ad details view.
     * @param adBean The ad bean object.
     * @author Marina Sotiropoulos
     */
    private void setHomeDetails(AdBean adBean) {
        lblHomeType.setText(properties.getProperty("TYPE_MSG"));
        lblHType.setText(adBean.getHomeBean().getType());
        lblHomeSurf.setText(properties.getProperty("SURFACE_MSG"));
        lblHSurface.setText(String.valueOf(adBean.getHomeBean().getSurface()));
        lblNRooms.setText(properties.getProperty("NUM_ROOMS_MSG"));
        lblNumRooms.setText(String.valueOf(adBean.getHomeBean().getNRooms()));
        lblNBaths.setText(properties.getProperty("NUM_BATHROOMS_MSG"));
        lblNumBathrooms.setText(String.valueOf(adBean.getHomeBean().getNBathrooms()));
        lblHFloor.setText(properties.getProperty("FLOOR_MSG"));
        lblFloor.setText(String.valueOf(adBean.getHomeBean().getFloor()));
        lblHLift.setText(properties.getProperty("ELEVATOR_MSG"));
        lblElevator.setText(String.valueOf(adBean.getHomeBean().getElevator()));
        lblHDescription.setText(properties.getProperty("DESCRIPTION_MSG"));
        txfHDescription.setText(Utility.formatText(adBean.getHomeBean().getDescription()));
        lblContacts.setText(properties.getProperty("CONTACTS_MSG"));
        lblOwnerEmail.setText(properties.getProperty("EMAIL_MSG"));
        lblEmail.setText(adBean.getOwnerBean().getEmail());
        lblOwnerTel.setText(properties.getProperty("PHONE_N_MSG"));
        lblTelephone.setText(adBean.getOwnerBean().getPhoneNumber());
    }

    /**
     * Sets the room data in the ad details view.
     * @param adBean The ad bean object.
     * @author Marina Sotiropoulos
     */
    private void setRoomDetails(AdBean adBean) {
        lblRoomSurf.setText(properties.getProperty("SURFACE_MSG"));
        lblRSurface.setText(String.valueOf(adBean.getRoomBean().getSurface()));
        lblPrivateBath.setText(properties.getProperty("PRIVATE_BATH_MSG"));
        lblBath.setText(String.valueOf(adBean.getRoomBean().getBathroom()));
        lblBalcony.setText(properties.getProperty("BALCONY_MSG"));
        lblBalc.setText(String.valueOf(adBean.getRoomBean().getBalcony()));
        lblConditioner.setText(properties.getProperty("CONDITIONER_MSG"));
        lblAirCond.setText(String.valueOf(adBean.getRoomBean().getConditioner()));
        lblTVConnection.setText(properties.getProperty("TV_CONNECTION_MSG"));
        lblTV.setText(String.valueOf(adBean.getRoomBean().getTV()));
        lblRDescription.setText(properties.getProperty("DESCRIPTION_MSG"));
        txfRDescription.setText(Utility.formatText(adBean.getRoomBean().getDescription()));
        lblAvl.setText(properties.getProperty("AVAILABILITY_MSG"));
        lblAvailability.setText(String.valueOf(adBean.getAdStart()));
    }

    /**
     * Handles the user's request to send a lease request for the ad.
     * It displays the lease request form and handles the user's input.
     *
     * @param adBean The ad bean object.
     * @author Marina Sotiropoulos
     */

    public void handleRequest(AdBean adBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/leaseRequestForm-view.fxml"));
            Parent root = loader.load();

            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.setTitle(properties.getProperty("LEASE_REQUEST_FORM_MSG"));
            Scene scene = new Scene(root, 550, 500);
            popUpStage.setResizable(false);
            popUpStage.setScene(scene);

            LeaseRequestJavaFxController controller = loader.getController();
            controller.initialize(context, new AdSearchJavaFXPage(context), adBean, popUpStage);

            popUpStage.showAndWait();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Method to set the map of the home in the ad details view.
     * It sets the image of the map and the address in the ad details view.
     *
     * @param map The map image.
     * @param address The address of the home.
     * @author Marina Sotiropoulos
     */

    private void setMap(byte[] map, String address) {
        // Set the image of the map
        imvMap.setImage(new Image(new ByteArrayInputStream(map)));
        imvMap.setPreserveRatio(false);
        imvMap.setFitWidth(700);
        imvMap.setFitHeight(350);
        // Set the address into the map
        lblAddress.setText(address);
    }

    /**
     * Handles the user's request to go back to the ad search page.
     *
     * @author Marina Sotiropoulos
     */

    public void handleBack() {
        adSearchPage.goToTenantHomePage(new TenantHomeJavaFXPage(context));
        context.request();
    }
}
