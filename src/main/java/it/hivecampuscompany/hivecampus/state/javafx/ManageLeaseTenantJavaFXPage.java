package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.bean.LeaseContractBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicAd;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.CssDecoration;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LeaseDecorator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The ManageLeaseTenantJavaFXPage class represents the manage lease tenant page in the JavaFX user interface.
 * It extends the ManageLeasePage class and provides methods for displaying the manage lease tenant page and handling user input.
 */
public class ManageLeaseTenantJavaFXPage extends ManageLeasePage {

    /**
     * Constructs a ManageLeaseTenantJavaFXPage object with the given context.
     *
     * @param context The context object for the manage lease tenant page.
     */
    public ManageLeaseTenantJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the display and management of an unsigned lease for the tenant.
     * This method sets up the UI components for viewing, downloading, and signing the lease contract.
     *
     * @throws InvalidSessionException if the session is invalid
     */
    @Override
    public void handle() throws InvalidSessionException {
        LeaseContractBean leaseContractBean = getUnSignedLease();
        VBox vBox = new VBox();
        context.getTab(2).setContent(vBox);

        if (leaseContractBean == null) {
            handleNoLease();
        } else {
            handleLease(leaseContractBean);
        }
    }

    /**
     * Handles the case when there is no lease to sign.
     * Displays a warning message to the user.
     */
    private void handleNoLease() {
        if (context.isFirstLease()) {
            showAlert(Alert.AlertType.WARNING, context.getLanguage().getProperty("NO_LEASE_MSG"));
            context.setFirstLease(false);
        }
    }

    /**
     * Handles the case when there is a lease to sign.
     * Sets up the UI components for viewing, downloading, and signing the lease contract.
     *
     * @param leaseContractBean The lease contract to be signed.
     */
    private void handleLease(LeaseContractBean leaseContractBean) {
        BasicAd basicAd = new BasicAd(leaseContractBean.getAdBean());
        LeaseDecorator leaseDecorator = new LeaseDecorator(basicAd, LeaseDecorator.Type.TENANT);
        CssDecoration cssDecoration = new CssDecoration(leaseDecorator);
        Node root = cssDecoration.setup();

        setupDownloadButton(root, leaseContractBean);
        setupSignButton(root);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setPadding(new Insets(10));
        scrollPane.fitToWidthProperty().setValue(true);
        context.setFirstLease(false);
        context.getTab(2).setContent(scrollPane);
    }

    /**
     * Sets up the download button for a given UI component.
     * Allows the user to download the lease contract as a PDF file.
     *
     * @param root The UI component root node where the button is located.
     * @param leaseContractBean The lease contract to be downloaded.
     */
    private void setupDownloadButton(Node root, LeaseContractBean leaseContractBean) {
        Button btnDownload = (Button) root.lookup("#btnDownload");
        btnDownload.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(context.getStage());
            if (selectedFile != null) {
                String path = selectedFile.getPath() + ".pdf";
                try (FileOutputStream fos = new FileOutputStream(path)) {
                    fos.write(leaseContractBean.getContract());
                } catch (IOException e) {
                    showAlert(Alert.AlertType.WARNING, e.getMessage());
                }
            }
        });
    }

    /**
     * Sets up the sign button for a given UI component.
     * Allows the user to sign the lease contract.
     *
     * @param root The UI component root node where the button is located.
     */
    private void setupSignButton(Node root) {
        Button btnSign = (Button) root.lookup("#btnSign");
        btnSign.setOnAction(event -> {
            try {
                signContract();
                showAlert(Alert.AlertType.CONFIRMATION, context.getLanguage().getProperty("SUCCESS_MSG_SIGN"));
                context.request();
            } catch (InvalidSessionException e) {
                context.invalidSessionExceptionHandle();
            } catch (MockOpenAPIException e) {
                showAlert(Alert.AlertType.ERROR, e.getMessage());
            }
        });
    }

    /**
     * Displays an alert with the specified type and message.
     *
     * @param typeAlert The type of alert to display.
     * @param message The message content of the alert.
     */
    private void showAlert(Alert.AlertType typeAlert, String message) {
        Alert alert = new Alert(typeAlert);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
