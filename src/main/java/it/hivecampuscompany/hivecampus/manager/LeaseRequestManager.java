package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.*;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class manages the lease requests.
 * It provides methods to search, modify and send lease requests.
 */

public class LeaseRequestManager {

    private Properties properties = LanguageLoader.getLanguageProperties();

    /**
     * Searches for lease requests associated with a given ad.
     *
     * @param sessionBean The session bean representing the current session.
     * @param adBean      The ad bean containing search criteria.
     * @return A list of lease request beans associated with the given ad.
     * @throws InvalidSessionException If the session is invalid or expired.
     * @author Fabio Barchiesi
     */
    public List<LeaseRequestBean> searchLeaseRequestsByAd(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();

        if (sessionManager.validSession(sessionBean)) {
            LeaseRequestDAO leaseRequestDAO = daoFactoryFacade.getLeaseRequestDAO();
            List<LeaseRequest> leaseRequestList = leaseRequestDAO.retrieveLeaseRequestsByAdID(adBean);
            List<LeaseRequestBean> leaseRequestBeanList = new ArrayList<>();

            for (LeaseRequest leaseRequest : leaseRequestList) {
                leaseRequestBeanList.add(leaseRequest.toBean());
            }
            return leaseRequestBeanList;
        }

        throw new InvalidSessionException();
    }

    /**
     * Modifies a lease request, updating its status.
     *
     * @param sessionBean      The session bean representing the current session.
     * @param leaseRequestBean The lease request bean containing the updated information.
     * @throws InvalidSessionException If the session is invalid or expired.
     * @author Fabio Barchiesi
     */
    public void modifyLeaseRequest(SessionBean sessionBean, LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();

        if (sessionManager.validSession(sessionBean)) {
            LeaseRequestDAO leaseRequestDAO = daoFactoryFacade.getLeaseRequestDAO();
            LeaseRequest leaseRequest = leaseRequestDAO.retrieveLeaseRequestByID(leaseRequestBean, sessionBean.getClient().equals(SessionBean.Client.JAVA_FX));
            leaseRequest.setStatus(leaseRequestBean.getStatus());

            if (leaseRequestBean.getStatus() == LeaseRequestStatus.ACCEPTED) {
                AdDAO adDAO = new AdDAOCSV();
                Ad ad = leaseRequest.getAd();
                ad.setAdStatus(AdStatus.PROCESSING);
                adDAO.updateAd(ad);
            }
            leaseRequestDAO.updateLeaseRequest(leaseRequest);
        } else {
            throw new InvalidSessionException();
        }
    }


    /**
     * Method to send a lease request to the owner of an ad.
     * It checks if the tenant has already sent a request for the same ad.
     * If the request is valid, it saves the lease request in the database.
     *
     * @param sessionBean      The session of the tenant.
     * @param leaseRequestBean The lease request to be sent.
     * @return A message indicating the result of the operation.
     * @throws InvalidSessionException If the session is invalid.
     * @author Marina Sotiropoulos
     */

    public String sendLeaseRequest(SessionBean sessionBean, LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();

        if (sessionManager.validSession(sessionBean)) {
            AccountDAO accountDAO = daoFactoryFacade.getAccountDAO();

            Account tenant = accountDAO.retrieveAccountInformationByEmail(sessionBean.getEmail());
            leaseRequestBean.setTenant(new AccountBean(tenant));

            AdDAO adDAO = daoFactoryFacade.getAdDAO();
            Ad ad = adDAO.retrieveAdByID(leaseRequestBean.getAdBean().getId(), sessionBean.getClient().equals(SessionBean.Client.JAVA_FX));

            LeaseRequestDAO leaseRequestDAO = daoFactoryFacade.getLeaseRequestDAO();
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
        } else throw new InvalidSessionException();
    }

    /**
     * Method to search the lease requests sent by the tenant.
     * It retrieves the lease requests from the database and converts them into LeaseRequestBean objects.
     *
     * @param sessionBean The session of the tenant.
     * @return A list of lease requests sent by the tenant.
     * @author Marina Sotiropoulos
     */

    public List<LeaseRequestBean> searchTenantRequests(SessionBean sessionBean) {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        LeaseRequestDAO leaseRequestDAO = daoFactoryFacade.getLeaseRequestDAO();
        List<LeaseRequest> leaseRequestList = leaseRequestDAO.retrieveLeaseRequestsByTenant(sessionBean, false);
        List<LeaseRequestBean> leaseRequestBeanList = new ArrayList<>();
        for (LeaseRequest leaseRequest : leaseRequestList) {
            leaseRequestBeanList.add(leaseRequest.toBean());
        }
        return leaseRequestBeanList;
    }

    /**
     * Method to delete a lease request.
     * It removes the lease request from the database.
     *
     * @param requestBean The lease request to be deleted.
     * @author Marina Sotiropoulos
     */

    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        LeaseRequestDAO leaseRequestDAO = daoFactoryFacade.getLeaseRequestDAO();
        leaseRequestDAO.deleteLeaseRequest(requestBean);
    }
}
