package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.model.AdStart;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.javafx.ManageAdsJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.OwnerHomeJavaFXPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ManageAdsJavaFXPageController extends JavaFxController {

    private ManageAdsPage manageAdsPage;
    private final AdManager manager;
    private static final Logger LOGGER = Logger.getLogger(ManageAdsJavaFXPageController.class.getName());

    @FXML
    Button btnCreate;
    @FXML
    ListView<Node> lvAds;
    @FXML
    Button btnBack;
    @FXML
    Label lblTitle;
    @FXML
    Label lblHInfo;
    @FXML
    Label lblStreet;
    @FXML
    TextField txfStreet;
    @FXML
    Label lblNumStreet;
    @FXML
    TextField txfNumStreet;
    @FXML
    Label lblCity;
    @FXML
    TextField txfCity;
    @FXML
    Label lblHType;
    @FXML
    ChoiceBox<String> cbxHType;
    @FXML
    Label lblHSurface;
    @FXML
    TextField txfHSurface;
    @FXML
    Label lblNumBath;
    @FXML
    TextField txfNumBath;
    @FXML
    Label lblFloor;
    @FXML
    TextField txfFloor;
    @FXML
    CheckBox ckbElevator;
    @FXML
    Label lblHImage;
    @FXML
    Button btnHomeImage;
    @FXML
    VBox vbHome;
    @FXML
    Label lblHDescription;
    @FXML
    TextArea txaHDescription;
    @FXML
    Label lblRInfo;
    @FXML
    Label lblRType;
    @FXML
    ChoiceBox<String> cbxRType;
    @FXML
    Label lblRSurface;
    @FXML
    TextField txfRSurface;
    @FXML
    Label lblPrice;
    @FXML
    TextField txfPrice;
    @FXML
    Label lblMonth;
    @FXML
    ChoiceBox<String> cbxMonth;
    @FXML
    Label lblServices;
    @FXML
    CheckBox ckbBath;
    @FXML
    CheckBox ckbBalcony;
    @FXML
    CheckBox ckbConditioner;
    @FXML
    CheckBox ckbTV;
    @FXML
    Label lblRDescription;
    @FXML
    TextArea txaRDescription;
    @FXML
    Label lblRImage;
    @FXML
    Button btnRoomImage;
    @FXML
    VBox vbRoom;
    @FXML
    Button btnPublish;

    @FXML
    ImageView imvHome;
    @FXML
    ImageView imvRoom;

    byte[] homeBytes;
    byte[] roomBytes;

    File homeFile;
    File roomFile;

    String homeType;
    String roomType;
    int month;
    int numRooms;

    public ManageAdsJavaFXPageController() {
        this.manager = new AdManager();
    }

    public void initialize(Context context) {
        this.context = context;

        // Retrieve ads
        List<AdBean> adBeans = retrieveAds(context.getSessionBean());

        if (adBeans.isEmpty()) {
            Label noAds = new Label("No ads found. Create an ad to start renting!");
            lvAds.getItems().add(noAds);
        }

        for (AdBean adBean : adBeans) {
            // Create a new list view item
            VBox vbItem = createPublishedAdCard(adBean);
            lvAds.getItems().add(vbItem);
        }

        btnCreate.setOnAction(event -> handleCreateAd());
    }

    public void initializeCreateAd(Context context, ManageAdsPage manageAdsPage) {
        this.context = context;
        this.manageAdsPage = manageAdsPage;

        // Options for home type choice box
        cbxHType.getItems().addAll("Studio apartment", "Two-room apartment", "Three-room apartment", "Four-room apartment");
        // Options for room type choice box
        cbxRType.getItems().addAll("Single", "Double");
        // Options for month choice box
        cbxMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December");

        // Listener for home type choice box
        cbxHType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            homeType = newValue;
            numRooms = cbxHType.getItems().indexOf(newValue) + 1;
        });

        // Listener for room type choice box
        cbxRType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            roomType = newValue;
            numRooms = cbxRType.getItems().indexOf(newValue) + 1;
        });

        // Listener for moth choice box
        cbxMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> month = cbxMonth.getItems().indexOf(newValue) + 1);

        btnBack.setOnAction(event -> handleBack());
        btnHomeImage.setOnAction(event -> handleChooseHomeImage());
        btnRoomImage.setOnAction(event -> handleChooseRoomImage());
        btnPublish.setOnAction(event -> handlePublish());
    }

    private VBox createPublishedAdCard(AdBean adBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/publishedAd-card.fxml"));
            VBox vbox = loader.load();

            PreviewAdJavaFxController controller = loader.getController();
            controller.setAdBean(adBean);
            controller.initializePublishedAds();

            return vbox;
        } catch (Exception e) {
            LOGGER.severe("Error while creating ad card");
        }
        return null;
    }

    private void handleCreateAd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/createAdForm-view.fxml"));
            context.getTab(0).setContent(loader.load()); // Caricamento del tab createAdForm (possibili modifiche)
            ManageAdsJavaFXPageController controller = loader.getController();
            controller.initializeCreateAd(context, new ManageAdsJavaFXPage(context));

        } catch (Exception e) {
            LOGGER.severe("Error while handling CreateAd");
        }
    }

    private void handleChooseHomeImage() {
        homeFile = openFileChooser();
        if (homeFile != null && homeFile.exists()) {
            try {
                homeBytes = Files.readAllBytes(homeFile.toPath());

                // Convert bytes to image
                Image image = byteToImage(homeBytes);

                // Set image view
                imvHome = new ImageView(image);
                setImageView(imvHome);

                Button deleteButton = createDeleteButton();
                StackPane stackPane = createStackPane(imvHome, deleteButton);

                vbHome.getChildren().clear();
                vbHome.getChildren().add(stackPane);

                deleteButton.setOnAction(event -> deleteImage(vbHome, stackPane));

            } catch (IOException e) {
                LOGGER.severe("Error while reading bytes from file");
            }
        } else {
            LOGGER.severe("Error while opening file chooser");
        }
    }

    private void handleChooseRoomImage() {
        roomFile = openFileChooser();
        if (roomFile != null && roomFile.exists()) {
            try {
                roomBytes = Files.readAllBytes(roomFile.toPath());

                // Convert bytes to image
                Image image = byteToImage(roomBytes);

                // Set image view
                imvRoom = new ImageView(image);
                setImageView(imvRoom);

                Button deleteButton = createDeleteButton();
                StackPane stackPane = createStackPane(imvRoom, deleteButton);

                vbRoom.getChildren().clear();
                vbRoom.getChildren().add(stackPane);

                deleteButton.setOnAction(event -> deleteImage(vbRoom, stackPane));

            } catch (IOException e) {
                LOGGER.severe("Error while reading bytes from file");
            }
        } else {
            LOGGER.severe("Error while opening file chooser");
        }
    }

    private void handlePublish() {

        //Check if all fields are filled
        if (!checkFields()) {
            showAlert(ERROR, String.valueOf(Alert.AlertType.ERROR), "EMPTY_FIELDS_MSG");
            return;
        }

        // Check if images are chosen
        if (imvHome.getImage() == null || imvRoom.getImage() == null) {
            showAlert(ERROR, String.valueOf(Alert.AlertType.ERROR), "Please select images");
            return;
        }

        // Retrieve data from the form
        String street = txfStreet.getText();
        String numStreet = txfNumStreet.getText();
        String city = txfCity.getText();
        int hSurface = Integer.parseInt(txfHSurface.getText());
        int numBath = Integer.parseInt(txfNumBath.getText());
        int floor = Integer.parseInt(txfFloor.getText());
        boolean elevator = ckbElevator.isSelected();
        String hDescription = txaHDescription.getText();
        int rSurface = Integer.parseInt(txfRSurface.getText());
        int price = Integer.parseInt(txfPrice.getText());
        String rDescription = txaRDescription.getText();
        boolean privateBath = ckbBath.isSelected();
        boolean balcony = ckbBalcony.isSelected();
        boolean conditioner = ckbConditioner.isSelected();
        boolean tvConnection = ckbTV.isSelected();

        //Save filename
        String homeFileName = homeFile.getName();
        String roomFileName = roomFile.getName();

        // Create beans
        String address = street + ", " + numStreet + ", " + city;
        int lift = elevator ? 1 : 0;
        Integer[] features = new Integer[]{numRooms, numBath, floor, lift};
        HomeBean homeBean = new HomeBean(address, homeType, hSurface, features, hDescription);

        // Save home image
        homeBean.setImage(homeBytes);
        homeBean.setImageName(homeFileName);

        boolean[] services = new boolean[]{privateBath, balcony, conditioner, tvConnection};
        RoomBean roomBean = new RoomBean(roomType, rSurface, services, rDescription);

        // Save room image
        roomBean.setImage(roomBytes);
        roomBean.setImageName(roomFileName);

        // Publish ad
        boolean res = manager.publishAd(context.getSessionBean(),homeBean, roomBean, price, AdStart.fromInt(month));

        if (res) {
            showAlert(INFORMATION, String.valueOf(Alert.AlertType.INFORMATION), "Ad published successfully");
            handleBack();
        } else {
            showAlert(ERROR, String.valueOf(Alert.AlertType.ERROR), "Error while publishing ad. Please try again.");
        }
    }

    private void handleBack() {
        manageAdsPage.goToOwnerHomePage(new OwnerHomeJavaFXPage(context));
        context.request();
    }

    private List<AdBean> retrieveAds(SessionBean sessionBean) {
        try {
            return manager.getDecoratedAdsByOwner(sessionBean, new AdBean(null));
        } catch (Exception e) {
            LOGGER.severe("Error while retrieving ads");
            return Collections.emptyList();
        }
    }

    private Button createDeleteButton() {
        Button deleteButton = new Button();
        URL imageUrl = getClass().getResource("/it/hivecampuscompany/hivecampus/images/delete.png");

        if (imageUrl == null) {
            LOGGER.severe("Errore: L'immagine non è stata trovata.");
        } else {
            Image deleteImage = new Image(imageUrl.toExternalForm());
            ImageView deleteImageView = new ImageView(deleteImage);
            deleteImageView.setFitWidth(20);
            deleteImageView.setPreserveRatio(true);
            deleteButton.setGraphic(deleteImageView);
            deleteButton.setStyle("-fx-background-color: transparent;");
        }
        return deleteButton;
    }

    private StackPane createStackPane(ImageView imageView, Button deleteButton) {
        StackPane stackPane = new StackPane();
        StackPane.setAlignment(deleteButton, Pos.TOP_RIGHT);
        StackPane.setMargin(deleteButton, new Insets(5, 5, 0, 0));
        stackPane.getChildren().addAll(imageView, deleteButton);
        return stackPane;
    }

    private void setImageView(ImageView imageView) {
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(false);
    }

    private void deleteImage(VBox vbox, StackPane stackPane) {
        vbox.getChildren().remove(stackPane);
    }

    private File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        return fileChooser.showOpenDialog(context.getStage());
    }

    private Image byteToImage(byte[] bytes) throws IOException {
       // Convert byte array to ByteArrayInputStream
       ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
       // Create Image from ByteArrayInputStream
       Image image = new Image(bis);
       bis.close(); // Close the stream
       return image;
    }

    private boolean checkFields() {
        return  !txfStreet.getText().isEmpty() && !txfNumStreet.getText().isEmpty() && !txfCity.getText().isEmpty() &&
                !txfHSurface.getText().isEmpty() && !txfNumBath.getText().isEmpty() && !txfFloor.getText().isEmpty() &&
                !txaHDescription.getText().isEmpty() && !txfRSurface.getText().isEmpty() && !txfPrice.getText().isEmpty() &&
                !txaRDescription.getText().isEmpty();
    }
}