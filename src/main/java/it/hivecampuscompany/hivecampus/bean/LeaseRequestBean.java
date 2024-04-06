package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

public class LeaseRequestBean {
    private final int id;
    private AdBean adBean;
    private final AccountBean tenant;
    private final String month;
    private final String duration;
    private final String message;
    private final LeaseRequestStatus status;

    public LeaseRequestBean(int id, AdBean adBean, AccountBean tenant, String month, String duration, String message, LeaseRequestStatus status) {
        this.id = id;
        this.adBean = adBean;
        this.tenant = tenant;
        this.month = month;
        this.duration = duration;
        this.message = message;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        String result = "";
        if (adBean != null) {
            result += adBean.toString() + ", ";
        }
        if (tenant != null){
            result += tenant.toString() + ", ";
        }
        result += month + ", " + duration + ", " + message;
        if (status != null) {
            result += ", " + status;
        }
        return result;
    }
}
