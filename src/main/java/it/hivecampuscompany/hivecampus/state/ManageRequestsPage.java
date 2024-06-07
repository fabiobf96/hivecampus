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
 *
 * @author Fabio Barchiesi
 */
public abstract class ManageRequestsPage implements State {

    protected Context context;

    /**
     * Constructs a ManageRequestsPage object with the given context.
     *
     * @param context The context object for the manage requests page.
     * @author Fabio Barchiesi
     */
    protected ManageRequestsPage(Context context) {
        this.context = context;
    }

    /**
     * Retrieves a list of available ads associated with the session.
     *
     * @return The list of AdBean objects representing available ads.
     * @throws InvalidSessionException if the session is invalid.
     * @author Fabio Barchiesi
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
     * @author Fabio Barchiesi
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
     * @author Fabio Barchiesi
     */
    public void updateLeaseRequest(LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        LeaseRequestManager requestManager = new LeaseRequestManager();
        requestManager.modifyLeaseRequest(context.getSessionBean(), leaseRequestBean);
    }

    /**
     * Navigates to the owner's home page by updating the context's state and making a request.
     *
     * @param homePage The OwnerHomePage object representing the owner's home page.
     * @author Fabio Barchiesi
     */
    public void goToOwnerHomePage(HomePage homePage) {
        context.setState(homePage);
        context.request();
    }

    /**
     * Navigates to the tenant's home page by updating the context's state and making a request.
     *
     * @param tenantHomePage The TenantHomePage object representing the tenant's home page.
     * @author Marina Sotiropoulos
     */
    public void goToTenantHomePage(HomePage tenantHomePage) {
        context.setState(tenantHomePage);
        context.request();
    }

    /**
     * Navigates to the manage requests page by updating the context's state and making a request.
     *
     * @param manageRequestsPage The ManageRequestsPage object representing the manage requests page.
     * @author Marina Sotiropoulos
     */
    public void goToManageRequestsPage(ManageRequestsTenantCLIPage manageRequestsPage) {
        context.setState(manageRequestsPage);
        context.request();
    }
}