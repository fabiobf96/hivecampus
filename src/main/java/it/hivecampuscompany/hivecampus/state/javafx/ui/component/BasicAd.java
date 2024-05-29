package it.hivecampuscompany.hivecampus.state.javafx.ui.component;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.ManageRequestsOwnerJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.PreviewAdJavaFxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * This class is a concrete implementation of the Component abstract class.
 * It is used to create a basic ad component in the JavaFX UI.
 * It extends the Component class and overrides the setup method to return the JavaFX node.
 *
 * @author Fabio Barchiesi
 */
public class BasicAd extends Component {
    private AdBean adBean;
    private Context context;

    /**
     * Constructor for the BasicAd class.
     *
     * @param adBean the ad bean to be used as the basic ad component
     * @param context the context of the ad component
     * @author Fabio Barchiesi
     */
    public BasicAd(AdBean adBean, Context context) {
        this.adBean = adBean;
        this.context = context;
    }

    /**
     * This method sets up the JavaFX node for the basic ad component.
     *
     * @return the JavaFX node for the basic ad component
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        try {
            FXMLLoader loader = new FXMLLoader(ManageRequestsOwnerJavaFXPage.class.getResource("/it/hivecampuscompany/hivecampus/previewRoom-card.fxml"));
            Node ad = loader.load();
            PreviewAdJavaFxController controller = loader.getController();
            controller.setAdBean(adBean);
            controller.initializePreviewFeatures(context);
            return ad;
        } catch (IOException | IllegalStateException e) {
            displayGraphicErrorAlert();
            return null;
        }
    }
}
