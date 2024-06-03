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
     * @author Fabio Barchiesi
     */
    public ManageLeaseTenantJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the display and management of an unsigned lease for the tenant.
     * This method sets up the UI components for viewing, downloading, and signing the lease contract.
     *
     * @throws InvalidSessionException if the session is invalid
     * @author Fabio Barchiesi
     */
    @Override
    public void handle() throws InvalidSessionException {
        LeaseContractBean leaseContractBean = getUnSignedLease();
        if (leaseContractBean == null) {
            showAlert(Alert.AlertType.WARNING, context.getLanguage().getProperty("NO_LEASE_MSG"));
        } else {
            BasicAd basicAd = new BasicAd(leaseContractBean.getAdBean());
            LeaseDecorator leaseDecorator = new LeaseDecorator(basicAd, LeaseDecorator.Type.TENANT);
            CssDecoration cssDecoration = new CssDecoration(leaseDecorator);
            Node root = cssDecoration.setup();
            Button btnDownload = (Button) root.lookup("#btnDownload");
            btnDownload.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showSaveDialog(context.getStage());
                if (selectedFile != null) {
                    String path = selectedFile.getPath() + ".pdf";
                    try (FileOutputStream fos = new FileOutputStream(path)) {
                        fos.write(leaseContractBean.getContract());
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
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
            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setPadding(new Insets(10));
            scrollPane.fitToWidthProperty().setValue(true);
            context.getTab(2).setContent(scrollPane);
        }
    }

    private void showAlert(Alert.AlertType typeAlert, String message) {
        Alert alert = new Alert(typeAlert);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
