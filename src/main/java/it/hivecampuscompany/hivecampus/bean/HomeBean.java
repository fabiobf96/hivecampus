package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.state.utility.Utility;

public class HomeBean {
    private int idHome;
    private final String address;
    private String type;
    private int surface;
    private Integer[] features;
    private String description;
    private byte[] homeImage;
    private String imageName;

    public HomeBean (String address, String type, int surface, Integer[] features, String description) {
        this.address = address;
        this.type = type;
        this.surface = surface;
        this.features = features;
        this.description = description;
    }

    public HomeBean(int id, String address) {
        this.idHome = id;
        this.address = address;
    }

    public HomeBean(int id, String address, String homeType, int surface, Integer[] features, String description) {
        this.idHome = id;
        this.address = address;
        this.type = homeType;
        this.surface = surface;
        this.features = features;
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public String getStreet() {
        return address.split(",")[0];
    }

    public String getStreetNumber() {
        return address.split(",")[1];
    }

    public String getCity() {
        return address.split(",")[2];
    }

    public String getType() {
        return type;
    }

    public int getSurface() {
        return surface;
    }

    public int getNRooms(){
        return features[0];
    }

    public int getNBathrooms(){
        return features[1];
    }

    public int getFloor(){
        return features[2];
    }

    public int getElevator(){
        return features[3];
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return idHome;
    }

    public void setIdHome(int idHome) {
        this.idHome = idHome;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public void setFeatures(Integer[] features) {
        this.features = features;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHomeImage(byte[] homeImage) {
        this.homeImage = homeImage;
    }

    public byte[] getHomeImage() {
        return homeImage;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString(){
        return address;
    }

    public String getDetails() {
        return  " - Type: " + type + "\n" +
                " - Surface: " + surface + "\n" +
                " - Rooms: " + features[0] + "\n" +
                " - Bathrooms: " + features[1] + "\n" +
                " - Floor: " + features[2] + "\n" +
                " - Elevator: " + features[3] + "\n\n" +
                "House Description: " + "\n" + Utility.formatText(description);
    }
}
