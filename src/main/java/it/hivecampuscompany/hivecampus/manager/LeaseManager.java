package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.LeaseContractBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.boundary.OpenApiBoundary;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseContractDAO;
import it.hivecampuscompany.hivecampus.dao.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseContract;

public class LeaseManager {
    public void loadLease(SessionBean sessionBean, LeaseContractBean leaseContractBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }
        AdDAO adDAO = daoFactoryFacade.getAdDAO();
        AccountDAO accountDAO = daoFactoryFacade.getAccountDAO();
        LeaseContractDAO leaseContractDAO = daoFactoryFacade.getLeaseContractDAO();
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
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }
        LeaseContractDAO leaseContractDAO = daoFactoryFacade.getLeaseContractDAO();
        LeaseContract leaseContract = leaseContractDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail(), sessionBean.getClient().equals(SessionBean.Client.JAVA_FX));
        if (leaseContract != null) {
            return leaseContract.toBean();
        } else return null;
    }

    public void signContract(SessionBean sessionBean) throws InvalidSessionException, MockOpenAPIException {
        SessionManager sessionManager = SessionManager.getInstance();
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }
        LeaseContractDAO leaseContractDAO = daoFactoryFacade.getLeaseContractDAO();
        LeaseContract leaseContract = leaseContractDAO.retrieveUnsignedLeaseByTenant(sessionBean.getEmail(), false);

        OpenApiBoundary openApiBoundary = new OpenApiBoundary();
        leaseContract.setSigned(openApiBoundary.signContract(leaseContract.getContract()));
        leaseContractDAO.updateLease(leaseContract);
        AdDAO adDAO = daoFactoryFacade.getAdDAO();
        Ad ad = leaseContract.getAd();
        ad.setAdStatus(AdStatus.LEASED);
        adDAO.updateAd(ad);
    }
}
