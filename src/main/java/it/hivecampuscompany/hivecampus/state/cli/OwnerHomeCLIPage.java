package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.HomePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.OwnerHomeCLIPageController;
import it.hivecampuscompany.hivecampus.state.cli.controller.AccountSettingsCLIController;

/**
 * The OwnerHomeCLIPage class represents the owner's home page in the command-line interface (CLI).
 * It extends the OwnerHomePage class and provides methods for handling user interactions on the CLI owner's home page.
 */
public class OwnerHomeCLIPage extends HomePage {

    /**
     * Constructs an OwnerHomeCLIPage object with the given context.
     *
     * @param context The context object for the owner's home page.
     * @author Fabio Barchiesi
     */
    protected OwnerHomeCLIPage(Context context) {
        super(context);
    }

    /**
     * Handles user interactions on the owner's home page in the CLI.
     * It displays the home page, prompts the user for input, and navigates to the corresponding page based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     * @author Fabio Barchiesi
     */
    @Override
    public void handle() throws InvalidSessionException {
        OwnerHomeCLIPageController controller = new OwnerHomeCLIPageController();
        controller.homePage();
        switch (controller.getChoice()) {
            case 1 -> {
                AccountSettingsCLIController accountSettingsCLIController = new AccountSettingsCLIController(context);
                accountSettingsCLIController.homePage();
            }
            case 2 -> goToManageAdsPage(new ManageAdsOwnerCLIPage(context));
            case 3 -> goToManageRequestsPage(new ManageRequestsOwnerCLIPage(context));
            case 4 -> goToManageLeasePage(new ManageLeaseOwnerCLIPage(context));
            case 5 -> goToManageRentRatesPage(new ManageRentRatesOwnerCLIPage(context));
            case 6 -> throw new InvalidSessionException();
            default -> controller.displayError("Invalid choice");
        }
        context.request();
    }
}