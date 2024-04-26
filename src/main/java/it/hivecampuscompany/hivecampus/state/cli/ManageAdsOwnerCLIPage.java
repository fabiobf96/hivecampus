package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageAdsCLIPageController;

public class ManageAdsOwnerCLIPage extends ManageAdsPage {
    private final ManageAdsCLIPageController controller;

    protected ManageAdsOwnerCLIPage(Context context) {
        super(context);
        controller = new ManageAdsCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        controller.showManageAdsOptions(context.getSessionBean());
        switch (controller.getChoice()) {
            case 1 -> {
                // Create an ad
                if (controller.createAdOptions(context.getSessionBean())) {
                    controller.publishAd(context.getSessionBean());
                }
                goToManageAdsPage(new ManageAdsOwnerCLIPage(context));
            }

            case 2, 3 -> {
                // Edit an ad
                controller.notImplementedYet();
                goToManageAdsPage(new ManageAdsOwnerCLIPage(context));
            }

            case 4 -> // Go back
                goToOwnerHomePage(new OwnerHomeCLIPage(context));

            default -> controller.invalidChoice();
        }
        context.request();
    }
}
