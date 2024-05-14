package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.boundary.OpenApiBoundary;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.Lease;

import java.time.Instant;

public class LeaseManager {
    public void loadLease(SessionBean sessionBean, LeaseBean leaseBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)){
            throw new InvalidSessionException();
        }
        AdDAO adDAO = new AdDAOCSV();
        AccountDAO accountDAO = new AccountDAOCSV();
        LeaseDAO leaseDAO = new LeaseDAOCSV();
        LeaseRequestBean leaseRequestBean = leaseBean.getLeaseRequestBean();
        Ad ad = adDAO.retrieveAdByID(leaseRequestBean.getAdBean().getId());
        ad.setAdStatus(AdStatus.RESERVED);
        adDAO.updateAd(ad);
        Account tenant = accountDAO.retrieveAccountInformationByEmail(leaseRequestBean.getTenant().getEmail());
        Lease lease = new Lease(ad, tenant, String.valueOf(leaseRequestBean.getLeaseMonth()), String.valueOf(leaseRequestBean.getDuration()), leaseBean.getContract(), false, Instant.now()); // Modificato leaseRequestBean.getMonth() e leaseRequestBean.getDuration() in String.valueOf(leaseRequestBean.getLeaseMonth()) e String.valueOf(leaseRequestBean.getDuration())
        leaseDAO.saveLease(lease);
    }
    public LeaseBean searchUnsignedLease(SessionBean sessionBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)){
            throw new InvalidSessionException();
        }
        LeaseDAO leaseDAO = new LeaseDAOCSV();
        Lease lease = leaseDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail());
        if (lease != null && sessionBean.getClient() == SessionBean.Client.CLI) {
            return lease.toBean();
        } else if (lease != null && sessionBean.getClient() == SessionBean.Client.JAVA_FX) {
            return lease.toBeanWithImage();
        }
        else {
            return null;
        }
    }
    public void signContract(SessionBean sessionBean) throws InvalidSessionException, MockOpenAPIException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)){
            throw new InvalidSessionException();
        }
        LeaseDAO leaseDAO = new LeaseDAOCSV();
        Lease lease = leaseDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail());
        try {
            OpenApiBoundary openApiBoundary = new OpenApiBoundary();
            lease.setSigned(openApiBoundary.signContract(lease.getContract()));
            leaseDAO.updateLease(lease);
            AdDAO adDAO = new AdDAOCSV();
            Ad ad = lease.getAd();
            ad.setAdStatus(AdStatus.LEASED);
            adDAO.updateAd(ad);
        } catch (MockOpenAPIException e) {
            lease.setTimeStamp(Instant.now());
            leaseDAO.updateLease(lease);
            throw new MockOpenAPIException (e.getMessage());
        }
    }
}
