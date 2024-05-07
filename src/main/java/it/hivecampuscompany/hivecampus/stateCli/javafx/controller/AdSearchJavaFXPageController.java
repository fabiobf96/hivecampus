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

    public void initialize(Context context) {
        this.context = context; // Set the context
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
        btnSearch.setText(properties.getProperty("SEARCH_MSG"));

        // Check if there are results in context
        if (context.getFiltersBean() != null) {
            List<AdBean> adBeans = retrieveAdsByFilters(context.getFiltersBean());
            setResults(adBeans);
        }

        btnSearch.setOnAction(event -> handleSearch());

        // Set the custom cell factory for the list view
        lvRooms.setCellFactory(listView -> new CustomListCell());
    }

    private void handleSearch() {
        // Manage the search button click event
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

        // Retrieve the ads that match the filters
        List<AdBean> adBeans = retrieveAdsByFilters(filtersBean);

        // Save the results in the session bean
        context.setFiltersBean(filtersBean);

        // Set the results in the list view
        setResults(adBeans);
    }

    private List<AdBean> retrieveAdsByFilters(FiltersBean filtersBean) {
        List<AdBean> adBeans = manager.searchDecoratedAdsByFilters(filtersBean);
        if (adBeans == null || adBeans.isEmpty()) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NO_ADS_FOUND_MSG"));
            return Collections.emptyList();
        }
        else return adBeans;
    }

    private Node createPreviewAd(AdBean adBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/previewRoomInfo-card.fxml"));
            Parent root = loader.load(); // Carica il file FXML e restituisce il nodo radice

            PreviewAdJavaFxController previewAdJavaFxController = loader.getController();
            previewAdJavaFxController.setAdBean(adBean);
            previewAdJavaFxController.initializePreviewDistance(); // Inizializza il controller dopo aver caricato il file FXML

            BasicComponent basicComponent = new BasicComponent(root);
            PreviewRoomDecorator previewRoomDecorator = new PreviewRoomDecorator(basicComponent, adBean, context);
            return previewRoomDecorator.setup();
        } catch (IOException e) {
            LOGGER.severe("Error loading the preview room card");
            return null;
        }
    }

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


    private void handlePreviewAd(AdBean adBean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/adDetails-view.fxml"));
            context.getTab(0).setContent(loader.load()); // Caricamento del tab per la visualizzazione dei dettagli dell'annuncio

            manager.getHomeMap(context.getSessionBean(), adBean);

            AdDetailsJavaFxController controller = loader.getController();
            controller.initialize(context, new AdSearchJavaFXPage(context), adBean);
        } catch (Exception e) {
            LOGGER.severe("Error while loading ad details page: " + e.getMessage());
        }
    }

    private Float validateNumericInput(String input, float defaultValue) {
        // Verify if the input is empty
        if (input.isEmpty()){
            return defaultValue;
        }
        else {
            // Verifica se il valore supera il limite o non rappresenta un numero
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
