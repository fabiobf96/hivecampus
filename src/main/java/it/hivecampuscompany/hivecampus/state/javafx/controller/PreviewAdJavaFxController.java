package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.state.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * The PreviewAdJavaFxController represents the controller for the preview ad page in the JavaFX user interface.
 * It is responsible for previewing the ad.
 */

public class PreviewAdJavaFxController extends JavaFxController {

    private final AdManager manager;

    @FXML
    private ImageView imvRoom;
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

    @FXML
    Button btnEdit;
    @FXML
    Button btnRoom;
    @FXML
    Button btnDelete;
    @FXML
    Label lblAdStatus;
    @FXML
    Label lblStatus;
    @FXML
    Label lblRoomType;
    @FXML
    Label lblMonthAvl;
    @FXML
    Label lblRPrice;
    @FXML
    Label lblType;
    @FXML
    Label lblMonth;
    @FXML
    Label lblPrice;

    private AdBean adBean;

    public PreviewAdJavaFxController() {
        manager = new AdManager();
    }

    public void setAdBean(AdBean bean){
        this.adBean = bean;
    }

    /**
     * Initializes the preview features of the ad.
     * It sets the text fields with the information of the ad.
     *
     * @author Marina Sotiropoulos
     */


    public void initializePreviewFeatures() {

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

        setImage(imvRoom, adBean, "room");
    }

    /**
     * Initializes the preview distance of the ad.
     * It sets the text fields with the information of the ad
     * by calling the setLabelText method.
     *
     * @author Marina Sotiropoulos
     */

    public void initializePreviewDistance() {

        setLabelText(lblDist, properties.getProperty("DISTANCE_MSG"));
        setLabelText(lblDistUni, properties.getProperty("UNIVERSITY_MSG"));
        setLabelText(lblAvl, properties.getProperty("AVAILABILITY_MSG"));
        setLabelText(lblUniversity, adBean.getUniversity());
        setLabelText(lblDistance, (adBean.getDistance()) + " km");
        setLabelText(lblAvailability, String.valueOf(adBean.getAdStart()));
    }

    /**
     * Initializes the published ads.
     * It sets the text fields with the information of the ad.
     * It also sets the buttons for editing and deleting the ad.
     *
     * @author Marina Sotiropoulos
     */

    public void initializePublishedAds(Context context) {
        this.context = context;
        setLabelText(lblTitle,adBean.getHomeBean().getAddress());
        setLabelText(lblAdStatus, properties.getProperty("AD_STATUS_MSG"));
        setLabelText(lblRoomType, properties.getProperty("TYPE_MSG"));
        setLabelText(lblMonthAvl, properties.getProperty("AVAILABILITY_MSG"));
        setLabelText(lblRPrice, properties.getProperty("PRICE_MSG"));
        setLabelText(lblStatus, String.valueOf(adBean.getAdStatus()));
        setLabelText(lblType, adBean.getRoomBean().getType());
        setLabelText(lblMonth, String.valueOf(adBean.getAdStart()));
        setLabelText(lblPrice, (adBean.getPrice()) + " €");

        setImage(imvRoom, adBean, "room");

        btnEdit.setOnAction(event -> handleEditAd());

        btnRoom.setOnAction(event -> {
            try {
                handleAddRoom(adBean.getHomeBean());
            } catch (InvalidSessionException e) {
                showAlert(ERROR, ERROR_TITLE_MSG, properties.getProperty("INVALID_SESSION_MSG"));
            }
        });

        btnDelete.setOnAction(event -> handleDeleteAd());
    }

    /**
     * Handles the edit ad.
     * It shows an alert with the message "Edit ad" and "Not implemented".
     *
     * @author Marina Sotiropoulos
     */

    private void handleEditAd() {
        showAlert(INFORMATION, properties.getProperty("EDIT_AD_MSG"), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    /**
     * Handles the add room.
     * It checks if the maximum number of rooms is reached.
     * If it is reached, it shows an alert with the message "Warning" and "Maximum rooms reached".
     * Otherwise, it calls the initializeCreateAd method from the ManageAdsJavaFXPageController class.
     *
     * @author Marina Sotiropoulos
     */

    private void handleAddRoom(HomeBean homeBean) throws InvalidSessionException {
        List<HomeBean> homeBeans = manager.getHomesByOwner(context.getSessionBean());
        HomeDAO homeDAO = new HomeDAOCSV();
        for(HomeBean home : homeBeans){
            if(home.getId() == homeBean.getId()){
                homeBean.setType(home.getType());
                homeBean.setSurface(home.getSurface());
                homeBean.setFeatures(new Integer[] {home.getNRooms(), home.getNBathrooms(), home.getFloor(), home.getElevator()});
                homeBean.setDescription(home.getDescription());
                homeBean.setHomeImage(homeDAO.getHomeImage(homeBean.getId()));
                break; // Exit the loop once a match is found
            }
        }

        if (manager.isMaxRoomsReached(homeBean)){
            showAlert(WARNING, properties.getProperty(WARNING_TITLE_MSG), properties.getProperty("MAX_ROOMS_REACHED_MSG"));
        }
        else {
            ManageAdsJavaFXPageController controller = new ManageAdsJavaFXPageController();
            controller.handleCreateAd(context, homeBean);
        }
    }

    /**
     * Handles the delete ad.
     * It shows an alert with the message "Delete ad" and "Not implemented".
     *
     * @author Marina Sotiropoulos
     */

    private void handleDeleteAd() {
        showAlert(INFORMATION, properties.getProperty("DELETE_AD_MSG"), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }
}
