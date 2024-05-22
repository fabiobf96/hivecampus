package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.*;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.SignUpPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.SignUpCLIPageController;

import java.security.NoSuchAlgorithmException;

/**
 * The SignUpCLIPage class represents the sign-up page in the command-line interface (CLI).
 * It extends the SignUpPage class and provides methods for handling user interactions on the CLI sign-up page.
 */
public class SignUpCLIPage extends SignUpPage {

    private final SignUpCLIPageController controller;

    /**
     * Constructs a SignUpCLIPage object with the given context.
     *
     * @param context The context object for the sign-up page.
     */
    protected SignUpCLIPage(Context context) {
        super(context);
        controller = new SignUpCLIPageController();
    }

    /**
     * Handles user interactions on the sign-up page in the CLI.
     * It prompts the user for information, registers the user, and navigates to the initial page upon successful registration.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    @Override
    public void handle() throws InvalidSessionException {
        try {
            controller.homePage();
            UserBean userBean = controller.getUserInformation();
            AccountBean accountBean = controller.getAccountInformation();
            registerUser(userBean, accountBean);
            controller.successMessage("SIGN_UP");
            goToInitialPage(new InitialCLIPage(context));
        } catch (PasswordMismatchException | InvalidEmailException | EmptyFieldsException e) {
            controller.displayError(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            controller.displayError(e.getMessage());
            controller.exit();
        } catch (DuplicateRowException e) {
            controller.displayError(e.getMessage());
            goToInitialPage(new InitialCLIPage(context));
        }
        context.request();
    }
}
