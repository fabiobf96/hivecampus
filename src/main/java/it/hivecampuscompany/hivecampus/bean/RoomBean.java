package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Room;

public class RoomBean {
    private int idRoom;
    private int idHome;
    private int surface;
    private String type;
    private boolean[] services;
    private String description;

    public RoomBean(){
        // Default constructor
    }

    public RoomBean(Room room) {
        this.idRoom = room.getIdRoom();
        this.idHome = room.getIdHome();
        this.surface = room.getSurface();
        this.type = room.getTypeRoom();
        this.services = new boolean[]{room.getBathroom(), room.getBalcony(), room.getConditioner(), room.getTV()};
        this.description = room.getDescription();
    }

    public int getIdRoom() {
        return idRoom;
    }

    public int getIdHome() {
        return idHome;
    }

    public int getSurface() {
        return surface;
    }

    public String getType() {
        return type;
    }

    public boolean getBathroom() {
        return services[0];
    }

    public boolean getBalcony() {
        return services[1];
    }

    public boolean getConditioner() {
        return services[2];
    }

    public boolean getTV() {
        return services[3];
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Room: " + type + "\n" +
                "Surface: " + surface + "\n" +
                "Private Bathroom: " + (services[0] ? "Yes" : "No") + "\n" +
                "Balcony: " + (services[1] ? "Yes" : "No") + "\n" +
                "Conditioner: " + (services[2] ? "Yes" : "No") + "\n" +
                "TV: " + (services[3] ? "Yes" : "No") + "\n" +
                "Description: " + description + "\n;";
    }
}
