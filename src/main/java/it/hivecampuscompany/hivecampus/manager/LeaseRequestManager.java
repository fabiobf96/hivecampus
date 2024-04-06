package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;

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
}
