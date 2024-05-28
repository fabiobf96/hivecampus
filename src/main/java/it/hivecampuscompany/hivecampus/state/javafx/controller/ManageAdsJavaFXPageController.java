package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.model.Month;
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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ManageAdsJavaFXPageController class represents a controller for the manage ads page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the manage ads view and handling user interactions.
 */

public class ManageAdsJavaFXPageController extends JavaFxController {

    private ManageAdsPage manageAdsPage;
    private final AdManager manager;
    private static final Logger LOGGER = Logger.getLogger(ManageAdsJavaFXPageController.class.getName());

    @FXML
    Button btnCreate;
    @FXML
    Label lblCreate;
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
    ChoiceBox<String> cbxHType = new ChoiceBox<>();
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
    ChoiceBox<String> cbxRType = new ChoiceBox<>();
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
    ChoiceBox<String> cbxMonth = new ChoiceBox<>();
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

    private HomeBean hBean;

    public ManageAdsJavaFXPageController() {
        this.manager = new AdManager();
    }

    /**
     * Initializes the manage ads view with the list of published ads.
     * It retrieves the ads from the database and displays them in the list view.
     *
     * @param context The context object for the manage ads page.
     * @author Marina Sotiropoulos
     */

    public void initialize(Context context) {
        this.context = context;
        lblCreate.setText(properties.getProperty("CREATE_AD_MSG"));

        List<AdBean> adBeans = retrieveAds(context.getSessionBean());

        if (adBeans.isEmpty()) {
            Label noAds = new Label(properties.getProperty("NO_ADS_CREATED_MSG"));
            lvAds.getItems().add(noAds);
        }

        for (AdBean adBean : adBeans) {
            VBox vbItem = createPublishedAdCard(adBean);
            lvAds.getItems().add(vbItem);
        }

        btnCreate.setOnAction(event -> handleCreateAd(context, null));
    }

    /**
     * Initializes the create ad form with the fields for the home and room information.
     * It sets the choice boxes for the home type, room type, and month.
     * It also sets the buttons for choosing the home and room images and publishing the ad.
     *
     * @param context The context object for the create ad form.
     * @param manageAdsPage The manage ads page object.
     * @author Marina Sotiropoulos
     */

