package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.state.utility.Utility;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.Component;

import java.awt.geom.Point2D;

public class Home extends Component<HomeBean> {
    private final int id;
    private Point2D coordinates;
    private final String address;
    private final String homeType;
    private int surface;
    private String description;
    private Integer[] features; // nRooms, nBathrooms, floor, elevator

    public Home(int id, String address, String homeType) {
        this.id = id;
        this.address = address;
        this.homeType = homeType;
    }

    public Home(int id, Point2D coordinates, String address, String homeType, int surface, String description) {
        this(id, address, homeType);
        this.coordinates = coordinates;
        this.surface = surface;
        this.description = description;
    }

    public Home(int id, Point2D coordinates, String address, String homeType, int surface, String description, Integer[] features) {
        this(id, coordinates, address, homeType, surface, description);
        this.features = features;
    }

    public int getId() {
        return id;
    }

    public double getLongitude() {
        return coordinates.getX();
    }

    public double getLatitude() {
        return coordinates.getY();
    }

    public String getAddress() {
        return address;
    }

    public String getHomeType() {
        return homeType;
    }

    public int getSurface() {
        return surface;
    }

    public String getDescription() {
        return description;
    }

    public Integer[] getFeatures() {
        return features;
    }

    public double calculateDistance(Point2D uniCoordinates) {
        return Utility.calculateDistance(getLongitude(), getLatitude(), uniCoordinates.getX(), uniCoordinates.getY());
    }

    @Override
    public HomeBean toBean() {
        return new HomeBean(getId(), getAddress(), getHomeType(), getSurface(), getFeatures(), getDescription());
    }
}
