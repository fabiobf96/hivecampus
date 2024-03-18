package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.bean.SessionBean;

import java.util.Properties;

public abstract class JavaFxController {
    protected Properties properties;
    protected SessionBean sessionBean;

    protected JavaFxController(){
        properties = LanguageLoader.getLanguageProperties();
    }
}
