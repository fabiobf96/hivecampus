package it.hivecampuscompany.hivecampus.model;

import java.time.Instant;

public class Lease {
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
        this.signed = signed;
        this.timeStamp = timeStamp;
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
}
