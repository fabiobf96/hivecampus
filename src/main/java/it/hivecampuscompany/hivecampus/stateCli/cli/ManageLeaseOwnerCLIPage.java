package it.hivecampuscompany.hivecampus.stateCli.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.stateCli.Context;
import it.hivecampuscompany.hivecampus.stateCli.ManageLeasePage;
import it.hivecampuscompany.hivecampus.stateCli.cli.controller.ManageLeaseOwnerCLIPageController;

public class ManageLeaseOwnerCLIPage extends ManageLeasePage {
    private ManageLeaseOwnerCLIPageController controller;
    private AdBean adBean;
    public ManageLeaseOwnerCLIPage(Context context) {
        super(context);
        controller = new ManageLeaseOwnerCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        try {
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
        } catch (InvalidSessionException e) {
            controller.sessionExpired();
            throw new InvalidSessionException();
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
            controller.successLoadLease();
        }
        else choiceAd();
    }
}
