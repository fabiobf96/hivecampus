package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.ManageRequestsTenantJavaFXPageController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Logger;

public class ManageRequestsTenantJavaFXPage extends ManageRequestsPage {

    private static final Logger LOGGER = Logger.getLogger(ManageRequestsTenantJavaFXPage.class.getName());

    public ManageRequestsTenantJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/tabManageRequests-view.fxml"));

            context.getTab(1).setText(context.getLanguage().getProperty("MANAGE_REQUEST_MSG"));
            context.getTab(1).setContent(loader.load());

            ManageRequestsTenantJavaFXPageController controller = loader.getController();
            controller.initialize(context);

        } catch (IOException e) {
            LOGGER.severe(context.getLanguage().getProperty("ERROR_HANDLING_MANAGE_REQUESTS_PAGE"));
        }
    }
}
