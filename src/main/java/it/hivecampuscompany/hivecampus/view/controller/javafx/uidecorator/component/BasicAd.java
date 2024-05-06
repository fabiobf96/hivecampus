package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.ManageRequestsOwnerJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.PreviewAdJavaFxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class BasicAd extends Component {
    private AdBean adBean;
    private Context context;
    public BasicAd(AdBean adBean, Context context) {
        this.adBean = adBean;
        this.context = context;
    }
    @Override
    public Node setup() {
        try {
            FXMLLoader loader = new FXMLLoader(ManageRequestsOwnerJavaFXPage.class.getResource("/it/hivecampuscompany/hivecampus/previewRoom-card.fxml"));
            Node ad = loader.load();
            PreviewAdJavaFxController controller = loader.getController();
            controller.setAdBean(adBean);
            controller.initializePreviewFeatures(context);
            return ad;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
