package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.controller.AdSearchJavaFXPageController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class AdSearchJavaFXPage extends AdSearchPage {

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
            throw new RuntimeException(e);
        }
    }
}
