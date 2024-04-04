package it.hivecampuscompany.hivecampus.model;

public class LeaseRequest {
    private final int id;
    private Ad ad;
    private final Account tenant;
    private final String month;
    private final String duration;
    private final String message;
    private LeaseRequestStatus status;

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
        return new String[] {String.valueOf(id), String.valueOf(ad.getId()), tenant.getEmail(), String.valueOf(status.getId()), month, duration, message};
    }

    @Override
    public String toString() {
        return "LeaseRequest{" +
                "id=" + id +
                ", ad=" + (ad != null ? ad.toString() : "null") +
                ", tenant=" + tenant.toString() +
                ", month='" + month + '\'' +
                ", duration='" + duration + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

    public int getID() {
        return id;
    }

    public Ad getAd() {
        return ad;
    }

    public void setStatus(LeaseRequestStatus status) {
        this.status = status;
    }
}
