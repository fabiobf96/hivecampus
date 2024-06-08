package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * The JavaFxController class represents a controller for the JavaFX user interface.
 * It provides methods for handling user interactions and displaying alerts.
 */

public abstract class JavaFxController {
    protected Properties properties;
    protected SessionBean sessionBean;
    protected Context context;

    protected static final String ERROR_TITLE_MSG = "ERROR_TITLE_MSG";
    protected static final String WARNING_TITLE_MSG = "WARNING_TITLE_MSG";
    protected static final String INFORMATION_TITLE_MSG = "INFORMATION_TITLE_MSG";
    protected static final String ERROR = "ERROR";
    protected static final String WARNING = "WARNING";
    protected static final String INFORMATION = "INFORMATION";

    protected static final String ITALIAN_PNG_URL = "/it/hivecampuscompany/hivecampus/images/italian_flag.png";
    protected static final String ENGLISH_PNG_URL = "/it/hivecampuscompany/hivecampus/images/english_flag.png";

    protected JavaFxController(){
        properties = LanguageLoader.getLanguageProperties();
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param typeAlert The type of alert dialog (ERROR, WARNING, INFORMATION).
     * @param title The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     * @author Marina Sotiropoulos
     */

    protected void showAlert(String typeAlert, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.valueOf(typeAlert.toUpperCase()));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the text and color of a label.
     *
     * @param label The label to set the text and color.
     * @param text The text to set in the label.
     * @author Marina Sotiropoulos
     */

    protected void setLabelText(Label label, String text) {
        label.setText(text);
        label.setTextFill(Color.BLACK);
    }

    /**
     * Sets the images of the language flags.
     * If the current language is English, the English flag is displayed and the Italian flag is hidden and vice versa.
     *
     * @param imvLang The image view of the current language flag.
     * @param imvLangChange The image view of the other language flag.
     * @author Marina Sotiropoulos
     */

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

    /**
     * Handles the language change event.
     * It retrieves the URL of the selected language and checks if it is Italian or English.
     * Then it loads the selected language and updates the context.
     *
     * @param imvLangChange The image view of the selected language flag.
     * @author Marina Sotiropoulos
     */

    protected void handleLanguageChange(ImageView imvLangChange) {
        String currentImageUrl = imvLangChange.getImage().getUrl();

        if (currentImageUrl.equals(Objects.requireNonNull(getClass().getResource(ITALIAN_PNG_URL)).toString())) {
            LanguageLoader.loadLanguage(1);

        } else {
            LanguageLoader.loadLanguage(0);
        }
        context.setLanguage(LanguageLoader.getLanguageProperties());
        context.request();
    }

    /**
     * Sets the image of the room or home in the image view.
     * If the choice is "room", it sets the image of the room in the image view.
     * If the choice is "home", it sets the image of the home in the image view.
     *
     * @param imageView The image view to set the image.
     * @param adBean The AdBean object representing the ad.
     * @param choice The choice of the image to set (room or home).
     * @author Marina Sotiropoulos
     */

    protected void setImage (ImageView imageView, AdBean adBean, String choice) {
        byte[] imageBytes;
        if (choice.equals("room")) {
            imageBytes = adBean.getRoomBean().getImage();
        }
        else imageBytes = adBean.getHomeBean().getImage();

        if (imageBytes != null) {
            imageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
            imageView.setPreserveRatio(false);
        }
    }

    /**
     * Retrieves the account information of the current user.
     *
     * @return The AccountBean object representing the account information.
     * @author Marina Sotiropoulos
     */

    protected AccountBean getAccountInfo() {
        LoginManager manager = new LoginManager();
        return manager.getAccountInfo(context.getSessionBean());
    }

}
