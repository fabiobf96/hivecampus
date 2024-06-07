package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.HomePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.TenantHomeCLIPageController;
import it.hivecampuscompany.hivecampus.state.cli.controller.AccountSettingsCLIController;

/**
 * The TenantHomeCLIPage class represents the tenant's home page in the command-line interface (CLI).
 * It extends the TenantHomePage class and provides methods for handling user interactions on the CLI tenant's home page.
 */

public class TenantHomeCLIPage extends HomePage {

    /**
     * Constructs a TenantHomeCLIPage object with the given context.
     * @param context The context object for the tenant's home page.
     * @author Marina Sotiropoulos
     */

    protected TenantHomeCLIPage(Context context) {
        super(context);
    }

    /**
     * Handles user interactions on the tenant's home page in the CLI.
     * It displays the home page, prompts the user for input, and navigates to the corresponding page based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     * @author Marina Sotiropoulos
     */

    @Override
    public void handle() throws InvalidSessionException {
        TenantHomeCLIPageController controller = new TenantHomeCLIPageController();
        controller.homePage();
        switch (controller.getChoice()) {
            case 1 -> {
                AccountSettingsCLIController accountSettingsCLIController = new AccountSettingsCLIController(context);
                accountSettingsCLIController.homePage();
            }
            case 2 -> goToAdSearchPage(new AdSearchCLIPage(context));
            case 3 -> goToManageRequestsPage(new ManageRequestsTenantCLIPage(context));
            case 4 -> goToManageLeasePage(new ManageLeaseTenantCLIPage(context));
            case 5 -> controller.notImplementedYet();
            case 6 -> throw new InvalidSessionException();
            default -> controller.invalidChoice();
        }
        context.request();
    }
}
