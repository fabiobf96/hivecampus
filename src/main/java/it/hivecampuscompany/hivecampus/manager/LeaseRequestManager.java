package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

import java.util.ArrayList;
import java.util.List;

public class LeaseRequestManager {
    public List<AdBean> searchAdsByFilters(FiltersBean filtersBean){
        List<AdBean> adBeanList = new ArrayList<>();
        return adBeanList;
    }
    public List<LeaseRequestBean> searchLeaseRequestsByAd (AdBean adBean){
        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
        List<LeaseRequest> leaseRequestList = leaseRequestDAO.retrieveLeaseRequestsByAdID(adBean);
        List<LeaseRequestBean> leaseRequestBeanList = new ArrayList<>();
        for (LeaseRequest leaseRequest : leaseRequestList) {
            leaseRequestBeanList.add(leaseRequest.toBasicBean());
        }
        return leaseRequestBeanList;
    }

    public void modifyLeaseRequest(LeaseRequestBean leaseRequestBean){
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
    }
}
