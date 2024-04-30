package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;

public class ManageRequestsTenantJavaFXPageController extends JavaFxController {

    private ManageRequestsPage manageRequestsPage;

   @FXML
    ListView<Node> lvRequests;

    public ManageRequestsTenantJavaFXPageController() {
        // Default constructor
    }

    public void initialize(Context context) {
        this.context = context;
    }
}
