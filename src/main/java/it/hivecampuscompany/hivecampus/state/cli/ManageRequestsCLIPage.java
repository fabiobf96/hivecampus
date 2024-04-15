package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageRequestsCLIPageController;

public class ManageRequestsCLIPage extends ManageRequestsPage {
    private AdBean adBean;
    private LeaseRequestBean leaseRequestBean;
    private ManageRequestsCLIPageController controller;
    public ManageRequestsCLIPage(Context context) {
        super(context);
        controller = new ManageRequestsCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        adBean = controller.selectAd(retrieveAvailableAds());
        if (adBean != null) {
            selectLeaseRequest();
        } else {
            goToOwnerHomePage(new OwnerHomeCLIPage(context));
            context.request();
        }
    }

    private void selectLeaseRequest() throws InvalidSessionException {
        controller.homePage();
        leaseRequestBean = controller.selectRequest(retrieveLeaseRequests(adBean));
        if (leaseRequestBean != null) {
            makeDecision();
        }
        else context.request();
    }

    private void makeDecision() throws InvalidSessionException {
        controller.homePage();
        int choice = controller.getChoice(adBean, leaseRequestBean);
        switch (choice) {
            case 1 -> {
                leaseRequestBean.setStatus(LeaseRequestStatus.ACCEPTED);
                updateLeaseRequest(leaseRequestBean);
                context.request();
            }
            case 2 -> {
                leaseRequestBean.setStatus(LeaseRequestStatus.REJECTED);
                updateLeaseRequest(leaseRequestBean);
                selectLeaseRequest();
            }
            case 3 -> selectLeaseRequest();
            default -> {
                controller.invalidChoice();
                makeDecision();
            }
        }
    }
}
