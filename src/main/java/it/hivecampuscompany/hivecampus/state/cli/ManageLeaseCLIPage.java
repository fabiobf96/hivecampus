package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageLeaseCLIPageController;

public class ManageLeaseCLIPage extends ManageLeasePage {
    private ManageLeaseCLIPageController controller;
    private AdBean adBean;
    public ManageLeaseCLIPage(Context context) {
        super(context);
        controller = new ManageLeaseCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        switch (controller.getChoice()) {
            case 1 -> choiceAd();
            case 2 -> {
                controller.notImplementedYet();
                context.request();
            }
            case 3 -> goToOwnerHomePage(new OwnerHomeCLIPage(context));
            default -> {
                controller.invalidChoice();
                context.request();
            }
        }
    }

    private void choiceAd() throws InvalidSessionException {
        controller.homePage();
        adBean = controller.selectAd(getProcessingAds());
        if (adBean != null) {
            createNewLease();
        }
        else context.request();
    }

    private void createNewLease() throws InvalidSessionException {
        controller.homePage();
        LeaseBean leaseBean = controller.getLease(adBean, getLeaseRequestInformation(adBean));
        if (leaseBean != null) {
            uploadLease(leaseBean);
        }
        else choiceAd();
    }
}
