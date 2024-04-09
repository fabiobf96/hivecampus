package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import java.util.logging.Logger;

public class SignupCLIController extends CLIController {

    private static final Logger LOGGER = Logger.getLogger(SignupCLIController.class.getName());

    public SignupCLIController() {
        view = new CliGUI();
        homePage();
    }
    @Override
    public void homePage(){
        view.displayWelcomeMessage(properties.getProperty("SIGN_UP_MSG"));
    }
    public UserBean createUserInformation() {
        UserBean userBean = new UserBean();
        boolean incorrect = true;
        while (incorrect) {
            try {
                userBean.setEmail(getField(properties.getProperty("EMAIL_MSG"),false));
                incorrect = false;
            } catch (InvalidEmailException ex) {
                view.displayMessage(properties.getProperty(ex.getMessage()));
            }
        }

        incorrect = true;
        while (incorrect) {
            try {
                userBean.setNewPassword(getField(properties.getProperty("PASSWORD_MSG"),false), getField(properties.getProperty("CONFIRM_PASSWORD_MSG"), false));
                incorrect = false;
            } catch (PasswordMismatchException ex) {
                view.displayMessage(properties.getProperty(ex.getMessage()));
            }
        }
        incorrect = true;
        String typeAccount = null;
        while (incorrect) {
            view.displayMessage("1. " + properties.getProperty("OWNER_MSG"));
            view.displayMessage("2. " + properties.getProperty("TENANT_MSG"));
            switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
                case 1 -> {
                    typeAccount = "owner";
                    incorrect = false;
                }
                case 2 -> {
                    typeAccount = "tenant";
                    incorrect = false;
                }
                default -> view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
            }
        }
        try {           /// devo essere gestito poi diversamente (ho aggiunto il try-catch)
            userBean.setRole(typeAccount);
        } catch (EmptyFieldsException e) {
            LOGGER.severe(e.getMessage());
            System.exit(1);
        }
        return userBean;
    }
    public AccountBean createAccountInformation(UserBean userBean){
        AccountBean accountBean = new AccountBean();
        accountBean.setEmail(userBean.getEmail());
        accountBean.setName(getField(properties.getProperty("NAME_MSG"), false));
        accountBean.setSurname(getField(properties.getProperty("SURNAME_MSG"), false));
        accountBean.setPhoneNumber(getField(properties.getProperty("PHONE_N_MSG"), false));
        view.clean();
        return accountBean;
    }
}
