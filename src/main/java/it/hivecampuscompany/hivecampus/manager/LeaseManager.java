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
        Lease lease = new Lease(ad, tenant, leaseRequestBean.getMonth(), leaseRequestBean.getDuration(), leaseBean.getContract(), false, Instant.now());
        leaseDAO.saveLease(lease);
    }
    public LeaseBean searchUnsignedLease(SessionBean sessionBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)){
            throw new InvalidSessionException();
        }
        LeaseDAO leaseDAO = new LeaseDAOCSV();
        return leaseDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail()).toBean();
    }
    public void signContract(SessionBean sessionBean, LeaseBean leaseBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)){
            throw new InvalidSessionException();
        }
        LeaseDAO leaseDAO = new LeaseDAOCSV();
        Lease lease = leaseDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail());
        OpenApiBoundary openApiBoundary = new OpenApiBoundary();
        if(openApiBoundary.signContract(lease.getContract())) {
            lease.setSigned(true);
            leaseDAO.updateLease(lease);
        }
    }
}
