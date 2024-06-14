package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.utility.CustomListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ManageRequestsTenantJavaFXPageController extends JavaFxController {

    private final LeaseRequestManager manager;
    private static final Logger LOGGER = Logger.getLogger(ManageRequestsTenantJavaFXPageController.class.getName());

   @FXML
    ListView<Node> lvRequests;
    List<LeaseRequestBean> requestBeans = new ArrayList<>();

    public ManageRequestsTenantJavaFXPageController() {
        this.manager = new LeaseRequestManager();
    }

    /**
     * Initializes the controller with the context.
     * It retrieves the lease requests of the tenant and creates a card for each lease request.
     * It adds the cards to the list view.
     *
     * @param context the context
     * @author Marina Sotiropoulos
     */

    public void initialize(Context context) {
        this.context = context;

        requestBeans = manager.searchTenantRequests(context.getSessionBean());

        if (requestBeans.isEmpty()) {
            Label noAds = new Label(properties.getProperty("NO_REQUESTS_FOUND_MSG"));
            lvRequests.getItems().add(noAds);
        }

        for (LeaseRequestBean requestBean : requestBeans) {
            HBox card = createLeaseRequestCard(requestBean);
            lvRequests.getItems().add(card);
        }

        lvRequests.setCellFactory(listView -> new CustomListCell());
    }

    /**
     * Creates a lease request card with the lease request information.
     * It adds a delete request button to the card.
     *
     * @param requestBean the lease request bean
     * @return the lease request card
     * @author Marina Sotiropoulos
     */

    private HBox createLeaseRequestCard(LeaseRequestBean requestBean) {
        // Create a VBox with the lease request status and a delete request button
        VBox vBox = new VBox();
        vBox.setPadding(new javafx.geometry.Insets(10));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.TOP_CENTER);

        Button btnDelete = new Button(properties.getProperty("DELETE_REQUEST_MSG"));

        // Setting the button handler for the delete request button
        btnDelete.setOnAction(event -> handleDeleteAd(requestBean));

        HBox hBox = new HBox();
        hBox.setSpacing(20);

        Label lblState = new Label();
        setLabelText(lblState, properties.getProperty("STATE_REQUEST_MSG"));

        Label lblStatus = new Label();
        setLabelText(lblStatus, requestBean.getStatus().toString());

        hBox.getChildren().addAll(lblState, lblStatus);
        vBox.getChildren().addAll(hBox, btnDelete);

        // Creating the lease request card
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/basicRequest-card.fxml"));
            VBox vbItem = loader.load();

            ManageRequestsJavaFxPageController controller = loader.getController();

            // Decorating the lease request card with the vbox created above
            HBox card = new HBox();

            Region region = new Region();

            card.getChildren().addAll(vbItem, region, vBox);

            card.setPadding(new javafx.geometry.Insets(20));
            HBox.setHgrow(region, Priority.ALWAYS);
            HBox.setHgrow(card, Priority.ALWAYS);

            controller.initialize(requestBean);

            return card;
        } catch (IOException e) {
            LOGGER.severe(properties.getProperty("ERROR_CREATING_LEASE_REQUEST_CARD"));
        }
        return null;
    }

    /**
     * Handles the deletion of a lease request. It shows a confirmation alert to the user.
     * If the user confirms the deletion, it removes the lease request from the list of lease requests.
     *
     * @param requestBean The lease request to be deleted.
     * @author Marina Sotiropoulos
     */

    private void handleDeleteAd(LeaseRequestBean requestBean) {

        if (!requestBean.getStatus().equals(LeaseRequestStatus.PROCESSING)) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("REQUEST_ALREADY_PROCESSED_MSG"));
        }

        else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle(properties.getProperty("DELETE_REQUEST_CONFIRMATION_TITLE_MSG"));
            confirmationAlert.setHeaderText(properties.getProperty("DELETE_REQUEST_CONFIRMATION_HEADER_MSG"));
            confirmationAlert.setContentText(properties.getProperty("DELETE_REQUEST_CONFIRMATION_CONTENT_MSG"));

            ButtonType buttonTypeYes = new ButtonType(properties.getProperty("YES_MSG"));
            ButtonType buttonTypeNo = new ButtonType(properties.getProperty("NO_MSG"));

            confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == buttonTypeYes) {
                // Perform the actual deletion of the ad here
                manager.deleteLeaseRequest(requestBean);
                lvRequests.getItems().remove(requestBeans.indexOf(requestBean));
            }
        }
    }
}