package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.model.Home;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeDAOCSV implements HomeDAO {
    private final File fd;

    public HomeDAOCSV() {
        fd = new File("db/csv/home-table.csv");
    }
    @Override
    public Home retrieveHomeByID(int id) {
        try(CSVReader reader = new CSVReader(new FileReader(fd))){
            List<String[]> homeTable = reader.readAll();
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
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
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
                if (Point2D.distance(homeLongitude, homeLatitude, uniCoordinates.getX(), uniCoordinates.getY()) <= distance) {
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
            // Handle exceptions appropriately
            throw new RuntimeException("Error retrieving homes by distance", e);
        }
        return homes;
    }

    @Override
    public Home retrieveHome(int id) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> homeTable = reader.readAll();
            homeTable.removeFirst();
            if (homeTable.stream().anyMatch(homeRecord -> Integer.parseInt(homeRecord[HomeAttributes.INDEX_ID]) == id)) {
                Integer[] features = {
                        Integer.parseInt(homeTable.get(0)[HomeAttributes.INDEX_NROOMS]),
                        Integer.parseInt(homeTable.get(0)[HomeAttributes.INDEX_NBATHROOMS]),
                        Integer.parseInt(homeTable.get(0)[HomeAttributes.INDEX_FLOOR]),
                        Integer.parseInt(homeTable.get(0)[HomeAttributes.INDEX_ELEVATOR])
                };

                return homeTable.stream()
                        .filter(homeRecord -> Integer.parseInt(homeRecord[HomeAttributes.INDEX_ID]) == id)
                        .map(homeRecord -> new Home(
                                Integer.parseInt(homeRecord[HomeAttributes.INDEX_ID]),

                                new Point2D.Double(Double.parseDouble(homeRecord[HomeAttributes.INDEX_LONGITUDE]), Double.parseDouble(homeRecord[HomeAttributes.INDEX_LATITUDE])),
                                homeRecord[HomeAttributes.INDEX_ADDRESS],
                                homeRecord[HomeAttributes.INDEX_TYPE],
                                Integer.parseInt(homeRecord[HomeAttributes.INDEX_SURFACE]),
                                homeRecord[HomeAttributes.INDEX_DESCRIPTION],
                                features
                        ))
                        .findFirst()
                        .orElse(null);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return null; // In caso di fallimento nella ricerca, restituisci null
    }



    private static class HomeAttributes{
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
