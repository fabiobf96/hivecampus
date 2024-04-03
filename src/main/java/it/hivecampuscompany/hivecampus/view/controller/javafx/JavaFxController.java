package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import javafx.scene.control.Alert;
import java.util.Properties;

public abstract class JavaFxController {
    protected Properties properties;
    protected SessionBean sessionBean;

    protected static final String ERROR_TITLE_MSG = "ERROR_TITLE_MSG";
    protected static final String ERROR = "ERROR";

    protected static final String ITALIAN_PNG_URL = "/it/hivecampuscompany/hivecampus/images/italian_flag.png";
    protected static final String ENGLISH_PNG_URL = "/it/hivecampuscompany/hivecampus/images/english_flag.png";

    protected JavaFxController(){
        properties = LanguageLoader.getLanguageProperties();
    }

    /*
    protected static SessionBean getSessionBean(){
        return sessionBean;
    }
    */

    protected void showAlert(String typeAlert, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.valueOf(typeAlert.toUpperCase()));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
