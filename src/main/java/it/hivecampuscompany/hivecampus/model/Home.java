package it.hivecampuscompany.hivecampus.model;

import java.awt.geom.Point2D;

public class Home {
    private int id;
    private Point2D coordinates;
    private String address;
    private String homeType;
    private int surface;
    private String description;
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

    @Override
    public String toString() {
        return address;
    }
}
