package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.Component;

public class Room extends Component<RoomBean> {
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

    @Override
    public RoomBean toBean() {
        return new RoomBean(idRoom, surface, typeRoom, services, description);
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
