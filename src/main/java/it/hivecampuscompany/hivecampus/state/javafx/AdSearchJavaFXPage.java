package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.controller.AdSearchJavaFXPageController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * The AdSearchJavaFXPage class represents the ad search page in the JavaFX user interface.
 * It extends the AdSearchPage class and provides methods for displaying the ad search page and handling user input.
 */
public class AdSearchJavaFXPage extends AdSearchPage {
    private static final Logger LOGGER = Logger.getLogger(AdSearchJavaFXPage.class.getName());

    /**
     * Constructs an AdSearchJavaFXPage object with the given context.
     * @param context The context object for the ad search page.
     * @author Marina Sotiropoulos
     */
    public AdSearchJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the ad search page in the application.
     * This method loads the ad search view, initializes the corresponding controller,
     * and displays the scene on the main stage.
     *
     * @author Marina Sotiropoulos
     */
    @Override
    public void handle() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/tabAdSearch-view.fxml"));

            context.getTab(0).setText(context.getLanguage().getProperty("SEARCH_AD_MSG"));
            context.getTab(0).setContent(loader.load());

            AdSearchJavaFXPageController controller = loader.getController();
            controller.initialize(context);

        } catch (IOException e) {
            LOGGER.severe(context.getLanguage().getProperty("ERROR_HANDLING_AD_SEARCH_PAGE"));
        }
    }
}
