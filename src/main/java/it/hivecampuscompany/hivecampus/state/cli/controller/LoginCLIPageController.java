package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;

/**
 * The LoginCLIPageController class represents a controller for the login page of the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying the login page and getting user credentials.
 */
public class LoginCLIPageController extends CLIController {

    /**
     * Overrides the homePage method to display the login page.
     * This method clears the view and displays a welcome message with "LOGIN_MSG".
     */
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("LOGIN_MSG").toUpperCase());
    }

    /**
     * Gets the user's login credentials from the CLI interface.
     *
     * @return The UserBean object containing the user's email and password.
     * @throws InvalidEmailException if the email entered by the user is invalid.
     */
    public UserBean getCredentials() throws InvalidEmailException {
        UserBean userBean = new UserBean();
        userBean.setEmail(getField(properties.getProperty("EMAIL_MSG"), false));
        userBean.setPassword(getField(properties.getProperty("PASSWORD_MSG"), false));
        view.clean();
        return userBean;
    }
}

