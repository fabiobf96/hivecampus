package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;

public class LeaseRequest {
    private int id;
    private Ad ad;
    private final Account tenant;
    private final Month month;
    private final Permanence duration;
    private final String message;
    private LeaseRequestStatus status;

    public LeaseRequest(int id, Account tenant, int month, int duration, int status, String message) {
        this(tenant, month, duration, message);
        this.id = id;
        this.status = LeaseRequestStatus.fromInt(status);
    }

    public LeaseRequest(int id, Ad ad, Account tenant, int month, int duration, String message, int status) {
        this(id, tenant, month, duration, status, message);
        this.ad = ad;
    }

    private LeaseRequest(Account tenant, int month, int duration, String message) {
        this.tenant = tenant;
        this.month = Month.fromInt(month);
        this.duration = Permanence.fromInt(duration);
        this.message = message;
    }

    public LeaseRequest(Ad ad, Account tenant, int month, int duration, int status, String message) {
        this(tenant, month, duration, message);
        this.ad = ad;
        this.status = LeaseRequestStatus.fromInt(status);
    }

    public Ad getAd() {
        return ad;
    }

    public void setStatus(LeaseRequestStatus status) {
        this.status = status;
    }

    public LeaseRequestBean toBean() {
        return new LeaseRequestBean(id, ad != null ? ad.toBean() : null, tenant != null ? tenant.toBean() : null, month.getMonth(), duration.getPermanence(), message, status);
    }
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public Account getTenant() {
        return tenant;
    }

    public Month getLeaseMonth() {
        return month;
    }

    public Permanence getDuration() {
        return duration;
    }

    public String getMessage() {
        return message;
    }

    public LeaseRequestStatus getStatus() {
        return status;
    }
}
