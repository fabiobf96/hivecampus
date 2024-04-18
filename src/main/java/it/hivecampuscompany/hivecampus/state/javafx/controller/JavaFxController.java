package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Properties;

public abstract class JavaFxController {
    protected Properties properties;
    protected SessionBean sessionBean;
    protected Context context;

    protected static final String ERROR_TITLE_MSG = "ERROR_TITLE_MSG";
    protected static final String ERROR = "ERROR";

    protected static final String ITALIAN_PNG_URL = "/it/hivecampuscompany/hivecampus/images/italian_flag.png";
    protected static final String ENGLISH_PNG_URL = "/it/hivecampuscompany/hivecampus/images/english_flag.png";

    protected JavaFxController(){
        properties = LanguageLoader.getLanguageProperties();
    }

    // SessionBean get method

    protected void showAlert(String typeAlert, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.valueOf(typeAlert.toUpperCase()));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void setLabelText(Label label, String text) {
        label.setText(text);
        label.setTextFill(Color.BLACK);
    }

    protected void setLanguageImage(ImageView imvLang, ImageView imvLangChange) {

        if (LanguageLoader.getCurrentLanguage() == 0) { // Se la lingua corrente è l'inglese
            imvLang.setImage(new Image(ENGLISH_PNG_URL));
            imvLangChange.setImage(new Image(ITALIAN_PNG_URL));
        }
        else {
            imvLang.setImage((new Image(ITALIAN_PNG_URL)));
            imvLangChange.setImage(new Image(ENGLISH_PNG_URL));
        }
    }

    protected void handleLanguageChange(ImageView imvLangChange) {
        // Recupera l'URL della lingua selezionata
        String currentImageUrl = imvLangChange.getImage().getUrl();
        // Se la lingua selezionata è l'italiano
        if (currentImageUrl.equals(Objects.requireNonNull(getClass().getResource(ITALIAN_PNG_URL)).toString())) {
            LanguageLoader.loadLanguage(1);

        } else { // Se la lingua selezionata è l'inglese
            LanguageLoader.loadLanguage(0);
        }
        properties = LanguageLoader.getLanguageProperties();
        context.request();
    }

}
