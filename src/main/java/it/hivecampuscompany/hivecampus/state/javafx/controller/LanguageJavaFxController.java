package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The LanguageJavaFxController class represents a controller for the language settings page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the language settings view and handling user interactions.
 */

public class LanguageJavaFxController extends JavaFxController {

    @FXML
    private Label lblLanguageTitle;
    @FXML
    private Label lblEnglish;
    @FXML
    private Label lblItalian;
    @FXML
    private Button btnEnglish;
    @FXML
    private Button btnItalian;

    public LanguageJavaFxController(){
        // Default constructor
    }

    /**
     * Initializes the language settings view with the language options.
     * It sets the text for the language title and buttons based on the current language.
     *
     * @param context The context object for the language settings page.
     */

    public void initializeLanguageSettingsView(Context context) {
        this.context = context;
        lblLanguageTitle.setText(properties.getProperty("LANGUAGE_SETTINGS_MSG"));
        lblEnglish.setText(properties.getProperty("ENGLISH_MSG"));
        lblItalian.setText(properties.getProperty("ITALIAN_MSG"));

        btnEnglish.setOnAction(event -> handleChangeLanguage(0));
        btnItalian.setOnAction(event -> handleChangeLanguage(1));
    }

    /**
     * Handles the language change based on the user's choice.
     * It loads the language properties file and sets the context language.
     *
     * @param choice The user's choice for the language.
     */

    private void handleChangeLanguage(int choice) {
        LanguageLoader.loadLanguage(choice);
        properties = LanguageLoader.getLanguageProperties();
        context.setLanguage(properties);
        initializeLanguageSettingsView(context);
    }
}
