package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.view.gui.cli.CLIView;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;

public class LoginCLIController extends CLIController {
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
        view.clean();
        return userBean;
    }
}
