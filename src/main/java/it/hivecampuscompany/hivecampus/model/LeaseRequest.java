package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;

public class LeaseRequest {

    private int idRequest;
    private Account tenant;
    private Ad ad;
    private String typePermanence;
    private String startPermanence;
    private String statusRequest;

    public LeaseRequest(LeaseRequestBean leaseRequestBean) {
    }

    public void save() {
    }
}
