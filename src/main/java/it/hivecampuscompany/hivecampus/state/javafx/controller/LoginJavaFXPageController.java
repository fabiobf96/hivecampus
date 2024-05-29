package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.LoginPage;
import it.hivecampuscompany.hivecampus.state.javafx.OwnerHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.SignUpJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.TenantHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.security.NoSuchAlgorithmException;

/**
 * The LoginJavaFXPageController class represents a controller for the login page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the login view and handling user interactions.
 */

public class LoginJavaFXPageController extends JavaFxController {

    private LoginPage loginPage;

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


    public LoginJavaFXPageController(){
        // Default constructor
    }

    /**
     * Initializes the login view with the login label, email and password fields, login button, Google login button, and sign-up button.
     * It sets the text for the labels and buttons based on the current language.
     *
     * @param context The context object for the login page.
     * @param loginPage The login page object.
     * @author Marina Sotiropoulos
     */

    public void initialize(Context context, LoginPage loginPage) {
        this.context = context;
        this.loginPage = loginPage;

        lblLogin.setText(properties.getProperty("LOGIN_MSG"));
        txfEmail.setPromptText(properties.getProperty("EMAIL_MSG"));
        txfPassword.setPromptText(properties.getProperty("PASSWORD_MSG"));
        btnLogin.setText(properties.getProperty("LOGIN_MSG"));
        lblOr.setText(properties.getProperty("OR_MSG"));
        btnGoogle.setText(properties.getProperty("LOGIN_WITH_GOOGLE_MSG"));
        lblAccount.setText(properties.getProperty("DON_T_HAVE_ACCOUNT_MSG"));
        btnSignUp.setText(properties.getProperty("SIGN_UP_MSG"));

        setLanguageImage(imvLang, imvLangChange);

        mibtnLangChange.setOnAction(event -> handleLanguageChange(imvLangChange));

        btnLogin.setOnAction(event -> handleLogin());

        btnSignUp.setOnAction(event -> handleSignUp());
        btnGoogle.setOnAction(event -> handleGoogleLogin());
    }

    /**
     * Handles the login process based on the user's email and password.
     * It authenticates the user and sets the session bean into the context.
     * It shows the correct homepage based on the account type.
     *
     * @author Marina Sotiropoulos
     */

    private void handleLogin() {
        String email = txfEmail.getText();
        String password = txfPassword.getText();
        UserBean userBean = new UserBean();

        try {
            userBean.setEmail(email);
            userBean.setPassword(password);
            sessionBean = loginPage.authenticate(userBean);

            context.setSessionBean(sessionBean);

            clearFields();

            showHomePage(sessionBean);
        }
        catch (InvalidEmailException | AuthenticateException | NoSuchAlgorithmException e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty(e.getMessage()));
        }
    }

    /**
     * Handles the user's request to sign up.
     * It redirects the user to the sign-up page.
     *
     * @author Marina Sotiropoulos
     */

    public void handleSignUp() {
       loginPage.goToSignUpPage(new SignUpJavaFXPage(context));
    }

    /**
     * Handles the user's request to log in with Google.
     * It displays a message indicating that the feature is not implemented.
     *
     * @author Marina Sotiropoulos
     */

    public void handleGoogleLogin() {
        showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    /**
     * Shows the homepage based on the user's role.
     * It redirects the user to the owner or tenant homepage.
     *
     * @param sessionBean The session bean object for the user.
     * @author Marina Sotiropoulos
     */

    private void showHomePage(SessionBean sessionBean) {
        try {
            switch (sessionBean.getRole()){
                case ("owner") -> loginPage.goToOwnerHomePage(new OwnerHomeJavaFXPage(context));
                case ("tenant") -> loginPage.goToTenantHomePage(new TenantHomeJavaFXPage(context));
                default -> System.exit(3);
            }
        } catch (Exception e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("ERROR_HOMEPAGE_WINDOW_MSG"));
            System.exit(1);
        }
        context.request();
    }

    /**
     * Clears the email and password fields.
     *
     * @author Marina Sotiropoulos
     */

    private void clearFields() {
        txfEmail.clear();
        txfPassword.clear();
    }
}