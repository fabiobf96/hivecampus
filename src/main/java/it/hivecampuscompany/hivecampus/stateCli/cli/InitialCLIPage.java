package it.hivecampuscompany.hivecampus.stateCli.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.stateCli.Context;
import it.hivecampuscompany.hivecampus.stateCli.InitialPage;
import it.hivecampuscompany.hivecampus.stateCli.cli.controller.InitialCLIPageController;
import it.hivecampuscompany.hivecampus.view.controller.cli.LanguageCLIController;

public class InitialCLIPage extends InitialPage {
    public InitialCLIPage(Context context) {
        super(context);
    }

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
