package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;

public class LeaseRequest extends Lease {
    private final String message;
    private LeaseRequestStatus status;

    public LeaseRequest(int id, Ad ad, Account tenant, int month, int duration, String message, int status) {
        super(id, ad, tenant, Month.fromInt(month), Permanence.fromInt(duration));
        this.message = message;
        this.status = LeaseRequestStatus.fromInt(status);

    }

    public LeaseRequest(Ad ad, Account tenant, int month, int duration, int status, String message) {
        this(0, ad, tenant, month, duration, message, status);
    }

    public void setStatus(LeaseRequestStatus status) {
        this.status = status;
    }

    public LeaseRequestBean toBean() {
        return new LeaseRequestBean(id, ad != null ? ad.toBean() : null, tenant != null ? tenant.toBean() : null, month.getMonth(), duration.getPermanence(), message, status);
    }

    public String getMessage() {
        return message;
    }

    public LeaseRequestStatus getStatus() {
        return status;
    }
}
