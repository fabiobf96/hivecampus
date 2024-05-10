package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseManager;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;

import java.util.List;

/**
 * The ManageLeasePage abstract class serves as a base class for pages related to managing leases in the application.
 * It implements the State interface and provides methods for managing leases, such as retrieving processing ads,
 * obtaining lease request information, uploading leases, signing contracts, and navigating to home pages.
 */
public abstract class ManageLeasePage implements State {

    protected Context context;

    /**
     * Constructs a ManageLeasePage object with the given context.
     *
     * @param context The context object for the manage lease page.
     */
    protected ManageLeasePage(Context context) {
        this.context = context;
    }

    /**
     * Retrieves a list of processing ads associated with the session.
     *
     * @return The list of AdBean objects representing processing ads.
     * @throws InvalidSessionException if the session is invalid.
     */
    public List<AdBean> getProcessingAds() throws InvalidSessionException {
        AdManager adManager = new AdManager();
        AdBean adBean = new AdBean(AdStatus.PROCESSING);
        return adManager.searchAdsByOwner(context.getSessionBean(), adBean);
    }

    /**
     * Retrieves lease request information for the given ad.
     *
     * @param adBean The AdBean object for which lease request information is required.
     * @return The LeaseRequestBean object representing lease request information.
     * @throws InvalidSessionException if the session is invalid.
     */
    public LeaseRequestBean getLeaseRequestInformation(AdBean adBean) throws InvalidSessionException {
        LeaseRequestManager leaseRequestManager = new LeaseRequestManager();
        adBean.setAdStatus(AdStatus.PROCESSING);
        return leaseRequestManager.searchLeaseRequestsByAd(context.getSessionBean(), adBean).getFirst();
    }

    /**
     * Uploads the given lease to the system.
     *
     * @param leaseBean The LeaseBean object representing the lease to be uploaded.
     * @throws InvalidSessionException if the session is invalid.
     */
    public void uploadLease(LeaseBean leaseBean) throws InvalidSessionException {
        LeaseManager leaseManager = new LeaseManager();
        leaseManager.loadLease(context.getSessionBean(), leaseBean);
    }

    /**
     * Signs the contract associated with the session.
     *
     * @throws InvalidSessionException if the session is invalid.
     * @throws MockOpenAPIException    if there is an issue with the mock OpenAPI.
     */
    public void signContract() throws InvalidSessionException, MockOpenAPIException {
        LeaseManager leaseManager = new LeaseManager();
        leaseManager.signContract(context.getSessionBean());
    }

    /**
     * Retrieves the unsigned lease associated with the session.
     *
     * @return The LeaseBean object representing the unsigned lease.
     * @throws InvalidSessionException if the session is invalid.
     */
    public LeaseBean getUnSignedLease() throws InvalidSessionException {
        LeaseManager leaseManager = new LeaseManager();
        return leaseManager.searchUnsignedLease(context.getSessionBean());
    }

    /**
     * Navigates to the owner's home page by updating the context's state.
     *
     * @param ownerHomePage The OwnerHomePage object representing the owner's home page.
     */
    public void goToOwnerHomePage(OwnerHomePage ownerHomePage) {
        context.setState(ownerHomePage);
    }

    /**
     * Navigates to the tenant's home page by updating the context's state and making a request.
     *
     * @param tenantHomePage The TenantHomePage object representing the tenant's home page.
     */
    public void goToTenantHomePage(TenantHomePage tenantHomePage) {
        context.setState(tenantHomePage);
        context.request();
    }
}