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
    private final File uni;
    public HomeDAOCSV() {
        fd = new File("db/csv/home-table.csv");
        uni = new File("db/csv/university-table.csv");
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
    public List<Home> retrieveHomesByDistance(String university, double distance) {

        Point2D uniCoordinates = getUniversityCoordinates(university);

        if (uniCoordinates == null) {
            System.out.println("University not found");
            System.exit(1);
        }

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

    public Point2D getUniversityCoordinates(String universityName){
        try (CSVReader reader = new CSVReader(new FileReader(uni))) {
            List<String[]> universityTable = reader.readAll();
            universityTable.removeFirst();
            return universityTable.stream()
                    .filter(universityRecord -> universityRecord[UniversityAttributes.INDEX_NAME].equals(universityName))
                    .findFirst()
                    .map(universityRecord -> new Point2D.Double(Double.parseDouble(universityRecord[UniversityAttributes.INDEX_LONGITUDE]), Double.parseDouble(universityRecord[UniversityAttributes.INDEX_LATITUDE])))
                    .orElse(null);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private static class UniversityAttributes{
        private static final int INDEX_NAME = 0;
        private static final int INDEX_LATITUDE = 2;
        private static final int INDEX_LONGITUDE = 3;
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
