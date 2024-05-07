package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Room;
import it.hivecampuscompany.hivecampus.view.utility.FormatText;

public class RoomBean {
    private int idRoom;
    private int idHome;
    private int surface;
    private final String typeRoom;
    private boolean[] services;
    private String description;

    public RoomBean(int id, String typeRoom) {
        this.idRoom = id;
        this.typeRoom = typeRoom;
    }

    public RoomBean(String typeRoom, int surface, boolean[] services, String description) {
        this.typeRoom = typeRoom;
        this.surface = surface;
        this.services = services;
        this.description = description;
    }

    public RoomBean(Room room) {
        this(room.getTypeRoom(), room.getSurface(), new boolean[]{room.getBathroom(), room.getBalcony(), room.getConditioner(), room.getTV()}, room.getDescription());
        this.idRoom = room.getIdRoom();
        this.idHome = room.getIdHome();
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

    @Override
    public String toString(){
        return typeRoom;
    }

    public String getPreview() {
        return  " - Type: " + typeRoom + "\n" +
                " - Surface: " + surface + "\n" +
                " - Private Bathroom: " + (services[0] ? "Yes" : "No") + "\n" +
                " - Balcony: " + (services[1] ? "Yes" : "No") + "\n" +
                " - Conditioner: " + (services[2] ? "Yes" : "No") + "\n" +
                " - TV: " + (services[3] ? "Yes" : "No");
    }

    public String getDetails() {
        return  " - Type: " + typeRoom + "\n" +
                " - Surface: " + surface + "\n" +
                " - Private Bathroom: " + (services[0] ? "Yes" : "No") + "\n" +
                " - Balcony: " + (services[1] ? "Yes" : "No") + "\n" +
                " - Conditioner: " + (services[2] ? "Yes" : "No") + "\n" +
                " - TV: " + (services[3] ? "Yes" : "No") + "\n\n" +
                "Room Description: " + "\n" + FormatText.formatText(description);
    }
}
