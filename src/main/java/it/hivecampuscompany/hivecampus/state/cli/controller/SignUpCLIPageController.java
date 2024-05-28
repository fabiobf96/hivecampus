package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;

/**
 * The SignUpCLIPageController class represents a controller for the sign-up page in the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying the sign-up page and collecting user information.
 */
public class SignUpCLIPageController extends CLIController {

    /**
     * Overrides the homePage method to display the sign-up page.
     * This method clears the view and displays a welcome message with "SIGN_UP_MSG" property in uppercase.
     *
     * @author Fabio Barchiesi
     */
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("SIGN_UP_MSG"));
    }

    /**
     * Retrieves user information required for sign-up.
     *
     * @return The UserBean object containing user information.
     * @throws InvalidEmailException     if the email provided is invalid.
     * @throws PasswordMismatchException if the passwords provided do not match.
     * @author Fabio Barchiesi
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
     * @author Fabio Barchiesi
     */
    public AccountBean getAccountInformation(String email) {
        AccountBean accountBean = new AccountBean();
        accountBean.setEmail(email);
        accountBean.setName(getField(properties.getProperty("NAME_MSG"), false));
        accountBean.setSurname(getField(properties.getProperty("SURNAME_MSG"), false));
        accountBean.setPhoneNumber(getField(properties.getProperty("PHONE_N_MSG"), false));
        return accountBean;
    }

    /**
     * Retrieve role information required for sing-up
     *
     * @return String that represent the role of user into the application
     * @author Fabio Barchiesi
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