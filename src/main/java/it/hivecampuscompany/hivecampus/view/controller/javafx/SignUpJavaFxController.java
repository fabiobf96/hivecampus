package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.view.gui.javafx.LoginJavaFxGUI;
import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpJavaFxController extends JavaFxController {

    private final LoginManager manager;

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
    private CheckBox ckbOwner;

    @FXML
    private CheckBox ckbTenant;

    @FXML
    private Button btnLogHere;

    public SignUpJavaFxController(){
        this.manager = new LoginManager();
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

    @FXML
    private void handleSignUpButtonClick() {
        String firstName = txfFirstName.getText();
        String lastName = txfLastName.getText();
        String email = txfEmail.getText();
        String password = txfPassword.getText();
        String confPassword = txfConfPassword.getText();
        String telephone = txfTelephone.getText();
        String typeAccount = null;

        boolean isOwner = ckbOwner.isSelected();
        boolean isTenant = ckbTenant.isSelected();

        // Controllo che entrambe le checkbox non siano selezionate contemporaneamente
        if (isOwner || isTenant) {
            if(isOwner) {
                typeAccount = "owner";
            } else {
                typeAccount = "tenant";
            }
        } else {
            // Messaggio di errore o azioni da intraprendere quando entrambe le checkbox sono deselezionate
            String ckbMessage = "Selezionare un tipo utente.";
            showErrorAlert(ckbMessage);
        }

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

            manager.signup(userBean, accountBean);
            //Account creato con successo
            //mostro il messaggio di successo e svuoto i campi.
            showSuccessAlert();
            clearFields();
            handleLogHereButtonClick();

        } catch (InvalidEmailException | PasswordMismatchException | DuplicateRowException e) {
            showErrorAlert(e.getMessage());
        }
    }

    public void handleLogHereButtonClick() {
        Stage stage = (Stage) btnLogHere.getScene().getWindow();

        LoginJavaFxGUI login = new LoginJavaFxGUI();
        try {
            login.start(stage);
        } catch (Exception e) {
            showErrorAlert("Error loading login window.");
        }

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

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("Account creato con successo!");
        alert.showAndWait();
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
