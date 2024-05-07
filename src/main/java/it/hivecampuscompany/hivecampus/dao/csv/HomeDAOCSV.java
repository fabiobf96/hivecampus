package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.view.utility.Utility;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeDAOCSV implements HomeDAO {
    private File fd;
    private File imageFd;
    private static final Logger LOGGER = Logger.getLogger(HomeDAOCSV.class.getName());

    public HomeDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("HOME_PATH"));
            imageFd = new File(properties.getProperty("HOME_IMAGES_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
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
                Integer[] features = {
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_NROOMS]),
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_NBATHROOMS]),
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_FLOOR]),
                        Integer.parseInt(homeRecord[HomeAttributes.INDEX_ELEVATOR])
                };
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

    @Override
    public Home saveHome(HomeBean homeBean, String ownerEmail) {
        // Check if the home already exists
        int existingHomeId = isHomeAlreadyExists(homeBean);
        if (existingHomeId != -1) {
            return retrieveHomeByID(existingHomeId);
        }

        int lastId = CSVUtility.findLastRowIndex(fd);

        Point2D coordinates = Utility.getCoordinates(homeBean.getAddress());

        if (coordinates == null) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve coordinates");
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
            LOGGER.log(Level.SEVERE, "Failed to write to file", e);
            return null;
        }
    }

    @Override
    public byte[] getHomeImage(int idHome) {
        try (CSVReader reader = new CSVReader(new FileReader(imageFd))) {
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
            LOGGER.log(Level.SEVERE, "Failed to load image from CSV", e);
        }
        return new byte[0];
    }

    private int isHomeAlreadyExists(HomeBean homeBean) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextLine;
            // Itera attraverso ogni riga del file CSV
            while ((nextLine = reader.readNext()) != null) {
                // Confronta i campi della riga corrente con quelli della casa da controllare
                if (nextLine[HomeAttributes.INDEX_ADDRESS].equals(homeBean.getAddress()) &&
                        nextLine[HomeAttributes.INDEX_TYPE].equals(homeBean.getType()) &&
                        Double.parseDouble(nextLine[HomeAttributes.INDEX_SURFACE]) == homeBean.getSurface() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_NROOMS]) == homeBean.getNRooms() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_NBATHROOMS]) == homeBean.getNBathrooms() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_FLOOR]) == homeBean.getFloor() &&
                        Integer.parseInt(nextLine[HomeAttributes.INDEX_ELEVATOR]) == homeBean.getElevator()) {
                    // Se la casa esiste, restituisci il suo ID
                    return Integer.parseInt(nextLine[HomeAttributes.INDEX_ID]);
                }
            }
        } catch (IOException | NumberFormatException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to read file or parse values", e);
        }
        return -1; // La casa non esiste
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

    private static class ImageAttributes {
        private static final int INDEX_ID_HOME = 1;
        private static final int INDEX_IMAGE = 4;
    }
}
