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

    @FXML
    private void initialize() {
        // Aggiungi un listener alle CheckBox per gestire il controllo
        ckbOwner.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                // Se Owner è selezionato, deseleziona Tenant
                ckbTenant.setSelected(false);
            }
        });

        ckbTenant.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                // Se Tenant è selezionato, deseleziona Owner
                ckbOwner.setSelected(false);
            }
        });
    }

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

        //Controllo campi inseriti
        try {
            userBean.setEmail(email);
            userBean.setNewPassword(password, confPassword); // controllo password in userBean
            userBean.setRole(typeAccount);

            accountBean.setName(firstName);
            accountBean.setSurname(lastName);
            accountBean.setEmail(email);
            accountBean.setPhoneNumber(telephone);

            signUpPage.registerUser(userBean, accountBean);
            //mostro il messaggio di successo e svuoto i campi.
            showAlert(INFORMATION, properties.getProperty("SUCCESS_TITLE_MSG"), properties.getProperty("ACCOUNT_CREATED_MSG"));
            clearFields();

            handleLogHere();

        } catch (InvalidEmailException | PasswordMismatchException | DuplicateRowException | EmptyFieldsException |
                 NoSuchAlgorithmException e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty(e.getMessage()));
        }
    }

    public void handleLogHere() {
        signUpPage.goToLoginPage(new LoginJavaFXPage(context));
    }

    private void clearFields() {
        // Svuota i campi dei TextField
        txfFirstName.clear();
        txfLastName.clear();
        txfEmail.clear();
        txfPassword.clear();
        txfConfPassword.clear();
        txfTelephone.clear();

        // Deseleziona le CheckBox
        ckbOwner.setSelected(false);
        ckbTenant.setSelected(false);
    }
}
