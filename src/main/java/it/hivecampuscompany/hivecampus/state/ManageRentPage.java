package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseManager;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;

import java.util.List;

public abstract class ManageRentPage implements State {
    protected Context context;
    protected ManageRentPage(Context context) {
        this.context = context;
    }

    public List<AdBean> getProcessingAds() throws InvalidSessionException {
        AdManager adManager = new AdManager();
        return adManager.searchProcessingAds(context.getSessionBean());
    }

    public LeaseRequestBean getLeaseRequestInformation(AdBean adBean) throws InvalidSessionException {
        LeaseRequestManager leaseRequestManager = new LeaseRequestManager();
        adBean.setAdStatus(AdStatus.PROCESSING);
        return leaseRequestManager.searchLeaseRequestsByAd(context.getSessionBean(), adBean).getFirst();
    }

    public void uploadLease(LeaseBean leaseBean) throws InvalidSessionException {
        LeaseManager leaseManager = new LeaseManager();
        leaseManager.loadLease(context.getSessionBean(), leaseBean);
    }
    public void goToOwnerHomePage(OwnerHomePage ownerHomePage) {
        context.setState(ownerHomePage);
    }

}
