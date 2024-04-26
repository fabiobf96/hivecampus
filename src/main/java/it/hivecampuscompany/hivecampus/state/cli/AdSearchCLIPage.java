package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.cli.controller.AdSearchCLIPageController;
import it.hivecampuscompany.hivecampus.state.cli.controller.LeaseRequestCLIPageController;

import java.util.List;

public class AdSearchCLIPage extends AdSearchPage {
    private final AdSearchCLIPageController adController;
    AdBean adBean;

    protected AdSearchCLIPage(Context context) {
        super(context);
        adController = new AdSearchCLIPageController();
    }

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

    private void handleAdDetails() throws InvalidSessionException {
        adController.showAdDetails(adBean);
        switch (adController.getChoice()) {
            case 1 -> handleLeaseRequest(); // display lease request form
            case 2 -> goToTenantHomePage(new TenantHomeCLIPage(context));
            default -> adController.invalidChoice();
        }
    }

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
