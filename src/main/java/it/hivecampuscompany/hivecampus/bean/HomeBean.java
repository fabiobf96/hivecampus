package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.view.utility.FormatText;
import java.awt.geom.Point2D;

public class HomeBean {
    private int idHome;
    private Point2D coordinates;
    private String address;
    private String type;
    private int surface;
    private Integer[] features;
    private String description;

    public HomeBean(){
        // Default constructor
    }

    public HomeBean(Home home) {
        this.idHome = home.getId();
        this.coordinates = new Point2D.Double(home.getLongitude(), home.getLatitude());
        this.address = home.getAddress();
        this.type = home.getHomeType();
        this.surface = home.getSurface();
        this.features = new Integer[]{home.getNRooms(), home.getNBathrooms(), home.getFloor(), home.getElevator()};
        this.description = home.getDescription();
    }

    public int getIdHome() {
        return idHome;
    }

    public Point2D getCoordinates() {
        return coordinates;
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

    public String getDetails() {
        return  " - Type: " + type + "\n" +
                " - Surface: " + surface + "\n" +
                " - Rooms: " + features[0] + "\n" +
                " - Bathrooms: " + features[1] + "\n" +
                " - Floor: " + features[2] + "\n" +
                " - Elevator: " + features[3] + "\n\n" +
                "House Description: " + "\n" + FormatText.formatText(description);
    }
}
