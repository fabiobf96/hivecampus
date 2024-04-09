package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

public class LeaseRequestBean {
    private final int id;
    private AdBean adBean;
    private final AccountBean tenant;
    private final String month;
    private final String duration;
    private final String message;
    private LeaseRequestStatus status;

    public LeaseRequestBean(int id, AdBean adBean, AccountBean tenant, String month, String duration, String message, LeaseRequestStatus status) {
        this.id = id;
        setAdBean(adBean);
        this.tenant = tenant;
        this.month = month;
        this.duration = duration;
        this.message = message;
        setStatus(status);
    }

    public int getId() {
        return id;
    }
    public AdBean getAdBean() {
        return adBean;
    }
    public AccountBean getTenant() {
        return tenant;
    }
    public String getMonth() {
        return month;
    }
    public String getDuration() {
        return duration;
    }
    public String getMessage() {
        return message;
    }
    public LeaseRequestStatus getStatus() {
        return status;
    }
    public void setStatus (LeaseRequestStatus status) {
        this.status = status;
    }
    public void setAdBean(AdBean adBean) {
        this.adBean = adBean;
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
