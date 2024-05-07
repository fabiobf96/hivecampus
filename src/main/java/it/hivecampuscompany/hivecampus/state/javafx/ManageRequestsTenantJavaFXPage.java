package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.ManageRequestsTenantJavaFXPageController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ManageRequestsTenantJavaFXPage extends ManageRequestsPage {

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
            throw new RuntimeException(e);
        }
    }
}
