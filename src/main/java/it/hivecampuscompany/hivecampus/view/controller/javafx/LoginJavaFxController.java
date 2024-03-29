package it.hivecampuscompany.hivecampus.view.controller.javafx;


import it.hivecampuscompany.hivecampus.view.gui.javafx.OwnerHomePageJavaFxGUI;
import it.hivecampuscompany.hivecampus.view.gui.javafx.SignUpJavaFxGUI;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import it.hivecampuscompany.hivecampus.view.gui.javafx.TenantHomePageJavaFxGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;


public class LoginJavaFxController extends JavaFxController {

    private final LoginManager manager;

    @FXML
    private Label lblLogin;

    @FXML
    private TextField txfEmail;

    @FXML
    private PasswordField txfPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblOr;

    @FXML
    private Button btnGoogle;

    @FXML
    private Label lblAccount;

    @FXML
    private Button btnSignUp;

    @FXML
    private MenuItem mibtnLangChange;

    @FXML
    private ImageView imvLang;

    @FXML
    private ImageView imvLangChange;



    private static final String ERROR_TITLE_MSG = "ERROR_TITLE_MSG";
    private static final String ERROR = "ERROR";


    public LoginJavaFxController(){
        this.manager = new LoginManager();

    }

    public void initialize(){
        lblLogin.setText(properties.getProperty("LOGIN_MSG"));
        txfEmail.setPromptText(properties.getProperty("EMAIL_MSG"));
        txfPassword.setPromptText(properties.getProperty("PASSWORD_MSG"));
        btnLogin.setText(properties.getProperty("LOGIN_MSG"));
        lblOr.setText(properties.getProperty("OR_MSG"));
        btnGoogle.setText(properties.getProperty("LOGIN_WITH_GOOGLE_MSG"));
        lblAccount.setText(properties.getProperty("DON_T_HAVE_ACCOUNT_MSG"));
        btnSignUp.setText(properties.getProperty("SIGN_UP_MSG"));

        setLanguageImage();

        mibtnLangChange.setOnAction(event -> handleLanguageChange());
        btnLogin.setOnAction(event -> handleLogin());
        btnSignUp.setOnAction(event -> handleSignUp());
        btnGoogle.setOnAction(event -> handleGoogleLogin());

    }

    private void setLanguageImage() {

        if (LanguageLoader.getCurrentLanguage() == 0) { // Se la lingua corrente è l'inglese
            imvLang.setImage(new Image(ENGLISH_PNG_URL));
            imvLangChange.setImage(new Image(ITALIAN_PNG_URL));
        }
        else {
            imvLang.setImage((new Image(ITALIAN_PNG_URL)));
            imvLangChange.setImage(new Image(ENGLISH_PNG_URL));
        }
    }

    private void handleLanguageChange() {
        // Recupera l'URL della lingua selezionata
        String currentImageUrl = imvLangChange.getImage().getUrl();
        // Se la lingua selezionata è l'italiano
        if (currentImageUrl.equals(Objects.requireNonNull(getClass().getResource(ITALIAN_PNG_URL)).toString())) {
            LanguageLoader.loadLanguage(1);

        } else { // Se la lingua selezionata è l'inglese
            LanguageLoader.loadLanguage(0);
        }
        properties = LanguageLoader.getLanguageProperties();
        initialize();
    }

    private void handleLogin() {
        String email = txfEmail.getText();
        String password = txfPassword.getText();
        UserBean userBean = new UserBean();

        try {
            userBean.setEmail(email);
            userBean.setPassword(password);
            sessionBean = manager.login(userBean);
            // Login successfully and empty the fields
            clearFields();
            // Shows the correct homepage based on the account type
            showHomePage(sessionBean);
        }
        catch (InvalidEmailException | PasswordMismatchException e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty(e.getMessage()));
        }
    }

    public void handleSignUp() {
        Stage stage = (Stage) btnSignUp.getScene().getWindow();

        SignUpJavaFxGUI signUp = new SignUpJavaFxGUI();
        try {
            signUp.start(stage);
        } catch (Exception e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("ERROR_SIGNUP_WINDOW_MSG"));
        }
    }

    public void handleGoogleLogin() {
        showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    private void showHomePage(SessionBean sessionBean) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        try {
            switch (sessionBean.getRole()){
                case ("owner"):
                    new OwnerHomePageJavaFxGUI().startWithSession(stage, sessionBean);
                    break;

                case ("tenant"):
                    new TenantHomePageJavaFxGUI().startWithSession(stage, sessionBean);
                    break;

                default : System.exit(3);
            }
        } catch (Exception e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("ERROR_HOMEPAGE_WINDOW_MSG"));
            System.exit(1);
        }
    }

    private void clearFields() {
        txfEmail.clear();
        txfPassword.clear();
    }
}