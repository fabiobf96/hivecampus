package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.SignUpPage;
import it.hivecampuscompany.hivecampus.state.javafx.LoginJavaFXPage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.security.NoSuchAlgorithmException;

/**
 * The SignUpJavaFXPageController represents the controller for the sign-up page in the JavaFX user interface.
 * It is responsible for managing the sign-up of the users.
 */

public class SignUpJavaFXPageController extends JavaFxController {
    private SignUpPage signUpPage;

    @FXML
    private Label lblSignUp;
    @FXML
    private TextField txfFirstName;
    @FXML
    private TextField txfLastName;
    @FXML
    private TextField txfEmail;
    @FXML
    private PasswordField txfPassword;
    @FXML
    private PasswordField txfConfPassword;
    @FXML
    private TextField txfTelephone;
    @FXML
    private Label lblChoose;
    @FXML
    private CheckBox ckbOwner;
    @FXML
    private CheckBox ckbTenant;
    @FXML
    private Button btnSignUp;
    @FXML
    private Label lblAccount;
    @FXML
    private Button btnLogHere;
    @FXML
    private MenuItem mibtnLangChange;
    @FXML
    private ImageView imvLang;
    @FXML
    private ImageView imvLangChange;

    private static final String ERROR_TITLE_MSG = "ERROR_TITLE_MSG";
    private static final String ERROR = "ERROR";

    public SignUpJavaFXPageController(){
        // Default constructor
    }

    /**
     * Initializes the controller with the context and the sign-up page.
     * It sets the text fields with the information of the sign-up page.
     * It also sets the language image and the language change button,
     * and the action for the sign-up and login buttons.
     *
     * @param context The context of the application
     * @param signUpPage The sign-up page
     * @author Marina Sotiropoulos
     */

    public void initializeSignUpView(Context context, SignUpPage signUpPage) {
        this.context = context;
        this.signUpPage = signUpPage;

        lblSignUp.setText(properties.getProperty("SIGN_UP_MSG"));
        txfFirstName.setPromptText(properties.getProperty("NAME_MSG"));
        txfLastName.setPromptText(properties.getProperty("SURNAME_MSG"));
        txfEmail.setPromptText(properties.getProperty("EMAIL_MSG"));
        txfPassword.setPromptText(properties.getProperty("PASSWORD_MSG"));
        txfConfPassword.setPromptText(properties.getProperty("CONFIRM_PASSWORD_MSG"));
        txfTelephone.setPromptText(properties.getProperty("PHONE_N_MSG"));
        lblChoose.setText(properties.getProperty("CHOOSE_ROLE_MSG"));
        ckbOwner.setText(properties.getProperty("OWNER_MSG"));
        ckbTenant.setText(properties.getProperty("TENANT_MSG"));
        btnSignUp.setText(properties.getProperty("SIGN_UP_MSG"));
        lblAccount.setText(properties.getProperty("ALREADY_ACCOUNT_MSG"));
        btnLogHere.setText(properties.getProperty("LOGIN_HERE_MSG"));

        setLanguageImage(imvLang, imvLangChange);
        mibtnLangChange.setOnAction(event -> handleLanguageChange(imvLangChange));

        btnSignUp.setOnAction(event -> handleSignUp());
        btnLogHere.setOnAction(event -> handleLogHere());
    }

    /**
     * Initializes the listener for the CheckBoxes.
     * If the Owner CheckBox is selected, the Tenant CheckBox is deselected and vice versa.
     *
     * @author Marina Sotiropoulos
     */

    @FXML
    private void initialize() {
        ckbOwner.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                ckbTenant.setSelected(false);
            }
        });

        ckbTenant.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                ckbOwner.setSelected(false);
            }
        });
    }

    /**
     * Handles the sign-up of the user.
     * It gets the information from the text fields and the CheckBoxes.
     * It creates the UserBean and the AccountBean with the information.
     * It registers the user with the sign-up page.
     * If the registration is successful, it shows a success message and clears the fields.
     * If there is an error, it shows an error message.
     *
     * @author Marina Sotiropoulos
     */

    private void handleSignUp() {
        String firstName = txfFirstName.getText();
        String lastName = txfLastName.getText();
        String email = txfEmail.getText();
        String password = txfPassword.getText();
        String confPassword = txfConfPassword.getText();
        String telephone = txfTelephone.getText();
        String typeAccount = null;

        boolean isOwner = ckbOwner.isSelected();
        boolean isTenant = ckbTenant.isSelected();

        if(isOwner) typeAccount = "owner";
        if (isTenant) typeAccount = "tenant";

        UserBean userBean = new UserBean();
        AccountBean accountBean = new AccountBean();

        try {
            userBean.setEmail(email);
            userBean.setNewPassword(password, confPassword);
            userBean.setRole(typeAccount);

            accountBean.setName(firstName);
            accountBean.setSurname(lastName);
            accountBean.setEmail(email);
            accountBean.setPhoneNumber(telephone);

            signUpPage.registerUser(userBean, accountBean);
            showAlert(INFORMATION, properties.getProperty("SUCCESS_TITLE_MSG"), properties.getProperty("ACCOUNT_CREATED_MSG"));
            clearFields();

            handleLogHere();

        } catch (InvalidEmailException | PasswordMismatchException | DuplicateRowException | EmptyFieldsException |
                 NoSuchAlgorithmException e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty(e.getMessage()));
        }
    }

    /**
     * Handles the login of the user. It goes to the login page.
     *
     * @author Marina Sotiropoulos
     */

    public void handleLogHere() {
        signUpPage.goToLoginPage(new LoginJavaFXPage(context));
    }

    /**
     * Clears the fields of the text fields and deselects the CheckBoxes.
     *
     * @author Marina Sotiropoulos
     */

    private void clearFields() {
        txfFirstName.clear();
        txfLastName.clear();
        txfEmail.clear();
        txfPassword.clear();
        txfConfPassword.clear();
        txfTelephone.clear();

        ckbOwner.setSelected(false);
        ckbTenant.setSelected(false);
    }
}
