package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Room;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class RoomDAOCSV implements RoomDAO {
    private final File fd;
    public RoomDAOCSV(){
        fd = new File("db/csv/room-table.csv");
    }
    @Override
    public Room retrieveRoomByID(int homeID, int roomID) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> roomTable = reader.readAll();
            roomTable.removeFirst();
            return roomTable.stream()
                    .filter(roomRecord -> Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]) == homeID && Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]) == roomID)
                    .findFirst()
                    .map(roomRecord -> new Room(
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]),
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]),
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_SURFACE]),
                            roomRecord[RoomAttributes.INDEX_TYPE],
                            new boolean[]{Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_BATHROOM]), Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_BALCONY]), Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_CONDITIONER]), Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_TV])},
                            roomRecord[RoomAttributes.INDEX_DESCRIPTION]
                    ))
                    .orElse(null);
        }catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> retrieveRoomsByFilters(int homeID, FiltersBean filtersBean) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> roomTable = reader.readAll();
            roomTable.removeFirst();
            return roomTable.stream()
                    .filter(roomRecord -> Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]) == homeID)
                    .filter(roomRecord -> filtersBean.getPrivateBathroom() == (Integer.parseInt(roomRecord[RoomAttributes.INDEX_BATHROOM]) == 1))
                    .filter(roomRecord -> filtersBean.getBalcony() == (Integer.parseInt(roomRecord[RoomAttributes.INDEX_BALCONY]) == 1))
                    .filter(roomRecord -> filtersBean.getConditioner() == (Integer.parseInt(roomRecord[RoomAttributes.INDEX_CONDITIONER]) == 1))
                    .filter(roomRecord -> filtersBean.getTvConnection() == (Integer.parseInt(roomRecord[RoomAttributes.INDEX_TV]) == 1))
                    .map(roomRecord -> new Room(
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]),
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]),
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_SURFACE]),
                            roomRecord[RoomAttributes.INDEX_TYPE],
                            new boolean[]{Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_BATHROOM]), Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_BALCONY]), Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_CONDITIONER]), Boolean.parseBoolean(roomRecord[RoomAttributes.INDEX_TV])},
                            roomRecord[RoomAttributes.INDEX_DESCRIPTION]
                    ))
                    .toList();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private static class RoomAttributes{
        private static final int INDEX_ID_ROOM = 0;
        private static final int INDEX_ID_HOME = 1;
        private static final int INDEX_TYPE = 2;
        private static final int INDEX_SURFACE = 3;
        private static final int INDEX_BATHROOM = 4;
        private static final int INDEX_BALCONY = 5;
        private static final int INDEX_CONDITIONER = 6;
        private static final int INDEX_TV = 7;
        private static final int INDEX_DESCRIPTION = 8;
    }
}
