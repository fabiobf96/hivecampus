package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.view.utility.CalculateDistance;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeDAOCSV implements HomeDAO {
    private File fd;
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(HomeDAOCSV.class.getName());

    public HomeDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("HOME_PATH"));
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
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> homeTable = reader.readAll();
            homeTable.removeFirst(); // Remove header
            for (String[] homeRecord : homeTable) {
                double homeLongitude = Double.parseDouble(homeRecord[HomeAttributes.INDEX_LONGITUDE]);
                double homeLatitude = Double.parseDouble(homeRecord[HomeAttributes.INDEX_LATITUDE]);
                // Calculate the distance between the university and the home by Harvesine formula
                if (CalculateDistance.haversineFormula(homeLongitude, homeLatitude, uniCoordinates.getX(), uniCoordinates.getY()) <= distance) {
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
        } catch (IOException | CsvException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), fd), e);
        }
        return homes;
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
}
