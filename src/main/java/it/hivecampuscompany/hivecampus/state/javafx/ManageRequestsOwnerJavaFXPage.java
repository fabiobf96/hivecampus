package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.PreviewAdJavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicRequest;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.CompositeVBox;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.CssDecoration;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.LeaseRequestDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ManageRequestsOwnerJavaFXPage extends ManageRequestsPage {
    private AdBean adBean;
    public ManageRequestsOwnerJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        try {
            ListView<Node> listView = new ListView<>();
            List<AdBean> adBeanList = retrieveAvailableAds();
            for (AdBean adBean : adBeanList) {
                FXMLLoader loader = new FXMLLoader(ManageRequestsOwnerJavaFXPage.class.getResource("/it/hivecampuscompany/hivecampus/previewRoom-card.fxml"));
                Node ad = loader.load();
                PreviewAdJavaFxController controller = loader.getController();
                controller.setAdBean(adBean);
                controller.initializePreviewFeatures();
                ad.setOnMouseClicked(event -> {
                    this.adBean = controller.getAdBean();
                    manageLeaseRequests();
                });
                listView.getItems().add(ad);
            }
            Stage stage = context.getStage();
            stage.setScene(new Scene(listView));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void manageLeaseRequests() {
        try {
            ListView<Node> listView = new ListView<>();
            CompositeVBox composite = new CompositeVBox();
            FXMLLoader loader = new FXMLLoader(ManageRequestsOwnerJavaFXPage.class.getResource("/it/hivecampuscompany/hivecampus/previewRoom-card.fxml"));
            Node ad = loader.load();
            Button btnGoBack = new Button("Go Back");
            btnGoBack.setOnAction(event -> context.request());
            ((VBox) ad).getChildren().add(btnGoBack);
            PreviewAdJavaFxController controller = loader.getController();
            controller.setAdBean(adBean);
            controller.initializePreviewFeatures();
            BasicComponent adComponent = new BasicComponent(ad);
            composite.addChildren(adComponent);
            List<LeaseRequestBean> requestBeanList = retrieveLeaseRequests(adBean);
            for (LeaseRequestBean requestBean : requestBeanList) {
                BasicRequest basicRequest = new BasicRequest(requestBean);
                LeaseRequestDecorator leaseRequestDecorator = new LeaseRequestDecorator(basicRequest);
                CssDecoration cssDecoration = new CssDecoration(leaseRequestDecorator);
                Node requestNode = cssDecoration.setup();
                Button btnAccept = (Button) requestNode.lookup("#btnAccept");
                Button btnReject = (Button) requestNode.lookup("#btnReject");
                btnAccept.setOnAction(event -> {
                    requestBean.setStatus(LeaseRequestStatus.ACCEPTED);
                    //try {
                    //    updateLeaseRequest(requestBean);
                        context.request();
                    //} catch (InvalidSessionException e) {
                    //    throw new RuntimeException(e);
                    //}
                });
                btnReject.setOnAction(event -> {
                    requestBean.setStatus(LeaseRequestStatus.REJECTED);
                    //try {
                    //    updateLeaseRequest(requestBean);
                        listView.getItems().remove(requestBeanList.indexOf(requestBean));
                    //} catch (InvalidSessionException e) {
                    //    throw new RuntimeException(e);
                    //}
                });
                listView.getItems().add(requestNode);
            }
            BasicComponent listRequests = new BasicComponent(listView);
            composite.addChildren(listRequests);
            CssDecoration cssDecoration = new CssDecoration(composite);
            Stage stage = context.getStage();
            stage.setScene(new Scene((Parent) cssDecoration.setup()));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidSessionException e) {
            throw new RuntimeException(e);
        }
    }
}
