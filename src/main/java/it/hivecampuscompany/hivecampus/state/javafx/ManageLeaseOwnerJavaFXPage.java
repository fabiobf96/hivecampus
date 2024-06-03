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
import javafx.scene.control.Label;
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
     * @author Fabio Barchiesi
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
     * @author Fabio Barchiesi
     */
    @Override
    public void handle() throws InvalidSessionException {
        List<AdBean> adBeanList = getProcessingAds();
        VBox vBox = new VBox(10);
        if (adBeanList.isEmpty()) {
            Label label = new Label(context.getLanguage().getProperty("NO_ACCEPTED_REQUESTS_MSG"));
            vBox.getChildren().add(label);
            context.getTab(2).setContent(vBox);
        } else {
            ScrollPane scrollPane = new ScrollPane();

            for (AdBean adBean : adBeanList) {
                LeaseRequestBean leaseRequestBean = getLeaseRequestInformation(adBean);
                BasicRequest basicRequest = new BasicRequest(leaseRequestBean);
                LeaseDecorator leaseDecorator = new LeaseDecorator(basicRequest, LeaseDecorator.Type.OWNER);
                CssDecoration cssDecoration = new CssDecoration(leaseDecorator);
                PreviewRoomDecorator previewRoomDecorator = new PreviewRoomDecorator(cssDecoration, adBean);
                CssDecoration decoration = new CssDecoration(previewRoomDecorator);
                Node root = decoration.setup();

                Button btnDelete = (Button) root.lookup("#btnDelete");
                btnDelete.setOnAction(e -> showAlert(Alert.AlertType.INFORMATION, context.getLanguage().getProperty("INFORMATION_TITLE_MSG"), context.getLanguage().getProperty("NOT_IMPLEMENTED_MSG")));

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
                vBox.getChildren().add(root);
            }
            scrollPane.setContent(vBox);
            scrollPane.setPadding(new Insets(5));
            scrollPane.fitToWidthProperty().setValue(true);
            context.getTab(2).setContent(scrollPane);
        }
    }

    private void showAlert(Alert.AlertType typeAlert, String title, String message) {
        Alert alert = new Alert(typeAlert);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
