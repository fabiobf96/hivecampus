package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.boundary.OpenStreetMapApiBoundary;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.view.utility.Utility;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The HomeDAOCSV class provides methods for managing home data stored in a CSV file.
 * It allows the application to retrieve homes by their ID, by distance from a university, by owner, and to save a new home.
 */

public class HomeDAOCSV implements HomeDAO {
    private File fd;
    private File homeFile;
    private final Properties languageProperties = LanguageLoader.getLanguageProperties();
    private static final Logger LOGGER = Logger.getLogger(HomeDAOCSV.class.getName());

    /**
     * Constructor for the HomeDAOCSV class.
     * It initializes the file paths of the home data CSV file and the home images CSV file.
     */

    public HomeDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("HOME_PATH"));
            homeFile = new File(properties.getProperty("HOME_IMAGES_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), e);
            System.exit(1);
        }
    }

    @Override
    public Home retrieveHomeByID(int id) {
        List<String[]> homeTable = CSVUtility.readAll(fd);
        homeTable.removeFirst();
        return homeTable.stream()
                .filter(homeRecord -> Integer.parseInt(homeRecord[HomeAttributes.INDEX_ID]) == id)
                .findFirst()
                .map(homeRecord -> new Home(
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_ID]),
                        new Point2D.Double(Double.parseDouble(homeRecord[HomeAttributes.INDEX_LONGITUDE]), Double.parseDouble(homeRecord[HomeAttributes.INDEX_LATITUDE])),
                        homeRecord[HomeAttributes.INDEX_ADDRESS],
                        homeRecord[HomeAttributes.INDEX_TYPE],
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_SURFACE]),
                        homeRecord[HomeAttributes.INDEX_DESCRIPTION]))
                .orElse(null);

    }

    /**
     * Method to retrieve all homes within a specified distance from a university.
     * The method reads the homes from the CSV file and calculates the distance between
     * each home and the university using the Harvesine formula.
     *
     * @param uniCoordinates The coordinates of the university.
     * @param distance       The maximum distance from the university.
     * @return A list of homes within the specified distance from the university.
     */

    @Override
    public List<Home> retrieveHomesByDistance(Point2D uniCoordinates, double distance) {
        List<Home> homes = new ArrayList<>();
        List<String[]> homeTable = CSVUtility.readAll(fd);
        homeTable.removeFirst(); // Remove header
        for (String[] homeRecord : homeTable) {
            double homeLongitude = Double.parseDouble(homeRecord[HomeAttributes.INDEX_LONGITUDE]);
            double homeLatitude = Double.parseDouble(homeRecord[HomeAttributes.INDEX_LATITUDE]);
            // Calculate the distance between the university and the home by Harvesine formula
            if (Utility.calculateDistance(homeLongitude, homeLatitude, uniCoordinates.getX(), uniCoordinates.getY()) <= distance) {
                Integer[] features = getFeatures(homeRecord);
                Home home = new Home(
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_ID]),
                        new Point2D.Double(homeLongitude, homeLatitude),
                        homeRecord[HomeAttributes.INDEX_ADDRESS],
                        homeRecord[HomeAttributes.INDEX_TYPE],
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_SURFACE]),
                        homeRecord[HomeAttributes.INDEX_DESCRIPTION],
                        features
                );
                homes.add(home);
            }
        }
        return homes;
    }

    /**
     * Method to retrieve a home feature from a home record.
     * @param homeRecord The record of the home.
     * @return An array of integers containing the home features.
     */

    private static Integer[] getFeatures(String[] homeRecord) {
        return new Integer[] {
                Integer.parseInt(homeRecord[HomeAttributes.INDEX_NROOMS]),
                Integer.parseInt(homeRecord[HomeAttributes.INDEX_NBATHROOMS]),
                Integer.parseInt(homeRecord[HomeAttributes.INDEX_FLOOR]),
                Integer.parseInt(homeRecord[HomeAttributes.INDEX_ELEVATOR])
        };
    }

    /**
     * Method to retrieve all homes owned by a specific user.
     * The method reads the homes from the CSV file and filters the homes by the owner's email.
     *
     * @param ownerEmail The email of the user who owns the homes.
     * @return A list of homes owned by the specified user.
     */

    @Override
    public List<Home> retrieveHomesByOwner(String ownerEmail) {
        List<Home> homes = new ArrayList<>();
        List<String[]> homeTable = CSVUtility.readAll(fd);
        homeTable.removeFirst(); // Remove header
        for (String[] homeRecord : homeTable) {
            if (homeRecord[HomeAttributes.INDEX_OWNER].equals(ownerEmail)) {
                Integer[] features = {
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_NROOMS]),
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_NBATHROOMS]),
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_FLOOR]),
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_ELEVATOR])
                };
                Home home = new Home(
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_ID]),
                        new Point2D.Double(Double.parseDouble(homeRecord[HomeAttributes.INDEX_LONGITUDE]), Double.parseDouble(homeRecord[HomeAttributes.INDEX_LATITUDE])),
                        homeRecord[HomeAttributes.INDEX_ADDRESS],
                        homeRecord[HomeAttributes.INDEX_TYPE],
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_SURFACE]),
                        homeRecord[HomeAttributes.INDEX_DESCRIPTION],
                        features);
                homes.add(home);
            }
        }
        return homes;
    }

    /**
     * Method to save a home to the CSV file.
     * The method checks if the home already exists in the file calling the isHomeAlreadyExists method.
     * If the home does not exist, the method retrieves the coordinates of the address using the OpenStreetMap API.
     * The method then writes the home to the CSV file and returns the home object.
     *
     * @param homeBean The bean containing the home data.
     * @param ownerEmail The email of the user who owns the home.
     * @return The home object saved to the CSV file.
     */

    @Override
    public Home saveHome(HomeBean homeBean, String ownerEmail) {
        // Check if the home already exists
        int existingHomeId = isHomeAlreadyExists(homeBean);
        if (existingHomeId != -1) {
            return retrieveHomeByID(existingHomeId);
        }

        int lastId = CSVUtility.findLastRowIndex(fd);

        Point2D coordinates;
        try {
            coordinates = OpenStreetMapApiBoundary.getCoordinates(homeBean.getAddress());
        } catch (IOException e) {
            LOGGER.severe(languageProperties.getProperty("FAILED_RETRIEVE_COORDINATES"));
            return null;
        }

        Integer[] features = {
                homeBean.getNRooms(),
                homeBean.getNBathrooms(),
                homeBean.getFloor(),
                homeBean.getElevator()
        };

        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] homeRecord = new String[12];
            homeRecord[HomeAttributes.INDEX_ID] = String.valueOf(lastId + 1);
            homeRecord[HomeAttributes.INDEX_OWNER] = ownerEmail;
            homeRecord[HomeAttributes.INDEX_LATITUDE] = String.valueOf(coordinates.getX());
            homeRecord[HomeAttributes.INDEX_LONGITUDE] = String.valueOf(coordinates.getY());
            homeRecord[HomeAttributes.INDEX_ADDRESS] = homeBean.getAddress();
            homeRecord[HomeAttributes.INDEX_TYPE] = homeBean.getType();
            homeRecord[HomeAttributes.INDEX_SURFACE] = String.valueOf(homeBean.getSurface());
            homeRecord[HomeAttributes.INDEX_NROOMS] = String.valueOf(homeBean.getNRooms());
            homeRecord[HomeAttributes.INDEX_NBATHROOMS] = String.valueOf(homeBean.getNBathrooms());
            homeRecord[HomeAttributes.INDEX_FLOOR] = String.valueOf(homeBean.getFloor());
            homeRecord[HomeAttributes.INDEX_ELEVATOR] = String.valueOf(homeBean.getElevator());
            homeRecord[HomeAttributes.INDEX_DESCRIPTION] = homeBean.getDescription();
            writer.writeNext(homeRecord);
            return new Home(lastId + 1, coordinates, homeBean.getAddress(), homeBean.getType(), homeBean.getSurface(), homeBean.getDescription(), features);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_WRITE_FILE"), e);
            return null;
        }
    }

    /**
     * Method to check if an image of a home already exists in the CSV file.
     * The method reads the images from the CSV file and compares the fields of each image with the fields of the image to check.
     * If the image already exists, the method returns true, else it returns false.
     *
     * @param imageName The name of the image to check.
     * @param idHome The ID of the home to which the image belongs.
     * @return Boolean True if the image already exists, false if it does not exist.
     */

    private boolean imageHomeAlreadyExists(String imageName, int idHome) {
        try (CSVReader reader = new CSVReader(new FileReader(homeFile))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.removeFirst();
            for (String[] imageRecord : imageTable) {
                if (Integer.parseInt(imageRecord[1]) == idHome && imageRecord[2].equals(imageName)){
                    return true;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(languageProperties.getProperty("ERROR_ACCESS"), homeFile), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(languageProperties.getProperty("ERROR_PARSER"), homeFile), e);
            System.exit(3);
        }
        return false;
    }

    /**
     * Method to save an image of a home to the CSV file.
     * The method checks if the image already exists in the file calling the imageHomeAlreadyExists method.
     * If the image does not exist, the method writes the image to the CSV file.
     *
     * @param imageName The name of the image.
     * @param imageType The type of the image.
     * @param byteArray The image as a byte array.
     * @param idHome The ID of the home to which the image belongs.
     */

    public void saveHomeImage (String imageName, String imageType, byte[] byteArray, int idHome) {
        // Check if the image already exists
        if (imageHomeAlreadyExists(imageName, idHome)) {
            return;
        }

        int idImage = CSVUtility.findLastRowIndex(homeFile) + 1;
        String[] home = new String[6];
        home[0] = String.valueOf(idImage);
        home[1] = String.valueOf(idHome);
        home[2] = imageName;
        home[3] = imageType;
        home[4] = CSVUtility.encodeBytesToBase64(byteArray);

        try (CSVWriter writer = new CSVWriter(new FileWriter(homeFile, true))) {
            writer.writeNext(home);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(languageProperties.getProperty("ERROR_ACCESS"), homeFile), e);
            System.exit(2);
        }
    }

    /**
     * Method to retrieve an image of a home from the CSV file.
     * The method reads the image from the CSV file and returns it as a byte array.
     *
     * @param idHome The ID of the home to which the image belongs.
     * @return The image as a byte array.
     */

    @Override
    public byte[] getHomeImage(int idHome) {
        try (CSVReader reader = new CSVReader(new FileReader(homeFile))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.removeFirst();
            String[] imageRecord = imageTable.stream()
                    .filter(image -> Integer.parseInt(image[ImageAttributes.INDEX_ID_HOME]) == idHome)
                    .findFirst()
                    .orElse(null);
            if (imageRecord != null) {
                return CSVUtility.decodeBase64ToBytes(imageRecord[ImageAttributes.INDEX_IMAGE]);
            }
        } catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_LOADING_CSV_IMAGE"), e);
        }
        return new byte[0];
    }

    /**
     * Method to check if a home already exists in the CSV file.
     * It reads the homes from the CSV file and compares the fields of each home with the fields of the home to check.
     * If the home already exists, the method returns the ID of the existing home, else it returns -1.
     *
     * @param homeBean The bean containing the home data to check.
     * @return Integer The ID of the existing home, or -1 if the home does not exist.
     */

    private int isHomeAlreadyExists(HomeBean homeBean) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextLine;
            // It reads the file line by line
            while ((nextLine = reader.readNext()) != null) {
                // It checks if the home already exists by comparing the fields
                if (nextLine[HomeAttributes.INDEX_ADDRESS].equals(homeBean.getAddress()) &&
                        nextLine[HomeAttributes.INDEX_TYPE].equals(homeBean.getType()) &&
                        Double.parseDouble(nextLine[HomeAttributes.INDEX_SURFACE]) == homeBean.getSurface() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_NROOMS]) == homeBean.getNRooms() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_NBATHROOMS]) == homeBean.getNBathrooms() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_FLOOR]) == homeBean.getFloor() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_ELEVATOR]) == homeBean.getElevator()) {
                    // If the home already exists, it returns the ID of the existing home
                    return Integer.parseInt(nextLine[HomeAttributes.INDEX_ID]);
                }
            }
        } catch (IOException | NumberFormatException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_READ_PARSE_VALUES"), e);
        }
        return -1; // If the home does not exist, it returns -1
    }

    private static class HomeAttributes {
        private static final int INDEX_ID = 0;
        private static final int INDEX_OWNER = 1;
        private static final int INDEX_LATITUDE = 2;
        private static final int INDEX_LONGITUDE = 3;
        private static final int INDEX_ADDRESS = 4;
        private static final int INDEX_TYPE = 5;
        private static final int INDEX_SURFACE = 6;
        private static final int INDEX_NROOMS = 7;
        private static final int INDEX_NBATHROOMS = 8;
        private static final int INDEX_FLOOR = 9;
        private static final int INDEX_ELEVATOR = 10;
        private static final int INDEX_DESCRIPTION = 11;
    }

    /**
     * Static inner class containing some indexes of the fields in the CSV image file.
     */

    private static class ImageAttributes {
        private static final int INDEX_ID_HOME = 1;
        private static final int INDEX_IMAGE = 4;
    }
}
