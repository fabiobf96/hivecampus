package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.LeaseBean;

import java.time.Instant;

public class Lease {
    private int id;
    private Ad ad;
    private Account tenant;
    private String starting;
    private String duration;
    private byte[] contract;
    private boolean signed;
    private Instant timeStamp;

    public Lease(Ad ad, Account tenant, String starting, String duration, byte[] contract, boolean signed, Instant timeStamp) {
        this.ad = ad;
        this.tenant = tenant;
        this.starting = starting;
        this.duration = duration;
        this.contract = contract;
        setSigned(signed);
        setTimeStamp(timeStamp);
    }

    public Lease(int id, Ad ad, String starting, String duration, byte[] contract, boolean signed, Instant timeStamp) {
        this.id = id;
        this.ad = ad;
        this.starting = starting;
        this.duration = duration;
        this.contract = contract;
        setSigned(signed);
        setTimeStamp(timeStamp);
    }

    public int getId() {
        return id;
    }

    public Ad getAd() {
        return ad;
    }

    public Account getTenant() {
        return tenant;
    }

    public String getStarting() {
        return starting;
    }

    public String getDuration() {
        return duration;
    }

    public byte[] getContract() {
        return contract;
    }

    public boolean isSigned() {
        return signed;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }
    public void setSigned(boolean signed){
        this.signed = signed;
    }
    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
    public LeaseBean toBean() {
        return new LeaseBean(null, starting, duration, contract, signed, timeStamp);
    }

}
