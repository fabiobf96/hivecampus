package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.bean.*;
import it.hivecampuscompany.hivecampus.manager.RoomSearchManager;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.PreviewRoomDecorator;
import it.hivecampuscompany.hivecampus.view.utility.CustomListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class RoomSearchJavaFxController extends JavaFxController implements TabInitializerController {
    private RoomSearchManager roomSearchManager;
    private Context context;
    private static final Logger LOGGER = Logger.getLogger(RoomSearchJavaFxController.class.getName());

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

    public RoomSearchJavaFxController() {
        // Default constructor
    }

    public void initialize(Context context) {
        this.context = context; // Set the context
        this.roomSearchManager = new RoomSearchManager();

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

        btnSearch.setOnAction(event -> handleSearch());

        // Set the custom cell factory for the list view
        lvRooms.setCellFactory(listView -> new CustomListCell());
    }

    private void handleSearch() {
        // Elimina tutti gli elementi attualmente presenti nella ListView
        lvRooms.getItems().clear();

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
        List <AdBean> adBeans = roomSearchManager.searchAdsByFilters(filtersBean);

        for (AdBean adBean: adBeans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/previewRoomInfo-card.fxml"));
                Parent root = loader.load(); // Carica il file FXML e restituisce il nodo radice

                PreviewRoomJavaFxController previewRoomJavaFxController = loader.getController();
                previewRoomJavaFxController.setAdBean(adBean);
                previewRoomJavaFxController.initializePreviewDistance(); // Inizializza il controller dopo aver caricato il file FXML

                BasicComponent basicComponent = new BasicComponent(root);
                PreviewRoomDecorator previewRoomDecorator = new PreviewRoomDecorator(basicComponent, adBean);

                // Carico la preview della stanza nella ListView
                lvRooms.getItems().add(previewRoomDecorator.setup());
            } catch (IOException e) {
                LOGGER.severe("Error loading the preview room card");
            }
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
