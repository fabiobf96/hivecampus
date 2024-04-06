package it.hivecampuscompany.hivecampus.bean;

public class RoomBean {
    private final int id;
    private final String typeRoom;
    public RoomBean(int id, String typeRoom) {
        this.id = id;
        this.typeRoom = typeRoom;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return typeRoom;
    }
}
