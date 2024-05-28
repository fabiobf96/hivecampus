package it.hivecampuscompany.hivecampus.dao.csv;

import it.hivecampuscompany.hivecampus.dao.UniversityDAO;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;

public class UniversityDAOCSV implements UniversityDAO {

    private final File fd;

    public UniversityDAOCSV() {
        fd = new File("db/csv/university-table.csv");
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
