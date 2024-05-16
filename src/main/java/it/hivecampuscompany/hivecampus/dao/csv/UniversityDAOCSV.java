package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The UniversityDAOCSV class provides methods for retrieving university data from a CSV file.
 * It implements the UniversityDAO interface and retrieves the coordinates of a university by its name and
 * returns them as a Point2D object.
 */

public class UniversityDAOCSV implements UniversityDAO {

    private final File uni;
    private final Properties properties = LanguageLoader.getLanguageProperties();
    private static final Logger LOGGER = Logger.getLogger(UniversityDAOCSV.class.getName());

    /**
     * Constructor for the UniversityDAOCSV class.
     * It initializes the file path of the university data CSV file.
     */

    public UniversityDAOCSV() {
        uni = new File("db/csv/university-table.csv");
    }

    /**
     * Retrieves the latitude and longitude coordinates of a university by its name.
     *
     * @param universityName The name of the university for which to retrieve the coordinates.
     * @return A Point2D object containing the latitude and longitude coordinates.
     */

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
            LOGGER.severe(properties.getProperty("FAILED_LOADING_CSV_PROPERTIES"));
        }
        return null;
    }

    /**
     * Inner class containing the indexes of the university attributes in the CSV file.
     */

    private static class UniversityAttributes{
        private static final int INDEX_NAME = 0;
        private static final int INDEX_LATITUDE = 2;
        private static final int INDEX_LONGITUDE = 3;
    }
}
