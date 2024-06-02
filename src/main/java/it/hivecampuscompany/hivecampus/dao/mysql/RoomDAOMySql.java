package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.model.Room;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDAOMySql implements RoomDAO {

    private final Connection connection = ConnectionManager.getInstance().getConnection();
    private static final Logger LOGGER = Logger.getLogger(RoomDAOMySql.class.getName());
    private final Properties properties = LanguageLoader.getLanguageProperties();

    @Override
    public Room retrieveRoomByID(int homeID, int roomID) {
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.RETRIEVE_ROOM_BY_ID)){
            pst.setInt(1, homeID);
            pst.setInt(2, roomID);
            try (ResultSet rs = pst.executeQuery()){
                rs.next();
                return new Room(
                        rs.getInt("idRoom"),
                        rs.getInt("home"),
                        rs.getInt("roomSurface"),
                        rs.getString("roomType"),
                        new boolean[] {rs.getBoolean("privateBath"), rs.getBoolean("balcony"), rs.getBoolean("conditioner"), rs.getBoolean("tv")},
                        rs.getString("roomDescription")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_ROOM_BY_ID"));
            return null;
        }
    }

    @Override
    public List<Room> retrieveRoomsByFilters(int homeID, FiltersBean filtersBean) {
        List<Room> rooms = new ArrayList<>();

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_ROOMS_BY_FILTERS)) {
            cstmt.setInt(1, homeID);
            cstmt.setBoolean(2, filtersBean.getPrivateBathroom());
            cstmt.setBoolean(3, filtersBean.getBalcony());
            cstmt.setBoolean(4, filtersBean.getConditioner());
            cstmt.setBoolean(5, filtersBean.getTvConnection());

            if (cstmt.execute()) {
                try (ResultSet res = cstmt.getResultSet()) {
                    while (res.next()) {
                        int idRoom = res.getInt(1);
                        String type = res.getString(3);
                        int surface = res.getInt(4);
                        boolean[] features = {res.getBoolean(5), res.getBoolean(6), res.getBoolean(7), res.getBoolean(8)};
                        String description = res.getString(9);
                        Room room = new Room(idRoom, homeID, surface, type, features, description);
                        rooms.add(room);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_ROOMS_BY_FILTERS"));
        }
        return rooms;
    }

    @Override
    public Room saveRoom(int homeID, RoomBean roomBean) {
        Room room = null;
        int newRoomId;

        // Call the stored procedure to save the new room
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.SAVE_ROOM)) {
            cstmt.setInt(1, homeID);
            cstmt.setDouble(2, roomBean.getSurface());
            cstmt.setString(3, roomBean.getType());
            cstmt.setBoolean(4, roomBean.getBathroom());
            cstmt.setBoolean(5, roomBean.getBalcony());
            cstmt.setBoolean(6, roomBean.getConditioner());
            cstmt.setBoolean(7, roomBean.getTV());
            cstmt.setString(8, roomBean.getDescription());
            cstmt.registerOutParameter(9, java.sql.Types.INTEGER);
            cstmt.execute();
            newRoomId = cstmt.getInt(9);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_SAVE_ROOM"));
            return null;
        }

        if (newRoomId != -1) {
            boolean[] features = {roomBean.getBathroom(), roomBean.getBalcony(), roomBean.getConditioner(), roomBean.getTV()};
            room = new Room(newRoomId, homeID, roomBean.getSurface(), roomBean.getType(), features, roomBean.getDescription());
        }

        return room;
    }

    @Override
    public void saveRoomImage(String imageName, String imageType, byte[] byteArray, int idRoom, int idHome) {
        // Check if the image already exists
        if (imageRoomAlreadyExists(imageName, idRoom, idHome)) {
            return;
        }

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.SAVE_ROOM_IMAGE)) {
            cstmt.setString(1, imageName);
            cstmt.setString(2, imageType);
            cstmt.setBytes(3, byteArray);
            cstmt.setInt(4, idRoom);
            cstmt.setInt(5, idHome);
            cstmt.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_SAVE_ROOM_IMAGE"));
        }
    }

    private boolean imageRoomAlreadyExists(String imageName, int idRoom, int idHome) {
        boolean exists = false;

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_ROOM_IMAGE)) {
            cstmt.setInt(1, idRoom);
            cstmt.setInt(2, idHome);

            if (cstmt.execute()) {  // execute the stored procedure and check if there is a result set
                try (ResultSet res = cstmt.getResultSet()) {
                    while (res.next()) {
                        if (res.getString(2).equals(imageName) && res.getInt(5) == idRoom && res.getInt(6) == idHome) {
                            exists = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_ROOM_IMAGE"));
        }
        return exists;
    }

    @Override
    public byte[] getRoomImage(Room room) {
        byte[] image = null;

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_ROOM_IMAGE)) {
            cstmt.setInt(1, room.getIdRoom());
            cstmt.setInt(2, room.getIdHome());

            if (cstmt.execute()) {
                try (ResultSet res = cstmt.getResultSet()) {
                    res.first();
                    image = res.getBytes("image");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_ROOM_IMAGE"));
        }
        return image;
    }

    @Override
    public long getRoomsAlreadyPresent(int homeID) {
        int roomCount = 0;

        try (CallableStatement callableStatement = connection.prepareCall(StoredProcedures.GET_ROOMS_ALREADY_PRESENT)) {
            callableStatement.setInt(1, homeID);
            callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);

            callableStatement.execute();
            roomCount = callableStatement.getInt(2);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_GET_ROOMS_ALREADY_PRESENT"));
        }
        return roomCount;
    }
}