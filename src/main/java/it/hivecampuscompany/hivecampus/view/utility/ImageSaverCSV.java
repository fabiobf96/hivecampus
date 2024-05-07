package it.hivecampuscompany.hivecampus.view.utility;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.csv.CSVUtility;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageSaverCSV {
    private File roomFile;
    private File homeFile;
    private static final Logger LOGGER = Logger.getLogger(ImageSaverCSV.class.getName());
    private Properties properties;
    static final String ERROR_ACCESS = "ERR_ACCESS";
    static final String ERROR_PARSER = "ERR_PARSER";

    public ImageSaverCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")){
            properties = new Properties();
            properties.load(input);
            roomFile = new File(properties.getProperty("ROOM_IMAGES_PATH"));
            homeFile = new File(properties.getProperty("HOME_IMAGES_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    public void saveRoomImage (String imageName, String imageType, byte[] byteArray, int idRoom, int idHome) {
        // Check if the image already exists
        if (imageRoomAlreadyExists(imageName, idRoom, idHome)) {
            return;
        }

        int idImage = getNextIdFromFile(roomFile);

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
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), roomFile), e);
            System.exit(2);
        }
    }

    public void saveHomeImage (String imageName, String imageType, byte[] byteArray, int idHome) {
        // Check if the image already exists
        if (imageHomeAlreadyExists(imageName, idHome)) {
            return;
        }

        int idImage = getNextIdFromFile(homeFile);

        String[] home = new String[6];
        home[0] = String.valueOf(idImage);
        home[1] = String.valueOf(idHome);
        home[2] = imageName;
        home[3] = imageType;
        home[4] = CSVUtility.encodeBytesToBase64(byteArray);

        try (CSVWriter writer = new CSVWriter(new FileWriter(homeFile, true))) {
            writer.writeNext(home);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), homeFile), e);
            System.exit(2);
        }
    }

    public int getNextIdFromFile(File fd) {
        int maxId = 0;

        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.remove(0);
            for (String[] imageRecord : imageTable) {
                int id = Integer.parseInt(imageRecord[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), fd), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_PARSER), fd), e);
            System.exit(3);
        }

        return (maxId == 0) ? 1 : maxId + 1;
    }

    private boolean imageHomeAlreadyExists(String imageName, int idHome) {
        try (CSVReader reader = new CSVReader(new FileReader(homeFile))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.remove(0);
            for (String[] imageRecord : imageTable) {
                if (Integer.parseInt(imageRecord[1]) == idHome && imageRecord[2].equals(imageName)){
                    return true;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), homeFile), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_PARSER), homeFile), e);
            System.exit(3);
        }
        return false;
    }

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
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), roomFile), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_PARSER), roomFile), e);
            System.exit(3);
        }
        return false;
    }
}
