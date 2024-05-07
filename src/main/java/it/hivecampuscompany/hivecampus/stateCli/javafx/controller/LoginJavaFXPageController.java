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

    private void handleLogin() {
        String email = txfEmail.getText();
        String password = txfPassword.getText();
        UserBean userBean = new UserBean();

        try {
            userBean.setEmail(email);
            userBean.setPassword(password);
            sessionBean = loginPage.authenticate(userBean);
            // Set the session bean into the context
            context.setSessionBean(sessionBean);
            // Login successfully and empty the fields
            clearFields();
            // Shows the correct homepage based on the account type
            showHomePage(sessionBean);
        }
        catch (InvalidEmailException | AuthenticateException | NoSuchAlgorithmException e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty(e.getMessage()));
        }
    }

    public void handleSignUp() {
       loginPage.goToSignUpPage(new SignUpJavaFXPage(context));
    }

    public void handleGoogleLogin() {
        showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

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

    private void clearFields() {
        txfEmail.clear();
        txfPassword.clear();
    }
}