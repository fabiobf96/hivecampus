package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

public class LoginCLIController extends CLIController {
    public LoginCLIController(){
        view = new CliGUI();
        homePage();
    }
    @Override
    public void homePage() {
        view.displayWelcomeMessage(properties.getProperty("LOGIN_MSG").toUpperCase());
    }

    public UserBean getCredentials(){
        UserBean userBean = new UserBean();
        try {
            userBean.setEmail(getField(properties.getProperty("EMAIL_MSG"),false));
            userBean.setPassword(getField(properties.getProperty("PASSWORD_MSG"),false));
        } catch (InvalidEmailException e) {
            view.displayMessage(e.getMessage());
            getCredentials();
        }
        view.clean();
        return userBean;
    }
}
