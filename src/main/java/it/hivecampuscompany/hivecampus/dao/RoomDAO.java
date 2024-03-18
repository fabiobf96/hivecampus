package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.model.Room;

import java.util.List;

public interface RoomDAO {
    List<Room> retrieveRoomsByIDHome(int idHome);
    List<Room> retrieveAvailableRoomsByIDHome(int idHome);
}
