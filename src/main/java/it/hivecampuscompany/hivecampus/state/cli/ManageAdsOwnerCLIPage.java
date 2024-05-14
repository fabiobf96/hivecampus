package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageAdsCLIPageController;

/**
 * The ManageAdsOwnerCLIPage class represents the manage ads page for the owner in the command-line interface (CLI).
 * It extends the ManageAdsPage class and provides methods for displaying the manage ads page and handling user input.
 */

public class ManageAdsOwnerCLIPage extends ManageAdsPage {
    private final ManageAdsCLIPageController controller;

    /**
     * Constructs a ManageAdsOwnerCLIPage object with the given context.
     * @param context The context object for the manage ads page.
     */

    protected ManageAdsOwnerCLIPage(Context context) {
        super(context);
        controller = new ManageAdsCLIPageController();
    }

    /**
     * Displays the manage ads page and handles user input for navigate
     * between the options such as create, edit, and delete an ad.
     * At this moment, only the create option is implemented.
     */

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
