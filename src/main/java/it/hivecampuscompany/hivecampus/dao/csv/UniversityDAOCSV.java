package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.UniversityDAO;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class UniversityDAOCSV implements UniversityDAO {

    private final File uni;
    private static final Logger LOGGER = Logger.getLogger(UniversityDAOCSV.class.getName());

    public UniversityDAOCSV() {
        uni = new File("db/csv/university-table.csv");
    }

    @Override
    public Point2D getUniversityCoordinates(String universityName) {
        try (CSVReader reader = new CSVReader(new FileReader(uni))) {
            List<String[]> universityTable = reader.readAll();
            universityTable.removeFirst();
            return universityTable.stream()
                    .filter(universityRecord -> universityRecord[UniversityDAOCSV.UniversityAttributes.INDEX_NAME].trim().equalsIgnoreCase(universityName.trim()))
                    .findFirst()
                    .map(universityRecord -> new Point2D.Double(Double.parseDouble(universityRecord[UniversityDAOCSV.UniversityAttributes.INDEX_LONGITUDE]), Double.parseDouble(universityRecord[UniversityDAOCSV.UniversityAttributes.INDEX_LATITUDE])))
                    .orElse(null);
        } catch (IOException | CsvException e) {
            LOGGER.severe("Failed to load university table");
        }
        return null;
    }


    private static class UniversityAttributes{
        private static final int INDEX_NAME = 0;
        private static final int INDEX_LATITUDE = 2;
        private static final int INDEX_LONGITUDE = 3;
    }
}
