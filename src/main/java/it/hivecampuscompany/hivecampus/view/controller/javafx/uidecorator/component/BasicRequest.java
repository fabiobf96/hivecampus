package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.state.javafx.controller.ManageRequestsOwnerJavaFxPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class BasicRequest extends Component{
    private LeaseRequestBean leaseRequestBean;
    public BasicRequest(LeaseRequestBean leaseRequestBean) {
        this.leaseRequestBean = leaseRequestBean;
    }
    @Override
    public Node setup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/basicRequest-card.fxml"));
            Node root = loader.load();
            ManageRequestsOwnerJavaFxPageController controller = loader.getController();
            controller.initialize(leaseRequestBean);
            return root;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}