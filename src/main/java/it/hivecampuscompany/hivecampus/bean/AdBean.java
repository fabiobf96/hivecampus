package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.AdStatus;

public class AdBean {
    private int id;
    private AdStatus adStatus;
    public AdBean (int id){
        this.id = id;
    }

    public AdBean(AdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public AdBean(int id, AdStatus adStatus){
        this.id = id;
        this.adStatus = adStatus;
    }

    public int getId() {
        return id;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }
}