package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

public class RoomSearchJavaFxController extends JavaFxController implements TabInitializerController {

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

    public void initialize(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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

        FiltersBean filtersBean;

        try{
            filtersBean = new FiltersBean(university,maxDistance,maxPrice,privateBath,balcony,conditioner,tvConnection);
            System.out.println(filtersBean);



        } catch (Exception e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("ERROR_SEARCH_MSG"));
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
