package it.hivecampuscompany.hivecampus.model;

public abstract class Lease {
    protected int id;
    protected Ad ad;
    protected Account tenant;
    protected Month month;
    protected Permanence duration;

    Lease(int id, Ad ad, Account tenant, Month month, Permanence duration) {
        setId(id);
        setAd(ad);
        setTenant(tenant);
        setMonth(month);
        setDuration(duration);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Account getTenant() {
        return tenant;
    }

    public void setTenant(Account tenant) {
        this.tenant = tenant;
    }

    public Month getLeaseMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Permanence getDuration() {
        return duration;
    }

    public void setDuration(Permanence duration) {
        this.duration = duration;
    }
}
