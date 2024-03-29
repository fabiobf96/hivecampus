package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LanguageJavaFxController extends JavaFxController{

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

    public void initializeLanguageSettingsView() {
        lblLanguageTitle.setText(properties.getProperty("LANGUAGE_SETTINGS_MSG"));
        lblEnglish.setText(properties.getProperty("ENGLISH_MSG"));
        lblItalian.setText(properties.getProperty("ITALIAN_MSG"));

        btnEnglish.setOnAction(event -> handleChangeLanguage(0));
        btnItalian.setOnAction(event -> handleChangeLanguage(1));
    }

    private void handleChangeLanguage(int choice) {
        LanguageLoader.loadLanguage(choice);
        properties = LanguageLoader.getLanguageProperties();
        initializeLanguageSettingsView();
    }
}
