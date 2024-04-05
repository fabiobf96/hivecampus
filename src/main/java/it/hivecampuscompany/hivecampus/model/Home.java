package it.hivecampuscompany.hivecampus.model;

import java.awt.geom.Point2D;

public class Home {
    private final int id;
    private final Point2D coordinates;
    private final String address;
    private final String homeType;
    private final int surface;
    private final String description;
    private Integer[] features; // nRooms, nBathrooms, floor, elevator

    public Home(int id, Point2D coordinates, String address, String homeType, int surface, String description) {
        this.id = id;
        this.coordinates = coordinates;
        this.address = address;
        this.homeType = homeType;
        this.surface = surface;
        this.description = description;
    }

    public Home(int id, Point2D coordinates, String address, String homeType, int surface, String description, Integer[] features) {
        this.id = id;
        this.coordinates = coordinates;
        this.address = address;
        this.homeType = homeType;
        this.surface = surface;
        this.description = description;
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

    public int getNRooms() {
        return features[0];
    }

    public int getNBathrooms() {
        return features[1];
    }

    public int getFloor() {
        return features[2];
    }

    public int getElevator() {
        return features[3];
    }

    @Override
    public String toString() {
        return address;
    }
}
