package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;

public class LeaseRequest {
    private int id;
    private Ad ad;
    private final Account tenant;
    private final String month;
    private final String duration;
    private final String message;
    private LeaseRequestStatus status;

    public LeaseRequest(int id, Account tenant, String month, String duration, String message) {
        this(tenant, month, duration, message);
        this.id = id;
    }
    public LeaseRequest(int id, Account tenant, String month, String duration, int status, String message) {
        this(tenant, month, duration, message);
        this.id = id;
        this.status = LeaseRequestStatus.fromInt(status);
    }

    public LeaseRequest(int id, Ad ad, Account tenant, String month, String duration, String message, int status) {
        this(id, tenant, month, duration, status, message);
        this.ad = ad;
    }

    public LeaseRequest(Ad ad, Account tenant, String month, String duration, int status, String message) {
        this(tenant, month, duration, message);
        this.ad = ad;
        this.status = LeaseRequestStatus.fromInt(status);
    }

    public LeaseRequest(Account tenant, String month, String duration, String message) {
        this.tenant = tenant;
        this.month = month;
        this.duration = duration;
        this.message = message;
    }

    public Ad getAd() {
        return ad;
    }

    public void setStatus(LeaseRequestStatus status) {
        this.status = status;
    }

    public String[] toCSVString() {
        return new String[]{String.valueOf(id), String.valueOf(ad.getId()), tenant.getEmail(), String.valueOf(status.getId()), month, duration, message};
    }
    public LeaseRequestBean toBasicBean() {
        return new LeaseRequestBean(id, ad != null ? ad.toBasicBean() : null, tenant != null ? tenant.toBasicBean() : null, month, duration, message, status);
    }
    public int getID() {
        return id;
    }

    public Account getTenant() {
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
}
