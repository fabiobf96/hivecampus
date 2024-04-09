package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.*;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

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
}
