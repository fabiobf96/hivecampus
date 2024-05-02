package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.manager.AdSearchManager;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.AdSearchJavaFXPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class PreviewAdJavaFxController extends JavaFxController {

    private static final Logger LOGGER = Logger.getLogger(PreviewAdJavaFxController.class.getName());
    private AdSearchManager adSearchManager;

    @FXML
    private VBox vbPreview;
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
        // Default constructor
    }

    public void setAdBean(AdBean bean){
        this.adBean = bean;
    }


    public void initializePreviewFeatures(Context context) {
        this.context = context;
        this.adSearchManager = new AdSearchManager();

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

        setImage(imvRoom, adBean, "room");

        vbPreview.setOnMouseClicked(event -> handlePreviewAd());
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

    public void initializePublishedAds() {
        // Imposta il colore a nero e il testo per tutte le label
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
        btnDelete.setOnAction(event -> handleDeleteAd());
    }

    private void handlePreviewAd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/adDetails-view.fxml"));
            context.getTab(0).setContent(loader.load()); // Caricamento del tab per la visualizzazione dei dettagli dell'annuncio

            adSearchManager.getHomeMap(context.getSessionBean(), adBean);

            AdDetailsJavaFxController controller = loader.getController();
            controller.initialize(context, new AdSearchJavaFXPage(context), adBean);
        } catch (Exception e) {
            LOGGER.severe("Error while loading ad details page: " + e.getMessage());
        }
    }

    private void handleEditAd() {
        showAlert("INFORMATION", properties.getProperty("EDIT_AD_MSG"), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    private void handleDeleteAd() {
        showAlert("INFORMATION", properties.getProperty("DELETE_AD_MSG"), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }
}
