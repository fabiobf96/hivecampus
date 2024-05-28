package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.cli.controller.AdSearchCLIPageController;
import it.hivecampuscompany.hivecampus.state.cli.controller.LeaseRequestCLIPageController;

import java.util.List;

/**
 * The AdSearchCLIPage class represents the ad search page in the command-line interface (CLI).
 * It extends the AdSearchPage class and provides methods for displaying the ad search page and handling user input.
 */

public class AdSearchCLIPage extends AdSearchPage {
    private final AdSearchCLIPageController adController;
    AdBean adBean;

    /**
     * Constructs an AdSearchCLIPage object with the given context.
     * @param context The context object for the ad search page.
     * @author Marina Sotiropoulos
     */

    protected AdSearchCLIPage(Context context) {
        super(context);
        adController = new AdSearchCLIPageController();
    }

    /**
     * Displays the ad search page and handles user input.
     * @throws InvalidSessionException if the session is invalid.
     * @author Marina Sotiropoulos
     */

    @Override
    public void handle() throws InvalidSessionException {
        adController.homePage();
        List<AdBean> adBeans = adController.searchAds();
        if(adBeans != null && !adBeans.isEmpty()) {
            // Display the preview of the ads
            adBean = adController.showAdsPreview(adBeans);
            if (adBean == null) {
                goToTenantHomePage(new TenantHomeCLIPage(context));
            } else {
                // Display the details of the selected ad
                handleAdDetails();
            }
        } else {
            goToTenantHomePage(new TenantHomeCLIPage(context));
        }
        context.request();
    }

    /**
     * Handles the display of ad details and user input.
     * @throws InvalidSessionException if the session is invalid.
     * @author Marina Sotiropoulos
     */

    private void handleAdDetails() throws InvalidSessionException {
        adController.showAdDetails(adBean);
        switch (adController.getChoice()) {
            case 1 -> handleLeaseRequest(); // display lease request form
            case 2 -> goToTenantHomePage(new TenantHomeCLIPage(context));
            default -> adController.invalidChoice();
        }
    }

    /**
     * Handles the display of the lease request form and user input.
     * @author Marina Sotiropoulos
     */

     private void handleLeaseRequest() throws InvalidSessionException {
        LeaseRequestCLIPageController requestController = new LeaseRequestCLIPageController();
        requestController.homePage();

        Boolean res  = requestController.leaseRequestForm(context.getSessionBean(), adBean);

        if (Boolean.FALSE.equals(res)) {
            handleAdDetails();
        }
        else goToTenantHomePage(new TenantHomeCLIPage(context));
    }
}
