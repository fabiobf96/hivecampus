package it.hivecampuscompany.hivecampus.graphic.cli.controller;

import it.hivecampuscompany.hivecampus.logic.bean.UserBean;
import it.hivecampuscompany.hivecampus.graphic.cli.view.CLIView;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidEmailException;

public class LoginCLIController extends CLIController{
    public LoginCLIController(){
        view = new CLIView();
        homePage();
    }
    @Override
    public void homePage() {
        view.displayWelcomeMessage(properties.getProperty("LOGIN_MSG").toUpperCase());
    }

    public UserBean getCredentials(){
        UserBean userBean = new UserBean();
        try {
            userBean.setEmail(getField(properties.getProperty("EMAIL_MSG")));
            userBean.setPassword(getField(properties.getProperty("PASSWORD_MSG")));
        } catch (InvalidEmailException e) {
            view.displayMessage(e.getMessage());
            getCredentials();
        }
        return userBean;
    }
}
