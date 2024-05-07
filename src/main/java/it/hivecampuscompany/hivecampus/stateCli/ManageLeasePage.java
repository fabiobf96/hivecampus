package it.hivecampuscompany.hivecampus.stateCli;

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

public abstract class ManageLeasePage implements State {
    protected Context context;
    protected ManageLeasePage(Context context) {
        this.context = context;
    }

    public List<AdBean> getProcessingAds() throws InvalidSessionException {
        AdManager adManager = new AdManager();
        AdBean adBean = new AdBean(AdStatus.PROCESSING);
        return adManager.getDecoratedAdsByOwner(context.getSessionBean(), adBean);
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
    public void signContract() throws InvalidSessionException, MockOpenAPIException {
        LeaseManager leaseManager = new LeaseManager();
        leaseManager.signContract(context.getSessionBean());
    }
    public LeaseBean getUnSignedLease() throws InvalidSessionException {
        LeaseManager leaseManager = new LeaseManager();
        return leaseManager.searchUnsignedLease(context.getSessionBean());
    }
    public void goToOwnerHomePage(OwnerHomePage ownerHomePage) {
        context.setState(ownerHomePage);
    }
    public void goToTenantHomePage(TenantHomePage tenantHomePage) {
        context.setState(tenantHomePage);
        context.request();
    }
}
