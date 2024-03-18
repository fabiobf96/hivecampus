package it.hivecampuscompany.hivecampus.view.controller.javafx;


import it.hivecampuscompany.hivecampus.view.gui.javafx.SignUpJavaFxGUI;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginJavaFxController extends JavaFxController {

    private final LoginManager manager;

    @FXML
    private TextField txfEmail;

    @FXML
    private PasswordField txfPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;


    public LoginJavaFxController(){
        this.manager = new LoginManager();
    }

    @FXML
    private void handleLoginButtonClick() {
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
            showHomePage(sessionBean, userBean);
        }
        catch (InvalidEmailException | PasswordMismatchException e) {
            showErrorAlert(e.getMessage());
        }
    }

    public void handleSignUpButtonClick() {
        Stage stage = (Stage) btnSignUp.getScene().getWindow();

        SignUpJavaFxGUI signUp = new SignUpJavaFxGUI();
        try {
            signUp.start(stage);
        } catch (Exception e) {
            showErrorAlert("Error loading signup window.");
        }
    }

    public void handleGoogleButtonClick() {
        String msg = "Sorry, this function is not available yet";
        showErrorAlert(msg);
    }

    private void showHomePage(SessionBean sessionBean, UserBean userBean) {
        //Stage stage = (Stage) btnLogin.getScene().getWindow();
        try {
            switch (userBean.getRole()){
                case ("owner"):
                    System.out.println("Owner homepage.");
                    break;

                case ("tenant"):
                    System.out.println("Tenant homepage.");
                    break;

                default : System.exit(3);
            }
        } catch (Exception e) {
            showErrorAlert("Error loading homepage window.");
            System.exit(1);
        }
    }


    private void clearFields() {
        txfEmail.clear();
        txfPassword.clear();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
