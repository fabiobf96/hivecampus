package it.hivecampuscompany.hivecampus.dao.csv;

import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UniversityDAOCSV implements UniversityDAO {

    private File fd;
    private static final Logger LOGGER = Logger.getLogger(UniversityDAOCSV.class.getName());

    public UniversityDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("UNIVERSITY_PATH"));
        } catch (IOException e) {
            Properties languageProperties = LanguageLoader.getLanguageProperties();
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), e);
            System.exit(1);
        }
    }

    @Override
    public Point2D getUniversityCoordinates(String universityName) {
            List<String[]> universityTable = CSVUtility.readAll(fd);
            universityTable.removeFirst();
            // Find the university record by name and return its coordinates as a Point2D object
            return universityTable.stream()
                    .filter(universityRecord -> universityRecord[UniversityDAOCSV.UniversityAttributes.INDEX_NAME].trim().equalsIgnoreCase(universityName.trim()))
                    .findFirst()
                    .map(universityRecord -> new Point2D.Double(Double.parseDouble(universityRecord[UniversityDAOCSV.UniversityAttributes.INDEX_LONGITUDE]), Double.parseDouble(universityRecord[UniversityDAOCSV.UniversityAttributes.INDEX_LATITUDE])))
                    .orElse(null);
    }

    private static class UniversityAttributes{
        private static final int INDEX_NAME = 0;
        private static final int INDEX_LATITUDE = 2;
        private static final int INDEX_LONGITUDE = 3;
    }
}
