package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.ManageRequestsTenantJavaFXPageController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * The ManageRequestsTenantJavaFXPage class represents the manage requests page in the JavaFX user interface for tenants.
 * It extends the ManageRequestsPage class and provides methods for displaying the manage requests page and handling user input.
 */
public class ManageRequestsTenantJavaFXPage extends ManageRequestsPage {

    private static final Logger LOGGER = Logger.getLogger(ManageRequestsTenantJavaFXPage.class.getName());

    /**
     * Constructs a ManageRequestsTenantJavaFXPage object with the given context.
     * @param context The context object for the manage requests page.
     * @author Marina Sotiropoulos
     */
    public ManageRequestsTenantJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the manage requests page in the application.
     * This method loads the manage requests view, initializes the corresponding controller,
     * and displays the scene on the main stage.
     *
     * @author Marina Sotiropoulos
     */
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
