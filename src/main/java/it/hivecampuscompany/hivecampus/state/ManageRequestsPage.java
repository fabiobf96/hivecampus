package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;

import java.util.List;

public abstract class ManageRequestsPage implements State {
    protected Context context;
    protected ManageRequestsPage(Context context) {
        this.context = context;
    }
    public List<AdBean> retrieveAvailableAds() throws InvalidSessionException {
        AdManager adManager = new AdManager();
        return adManager.searchAvailableAds(context.getSessionBean());
    }
    public List<LeaseRequestBean> retrieveLeaseRequests(AdBean adBean) throws InvalidSessionException {
        LeaseRequestManager requestManager = new LeaseRequestManager();
        return requestManager.searchLeaseRequestsByAd(context.getSessionBean(), adBean);
    }
    public void updateLeaseRequest(LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        LeaseRequestManager requestManager = new LeaseRequestManager();
        requestManager.modifyLeaseRequest(context.getSessionBean(), leaseRequestBean);
    }
    public void goToOwnerHomePage(OwnerHomePage homePage) {
        context.setState(homePage);
        context.request();
    }
}
