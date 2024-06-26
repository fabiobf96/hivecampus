package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.state.utility.Utility;

public class RoomBean {
    private int idRoom;
    private int idHome;
    private int surface;
    private final String typeRoom;
    private boolean[] services;
    private String description;
    private byte[] roomImage;
    private String imageName;

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

    public RoomBean(int idRoom, int surface, String typeRoom, boolean[] services, String description) {
        this.idRoom = idRoom;
        this.surface = surface;
        this.typeRoom = typeRoom;
        this.services = services;
        this.description = description;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdHome() {
        return idHome;
    }

    public void setIdHome(int idHome) {
        this.idHome = idHome;
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

    public void setRoomImage(byte[] roomImage) {
        this.roomImage = roomImage;
    }

    public byte[] getRoomImage() {
        return roomImage;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
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
                "Room Description: " + "\n" + Utility.formatText(description);
    }
}
