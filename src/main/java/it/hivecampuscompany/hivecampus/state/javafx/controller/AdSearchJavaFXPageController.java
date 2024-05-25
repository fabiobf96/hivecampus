package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.AdSearchJavaFXPage;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.PreviewRoomDecorator;
import it.hivecampuscompany.hivecampus.view.utility.CustomListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * The AdSearchJavaFXPageController class represents a controller for the ad search page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the ad search view and handling user interactions.
 */

public class AdSearchJavaFXPageController extends JavaFxController {
    private AdManager manager;
    private static final Logger LOGGER = Logger.getLogger(AdSearchJavaFXPageController.class.getName());

    @FXML
    private Label lblFilters;
    @FXML
    private Label lblServices;
    @FXML
    private CheckBox ckbPrivateBath;
    @FXML
    private CheckBox ckbBalcony;
    @FXML
    private CheckBox ckbConditioner;
    @FXML
    private CheckBox ckbTvConnection;
    @FXML
    private Label lblRentalPrice;
    @FXML
    private Label lblDistance;
    @FXML
    private TextField searchField;
    @FXML
    private TextField txfMaxPrice;
    @FXML
    private TextField txfDistance;
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<Node> lvRooms;

    public AdSearchJavaFXPageController() {
        // Default constructor
    }

    /**
     * Initializes the ad search view with the filters and search results into the list view.
     * It sets the labels, checkboxes, text fields, and buttons with the corresponding properties.
     * A similar cache has been managed through the context to retrieve a previously carried out search
     * and show the results if present.
     *
     * @param context The context object for the ad search page.
     */

    public void initialize(Context context) {
        this.context = context;
        this.manager = new AdManager();

        lblFilters.setText(properties.getProperty("FILTERS_MSG"));
        lblServices.setText(properties.getProperty("SERVICES_MSG"));
        ckbPrivateBath.setText(properties.getProperty("PRIVATE_BATH_MSG"));
        ckbBalcony.setText(properties.getProperty("BALCONY_MSG"));
        ckbConditioner.setText(properties.getProperty("CONDITIONER_MSG"));
        ckbTvConnection.setText(properties.getProperty("TV_CONNECTION_MSG"));
        lblRentalPrice.setText(properties.getProperty("RENTAL_PRICE_MSG"));
        lblDistance.setText(properties.getProperty("DISTANCE_MSG"));
        searchField.setPromptText(properties.getProperty("SEARCH_BAR_MSG"));

        // Check if there are results in context
        if (context.getFiltersBean() != null) {
            List<AdBean> adBeans = retrieveAdsByFilters(context.getFiltersBean());
            setResults(adBeans);
        }

        btnSearch.setOnAction(event -> handleSearch());

        // Set the custom cell factory for the list view
        lvRooms.setCellFactory(listView -> new CustomListCell());
    }

    /**
     * Handles the search button click event.
     * It retrieves the filters from the user input and searches the ads that match the filters.
     * It saves the results in the session bean and sets the results in the list view.
     * If no ads are found, it displays an error message.
     * For each search results it sets the filter bean in the context.
     */

    private void handleSearch() {
        String university = searchField.getText().trim();
        Float maxDistance = validateNumericInput(txfDistance.getText(), 15F); // Check distance
        Integer maxPrice = (validateNumericInput(txfMaxPrice.getText(), 1000)).intValue(); // Check price
        Boolean privateBath = ckbPrivateBath.isSelected();
        Boolean balcony = ckbBalcony.isSelected();
        Boolean conditioner = ckbConditioner.isSelected();
        Boolean tvConnection = ckbTvConnection.isSelected();

        if (university.isEmpty()) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("START_SEARCH_MSG"));
            return;
        }

        FiltersBean filtersBean = new FiltersBean(university,maxDistance,maxPrice,privateBath,balcony,conditioner,tvConnection);

        List<AdBean> adBeans = retrieveAdsByFilters(filtersBean);

        context.setFiltersBean(filtersBean);

        setResults(adBeans);
    }

    /**
     * Retrieves the ads that match the filters from the database.
     * It searches the ads by the filters and returns the list of ads.
     * If no ads are found, it displays an error message.
     *
     * @param filtersBean The FiltersBean object representing the filters.
     * @return List of ads that match the filters.
     */

    private List<AdBean> retrieveAdsByFilters(FiltersBean filtersBean) {
        List<AdBean> adBeans = manager.searchDecoratedAdsByFilters(filtersBean);
        if (adBeans == null || adBeans.isEmpty()) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NO_ADS_FOUND_MSG"));
            return Collections.emptyList();
        }
        else return adBeans;
    }

    /**
     * Creates a preview card for the ad.
     * It loads the FXML file for the preview card and sets the ad bean in the controller.
     * It initializes the preview distance and sets the controller in the preview room decorator.
     *
     * @param adBean The AdBean object representing the ad.
     * @return The Node object representing the preview card.
     */

    private Node createPreviewAd(AdBean adBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/previewRoomInfo-card.fxml"));
            Parent root = loader.load();

            PreviewAdJavaFxController previewAdJavaFxController = loader.getController();
            previewAdJavaFxController.setAdBean(adBean);
            previewAdJavaFxController.initializePreviewDistance();

            BasicComponent basicComponent = new BasicComponent(root);
            PreviewRoomDecorator previewRoomDecorator = new PreviewRoomDecorator(basicComponent, adBean, context);
            return previewRoomDecorator.setup();
        } catch (IOException e) {
            LOGGER.severe(properties.getProperty("FAILED_LOADING_PREVIEW_AD"));
            return null;
        }
    }

    /**
     * Sets the search results in the list view.
     * It clears the list view and adds the preview cards for each ad in the results.
     * It sets the on click event for each preview card to handle the ad details.
     *
     * @param results The List of AdBean objects representing the search results.
     */

    private void setResults(List<AdBean> results) {
        lvRooms.getItems().clear();
        for (AdBean adBean: results) {
            Node node = createPreviewAd(adBean);
            if (node != null) {
                lvRooms.getItems().add(node);
                node.setOnMouseClicked(event -> handlePreviewAd(adBean));
            }
        }
    }

    /**
     * Handles the preview card click event.
     * It loads the ad details view and sets the controller with the ad bean.
     *
     * @param adBean The AdBean object representing the ad.
     */

    private void handlePreviewAd(AdBean adBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/adDetails-view.fxml"));
            context.getTab(0).setContent(loader.load());

            manager.getHomeMap(context.getSessionBean(), adBean);

            AdDetailsJavaFxController controller = loader.getController();
            controller.initialize(context, new AdSearchJavaFXPage(context), adBean);
        } catch (Exception e) {
            LOGGER.severe(properties.getProperty("FAILED_LOADING_AD_DETAILS"));
        }
    }

    /**
     * Validates the numeric input.
     * It checks if the input is empty, exceeds the limit, or is not a number.
     * If the input is empty, it returns the default value.
     * If the input exceeds the limit, it returns the default value.
     * If the input is not a number, it displays an error message.
     *
     * @param input The String representing the input.
     * @param defaultValue The float representing the default value.
     * @return The Float representing the validated input.
     */

    private Float validateNumericInput(String input, float defaultValue) {
        if (input.isEmpty()){
            return defaultValue;
        }
        else {
            try {
                float floatValue = Float.parseFloat(input);
                if (floatValue > defaultValue) {
                    return defaultValue;
                }
            } catch (NumberFormatException e) {
                showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("INVALID_FILTER_MSG"));
            }
        }
        return Float.parseFloat(input);
    }
}
