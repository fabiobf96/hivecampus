package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

public class LeaseRequestBean {
    private int id;
    private LeaseRequestStatus leaseRequestStatus;
    public LeaseRequestBean (int id){
        this.id = id;
    }

    public LeaseRequestBean(int id, int leaseRequestStatus) {
        this.id = id;
        this.leaseRequestStatus = LeaseRequestStatus.fromInt(leaseRequestStatus);
    }

    public int getId() {
        return id;
    }

    public LeaseRequestStatus getLeaseRequestStatus() {
        return leaseRequestStatus;
    }
}
