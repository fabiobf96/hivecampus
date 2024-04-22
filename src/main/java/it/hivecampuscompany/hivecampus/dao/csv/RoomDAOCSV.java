package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Room;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDAOCSV implements RoomDAO {
    private File fd;
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(RoomDAOCSV.class.getName());

    public RoomDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("ROOM_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    @Override
    public Room retrieveRoomByID(int homeID, int roomID) {
        List<String[]> roomTable = CSVUtility.readAll(fd);
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
    }

    /*
    Nello stream, l'espressione filter è una condizione che determina se includere o meno un elemento nello stream risultante.
    Quando viene applicato un filtro, gli elementi dello stream vengono confrontati con la condizione del filtro e solo quelli che soddisfano la condizione vengono inclusi nello stream di output.

    Nel nostro caso, filter è una variabile booleana che rappresenta se vogliamo applicare un filtro o meno.
    Se filter è true, il filtro viene applicato, altrimenti viene ignorato.
     */

    @Override
    public List<Room> retrieveRoomsByFilters(int homeID, FiltersBean filtersBean) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> roomTable = reader.readAll();
            roomTable.removeFirst();
            return roomTable.stream()
                    .filter(roomRecord -> Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]) == homeID)
                    .filter(roomRecord -> !filtersBean.getPrivateBathroom() || Integer.parseInt(roomRecord[RoomAttributes.INDEX_BATHROOM]) == 1) // ritorna true se non è stato specificato il filtro o se il valore del campo è 1
                    .filter(roomRecord -> !filtersBean.getBalcony() || Integer.parseInt(roomRecord[RoomAttributes.INDEX_BALCONY]) == 1)
                    .filter(roomRecord -> !filtersBean.getConditioner() || Integer.parseInt(roomRecord[RoomAttributes.INDEX_CONDITIONER]) == 1)
                    .filter(roomRecord -> !filtersBean.getTvConnection() || Integer.parseInt(roomRecord[RoomAttributes.INDEX_TV]) == 1)
                    .map(roomRecord -> new Room(
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]),
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]),
                            Integer.parseInt(roomRecord[RoomAttributes.INDEX_SURFACE]),
                            roomRecord[RoomAttributes.INDEX_TYPE],
                            new boolean[]{Integer.parseInt(roomRecord[RoomAttributes.INDEX_BATHROOM]) == 1, Integer.parseInt(roomRecord[RoomAttributes.INDEX_BALCONY]) == 1, Integer.parseInt(roomRecord[RoomAttributes.INDEX_CONDITIONER]) == 1, Integer.parseInt(roomRecord[RoomAttributes.INDEX_TV]) == 1},
                            roomRecord[RoomAttributes.INDEX_DESCRIPTION]
                    ))
                    .toList();
        } catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), fd), e);
        }
        return Collections.emptyList();
    }

    private static class RoomAttributes {
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
