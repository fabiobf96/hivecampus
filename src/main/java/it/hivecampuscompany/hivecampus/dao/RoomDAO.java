package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.model.Room;

import java.util.List;

/**
 * RoomDAO interface for managing room data within homes.
 * Provides an operation for retrieving a room by its unique identifiers within a home context
 * and filtering rooms by various criteria, as well as saving a new room.
 */
public interface RoomDAO {

    /**
     * Retrieves a room by its unique identifiers, specified by a home ID and a room ID.
     *
     * @param homeID The ID of the home in which the room is located.
     * @param roomID The specific ID of the room to retrieve.
     * @return The {@link Room} object if found, otherwise null. This method ensures
     * that the room is uniquely identified within the context of its home.
     * @author Fabio Barchiesi
     */
    Room retrieveRoomByID(int homeID, int roomID);

    /**
     * Retrieves a list of rooms by filters.
     *
     * @param homeID      The ID of the home in which the rooms are located.
     * @param filtersBean The filters to apply to the room search.
     * @return The list of {@link Room} objects that match the filters.
     * @author Marina Sotiropoulos
     */
    List<Room> retrieveRoomsByFilters(int homeID, FiltersBean filtersBean);

    /**
     * Saves a room object in the database.
     *
     * @param homeID   The ID of the home in which the room is located.
     * @param roomBean The room to be saved.
     * @return The saved {@link Room} object.
     * @author Marina Sotiropoulos
     */

    Room saveRoom(int homeID, RoomBean roomBean);

    /**
     * Saves the image of a room.
     *
     * @param imageName The name of the image.
     * @param imageType The type of the image.
     * @param byteArray The byte array representing the image.
     * @param idRoom    The unique identifier of the room.
     * @param idHome    The unique identifier of the home.
     * @author Marina Sotiropoulos
     */

    void saveRoomImage(String imageName, String imageType, byte[] byteArray, int idRoom, int idHome);

    /**
     * Retrieves the image of a room by its unique identifiers, specified by a home ID and a room ID.
     *
     * @param idRoom The ID of the room for which to retrieve the image.
     * @param idHome The ID of the home in which the room is located.
     * @return The byte array representing the image of the room if found, otherwise null.
     * @author Marina Sotiropoulos
     */

    byte[] getRoomImage(int idRoom, int idHome);

    /**
     * Retrieves the number of rooms already present in a home.
     *
     * @param homeID The ID of the home for which to retrieve the number of rooms.
     * @return The number of rooms already present in the home.
     * @author Marina Sotiropoulos
     */

    long getRoomsAlreadyPresent(int homeID);
}
