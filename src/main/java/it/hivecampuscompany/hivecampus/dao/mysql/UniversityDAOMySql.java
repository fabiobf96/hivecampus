package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.awt.geom.Point2D;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UniversityDAOMySql implements UniversityDAO {

    private final Connection connection = ConnectionManager.getInstance().getConnection();
    private static final Logger LOGGER = Logger.getLogger(UniversityDAOMySql.class.getName());
    private final Properties properties = LanguageLoader.getLanguageProperties();

    @Override
    public Point2D getUniversityCoordinates(String universityName) {
        Point2D coordinates = null;
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_UNIVERSITY_COORDINATES)) {
            cstmt.setString(1, universityName);
            if (cstmt.execute()) {
                try (ResultSet res = cstmt.getResultSet()) {
                    if (res.next()) {
                        coordinates = new Point2D.Double(res.getDouble(1), res.getDouble(2));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_UNIVERSITY_COORDINATES"));
        }
        return coordinates;
    }
}
