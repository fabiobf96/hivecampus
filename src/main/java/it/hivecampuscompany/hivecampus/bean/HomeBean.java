package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.viewCli.utility.FormatText;

public class HomeBean {
    private int idHome;
    private String address;
    private String type;
    private int surface;
    private Integer[] features;
    private String description;
    private byte[] image;
    private String imageName;

    public HomeBean(){
        // Default constructor
    }

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

    public HomeBean(Home home) {
        this.idHome = home.getId();
        this.address = home.getAddress();
        this.type = home.getHomeType();
        this.surface = home.getSurface();
        this.features = new Integer[]{home.getNRooms(), home.getNBathrooms(), home.getFloor(), home.getElevator()};
        this.description = home.getDescription();
    }

    public String getAddress() {
        return address;
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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
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
