package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.AdBean;

public class Ad {
    private final int id;
    private Account owner;
    private Home home;
    private Room room;
    private AdStatus adStatus;
    private AdStart adStart;
    private final int price;


    public Ad(int id, Home home, Room room, int price) {
        this.id = id;
        this.home = home;
        this.room = room;
        this.price = price;
    }
    public Ad(int id, Home home, Room room, int adStatus, int adStart, int price) {
        this(id, home, room, price);
        this.adStatus = AdStatus.fromInt(adStatus);
        this.adStart = AdStart.fromInt(adStart);
    }
    public Ad(int id, Account owner, Home home, Room room, int adStatus, int adStart, int price) {
        this(id, home, room, adStatus, adStart, price);
        this.owner = owner;
    }


    public AdBean toBean() {
        return new AdBean(id, home.toBasicBean(), room.toBasicBean(), price, adStatus.getId(), adStart.getMonth());
    }

    public int getId() {
        return id;
    }

    public Account getOwner() {
        return owner;
    }

    public Home getHome() {
        return home;
    }

    public Room getRoom() {
        return room;
    }

    public AdStatus getAdStatus(){
        return adStatus;
    }

    public AdStart getAdStart(){
        return adStart;
    }

    public void setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public int getPrice() {
        return price;
    }
    public AdBean toBasicBean() {
        return new AdBean(id, home.toBasicBean(), room.toBasicBean(), price);
    }
}
