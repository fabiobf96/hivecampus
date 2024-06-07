package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.dao.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.dao.facade.PersistenceType;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.*;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeaseRequestTest {

    private SessionManager sessionManager;
    private LeaseRequestManager requestManager;
    private LeaseRequestDAO leaseRequestDAO;

    private SessionBean sessionBean;
    private LeaseRequestBean reqBean1;
    private LeaseRequestBean reqBean2;

    private Properties properties;

    @BeforeEach
    void setUp() {
        // Configure the test environment

        DAOFactoryFacade.getInstance().setPersistenceType(PersistenceType.CSV);

        sessionManager = SessionManager.getInstance();
        AccountDAO accountDAO = new AccountDAOCSV();
        AdDAOCSV adDAO = new AdDAOCSV();
        leaseRequestDAO = new LeaseRequestDAOCSV();
        requestManager = new LeaseRequestManager();
        properties = LanguageLoader.getLanguageProperties();

        // Create a new valid session
        User user = new User("marta.rossi@gmail.com", "pippo", "tenant");
        Session session = sessionManager.createSession(user);
        sessionBean = new SessionBean(session);
        sessionBean.setClient(SessionBean.Client.CLI);

        // Retrieve the tenant account
        Account tenant = accountDAO.retrieveAccountInformationByEmail(user.getEmail());

        // Retrieve an ad for which the tenant has already sent a request
        Ad ad1 = adDAO.retrieveAdByID(1, false);

        // Retrieve an ad for which the tenant did not send a request
        Ad ad2 = adDAO.retrieveAdByID(8, false);

        // Create a lease request for the ad1
        reqBean1 = new LeaseRequestBean();
        reqBean1.setAdBean(ad1.toBean());
        reqBean1.setTenant(tenant.toBean());
        reqBean1.setDuration(6); // 6 months
        reqBean1.setLeaseMonth(9); // September
        reqBean1.setMessage("I would like to rent this apartment"); // Message to the owner
        reqBean1.setStatus(LeaseRequestStatus.PROCESSING);

        // Create a lease request for the ad2
        reqBean2 = new LeaseRequestBean();
        reqBean2.setAdBean(ad2.toBean());
        reqBean2.setTenant(tenant.toBean());
        reqBean2.setDuration(6); // 6 months
        reqBean2.setLeaseMonth(9); // September
        reqBean2.setMessage("I would like to rent this apartment"); // Message to the owner
        reqBean2.setStatus(LeaseRequestStatus.PROCESSING);

    }

    @Test
    void testSendLeaseRequest_InvalidSession() {
        // Simulate the logout of the user by deleting the session to invalidate the session
        sessionManager.deleteSession(sessionBean);
        // Verify the failure of sending the lease request due to an invalid session
        assertThrows(InvalidSessionException.class, () -> requestManager.sendLeaseRequest(sessionBean, reqBean1));
    }

    @Test
    void testSendLeaseRequest_Failure() {
        String result;
        try {
            result = requestManager.sendLeaseRequest(sessionBean, reqBean1);
        } catch (InvalidSessionException e) {
            throw new RuntimeException(e);
        }
        assertEquals(properties.getProperty("REQUEST_ALREADY_SENT_MSG"), result);
        leaseRequestDAO.deleteLeaseRequest(reqBean2);
    }

    @Test
    void testSendLeaseRequest_Success() {
        String result;
        try {
            result = requestManager.sendLeaseRequest(sessionBean, reqBean2);
        } catch (InvalidSessionException e) {
            throw new RuntimeException(e);
        }
        assertEquals(properties.getProperty("SUCCESS_MSG_SEND_REQUEST"), result);
    }
}