package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.RoomBean;

public class Room {
    private final int idRoom;
    private final int idHome;
    private final int surface;
    private final String typeRoom;
    private final boolean[] services;
    private final String description;
    private byte[] roomImage;

    public Room(int idRoom, int idHome, int surface, String typeRoom, boolean[] services, String description) {
        this.idRoom = idRoom;
        this.idHome = idHome;
        this.surface = surface;
        this.typeRoom = typeRoom;
        this.services = services;
        this.description = description;
    }
    public Room(int idRoom, int idHome, int surface, String typeRoom, boolean[] services, String description, byte[] roomImage) {
        this (idRoom, idHome, surface, typeRoom, services, description);
        this.roomImage = roomImage;
    }
    public RoomBean toBasicBean() {
        return new RoomBean(idRoom, typeRoom);
    }
    public RoomBean toBean() {
        return new RoomBean(idRoom, surface, typeRoom, services, description);
    }
    public RoomBean toBeanWithImage() {
        RoomBean roomBean = toBean();
        roomBean.setImage(roomImage);
        return roomBean;
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

    public boolean[] getServices() {
        return services;
    }

    public String getDescription() {
        return description;
    }
}
