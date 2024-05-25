package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.model.Room;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDAOMySql implements RoomDAO {

    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(RoomDAOMySql.class.getName());

    @Override
    public Room retrieveRoomByID(int homeID, int roomID) {
        return null;
    }

    @Override
    public List<Room> retrieveRoomsByFilters(int homeID, FiltersBean filtersBean) {
        return List.of();
    }

    @Override
    public Room saveRoom(int homeID, RoomBean roomBean) {
        Room room = null;
        int newRoomId = -1;

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
            LOGGER.log(Level.SEVERE, "FAILED_SAVE_ROOM");
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

    }

    @Override
    public byte[] getRoomImage(int idRoom, int idHome) {
        return new byte[0];
    }

    @Override
    public long getRoomsAlreadyPresent(int homeID) {
        return 0;
    }
}
