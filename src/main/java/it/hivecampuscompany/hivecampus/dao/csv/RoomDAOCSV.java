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

/**
 * RoomDAOCSV class for managing room data in a CSV file.
 * Implements the RoomDAO interface and provides operations to retrieve, save, and filter rooms.
 */

public class RoomDAOCSV implements RoomDAO {
    private File fd;
    private File roomFile;
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(RoomDAOCSV.class.getName());

    /**
     * Constructor for the RoomDAOCSV class.
     * It initializes the file paths of the room data CSV file and the room images CSV file.
     */

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
                        roomRecord[RoomAttributes.INDEX_DESCRIPTION],
                        getRoomImage(Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_ROOM]), Integer.parseInt(roomRecord[RoomAttributes.INDEX_ID_HOME]))
                ))
                .orElse(null);
    }

    /**
     * Method to retrieve a list of rooms that match the filters.
     * It reads the CSV file and filters the rooms based on the filters.
     * If a filter is not specified, it is not applied, otherwise it is applied.
     * Then it maps the filtered records to Room objects and returns them as a list.
     *
     * @param homeID The ID of the home to search for rooms.
     * @param filtersBean The filters to apply to the search.
     * @return List of rooms that match the filters.
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

    /**
     * Method to save a room in the CSV file.
     * It reads the CSV file to get the last ID of the rooms for the home.
     * Then it creates a new Room object with the new ID and the data from the RoomBean.
     * Finally, it writes the new room to the CSV file and returns it.
     *
     * @param homeID The ID of the home to which the room belongs.
     * @param roomBean The RoomBean object containing the data of the room to save.
     * @return The Room object saved in the CSV file.
     */

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

    /**
     * Method to retrieve the number of rooms already present in the CSV file for a home.
     * It reads the CSV file and counts the number of rooms that have the same homeID.
     * If the list of rooms is empty, it returns 0. Otherwise, it returns the count.
     *
     * @param homeID The ID of the home for which to count the rooms.
     * @return long value representing the number of rooms already present.
     */

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

    /**
     * Method to check if an image of a room already exists in the CSV file.
     * It reads the CSV file and filters the image records based on the roomID and homeID.
     * If an image record is found, it returns true, otherwise it returns false.
     *
     * @param imageName The name of the image file.
     * @param idRoom The ID of the room to which the image belongs.
     * @param idHome The ID of the home to which the room belongs.
     * @return boolean value representing if the image already exists.
     */

    private boolean imageRoomAlreadyExists(String imageName, int idRoom, int idHome) {
        try (CSVReader reader = new CSVReader(new FileReader(roomFile))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.remove(0);
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

    /**
     * Method to save an image of a room in the CSV file.
     * It checks if the image already exists in the CSV file.
     * If the image does not exist, it creates a new image record with the image data and writes it to the CSV file.
     *
     * @param imageName The name of the image file.
     * @param imageType The type of the image file.
     * @param byteArray Byte array containing the image data.
     * @param idRoom The ID of the room to which the image belongs.
     * @param idHome The ID of the home to which the room belongs.
     */

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

    /**
     * Method to retrieve the image of a room from the CSV file.
     * It reads the CSV file and filters the image records based on the roomID and homeID.
     * If an image record is found, it decodes the base64 image and returns it as a byte array.
     * Otherwise, it returns an empty byte array.
     *
     * @param idRoom The ID of the room for which to retrieve the image.
     * @param idHome The ID of the home to which the room belongs.
     * @return Byte array containing the image of the room.
     */


    @Override
    public byte[] getRoomImage(int idRoom, int idHome) {
        try (CSVReader reader = new CSVReader(new FileReader(roomFile))) {
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

    /**
     * Static inner class containing some indexes of the fields in the CSV image file.
     */

    private static class ImageAttributes {
        private static final int INDEX_ID_ROOM = 1;
        private static final int INDEX_ID_HOME = 2;
        private static final int INDEX_IMAGE = 5;
    }
}