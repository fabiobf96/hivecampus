package it.hivecampuscompany.hivecampus.stateCli.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

public class SignUpCLIPageController extends CLIController {

    public SignUpCLIPageController() {
        view = new CliGUI();
        homePage();
    }
    @Override
    public void homePage(){
        view.displayWelcomeMessage(properties.getProperty("SIGN_UP_MSG"));
    }

    public UserBean getUserInformation() throws InvalidEmailException, PasswordMismatchException {
        UserBean userBean = new UserBean();
        userBean.setEmail(getField(properties.getProperty("EMAIL_MSG"), false));
        userBean.setNewPassword(getField(properties.getProperty("PASSWORD_MSG"), false), getField(properties.getProperty("CONFIRM_PASSWORD_MSG"), false));
        return userBean;
    }

    public AccountBean getAccountInformation(){
        AccountBean accountBean = new AccountBean();
        accountBean.setName(getField(properties.getProperty("NAME_MSG"), false));
        accountBean.setSurname(getField(properties.getProperty("SURNAME_MSG"), false));
        accountBean.setPhoneNumber(getField(properties.getProperty("PHONE_N_MSG"), false));
        return accountBean;
    }
}
