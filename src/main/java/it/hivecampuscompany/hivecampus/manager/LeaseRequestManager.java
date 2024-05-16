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
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class manages the lease requests.
 * It provides methods to search, modify and send lease requests.
 */

public class LeaseRequestManager {

    Properties properties = LanguageLoader.getLanguageProperties();

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

    /**
     * Method to send a lease request to the owner of an ad.
     * It checks if the tenant has already sent a request for the same ad.
     * If the request is valid, it saves the lease request in the database.
     *
     * @param sessionBean The session of the tenant.
     * @param leaseRequestBean The lease request to be sent.
     * @return A message indicating the result of the operation.
     * @throws InvalidSessionException If the session is invalid.
     */

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
                return properties.getProperty("SUCCESS_MSG_SEND_REQUEST");
            }
            return properties.getProperty("REQUEST_ALREADY_SENT_MSG");
        }
        else throw new InvalidSessionException();
    }

    /**
     * Method to search the lease requests sent by the tenant.
     * It retrieves the lease requests from the database and converts them into LeaseRequestBean objects.
     *
     * @param sessionBean The session of the tenant.
     * @return A list of lease requests sent by the tenant.
     */

    public List<LeaseRequestBean> searchTenantRequests(SessionBean sessionBean) {
        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
        List<LeaseRequest> leaseRequestList = leaseRequestDAO.retrieveLeaseRequestsByTenant(sessionBean);
        List<LeaseRequestBean> leaseRequestBeanList = new ArrayList<>();
        for (LeaseRequest leaseRequest : leaseRequestList) {
            leaseRequestBeanList.add(leaseRequest.toBasicBean());
        }
        return leaseRequestBeanList;
    }

    /**
     * Method to delete a lease request.
     * It removes the lease request from the database.
     *
     * @param requestBean The lease request to be deleted.
     */

    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
        leaseRequestDAO.deleteLeaseRequest(requestBean);
    }
}
