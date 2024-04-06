package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.AdStatus;

public class AdBean {
    private int id;
    private AccountBean owner;
    private HomeBean home;
    private RoomBean room;
    private AdStatus adStatus;
    private int price;
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

    public AdBean(int id, HomeBean home, RoomBean room, AdStatus adStatus, int price) {
        this.id = id;
        this.home = home;
        this.room = room;
        this.adStatus = adStatus;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }
    @Override
    public String toString(){
        String result = home.toString() + ", " + room.toString();
        if (adStatus != null){
            result += ", " + adStatus;
        }
        if (owner != null){
            owner.toString();
        }
        result += ", " + price;
        return result;
    }
}