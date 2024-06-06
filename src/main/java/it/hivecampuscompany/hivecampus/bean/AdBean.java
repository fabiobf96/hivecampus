package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.*;

public class AdBean {
    private int id;
    private AccountBean ownerBean;
    private HomeBean homeBean;
    private RoomBean roomBean;
    private AdStatus adStatus;
    private Month adStart;
    private int price;
    private String university;
    private double distance;
    private byte[] map;

    public AdBean(AdStatus adStatus){
        this.adStatus = adStatus;
    }
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
        this.adStart = Month.fromInt(adStart);
    }

    public AdBean(Ad ad, String university, double distance){
        this.id = ad.getId();
        this.ownerBean = ad.getOwner().toBean();
        this.homeBean =  ad.getHome().toBean();
        this.roomBean = ad.getRoom().toBean();
        this.price = ad.getPrice();
        this.adStatus = ad.getAdStatus();
        this.adStart = ad.getAdStart();
        this.university = university;
        this.distance = distance;
    }

    // Aggiunto per far funzionare il metodo toBean in AD
    public AdBean(int id, AccountBean ownerBean, HomeBean homeBean, RoomBean roomBean, int price, int adStatus, int adStart) {
        this(id, homeBean, roomBean, price, adStatus, adStart);
        this.ownerBean = ownerBean;
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

    public AccountBean getOwnerBean() {
        return ownerBean;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }

    public int getPrice() {
        return price;
    }

    public void setMap(byte[] map) {
        this.map = map;
    }

    public byte[] getMap() {
        return map;
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

    public Month getAdStart() {
        return adStart;
    }

    public String getUniversity() {
        return university;
    }

    public double getDistance() {
        return distance;
    }

    public String adTitle() {
        return " " + roomBean.getType() + " - " + homeBean.getAddress() + " - " + price + " €";
    }

    // Aggiunti con la nuova versione
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public void setUniversity(String university) {
        this.university = university;
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