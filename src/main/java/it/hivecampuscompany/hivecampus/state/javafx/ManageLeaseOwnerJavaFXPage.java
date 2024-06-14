package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseContractBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicRequest;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.CssDecoration;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LeaseDecorator;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.PreviewRoomDecorator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The ManageLeaseOwnerJavaFXPage class represents the manage lease owner page in the JavaFX user interface.
 * It extends the ManageLeasePage class and provides methods for displaying the manage lease owner page and handling user input.
 */
public class ManageLeaseOwnerJavaFXPage extends ManageLeasePage {

    /**
     * Constructs a ManageLeaseOwnerJavaFXPage object with the given context.
     *
     * @param context The context object for the manage lease owner page.
     * @see Context
     */
    public ManageLeaseOwnerJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the processing and display of ads and lease requests.
     * This method retrieves the list of processing ads, creates the necessary UI components
     * for each ad and lease request, and sets up the functionality for uploading lease contracts.
     *
     * @throws InvalidSessionException if the session is invalid
     */
    @Override
    public void handle() throws InvalidSessionException {
        List<AdBean> adBeanList = getProcessingAds();
        VBox vBox = new VBox(10);

        if (adBeanList.isEmpty()) {
            handleEmptyAdList(vBox);
        } else {
            handleAdList(vBox, adBeanList);
        }
    }

    /**
     * Handles the case when the list of ads is empty.
     * Displays a warning alert if it's the first time the user is viewing the tab.
     *
     * @param vBox The VBox to set as the content of the tab.
     */
    private void handleEmptyAdList(VBox vBox) {
        context.getTab(2).setContent(vBox);
        if (context.isFirstLease()) {
            showAlert(Alert.AlertType.WARNING, context.getLanguage().getProperty("WARNING_TITLE_MSG"), context.getLanguage().getProperty("NO_ACCEPTED_REQUESTS_MSG"));
            context.setFirstLease(false);
        }
    }

    /**
     * Handles the case when there are ads to be processed.
     * Creates UI components for each ad and adds them to the VBox.
     *
     * @param vBox The VBox to which ad components will be added.
     * @param adBeanList The list of ads to be processed.
     * @throws InvalidSessionException if the session is invalid
     */
    private void handleAdList(VBox vBox, List<AdBean> adBeanList) throws InvalidSessionException {
        ScrollPane scrollPane = new ScrollPane();

        for (AdBean adBean : adBeanList) {
            Node root = createAdNode(adBean);
            vBox.getChildren().add(root);
        }

        scrollPane.setContent(vBox);
        scrollPane.setPadding(new Insets(5));
        scrollPane.fitToWidthProperty().setValue(true);
        context.setFirstLease(false);
        context.getTab(2).setContent(scrollPane);
    }

    /**
     * Creates a UI component for a given ad.
     *
     * @param adBean The ad for which to create the UI component.
     * @return The created UI component for the ad.
     * @throws InvalidSessionException if the session is invalid
     */
    private Node createAdNode(AdBean adBean) throws InvalidSessionException {
        LeaseRequestBean leaseRequestBean = getLeaseRequestInformation(adBean);
        BasicRequest basicRequest = new BasicRequest(leaseRequestBean);
        LeaseDecorator leaseDecorator = new LeaseDecorator(basicRequest, LeaseDecorator.Type.OWNER);
        CssDecoration cssDecoration = new CssDecoration(leaseDecorator);
        PreviewRoomDecorator previewRoomDecorator = new PreviewRoomDecorator(cssDecoration, adBean);
        CssDecoration decoration = new CssDecoration(previewRoomDecorator);
        Node root = decoration.setup();

        setupDeleteButton(root);
        setupUploadButton(root, leaseRequestBean, adBean);

        return root;
    }

    /**
     * Sets up the delete button for a given UI component.
     *
     * @param root The UI component root node where the button is located.
     */
    private void setupDeleteButton(Node root) {
        Button btnDelete = (Button) root.lookup("#btnDelete");
        btnDelete.setOnAction(e -> showAlert(Alert.AlertType.INFORMATION, context.getLanguage().getProperty("INFORMATION_TITLE_MSG"), context.getLanguage().getProperty("NOT_IMPLEMENTED_MSG")));
    }

    /**
     * Sets up the upload button for a given UI component.
     * Allows the user to upload a lease contract.
     *
     * @param root The UI component root node where the button is located.
     * @param leaseRequestBean The lease request associated with the ad.
     * @param adBean The ad associated with the lease request.
     */
    private void setupUploadButton(Node root, LeaseRequestBean leaseRequestBean, AdBean adBean) {
        Button btnUpload = (Button) root.lookup("#btnUpload");
        btnUpload.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleziona un contratto");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documenti", "*.pdf"));
            File selectedFile = fileChooser.showOpenDialog(context.getStage());

            leaseRequestBean.setAdBean(adBean);

            try {
                LeaseContractBean leaseContractBean = new LeaseContractBean(leaseRequestBean, selectedFile.getPath());
                uploadLease(leaseContractBean);
                showAlert(Alert.AlertType.INFORMATION, context.getLanguage().getProperty("INFORMATION_TITLE_MSG"), context.getLanguage().getProperty("SUCCESS_MSG_LOADED"));
                context.request();
            } catch (IOException ex) {
                showAlert(Alert.AlertType.WARNING, context.getLanguage().getProperty("WARNING_TITLE_MSG"), ex.getMessage());
            } catch (InvalidSessionException ex) {
                context.invalidSessionExceptionHandle();
            }
        });
    }

    /**
     * Displays an alert with the specified type, title, and message.
     *
     * @param typeAlert The type of alert to display.
     * @param title The title of the alert.
     * @param message The message content of the alert.
     */
    private void showAlert(Alert.AlertType typeAlert, String title, String message) {
        Alert alert = new Alert(typeAlert);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}