package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.OwnerHomePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.OwnerHomeCLIPageController;
import it.hivecampuscompany.hivecampus.view.controller.cli.AccountSettingsCLIController;

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
            case 2 -> goToManageAdsPage(new ManageAdsCLIPage(context));
            case 3 -> goToManageRequestPage(new ManageRequestsCLIPage(context));
            case 4 -> goToManageContractPage(new ManageContractsCLIPage(context));
            case 5 -> goToManageRentPage(new ManageRentCLIPage(context));
            case 6 -> controller.exit();
            default -> controller.displayError("Invalid choice");
        }
        context.request();
    }
}
