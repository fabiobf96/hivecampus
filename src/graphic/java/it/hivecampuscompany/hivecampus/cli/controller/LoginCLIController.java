package it.hivecampuscompany.hivecampus.cli.controller;

import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.cli.view.CLIView;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;

import javax.security.auth.login.FailedLoginException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class LoginCLIController extends CLIController{
    @Override
    public void homePage() {
        view = new CLIView();
        view.displayWelcomeMessage(properties.getProperty("LOGIN_MSG").toUpperCase());
    }

    public UserBean getCredentials(){
        UserBean userBean = new UserBean();
        try {
            userBean.setEmail(getField(properties.getProperty("EMAIL_MSG") +": "));
            userBean.setPassword(getField(properties.getProperty("PASSWORD_MSG")+ ": "));
        } catch (InvalidEmailException e) {
            view.displayMessage(e.getMessage());
            getCredentials();
        }
        return userBean;
    }
}
