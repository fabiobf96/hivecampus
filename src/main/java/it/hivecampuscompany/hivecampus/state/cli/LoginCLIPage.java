package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.LoginPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.LoginCLIPageController;

import java.security.NoSuchAlgorithmException;

/**
 * The LoginCLIPage class represents the login page in the command-line interface (CLI).
 * It extends the LoginPage class and provides methods for handling user interactions on the CLI login page.
 */
public class LoginCLIPage extends LoginPage {

    /**
     * Constructs a LoginCLIPage object with the given context.
     *
     * @param context The context object for the login page.
     * author Fabio Barchiesi
     */
    protected LoginCLIPage(Context context) {
        super(context);
    }

    /**
     * Handles user interactions on the CLI login page.
     * It displays the login page, prompts the user for credentials, authenticates the user,
     * and navigates to the appropriate home page based on the user's role.
     *
     * @throws InvalidSessionException if the session is invalid.
     * @author Fabio Barchiesi
     */
    @Override
    public void handle() throws InvalidSessionException {
        LoginCLIPageController controller = new LoginCLIPageController();
        try {
            controller.homePage();
            SessionBean sessionBean = authenticate(controller.getCredentials());
            switch (sessionBean.getRole()) {
                case "tenant" -> goToTenantHomePage(new TenantHomeCLIPage(context));
                case "owner" -> goToOwnerHomePage(new OwnerHomeCLIPage(context));
                default -> controller.displayError("");
            }
            context.setSessionBean(sessionBean);
        } catch (AuthenticateException e) {
            controller.displayError(e.getMessage());
            goToInitialPage(new InitialCLIPage(context));
        } catch (NoSuchAlgorithmException e) {
            controller.displayError(e.getMessage());
            System.exit(1);
        } catch (InvalidEmailException e) {
            controller.displayError(e.getMessage());
        }
        context.request();
    }
}