package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.controller.AdSearchJavaFXPageController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Logger;

public class AdSearchJavaFXPage extends AdSearchPage {
    private static final Logger LOGGER = Logger.getLogger(AdSearchJavaFXPage.class.getName());

    public AdSearchJavaFXPage(Context context) {
        super(context);
    }

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
