package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.RoomBean;

public class Room {
    private final int idRoom;
    private final int idHome;
    private final int surface;
    private final String typeRoom;
    private final boolean[] services;
    private final String description;

    public Room(int idRoom, int idHome, int surface, String typeRoom, boolean[] services, String description) {
        this.idRoom = idRoom;
        this.idHome = idHome;
        this.surface = surface;
        this.typeRoom = typeRoom;
        this.services = services;
        this.description = description;
    }

    public RoomBean toBasicBean() {
        return new RoomBean(idRoom, typeRoom);
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

    public String getTypeRoom() {
        return typeRoom;
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
}
