package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.*;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.*;
import java.util.ArrayList;
import java.util.List;

public class LeaseRequestManager {

    public List<LeaseRequestBean> searchLeaseRequestsByAd (SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.validSession(sessionBean)) {
            LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
            List<LeaseRequest> leaseRequestList = leaseRequestDAO.retrieveLeaseRequestsByAdID(adBean);
            List<LeaseRequestBean> leaseRequestBeanList = new ArrayList<>();
            for (LeaseRequest leaseRequest : leaseRequestList) {
                leaseRequestBeanList.add(leaseRequest.toBasicBean());
            }
            return leaseRequestBeanList;
        }
        throw new InvalidSessionException();
    }

    public void modifyLeaseRequest(SessionBean sessionBean, LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.validSession(sessionBean)) {
            LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
            LeaseRequest leaseRequest = leaseRequestDAO.retrieveLeaseRequestByID(leaseRequestBean);
            leaseRequest.setStatus(leaseRequestBean.getStatus());
            if (leaseRequestBean.getStatus() == LeaseRequestStatus.ACCEPTED) {
                AdDAO adDAO = new AdDAOCSV();
                Ad ad = leaseRequest.getAd();
                ad.setAdStatus(AdStatus.PROCESSING);
                adDAO.updateAd(ad);
            }
            leaseRequestDAO.updateLeaseRequest(leaseRequest);
        } else throw new InvalidSessionException();
    }

    public String sendLeaseRequest(SessionBean sessionBean, LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if(sessionManager.validSession(sessionBean)) {
            AccountDAO accountDAO = new AccountDAOCSV();

            Account tenant = accountDAO.retrieveAccountInformationByEmail(sessionBean.getEmail());
            leaseRequestBean.setTenant(new AccountBean(tenant));

            AdDAOCSV adDAO = new AdDAOCSV();
            Ad ad = adDAO.retrieveAdByID(leaseRequestBean.getAdBean().getId());

            LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
            LeaseRequest leaseRequest = new LeaseRequest(
                    ad,
                    tenant,
                    leaseRequestBean.getLeaseMonth().getMonth(),
                    leaseRequestBean.getDuration().getPermanence(),
                    leaseRequestBean.getStatus().getId(),
                    leaseRequestBean.getMessage());

            if (leaseRequestDAO.validRequest(tenant.getEmail(), ad.getId())) {
                leaseRequestDAO.saveLeaseRequest(leaseRequest);
                return "Lease request sent successfully";
            }
            return "Lease request already sent";
        }
        else throw new InvalidSessionException();
    }

    public List<LeaseRequestBean> searchTenantRequests(SessionBean sessionBean) {
        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
        List<LeaseRequest> leaseRequestList = leaseRequestDAO.retrieveLeaseRequestsByTenant(sessionBean);
        List<LeaseRequestBean> leaseRequestBeanList = new ArrayList<>();
        for (LeaseRequest leaseRequest : leaseRequestList) {
            leaseRequestBeanList.add(leaseRequest.toBasicBean());
        }
        return leaseRequestBeanList;
    }

    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
        leaseRequestDAO.deleteLeaseRequest(requestBean);
    }
}
