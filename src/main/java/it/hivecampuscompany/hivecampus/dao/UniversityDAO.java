package it.hivecampuscompany.hivecampus.dao;

import java.awt.geom.Point2D;

/**
 * UniversityDAO interface for managing university data.
 * Provides an operation for retrieving university coordinates by name.
 */

public interface UniversityDAO {

    /**
     * Retrieves the coordinates of a university by its name.
     *
     * @param universityName The name of the university to retrieve.
     * @return The {@link Point2D} object with the coordinates if found, otherwise null.
     */

    Point2D getUniversityCoordinates(String universityName);
}
