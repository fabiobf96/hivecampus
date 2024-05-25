package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.Component;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.ImageDecorator;

public class Ad {
    private final int id;
    private Account owner;
    private Component<HomeBean> home;
    private Component<RoomBean> room;
    private AdStatus adStatus;
    private Month adStart;
    private final int price;

    /*
    public Ad(int id, Home home, Room room, int price) {
        this.id = id;
        this.home = home;
        this.room = room;
        this.price = price;
    }
    public Ad(int id, Home home, Room room, int adStatus, int adStart, int price) {
        this(id, home, room, price);
        this.adStatus = AdStatus.fromInt(adStatus);
        this.adStart = Month.fromInt(adStart);
    }
    public Ad(int id, Account owner, Home home, Room room, int adStatus, int adStart, int price) {
        this(id, home, room, adStatus, adStart, price);
        this.owner = owner;
    }
    */

    public Ad(int id, Component<HomeBean> home, Component<RoomBean> room, int price) {
        this.id = id;
        this.home = home;
        this.room = room;
        this.price = price;
    }
    public Ad(int id, Component<HomeBean> home, Component<RoomBean> room, int adStatus, int adStart, int price) {
        this(id, home, room, price);
        this.adStatus = AdStatus.fromInt(adStatus);
        this.adStart = Month.fromInt(adStart);
    }
    public Ad(int id, Account owner, Component<HomeBean> home, Component<RoomBean> room, int adStatus, int adStart, int price) {
        this(id, home, room, adStatus, adStart, price);
        this.owner = owner;
    }

    /*
    public AdBean toBean() {
        return new AdBean(id, home.toBasicBean(), room.toBean(), price, adStatus != null ? adStatus.getId() : -1, adStart != null ? adStart.getMonth() : -1);
    }
    public AdBean toBeanWithImage() {
        return new AdBean(id, home.toBasicBean(), room.toBeanWithImage(), price, adStatus != null ? adStatus.getId() : -1, adStart != null ? adStart.getMonth() : -1);
    }
    */

    public int getId() {
        return id;
    }

    public Account getOwner() {
        return owner;
    }

    // Modificati per richiesta di cast
    public Home getHome() {
        return (Home) home;
    }

    public Room getRoom() {
        return (Room) room;
    }
    //

    public AdStatus getAdStatus(){
        return adStatus;
    }

    public Month getAdStart(){
        return adStart;
    }

    public void setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public int getPrice() {
        return price;
    }

    // Aggiunti con la nuova versione
    public void setRoom(Component<RoomBean> room) {
        this.room = room;
    }
    public void setHome(Component<HomeBean> home) {
        this.home = home;
    }

    /*
    public AdBean toBasicBean() {
        return new AdBean(id, home.toBasicBean(), room.toBasicBean(), price);
    }
    */

    // Modificati con la nuova versione
    public AdBean toBean() {
        return new AdBean(id, owner != null ? owner.toBasicBean() : null, home.toBean(), room.toBean(), price, adStatus != null ? adStatus.getId() : -1, adStart != null ? adStart.getMonth() : -1);
    }

    public AdBean toBeanWithImage() {
        return new AdBean(id, ((Home) home).toBasicBean(), ((Room) room).toBeanWithImage(), price, adStatus != null ? adStatus.getId() : -1, adStart != null ? adStart.getMonth() : -1);
    }

    public AdBean toBasicBean() {
        return new AdBean(id, ((Home) home).toBasicBean(), ((Room) room).toBasicBean(), price);
    }
}
