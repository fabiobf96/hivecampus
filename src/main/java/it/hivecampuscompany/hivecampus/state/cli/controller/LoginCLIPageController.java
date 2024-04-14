package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

public class LoginCLIPageController extends CLIController {
    public LoginCLIPageController() {
        properties = LanguageLoader.getLanguageProperties();
        view = new CliGUI();
    }
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("LOGIN_MSG").toUpperCase());
    }

    public UserBean getCredentials() throws InvalidEmailException {
        UserBean userBean = new UserBean();
        userBean.setEmail(getField(properties.getProperty("EMAIL_MSG"),false));
        userBean.setPassword(getField(properties.getProperty("PASSWORD_MSG"),false));
        view.clean();
        return userBean;
    }
}
