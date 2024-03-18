package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.view.gui.cli.CLIView;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;

public class SignupCLIController extends CLIController {
    public SignupCLIController() {
        view = new CLIView();
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
                userBean.setEmail(getField(properties.getProperty("EMAIL_MSG")));
                incorrect = false;
            } catch (InvalidEmailException ex) {
                view.displayMessage(properties.getProperty(ex.getMessage()));
            }
        }

        incorrect = true;
        while (incorrect) {
            try {
                userBean.setNewPassword(getField(properties.getProperty("PASSWORD_MSG")), getField(properties.getProperty("CONFIRM_PASSWORD_MSG")));
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
        userBean.setRole(typeAccount);
        return userBean;
    }
    public AccountBean createAccountInformation(UserBean userBean){
        AccountBean accountBean = new AccountBean();
        accountBean.setEmail(userBean.getEmail());
        accountBean.setName(getField(properties.getProperty("NAME_MSG")));
        accountBean.setSurname(getField(properties.getProperty("SURNAME_MSG")));
        accountBean.setPhoneNumber(getField(properties.getProperty("PHONE_N_MSG")));
        view.clean();
        return accountBean;
    }
}
