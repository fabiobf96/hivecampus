package it.hivecampuscompany.hivecampus.stateCli.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.stateCli.Context;
import it.hivecampuscompany.hivecampus.stateCli.OwnerHomePage;
import it.hivecampuscompany.hivecampus.stateCli.cli.controller.OwnerHomeCLIPageController;
import it.hivecampuscompany.hivecampus.viewCli.controller.cli.AccountSettingsCLIController;

public class OwnerHomeCLIPage extends OwnerHomePage {
    OwnerHomeCLIPageController controller;
    protected OwnerHomeCLIPage(Context context) {
        super(context);
        controller = new OwnerHomeCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        switch (controller.getChoice()) {
            case 1 -> {
                AccountSettingsCLIController accountSettingsCLIController = new AccountSettingsCLIController();
                accountSettingsCLIController.homePage();
            }
            case 2 -> goToManageAdsPage(new ManageAdsOwnerCLIPage(context));
            case 3 -> goToManageRequestPage(new ManageRequestsOwnerCLIPage(context));
            case 4 -> goToManageLeasePage(new ManageLeaseOwnerCLIPage(context));
            case 5 -> goToManageRentRatesPage(new ManageRentRatesOwnerCLIPage(context));
            case 6 -> throw new InvalidSessionException();
            default -> controller.displayError("Invalid choice");
        }
        context.request();
    }
}
