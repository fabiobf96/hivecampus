package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.state.cli.ManageRequestsTenantCLIPage;

import java.util.List;

/**
 * The ManageRequestsPage abstract class serves as a base class for pages related to managing lease requests in the application.
 * It implements the State interface and provides methods for managing lease requests, such as retrieving available ads,
 * retrieving lease requests, updating lease requests, and navigating to the owner's home page.
 */
public abstract class ManageRequestsPage implements State {

    protected Context context;

    /**
     * Constructs a ManageRequestsPage object with the given context.
     *
     * @param context The context object for the manage requests page.
     */
    protected ManageRequestsPage(Context context) {
        this.context = context;
    }

    /**
     * Retrieves a list of available ads associated with the session.
     *
     * @return The list of AdBean objects representing available ads.
     * @throws InvalidSessionException if the session is invalid.
     */
    public List<AdBean> retrieveAvailableAds() throws InvalidSessionException {
        AdManager adManager = new AdManager();
        AdBean adBean = new AdBean(AdStatus.AVAILABLE);
        return adManager.searchAdsByOwner(context.getSessionBean(), adBean);
    }

    /**
     * Retrieves a list of lease requests associated with the given ad.
     *
     * @param adBean The AdBean object for which lease requests are required.
     * @return The list of LeaseRequestBean objects representing lease requests.
     * @throws InvalidSessionException if the session is invalid.
     */
    public List<LeaseRequestBean> retrieveLeaseRequests(AdBean adBean) throws InvalidSessionException {
        adBean.setAdStatus(AdStatus.AVAILABLE);
        LeaseRequestManager requestManager = new LeaseRequestManager();
        return requestManager.searchLeaseRequestsByAd(context.getSessionBean(), adBean);
    }

    /**
     * Updates the given lease request.
     *
     * @param leaseRequestBean The LeaseRequestBean object representing the lease request to be updated.
     * @throws InvalidSessionException if the session is invalid.
     */
    public void updateLeaseRequest(LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        LeaseRequestManager requestManager = new LeaseRequestManager();
        requestManager.modifyLeaseRequest(context.getSessionBean(), leaseRequestBean);
    }

    /**
     * Navigates to the owner's home page by updating the context's state and making a request.
     *
     * @param homePage The OwnerHomePage object representing the owner's home page.
     */
    public void goToOwnerHomePage(OwnerHomePage homePage) {
        context.setState(homePage);
        context.request();
    }

    public void goToTenantHomePage(TenantHomePage tenantHomePage) {
        context.setState(tenantHomePage);
        context.request();
    }

    public void goToManageRequestsPage(ManageRequestsTenantCLIPage manageRequestsPage) {
        context.setState(manageRequestsPage);
        context.request();
    }
}