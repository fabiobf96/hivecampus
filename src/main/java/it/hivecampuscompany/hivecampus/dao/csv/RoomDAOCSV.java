package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Room;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDAOCSV implements RoomDAO {
    private File fd;
    private File imageFd;
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(RoomDAOCSV.class.getName());

    public RoomDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("ROOM_PATH"));
            imageFd = new File(properties.getProperty("ROOM_IMAGES_PATH"));
        } catch (IOException e) {
            Properties languageProperties = LanguageLoader.getLanguageProperties();
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), e);
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
                        new boolean[]{Integer.parseInt(roomRecord[RoomAttributes.INDEX_BATHROOM]) == 1, Integer.parseInt(roomRecord[RoomAttributes.INDEX_BALCONY]) == 1, Integer.parseInt(roomRecord[RoomAttributes.INDEX_CONDITIONER]) == 1, Integer.parseInt(roomRecord[RoomAttributes.INDEX_TV]) == 1},
                        roomRecord[RoomAttributes.INDEX_DESCRIPTION],
                        getRoomImage(Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]), Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]))
                ))
                .orElse(null);
    }

    /*
    Nello stream, l'espressione filter è una condizione che determina se includere o meno un elemento nello stream risultante.
    Quando viene applicato un filtro, gli elementi dello stream vengono confrontati con la condizione del filtro e solo quelli che soddisfano la condizione vengono inclusi nello stream di uscita.

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
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_READ_PARSE_VALUES"), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Room saveRoom(int homeID, RoomBean roomBean) {

        Room room = null;

        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> roomTable = reader.readAll();
            roomTable.removeFirst();
            int idRoom = roomTable.stream()
                    .filter(roomRecord -> Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]) == homeID)
                    .mapToInt(roomRecord -> Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]))
                    .max()
                    .orElse (0);
            room = new Room(idRoom+1, homeID, roomBean.getSurface(), roomBean.getType(), new boolean[]{roomBean.getBathroom(), roomBean.getBalcony(), roomBean.getConditioner(), roomBean.getTV()}, roomBean.getDescription());

        } catch (IOException | CsvException | RuntimeException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), e);
        }

        // Save the room in the CSV file
        if (room != null) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
                String[] roomRecord = new String[9];
                roomRecord[RoomAttributes.INDEX_ID_ROOM] = String.valueOf(room.getIdRoom());
                roomRecord[RoomAttributes.INDEX_ID_HOME] = String.valueOf(room.getIdHome());
                roomRecord[RoomAttributes.INDEX_TYPE] = room.getTypeRoom();
                roomRecord[RoomAttributes.INDEX_SURFACE] = String.valueOf(room.getSurface());
                roomRecord[RoomAttributes.INDEX_BATHROOM] = room.getBathroom() ? "1" : "0";
                roomRecord[RoomAttributes.INDEX_BALCONY] = room.getBalcony() ? "1" : "0";
                roomRecord[RoomAttributes.INDEX_CONDITIONER] = room.getConditioner() ? "1" : "0";
                roomRecord[RoomAttributes.INDEX_TV] = room.getTV() ? "1" : "0";
                roomRecord[RoomAttributes.INDEX_DESCRIPTION] = room.getDescription();
                writer.writeNext(roomRecord);
                return room;
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_SAVE_ROOM"), e);
            }
        }
        return null;
    }

    @Override
    public long getRoomsAlreadyPresent(int homeID) {
        long roomCount = 0;
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> roomTable = reader.readAll();
            if (roomTable.isEmpty()) {
                return roomCount; // Se la lista di camere è vuota, il conteggio è già zero
            }
            roomTable.remove(0); // Rimuovi l'intestazione
            for (String[] roomRecord : roomTable) {
                if (Integer.parseInt(roomRecord[RoomDAOCSV.RoomAttributes.INDEX_ID_HOME]) == homeID) {
                    roomCount++;
                }
            }
        } catch (CsvException | IOException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_LOADING_ROOM_DATA"), e);
        }
        return roomCount;
    }



    @Override
    public byte[] getRoomImage(int idRoom, int idHome) {
        try (CSVReader reader = new CSVReader(new FileReader(imageFd))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.removeFirst();
            String[] imageRecord = imageTable.stream()
                    .filter(image -> Integer.parseInt(image[ImageAttributes.INDEX_ID_HOME]) == idHome && Integer.parseInt(image[ImageAttributes.INDEX_ID_ROOM]) == idRoom)
                    .findFirst()
                    .orElse(null);
            if (imageRecord != null) {
                return CSVUtility.decodeBase64ToBytes(imageRecord[ImageAttributes.INDEX_IMAGE]);
            }
        }
        catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_LOADING_CSV_IMAGE"), e);
        }
        return new byte[0];
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

    private static class ImageAttributes {
        private static final int INDEX_ID_ROOM = 1;
        private static final int INDEX_ID_HOME = 2;
        private static final int INDEX_IMAGE = 5;
    }
}