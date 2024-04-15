package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.model.Room;
import java.util.List;

/**
 * RoomDAO interface for managing room data within homes.
 * Provides an operation for retrieving a room by its unique identifiers within a home context.
 */
public interface RoomDAO {

    /**
     * Retrieves a room by its unique identifiers, specified by a home ID and a room ID.
     *
     * @param homeID The ID of the home in which the room is located.
     * @param roomID The specific ID of the room to retrieve.
     * @return The {@link Room} object if found, otherwise null. This method ensures
     *         that the room is uniquely identified within the context of its home.
     */
    Room retrieveRoomByID(int homeID, int roomID);

    List<Room> retrieveRoomsByFilters(int homeID, FiltersBean filtersBean);

    byte[] getRoomImage(int homeID, int roomID);
}
