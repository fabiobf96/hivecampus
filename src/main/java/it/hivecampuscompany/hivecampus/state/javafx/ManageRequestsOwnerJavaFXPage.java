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
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.CompositeVBox;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.CssDecoration;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LeaseRequestDecorator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;

public class ManageRequestsOwnerJavaFXPage extends ManageRequestsPage {

    public ManageRequestsOwnerJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        List<AdBean> adBeanList = retrieveAvailableAds();
        ListView<Node> listView = new ListView<>();
        for (AdBean adBean : adBeanList) {
            BasicAd basicAd = new BasicAd(adBean, context);
            listView.getItems().add(basicAd.setup());
        }
        listView.setOnMouseClicked(event -> {
            // Ottieni l'indice dell'elemento selezionato
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index != -1) { // Controlla se l'indice è valido
                AdBean adBean = adBeanList.get(index); // Recupera l'AdBean associato
                try {
                    manageLeaseRequests(adBean); // Chiama il metodo con il bean corrispondente
                } catch (InvalidSessionException e) {
                    context.invalidSessionExceptionHandle();
                }
            }
        });
        context.getTab(1).setText(context.getLanguage().getProperty("MANAGE_REQUEST_MSG"));
        context.getTab(1).setContent(listView);
    }

    public void manageLeaseRequests(AdBean adBean) throws InvalidSessionException {
        BasicAd basicAd = new BasicAd(adBean, context);
        ListView<Node> listView = new ListView<>();
        CompositeVBox composite = new CompositeVBox();
        Button btnGoBack = new Button("Go Back");
        btnGoBack.setOnAction(event -> context.request());
        composite.addChildren(basicAd);
        composite.addChildren(new BasicComponent(btnGoBack));
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
