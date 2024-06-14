package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicAd;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicComponent;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicRequest;
import it.hivecampuscompany.hivecampus.state.javafx.ui.composite.CompositeVBox;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.CssDecoration;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LeaseRequestDecorator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The ManageRequestsOwnerJavaFXPage class represents the manage requests owner page in the JavaFX user interface.
 * It extends the ManageRequestsPage class and provides methods for displaying the manage requests owner page and handling user input.
 */
public class ManageRequestsOwnerJavaFXPage extends ManageRequestsPage {

    /**
     * Constructs a ManageRequestsOwnerJavaFXPage object with the given context.
     * @param context The context object for the manage requests owner page.
     * @author Fabio Barchiesi
     */
    public ManageRequestsOwnerJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the processing and display of lease requests for the owner.
     * This method retrieves the list of available ads, creates the necessary UI components
     * for each ad, and sets up the functionality for managing lease requests.
     *
     * @throws InvalidSessionException if the session is invalid
     * @author Fabio Barchiesi
     */
    @Override
    public void handle() throws InvalidSessionException {
        List<AdBean> adBeanList = retrieveAvailableAds();
        if (adBeanList.isEmpty()){
            if (context.isFirstRequest()) {
                showAlert(context.getLanguage().getProperty("NO_ADS_CREATED_MSG"));
            }
            context.getTab(1).setContent(new VBox());
        } else {
            ListView<Node> listView = new ListView<>();
            for (AdBean adBean : adBeanList) {
                BasicAd basicAd = new BasicAd(adBean);
                listView.getItems().add(basicAd.setup());
            }
            listView.setOnMouseClicked(event -> {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (index != -1) {
                    AdBean adBean = adBeanList.get(index);
                    try {
                        manageLeaseRequests(adBean);
                    } catch (InvalidSessionException e) {
                        context.invalidSessionExceptionHandle();
                    }
                }
            });
            context.getTab(1).setText(context.getLanguage().getProperty("MANAGE_REQUEST_MSG"));
            context.setFirstRequest(false);
            context.getTab(1).setContent(listView);
        }
    }

    /**
     * Manages the lease requests for a given advertisement.
     * This method sets up the GUI components, retrieves lease requests, and handles
     * the accept and reject actions for each request.
     *
     * @param adBean the advertisement bean containing details of the advertisement
     * @throws InvalidSessionException if the session is invalid
     */
    public void manageLeaseRequests(AdBean adBean) throws InvalidSessionException {
        List<LeaseRequestBean> requestBeanList = retrieveLeaseRequests(adBean);
        if (requestBeanList.isEmpty()) {
            showAlert(context.getLanguage().getProperty("NO_REQUESTS_MSG"));
        }
        else {
            BasicAd basicAd = new BasicAd(adBean);
            ListView<Node> listView = new ListView<>();
            CompositeVBox composite = new CompositeVBox();
            Button btnGoBack = new Button("Go Back");
            btnGoBack.setOnAction(event -> context.request());
            composite.addChildren(basicAd);
            composite.addChildren(new BasicComponent(btnGoBack));

            for (LeaseRequestBean requestBean : requestBeanList) {
                BasicRequest basicRequest = new BasicRequest(requestBean);
                LeaseRequestDecorator leaseRequestDecorator = new LeaseRequestDecorator(basicRequest);
                CssDecoration cssDecoration = new CssDecoration(leaseRequestDecorator);
                Node requestNode = cssDecoration.setup();
                Button btnAccept = (Button) requestNode.lookup("#btnAccept");
                Button btnReject = (Button) requestNode.lookup("#btnReject");

                btnAccept.setOnAction(event -> {
                    requestBean.setStatus(LeaseRequestStatus.ACCEPTED);
                    try {
                        updateLeaseRequest(requestBean);
                        context.request();
                    } catch (InvalidSessionException e) {
                        context.invalidSessionExceptionHandle();
                    }
                });

                btnReject.setOnAction(event -> {
                    requestBean.setStatus(LeaseRequestStatus.REJECTED);
                    try {
                        updateLeaseRequest(requestBean);
                        listView.getItems().remove(requestBeanList.indexOf(requestBean));
                        requestBeanList.remove(requestBean);
                    } catch (InvalidSessionException e) {
                        context.invalidSessionExceptionHandle();
                    }
                });
                listView.getItems().add(requestNode);
            }
            BasicComponent listRequests = new BasicComponent(listView);
            composite.addChildren(listRequests);
            CssDecoration cssDecoration = new CssDecoration(composite);
            VBox vBox = new VBox(cssDecoration.setup());
            vBox.setPadding(new Insets(10));
            context.getTab(1).setContent(vBox);
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