    public void initializeCreateAd(Context context, ManageAdsPage manageAdsPage) {
        this.context = context;
        this.manageAdsPage = manageAdsPage;

        btnBack.setText(properties.getProperty("GO_BACK_MSG"));
        lblTitle.setText(properties.getProperty("CREATE_AD_FORM_MSG").toUpperCase());

        lblHInfo.setText(properties.getProperty("HOME_INFO_MSG"));
        lblStreet.setText(properties.getProperty("STREET_FIELD_REQUEST_MSG"));
        lblNumStreet.setText(properties.getProperty("STREET_NUMBER_FIELD_REQUEST_MSG"));
        lblCity.setText(properties.getProperty("CITY_FIELD_REQUEST_MSG"));
        lblHType.setText(properties.getProperty("TYPE_FIELD_REQUEST_MSG"));
        lblHSurface.setText(properties.getProperty("SURFACE_FIELD_REQUEST_MSG"));
        lblNumBath.setText(properties.getProperty("NUM_BATH_FIELD_REQUEST_MSG"));
        lblFloor.setText(properties.getProperty("FLOOR_FIELD_REQUEST_MSG"));
        ckbElevator.setText(properties.getProperty("LIFT_MSG"));
        lblHImage.setText(properties.getProperty("IMAGE_MSG"));
        lblHDescription.setText(properties.getProperty("DESCRIPTION_MSG"));
        txaHDescription.setPromptText(properties.getProperty("HOME_DESCRIPTION_PROMPT_MSG"));

        lblRInfo.setText(properties.getProperty("ROOM_INFO_MSG"));
        lblRType.setText(properties.getProperty("TYPE_FIELD_REQUEST_MSG"));
        lblRSurface.setText(properties.getProperty("SURFACE_FIELD_REQUEST_MSG"));
        lblPrice.setText(properties.getProperty("PRICE_FIELD_REQUEST_MSG"));
        lblMonth.setText(properties.getProperty("MONTH_AVAILABLE_FIELD_REQUEST_MSG"));
        lblServices.setText(properties.getProperty("SERVICES_MSG"));
        lblRDescription.setText(properties.getProperty("DESCRIPTION_MSG"));
        txaRDescription.setPromptText(properties.getProperty("ROOM_DESCRIPTION_PROMPT_MSG"));
        lblRImage.setText(properties.getProperty("IMAGE_MSG"));

        ckbBath.setText(properties.getProperty("PRIVATE_BATH_MSG"));
        ckbBalcony.setText(properties.getProperty("BALCONY_MSG"));
        ckbConditioner.setText(properties.getProperty("CONDITIONER_MSG"));
        ckbTV.setText(properties.getProperty("TV_CONNECTION_MSG"));

        btnPublish.setText(properties.getProperty("PUBLISH_AD_MSG"));

        cbxHType.getItems().addAll(
                properties.getProperty("STUDIO_APARTMENT_MSG"),
                properties.getProperty("TWO_BEDROOM_APARTMENT_MSG"),
                properties.getProperty("THREE_BEDROOM_APARTMENT_MSG"),
                properties.getProperty("FOUR_BEDROOM_APARTMENT_MSG")
        );

        cbxRType.getItems().addAll(
                properties.getProperty("SINGLE_ROOM_MSG"),
                properties.getProperty("DOUBLE_ROOM_MSG")
        );

        cbxMonth.getItems().addAll(
                properties.getProperty("JANUARY_MSG"),
                properties.getProperty("FEBRUARY_MSG"),
                properties.getProperty("MARCH_MSG"),
                properties.getProperty("APRIL_MSG"),
                properties.getProperty("MAY_MSG"),
                properties.getProperty("JUNE_MSG"),
                properties.getProperty("JULY_MSG"),
                properties.getProperty("AUGUST_MSG"),
                properties.getProperty("SEPTEMBER_MSG"),
                properties.getProperty("OCTOBER_MSG"),
                properties.getProperty("NOVEMBER_MSG"),
                properties.getProperty("DECEMBER_MSG")
        );

        // Listener for home type choice box
        cbxHType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            homeType = newValue;
            numRooms = cbxHType.getItems().indexOf(newValue) + 1;
        });

        // Listener for room type choice box
        cbxRType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> roomType = newValue);

        // Listener for moth choice box
        cbxMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> month = cbxMonth.getItems().indexOf(newValue) + 1);

        btnBack.setOnAction(event -> handleBack());
        btnHomeImage.setOnAction(event -> handleChooseHomeImage());
        btnRoomImage.setOnAction(event -> handleChooseRoomImage());
        btnPublish.setOnAction(event -> handlePublish());
    }

    /**
     * Sets the home information in the form fields.
     * It sets the home information in the text fields and choice boxes.
     * It also sets the home image in the image view.
     *
     * @param homeBean The home bean object with the home information.
     * @author Marina Sotiropoulos
     */

    public void setHomeInfo(HomeBean homeBean) {

        this.hBean = homeBean;

        txfStreet.setText(homeBean.getStreet());
        txfStreet.setEditable(false); // Make the text field non-editable

        txfNumStreet.setText(homeBean.getStreetNumber());
        txfNumStreet.setEditable(false);

        txfCity.setText(homeBean.getCity());
        txfCity.setEditable(false);

        cbxHType.setValue(cbxHType.getItems().get(homeBean.getNRooms()-1));
        cbxHType.setDisable(true); // Disable ChoiceBox

        txfHSurface.setText(String.valueOf(homeBean.getSurface()));
        txfHSurface.setEditable(false);

        txfNumBath.setText(String.valueOf(homeBean.getNRooms()));
        txfNumBath.setEditable(false);

        txfFloor.setText(String.valueOf(homeBean.getFloor()));
        txfFloor.setEditable(false);

        ckbElevator.setSelected(homeBean.getElevator() == 1);
        ckbElevator.setDisable(true);

        txaHDescription.setText(homeBean.getDescription());
        txaHDescription.setEditable(false); // Make text area non-editable

        try {
            imvHome.setImage(byteToImage(homeBean.getImage()));
            setImageView(imvHome);
            btnHomeImage.setDisable(true); // Disable button
        } catch (IOException e) {
            showAlert(ERROR, ERROR_TITLE_MSG, properties.getProperty("FAILED_READ_IMAGE"));
        }

        // Change styles to indicate non-editable state
        cbxHType.setStyle("-fx-opacity: 1.0; -fx-text-fill: black;");
        ckbElevator.setStyle("-fx-opacity: 1.0; -fx-text-fill: black;");
    }

    /**
     * Creates a card for the published ad with the ad information.
     * It loads the publishedAd-card.fxml file and sets the ad information in the controller.
     *
     * @param adBean The ad bean object with the ad information.
     * @return The VBox object with the ad card.
     * @author Marina Sotiropoulos
     */

    private VBox createPublishedAdCard(AdBean adBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/publishedAd-card.fxml"));
            VBox vbox = loader.load();

            PreviewAdJavaFxController controller = loader.getController();
            controller.setAdBean(adBean);
            controller.initializePublishedAds(context);

            return vbox;
        } catch (IOException e) {
            LOGGER.severe(properties.getProperty("ERROR_CREATING_AD_CARD"));
        }
        return null;
    }

    /**
     * Handles the create ad event. It loads the create ad form in a new tab
     * and sets the controller with the context and manage ads page.
     *
     * @author Marina Sotiropoulos
     */

    public void handleCreateAd(Context context, HomeBean homeBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/createAdForm-view.fxml"));
            context.getTab(0).setContent(loader.load());
            ManageAdsJavaFXPageController controller = loader.getController();
            controller.initializeCreateAd(context, new ManageAdsJavaFXPage(context));

            if (homeBean != null) {
                controller.setHomeInfo(homeBean);
            }

        } catch (Exception e) {
            LOGGER.severe(properties.getProperty("ERROR_CREATING_AD_CARD"));
        }
    }

    /**
     * Handles the choice of the home image.
     * It opens a file chooser dialog to select the home image.
     * It reads the bytes from the file and sets the image in the image view.
     *
     * @author Marina Sotiropoulos
     */

    private void handleChooseHomeImage() {
        homeFile = openFileChooser();
        if (homeFile != null && homeFile.exists()) {
            try {
                homeBytes = Files.readAllBytes(homeFile.toPath());

                Image image = byteToImage(homeBytes);

                imvHome = new ImageView(image);
                setImageView(imvHome);

                Button deleteButton = createDeleteButton();
                StackPane stackPane = createStackPane(imvHome, deleteButton);

                vbHome.getChildren().clear();
                vbHome.getChildren().add(stackPane);

                deleteButton.setOnAction(event -> deleteImage(vbHome, stackPane));

            } catch (IOException e) {
                LOGGER.severe(properties.getProperty("FAILED_READ_PARSE_VALUES"));
            }
        } else {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.severe(properties.getProperty("FAILED_OPENING_FILE_CHOOSER"));
            }
        }
    }

    /**
     * Handles the choice of the room image.
     * It opens a file chooser dialog to select the room image.
     * It reads the bytes from the file and sets the image in the image view.
     *
     * @author Marina Sotiropoulos
     */

    private void handleChooseRoomImage() {
        roomFile = openFileChooser();
        if (roomFile != null && roomFile.exists()) {
            try {
                roomBytes = Files.readAllBytes(roomFile.toPath());

                Image image = byteToImage(roomBytes);

                imvRoom = new ImageView(image);
                setImageView(imvRoom);

                Button deleteButton = createDeleteButton();
                StackPane stackPane = createStackPane(imvRoom, deleteButton);

                vbRoom.getChildren().clear();
                vbRoom.getChildren().add(stackPane);

                deleteButton.setOnAction(event -> deleteImage(vbRoom, stackPane));

            } catch (IOException e) {
                LOGGER.severe(properties.getProperty("FAILED_READ_PARSE_VALUES"));
            }
        } else {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.severe(properties.getProperty("FAILED_OPENING_FILE_CHOOSER"));
            }
        }
    }

    /**
     * Handles the publish ad event.
     * It retrieves the information from the form fields and creates the home and room beans.
     * It saves the images and filenames in the beans and publishes the ad.
     * If the ad is published successfully, it shows a success message and goes back to the owner home page.
     *
     * @author Marina Sotiropoulos
     */

    private void handlePublish() {
        HomeBean homeBean;

        //Check if all fields are filled
        if (!checkFields()) {
            showAlert(ERROR, String.valueOf(Alert.AlertType.ERROR), properties.getProperty("EMPTY_FIELDS_MSG"));
            return;
        }

        // Check if images are chosen
        if (imvHome.getImage() == null || imvRoom.getImage() == null) {
            showAlert(ERROR, String.valueOf(Alert.AlertType.ERROR), properties.getProperty("PLEASE_SELECT_IMAGE_MSG"));
            return;
        }

        // Retrieve data from the form and create home bean
        homeBean = Objects.requireNonNullElseGet(hBean, this::getHomeDataForm);

        // Retrieve data from the form and create room bean
        RoomBean roomBean = getRoomDataForm();

        int price = Integer.parseInt(txfPrice.getText());

        // Publish ad
        boolean res = manager.publishAd(context.getSessionBean(),homeBean, roomBean, price, Month.fromInt(month));

        if (res) {
            showAlert(INFORMATION, String.valueOf(Alert.AlertType.INFORMATION), properties.getProperty("AD_PUBLISHED_MSG"));
            handleBack();
        } else {
            showAlert(ERROR, String.valueOf(Alert.AlertType.ERROR), properties.getProperty("FAILED_PUBLISH_AD"));
        }
    }

    /**
     * Retrieves the home information from the form fields.
     * It retrieves the home information from the text fields and check boxes.
     * It creates a home bean with the home information and returns the home bean.
     *
     * @return The home bean object with the home information.
     * @author Marina Sotiropoulos
     */

    private HomeBean getHomeDataForm() {
        String street = txfStreet.getText();
        String numStreet = txfNumStreet.getText();
        String city = txfCity.getText();
        int hSurface = Integer.parseInt(txfHSurface.getText());
        int numBath = Integer.parseInt(txfNumBath.getText());
        int floor = Integer.parseInt(txfFloor.getText());
        boolean elevator = ckbElevator.isSelected();
        String hDescription = txaHDescription.getText();

        String homeFileName = homeFile.getName();

        String address = street + ", " + numStreet + ", " + city;
        int lift = elevator ? 1 : 0;
        Integer[] features = new Integer[]{numRooms, numBath, floor, lift};
        HomeBean homeBean = new HomeBean(address, homeType, hSurface, features, hDescription);

        homeBean.setImage(homeBytes);
        homeBean.setImageName(homeFileName);

        return homeBean;
    }

    /**
     * Retrieves the room information from the form fields.
     * It retrieves the room information from the text fields and check boxes.
     * It creates a room bean with the room information and returns the room bean.
     *
     * @return The room bean object with the room information.
     * @author Marina Sotiropoulos
     */

    private RoomBean getRoomDataForm() {
        int rSurface = Integer.parseInt(txfRSurface.getText());
        String rDescription = txaRDescription.getText();
        boolean privateBath = ckbBath.isSelected();
        boolean balcony = ckbBalcony.isSelected();
        boolean conditioner = ckbConditioner.isSelected();
        boolean tvConnection = ckbTV.isSelected();

        String roomFileName = roomFile.getName();

        boolean[] services = new boolean[]{privateBath, balcony, conditioner, tvConnection};
        RoomBean roomBean = new RoomBean(roomType, rSurface, services, rDescription);

        roomBean.setImage(roomBytes);
        roomBean.setImageName(roomFileName);

        return roomBean;
    }

    /**
     * Handles the back event. It goes back to the owner home page.
     *
     * @author Marina Sotiropoulos
     */

    private void handleBack() {
        manageAdsPage.goToOwnerHomePage(new OwnerHomeJavaFXPage(context));
        context.request();
    }

    /**
     * Retrieves the ads from the database.
     * It retrieves the ads by the owner and returns the list of ads.
     *
     * @param sessionBean The session bean object for the user.
     * @return List of ads by the owner.
     * @author Marina Sotiropoulos
     */

    private List<AdBean> retrieveAds(SessionBean sessionBean) {
        try {
            return manager.getDecoratedAdsByOwner(sessionBean, new AdBean(null));
        } catch (Exception e) {
            LOGGER.severe(properties.getProperty("ERROR_RETRIEVING_ADS"));
            return Collections.emptyList();
        }
    }

    /**
     * Creates a delete button for the image.
     * It sets the image of the delete button and the style.
     *
     * @return The Button object representing the delete button.
     * @author Marina Sotiropoulos
     */

    private Button createDeleteButton() {
        Button deleteButton = new Button();
        URL imageUrl = getClass().getResource("/it/hivecampuscompany/hivecampus/images/delete.png");

        if (imageUrl == null) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.severe(properties.getProperty("FAILED_IMAGE_FOUND"));
            }
        }
        else {
            Image deleteImage = new Image(imageUrl.toExternalForm());
            ImageView deleteImageView = new ImageView(deleteImage);
            deleteImageView.setFitWidth(20);
            deleteImageView.setPreserveRatio(true);
            deleteButton.setGraphic(deleteImageView);
            deleteButton.setStyle("-fx-background-color: transparent;");
        }
        return deleteButton;
    }

    /**
     * Creates a stack pane with the image view and the delete button.
     * It sets the alignment of the delete button in the stack pane.
     *
     * @param imageView The image view object representing the image.
     * @param deleteButton The delete button object.
     * @return The StackPane object with the image view and delete button.
     * @author Marina Sotiropoulos
     */

    private StackPane createStackPane(ImageView imageView, Button deleteButton) {
        StackPane stackPane = new StackPane();
        StackPane.setAlignment(deleteButton, Pos.TOP_RIGHT);
        StackPane.setMargin(deleteButton, new Insets(5, 5, 0, 0));
        stackPane.getChildren().addAll(imageView, deleteButton);
        return stackPane;
    }

    /**
     * Sets the image view with the image.
     * It sets the fit width and height of the image view.
     *
     * @param imageView The image view object to set the image.
     * @author Marina Sotiropoulos
     */

    private void setImageView(ImageView imageView) {
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(false);
    }

    /**
     * Deletes the image from the view.
     * It removes the stack pane with the image view and delete button from the view.
     *
     * @param vbox The VBox object representing the container of the image.
     * @param stackPane The StackPane object with the image view and delete button.
     * @author Marina Sotiropoulos
     */

    private void deleteImage(VBox vbox, StackPane stackPane) {
        vbox.getChildren().remove(stackPane);
    }

    /**
     * Method to open a file chooser dialog to select an image.
     * It sets the title and extension filters for the file chooser.
     *
     * @return The File object representing the selected image file.
     * @author Marina Sotiropoulos
     */

    private File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(properties.getProperty("SELECT_IMAGE_MSG"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        return fileChooser.showOpenDialog(context.getStage());
    }

    /**
     * Converts a byte array to an image.
     * It creates a ByteArrayInputStream from the byte array and creates an image from the ByteArrayInputStream.
     *
     * @param bytes The byte array representing the image.
     * @return The Image object created from the byte array.
     * @throws IOException If an I/O error occurs.
     *
     * @author Marina Sotiropoulos
     */

    private Image byteToImage(byte[] bytes) throws IOException {
       // Convert byte array to ByteArrayInputStream
       ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
       // Create Image from ByteArrayInputStream
       Image image = new Image(bis);
       bis.close(); // Close the stream
       return image;
    }

    /**
     * Checks if all the fields are filled in the form.
     * It checks if all the text fields and text areas are filled.
     *
     * @return True if all fields are filled, false otherwise.
     * @author Marina Sotiropoulos
     */

    private boolean checkFields() {
        return  !txfStreet.getText().isEmpty() && !txfNumStreet.getText().isEmpty() && !txfCity.getText().isEmpty() &&
                !txfHSurface.getText().isEmpty() && !txfNumBath.getText().isEmpty() && !txfFloor.getText().isEmpty() &&
                !txaHDescription.getText().isEmpty() && !txfRSurface.getText().isEmpty() && !txfPrice.getText().isEmpty() &&
                !txaRDescription.getText().isEmpty();
    }
}