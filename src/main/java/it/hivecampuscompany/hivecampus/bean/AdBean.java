package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.*;

public class AdBean {
    private final int id;
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

    public AdBean(int id, HomeBean homeBean, RoomBean roomBean, int price) {
        this.id = id;
        this.homeBean = homeBean;
        this.roomBean = roomBean;
        this.price = price;
    }

    public AdBean(int id, HomeBean homeBean, RoomBean roomBean, int price, int adStatus, int adStart) {
        this(id, homeBean, roomBean, price);
        this.adStatus = AdStatus.fromInt(adStatus);
        this.adStart = AdStart.fromInt(adStart);
    }

    public AdBean(Ad ad, String university, double distance){
        this.id = ad.getId();
        this.ownerBean = new AccountBean(ad.getOwner());
        this.homeBean =  new HomeBean(ad.getHome());
        this.roomBean = new RoomBean(ad.getRoom());
        this.price = ad.getPrice();
        this.adStatus = ad.getAdStatus();
        this.adStart = ad.getAdStart();
        this.university = university;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public HomeBean getHomeBean() {
        return homeBean;
    }

    public RoomBean getRoomBean() {
        return roomBean;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString(){
        String result = homeBean.toString() + ", " + roomBean.toString();
        if (adStatus != null){
            result += ", " + adStatus;
        }
        if (adStart != null){
            result += ", " + adStart;
        }
        if (ownerBean != null){
            ownerBean.toString();
        }
        result += ", " + price + " €";
        return result;
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

    public String adTitle() {
        return " " + homeBean.getType() + " - " + homeBean.getAddress() + " - " + price + " €";
    }

    public String getPreview() {
        return  adTitle() + "\n\n" +
                "Room Features: " + "\n" + roomBean.getPreview() + "\n\n" +
                "Distance from university: " + distance + " km" + "\n"
                + "____________________________________________________________\n";
    }

    public String getDetails() {
        return  adTitle() + "\n\n" +
                "Home Features: " + "\n" + homeBean.getDetails() + "\n\n" +
                "Room Features: " + "\n" + roomBean.getDetails() + "\n\n" +
                "Month Availability: " + adStart + "\n\n" +
                "Owner information: " + "\n" + ownerBean.getDetails() + "\n"
                + "____________________________________________________________\n";
    }

    public String toFormatString(String format) {
        return switch (format) {
            case "preview" -> getPreview();
            case "details" -> getDetails();
            default -> toString();
        };
    }
}