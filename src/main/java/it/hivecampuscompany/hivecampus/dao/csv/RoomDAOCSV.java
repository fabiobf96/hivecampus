package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Room;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RoomDAOCSV implements RoomDAO {
    private File fd;
    private File roomFile;
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(RoomDAOCSV.class.getName());

    public RoomDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("ROOM_PATH"));
            roomFile = new File(properties.getProperty("ROOM_IMAGES_PATH"));
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
                        roomRecord[RoomAttributes.INDEX_DESCRIPTION])
                        //getRoomImage(Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]), Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]))
                )
                .orElse(null);
    }

    @Override
    public List<Room> retrieveRoomsByFilters(int homeID, FiltersBean filtersBean) {
        // Read the CSV file and filter the rooms based on the filters
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
        // Read the CSV file to get the last ID of the rooms for the home
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

        if (room != null) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
                String[] roomRecord = new String[9];
                roomRecord[RoomAttributes.INDEX_ID_ROOM] = String.valueOf(room.getIdRoom());
                roomRecord[RoomAttributes.INDEX_ID_HOME] = String.valueOf(room.getIdHome());
                roomRecord[RoomAttributes.INDEX_TYPE] = room.getTypeRoom();
                roomRecord[RoomAttributes.INDEX_SURFACE] = String.valueOf(room.getSurface());
                roomRecord[RoomAttributes.INDEX_BATHROOM] = room.getServices()[0] ? "1" : "0";
                roomRecord[RoomAttributes.INDEX_BALCONY] = room.getServices()[1] ? "1" : "0";
                roomRecord[RoomAttributes.INDEX_CONDITIONER] = room.getServices()[2] ? "1" : "0";
                roomRecord[RoomAttributes.INDEX_TV] = room.getServices()[3] ? "1" : "0";
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
        // Read the CSV file and count the number of rooms that have the same homeID
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> roomTable = reader.readAll();
            if (roomTable.isEmpty()) {
                return roomCount; // Se la lista di camere è vuota, il conteggio è già zero
            }
            roomTable.removeFirst(); // Rimuovi l'intestazione
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

     private boolean imageRoomAlreadyExists(String imageName, int idRoom, int idHome) {
        // Read the CSV file and filter the image records based on the roomID and homeID
        try (CSVReader reader = new CSVReader(new FileReader(roomFile))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.removeFirst();
            for (String[] imageRecord : imageTable) {
                if (Integer.parseInt(imageRecord[1]) == idRoom && Integer.parseInt(imageRecord[2]) == idHome && imageRecord[3].equals(imageName)){
                    return true;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERROR_ACCESS"), roomFile), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERROR_PARSER"), roomFile), e);
            System.exit(3);
        }
        return false;
    }

    public void saveRoomImage (String imageName, String imageType, byte[] byteArray, int idRoom, int idHome) {
        // Check if the image already exists
        if (imageRoomAlreadyExists(imageName, idRoom, idHome)) {
            return;
        }

        int idImage = CSVUtility.findLastRowIndex(roomFile) + 1;

        String[] room = new String[6];
        room[0] = String.valueOf(idImage);
        room[1] = String.valueOf(idRoom);
        room[2] = String.valueOf(idHome);
        room[3] = imageName;
        room[4] = imageType;
        room[5] = CSVUtility.encodeBytesToBase64(byteArray);

        try (CSVWriter writer = new CSVWriter(new FileWriter(roomFile, true))) {
            writer.writeNext(room);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERROR_ACCESS"), roomFile), e);
            System.exit(2);
        }
    }

    @Override
    public byte[] getRoomImage(Room room) {
        // Read the CSV file and filter the image records based on the roomID and homeID
        try (CSVReader reader = new CSVReader(new FileReader(roomFile))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.removeFirst();
            String[] imageRecord = imageTable.stream()
                    .filter(image -> Integer.parseInt(image[ImageAttributes.INDEX_ID_HOME]) == room.getIdHome() && Integer.parseInt(image[ImageAttributes.INDEX_ID_ROOM]) == room.getIdRoom())
                    .findFirst()
                    .orElse(null);
            // If an image record is found, decode the base64 image and return it as a byte array
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