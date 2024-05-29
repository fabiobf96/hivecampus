package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.ManageAdsJavaFXPageController;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The ManageAdsJavaFXPage class represents the manage ads page in the JavaFX user interface.
 * It extends the ManageAdsPage class and provides methods for displaying the manage ads page and handling user input.
 */
public class ManageAdsJavaFXPage extends ManageAdsPage {

    private static final Logger LOGGER = Logger.getLogger(ManageAdsJavaFXPage.class.getName());

    /**
     * Constructs a ManageAdsJavaFXPage object with the given context.
     * @param context The context object for the manage ads page.
     * @author Marina Sotiropoulos
     */
    public ManageAdsJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the manage ads page in the application.
     * This method loads the manage ads view, initializes the corresponding controller,
     * and displays the scene on the main stage.
     *
     * @author Marina Sotiropoulos
     */
    @Override
    public void handle() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/tabManageAds-view.fxml"));

            context.getTab(0).setText(context.getLanguage().getProperty("MANAGE_ADS_MSG"));
            context.getTab(0).setContent(loader.load());

            ManageAdsJavaFXPageController controller = loader.getController();
            controller.initialize(context);

        } catch (IOException e) {
            LOGGER.severe(context.getLanguage().getProperty("ERROR_HANDLING_MANAGE_ADS_PAGE"));
        }
    }
}
