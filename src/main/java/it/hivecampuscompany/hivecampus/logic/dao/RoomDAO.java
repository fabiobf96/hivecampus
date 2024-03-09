package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.List;

public interface RoomDAO {
    List<Room> retrieveRoomsByIDHome(int idHome);
    List<Room> retrieveAvailableRoomsByIDHome(int idHome);
}
