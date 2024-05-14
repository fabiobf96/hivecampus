package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Month;
import it.hivecampuscompany.hivecampus.model.Permanence;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

public class LeaseRequestBean {
    private int id;
    private AdBean adBean;
    private AccountBean tenant;
    private Month month;
    private Permanence permanence;
    private String message;
    private LeaseRequestStatus status;

    public LeaseRequestBean(){
        // Default constructor
    }

    public LeaseRequestBean(int id, AdBean adBean, AccountBean tenant, int month, int duration, String message, LeaseRequestStatus status) {
        this.id = id;
        setAdBean(adBean);
        setTenant(tenant);
        setLeaseMonth(month);
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
    public Month getLeaseMonth() {
        return month;
    }
    public Permanence getDuration() {
        return permanence;
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
    public void setLeaseMonth(int month) {
        this.month = Month.fromInt(month);
    }
    public void setDuration(int duration) {
        this.permanence = Permanence.fromInt(duration);
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
        result += month.toString() + ", " + permanence.toString() + ", " + message;
        if (status != null) {
            result += ", " + status;
        }
        return result;
    }

    public String getPreview() {
        return adBean + " - " + month.toString() + " - " + permanence.toString() + " - " + status.toString();
    }

    public String getDetails() {
        return  adBean + "\n\n" +
                "Type of Stay: " + permanence.toString() + "\n\n" +
                "Start of Stay: " + month.toString() + "\n\n" +
                "Message for the Owner: " + message + "\n\n" +
                "Request Status: " + status.toString() + "\n"
                + "____________________________________________________________\n";
    }
}
