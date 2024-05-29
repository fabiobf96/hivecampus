package it.hivecampuscompany.hivecampus.state.javafx.ui.component;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.state.javafx.controller.ManageRequestsJavaFxPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * This class is a concrete implementation of the Component abstract class.
 * It is used to create a basic request component in the JavaFX UI.
 * It extends the Component class and overrides the setup method to return the JavaFX node.
 *
 * @author Fabio Barchiesi
 */
public class BasicRequest extends Component{
    private LeaseRequestBean leaseRequestBean;

    /**
     * Constructor for the BasicRequest class.
     *
     * @param leaseRequestBean the lease request bean to be used as the basic request component
     * @author Fabio Barchiesi
     */
    public BasicRequest(LeaseRequestBean leaseRequestBean) {
        this.leaseRequestBean = leaseRequestBean;
    }

    /**
     * This method sets up the JavaFX node for the basic request component.
     *
     * @return the JavaFX node for the basic request component
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/basicRequest-card.fxml"));
            Node root = loader.load();
            ManageRequestsJavaFxPageController controller = loader.getController();
            controller.initialize(leaseRequestBean);
            return root;
        } catch (IOException | IllegalStateException e) {
            displayGraphicErrorAlert();
            return null;
        }
    }
}
