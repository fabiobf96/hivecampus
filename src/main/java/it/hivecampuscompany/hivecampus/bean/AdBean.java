package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.*;

public class AdBean {
    private int id;
    private AccountBean ownerBean;
    private HomeBean homeBean;
    private RoomBean roomBean;
    private AdStatus adStatus;
    private AdStart adStart;
    private int price;
    private String university;
    private double distance;


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

    public AdBean(Ad ad){
        this.id = ad.getId();
        this.ownerBean = new AccountBean(ad.getOwner());
        this.homeBean =  new HomeBean(ad.getHome());
        this.roomBean = new RoomBean(ad.getRoom());
        this.price = ad.getPrice();
        this.adStatus = ad.getAdStatus();
        this.adStart = ad.getAdStart();
        this.university = ad.getUniversity();
        this.distance = ad.getDistance();

    }

    public int getId() {
        return id;
    }

    public AccountBean getOwnerBean() {
        return ownerBean;
    }

    public HomeBean getHomeBean() {
        return homeBean;
    }

    public RoomBean getRoomBean() {
        return roomBean;
    }

    public int getPrice() {
        return price;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }

    public AdStart getAdStart() {
        return adStart;
    }

    public String getUniversity() {
        return university;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Ad: " + id + "\n" +
                "Owner: " + ownerBean + "\n" +
                "Home: " + homeBean + "\n" +
                "Room: " + roomBean + "\n" +
                "Price: " + price + "\n" +
                "Status: " + adStatus + "\n" +
                "Start: " + adStart + "\n";
    }
}