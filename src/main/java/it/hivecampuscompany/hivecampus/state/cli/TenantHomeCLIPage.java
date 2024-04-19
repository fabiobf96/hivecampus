package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.TenantHomePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.TenantHomeCLIPageController;
import it.hivecampuscompany.hivecampus.view.controller.cli.AccountSettingsCLIController;

public class TenantHomeCLIPage extends TenantHomePage {

    TenantHomeCLIPageController controller;

    protected TenantHomeCLIPage(Context context) {
        super(context);
        controller = new TenantHomeCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        switch (controller.getChoice()) {
            case 1 -> {
                AccountSettingsCLIController accountSettingsCLIController = new AccountSettingsCLIController();
                accountSettingsCLIController.homePage();
            }
            case 2 -> goToAdSearchPage(new AdSearchCLIPage(context));
            case 3,4,5 -> controller.notImplementedYet();
            case 6 -> controller.exit();
            default -> controller.invalidChoice();
        }
        context.request();
    }
}
