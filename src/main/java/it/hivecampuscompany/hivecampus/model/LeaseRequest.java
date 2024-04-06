package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;

public class LeaseRequest {
    private final int id;
    private Ad ad;
    private final Account tenant;
    private final String month;
    private final String duration;
    private final String message;
    private LeaseRequestStatus status;
    public LeaseRequest(int id, Account tenant, String month, String duration, String message) {
        this.id = id;
        this.tenant = tenant;
        this.month = month;
        this.duration = duration;
        this.message = message;
    }
    public LeaseRequest(int id, Account tenant, String month, String duration, int status, String message) {
        this.id = id;
        this.tenant = tenant;
        this.month = month;
        this.duration = duration;
        this.message = message;
        this.status = LeaseRequestStatus.fromInt(status);
    }

    public LeaseRequest(int id, Ad ad, Account tenant, String month, String duration, String message, int status) {
        this(id, tenant, month, duration, status, message);
        this.ad = ad;
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
}
