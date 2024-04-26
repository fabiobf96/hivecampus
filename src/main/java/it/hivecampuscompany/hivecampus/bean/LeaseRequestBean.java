package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

public class LeaseRequestBean {
    private int id;
    private AdBean adBean;
    private AccountBean tenant;
    private String month;
    private String duration;
    private String message;
    private LeaseRequestStatus status;

    public LeaseRequestBean(){
        // Default constructor
    }

    public LeaseRequestBean(int id, AdBean adBean, AccountBean tenant, String month, String duration, String message, LeaseRequestStatus status) {
        this.id = id;
        setAdBean(adBean);
        setTenant(tenant);
        setMonth(month);
        setDuration(duration);
        setMessage(message);
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
    public void setTenant(AccountBean tenant) {
        this.tenant = tenant;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        String result = "";
        if (adBean != null) {
            result += adBean + ", ";
        }
        if (tenant != null){
            result += tenant + ", ";
        }
        result += month + ", " + duration + ", " + message;
        if (status != null) {
            result += ", " + status;
        }
        return result;
    }
}
