package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.InitialPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.InitialCLIPageController;
import it.hivecampuscompany.hivecampus.view.controller.cli.LanguageCLIController;

/**
 * The InitialCLIPage class represents the initial page of the command-line interface (CLI).
 * It extends the InitialPage class and provides methods for handling user interactions on the initial CLI page.
 */
public class InitialCLIPage extends InitialPage {

    /**
     * Constructs an InitialCLIPage object with the given context.
     *
     * @param context The context object for the CLI page.
     */
    public InitialCLIPage(Context context) {
        super(context);
    }

    /**
     * Handles user interactions on the initial CLI page.
     * It displays the home page, prompts the user for input, and navigates to the appropriate page based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    @Override
    public void handle() throws InvalidSessionException {
        InitialCLIPageController controller = new InitialCLIPageController();
        controller.homePage();
        switch (controller.getChoice()) {
            case 1 -> {
                LanguageCLIController languageController = new LanguageCLIController();
                languageController.homePage();
            }
            case 2 -> goToLoginPage(new LoginCLIPage(context));
            case 3 -> goToSignUpPage(new SignUpCLIPage(context));
            case 4 -> controller.exit();
            default -> controller.invalidChoice();
        }
        context.request();
    }
}
