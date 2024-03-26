package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.PreviewRoomDecorator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;

public class RoomSearchJavaFxController extends JavaFxController implements TabInitializerController {
    //private static final Logger logger = Logger.getLogger(RoomSearchJavaFxController.class.getName());

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
    private Button btnSearch;

    @FXML
    protected ListView<Node> lvRooms;

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
        System.out.println("Search");
        try {
            for (int i = 0; i < 4; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/previewRoomInfo-card.fxml"));
                BasicComponent basicComponent = new BasicComponent(loader.load());
                PreviewRoomDecorator previewRoomDecorator = new PreviewRoomDecorator(basicComponent);
                lvRooms.getItems().add(previewRoomDecorator.setup());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
