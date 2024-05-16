package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.LoginJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.SignUpJavaFXPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 * The InitialJavaFXPageController class represents a controller for the initial page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the initial view and handling user interactions.
 */

public class InitialJavaFXPageController extends JavaFxController {

    @FXML
    Button btnLoginChoice;
    @FXML
    Button btnSignUpChoice;
    @FXML
    private MenuItem mibtnLangChange;
    @FXML
    private ImageView imvLang;
    @FXML
    private ImageView imvLangChange;

    /**
     * Initializes the initial view with the login and sign-up buttons.
     * It sets the text for the login and sign-up buttons based on the current language.
     *
     * @param context The context object for the initial page.
     * @param initialJavaFXPage The initial JavaFX page object.
     */

    public void initialize(Context context, InitialJavaFXPage initialJavaFXPage) {
        this.context = context;

        btnLoginChoice.setText(properties.getProperty("LOGIN_MSG"));
        btnSignUpChoice.setText(properties.getProperty("SIGN_UP_MSG"));

        btnLoginChoice.setOnAction(e -> initialJavaFXPage.goToLoginPage(new LoginJavaFXPage(initialJavaFXPage.getContext())));
        btnSignUpChoice.setOnAction(e -> initialJavaFXPage.goToSignUpPage(new SignUpJavaFXPage(initialJavaFXPage.getContext())));
        setLanguageImage(imvLang, imvLangChange);
        mibtnLangChange.setOnAction(event -> handleLanguageChange(imvLangChange));
    }
}
