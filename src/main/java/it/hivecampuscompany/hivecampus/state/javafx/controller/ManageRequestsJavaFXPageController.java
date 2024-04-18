package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import java.util.logging.Logger;

public class ManageRequestsJavaFXPageController extends JavaFxController implements TabInitializerController {

    private ManageRequestsPage manageRequestsPage;
    private static final Logger LOGGER = Logger.getLogger(ManageRequestsJavaFXPageController.class.getName());

   @FXML
    ListView<Node> lvRequests;

    public ManageRequestsJavaFXPageController() {
        // Default constructor
    }

    @Override
    public void initialize(Context context) {
        this.context = context;
    }
}
