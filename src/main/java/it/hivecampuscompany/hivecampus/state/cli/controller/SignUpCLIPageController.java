package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;

/**
 * The SignUpCLIPageController class represents a controller for the sign-up page in the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying the sign-up page and collecting user information.
 */
public class SignUpCLIPageController extends CLIController {

    /**
     * Displays the sign-up page with a welcome message.
     */
    @Override
    public void homePage() {
        view.displayWelcomeMessage(properties.getProperty("SIGN_UP_MSG"));
    }

    /**
     * Retrieves user information required for sign-up.
     *
     * @return The UserBean object containing user information.
     * @throws InvalidEmailException     if the email provided is invalid.
     * @throws PasswordMismatchException if the passwords provided do not match.
     */
    public UserBean getUserInformation() throws InvalidEmailException, PasswordMismatchException, EmptyFieldsException {
        UserBean userBean = new UserBean();
        userBean.setEmail(getField(properties.getProperty("EMAIL_MSG"), false));
        userBean.setNewPassword(getField(properties.getProperty("PASSWORD_MSG"), false), getField(properties.getProperty("CONFIRM_PASSWORD_MSG"), false));
        userBean.setRole(getRole());
        return userBean;
    }

    /**
     * Retrieves account information required for sign-up.
     *
     * @return The AccountBean object containing account information.
     */
    public AccountBean getAccountInformation() {
        AccountBean accountBean = new AccountBean();
        accountBean.setName(getField(properties.getProperty("NAME_MSG"), false));
        accountBean.setSurname(getField(properties.getProperty("SURNAME_MSG"), false));
        accountBean.setPhoneNumber(getField(properties.getProperty("PHONE_N_MSG"), false));
        return accountBean;
    }

    /**
     * Retrieve role information required for sing-up
     *
     * @return String that represent the role of user into the application
     */
    private String getRole() {
        while (true) {
            view.displayMessage("1. " + properties.getProperty("OWNER_MSG"));
            view.displayMessage("2. " + properties.getProperty("TENANT_MSG"));
            switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
                case 1 -> {
                    return "owner";
                }
                case 2 -> {
                    return "tenant";
                }
                default -> view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
            }
        }
    }
}
