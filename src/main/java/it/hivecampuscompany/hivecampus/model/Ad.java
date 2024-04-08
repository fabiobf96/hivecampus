package it.hivecampuscompany.hivecampus.model;

public class Ad {
    private final int id;
    private Account owner;
    private Home home;
    private Room room;
    private AdStatus adStatus;
    private AdStart adStart;
    private final int price;
    private String university;
    private double distance;

    private Integer[] leaseInfo;

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

    public Ad(int id, Account owner, Home home, Room room, int adStatus, int adMonth, int price, String university, double distance) {
        this.id = id;
        this.owner = owner;
        this.home = home;
        this.room = room;
        this.adStatus = AdStatus.fromInt(adStatus);
        this.adStart = AdStart.fromInt(adMonth);
        this.price = price;
        this.university = university;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", owner=" + (owner != null ? owner.toString() : "null") + // Gestisce owner null
                ", home=" + (home != null ? home.toString() : "null") + // Gestisce home null
                ", room=" + (room != null ? room : "null") + // Gestisce room null
                ", adStatus=" + (adStatus != null ? adStatus : "null") + // Gestisce adStatus null
                ", price=" + price +
                '}';
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

    public String getUniversity() {
        return university;
    }

    public double getDistance() {
        return distance;
    }
}
