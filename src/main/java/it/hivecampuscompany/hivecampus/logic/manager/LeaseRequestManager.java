package it.hivecampuscompany.hivecampus.logic.manager;

import it.hivecampuscompany.hivecampus.logic.bean.*;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;

import java.util.ArrayList;
import java.util.List;

public class LeaseRequestManager {
    public List<AdBean> searchAdsByFilters(FiltersBean filtersBean){
        List<AdBean> adBeanList = new ArrayList<>();
        return adBeanList;
    }

    public void sendLeaseRequest(LeaseRequestBean leaseRequestBean){
        LeaseRequest leaseRequest = new LeaseRequest(leaseRequestBean);
        leaseRequest.save();
    }

    public List<LeaseRequestBean> searchLeaseRequestsByRoomID (RoomBean roomBean){

    }
}
