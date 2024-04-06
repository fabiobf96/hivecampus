package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.RoomBean;

public class Room {
    private int id;
    private int surface;
    private String typeRoom;
    private boolean[] services;
    private String description;

    public Room(int id, int surface, String typeRoom, boolean[] services, String description) {
        this.id = id;
        this.surface = surface;
        this.typeRoom = typeRoom;
        this.services = services;
        this.description = description;
    }

    public RoomBean toBasicBean() {
        return new RoomBean(id, typeRoom);
    }
}
