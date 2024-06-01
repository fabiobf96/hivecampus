package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.LeaseContractBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.boundary.OpenApiBoundary;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseContractDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseContractDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseContract;

public class LeaseManager {
    public void loadLease(SessionBean sessionBean, LeaseContractBean leaseContractBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }
        AdDAO adDAO = new AdDAOCSV();
        AccountDAO accountDAO = new AccountDAOCSV();
        LeaseContractDAO leaseContractDAO = new LeaseContractDAOCSV();
        LeaseRequestBean leaseRequestBean = leaseContractBean.getLeaseRequestBean();
        Ad ad = adDAO.retrieveAdByID(leaseRequestBean.getAdBean().getId(), sessionBean.getClient().equals(SessionBean.Client.JAVA_FX));
        ad.setAdStatus(AdStatus.RESERVED);
        adDAO.updateAd(ad);
        Account tenant = accountDAO.retrieveAccountInformationByEmail(leaseRequestBean.getTenant().getEmail());
        LeaseContract leaseContract = new LeaseContract(ad, tenant, leaseRequestBean.getLeaseMonth().getMonth(), leaseRequestBean.getDuration().getPermanence(), leaseContractBean.getContract(), false); // Modificato leaseRequestBean.getMonth() e leaseRequestBean.getDuration() in String.valueOf(leaseRequestBean.getLeaseMonth()) e String.valueOf(leaseRequestBean.getDuration())
        leaseContractDAO.saveLease(leaseContract);
    }

    public LeaseContractBean searchUnsignedLease(SessionBean sessionBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }
        LeaseContractDAO leaseContractDAO = new LeaseContractDAOCSV();
        LeaseContract leaseContract = leaseContractDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail(), sessionBean.getClient().equals(SessionBean.Client.JAVA_FX));
        if (leaseContract != null) {
            return leaseContract.toBean();
        } else return null;
    }

    public void signContract(SessionBean sessionBean) throws InvalidSessionException, MockOpenAPIException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }
        LeaseContractDAO leaseContractDAO = new LeaseContractDAOCSV();
        LeaseContract leaseContract = leaseContractDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail(), false);
        try {
            OpenApiBoundary openApiBoundary = new OpenApiBoundary();
            leaseContract.setSigned(openApiBoundary.signContract(leaseContract.getContract()));
            leaseContractDAO.updateLease(leaseContract);
            AdDAO adDAO = new AdDAOCSV();
            Ad ad = leaseContract.getAd();
            ad.setAdStatus(AdStatus.LEASED);
            adDAO.updateAd(ad);
        } catch (MockOpenAPIException e) {
            throw new MockOpenAPIException(e.getMessage());
        }
    }
}
