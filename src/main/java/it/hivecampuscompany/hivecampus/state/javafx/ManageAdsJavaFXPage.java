package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.ManageAdsJavaFXPageController;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.logging.Logger;

public class ManageAdsJavaFXPage extends ManageAdsPage {

    private static final Logger LOGGER = Logger.getLogger(ManageAdsJavaFXPage.class.getName());

    public ManageAdsJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/tabManageAds-view.fxml"));

            context.getTab(0).setText(context.getLanguage().getProperty("MANAGE_ADS_MSG"));
            context.getTab(0).setContent(loader.load());

            ManageAdsJavaFXPageController controller = loader.getController();
            controller.initialize(context);

        } catch (IOException e) {
            LOGGER.severe("Error while loading ManageAdsJavaFXPage");
        }
    }
}
