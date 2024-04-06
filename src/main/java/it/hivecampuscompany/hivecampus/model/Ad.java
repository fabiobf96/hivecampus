package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.AdBean;

public class Ad {
    private final int id;
    private Account owner;
    private Home home;
    private Room room;
    private AdStatus adStatus;
    private final int price;

    public Ad(int id, int adStatus, int price) {
        this.id = id;
        this.adStatus = AdStatus.fromInt(adStatus);
        this.price = price;
    }

    public Ad(int id, Home home, Room room, int price) {
        this.id = id;
        this.home = home;
        this.room = room;
        this.price = price;
    }

    public AdBean toBasicBean() {
        return new AdBean(id, home.toBasicBean(), room.toBasicBean(), price);
    }

    public int getId() {
        return id;
    }
    public AdStatus getAdStatus(){
        return adStatus;
    }

    public void setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public int getPrice() {
        return price;
    }
}
