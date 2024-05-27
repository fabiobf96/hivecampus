package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.model.Home;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * HomeDAO interface for managing home data.
 * Provides an operation for retrieving homes by their ID, by distance from a university, by owner, and for saving a new home.
 */
public interface HomeDAO {

    /**
     * Retrieves a home by its ID.
     *
     * @param id The unique identifier of the home to retrieve.
     * @return The {@link Home} object if found, otherwise null.
     */
    Home retrieveHomeByID(int id);

    /**
     * Retrieves all homes within a specified distance from a university
     * by calculating the distance between each home and the university using the Haversine formula.
     *
     * @param unicoordinates The coordinates of the university.
     * @param distance The distance from the point.
     * @return A list of {@link Home} objects if found, otherwise an empty list.
     */

    List<Home> retrieveHomesByDistance(Point2D unicoordinates, double distance);

    /**
     * Retrieves homes by owner email.
     *
     * @param ownerEmail The email of the owner.
     * @return A list of {@link Home} objects if found, otherwise an empty list.
     */

    List<Home> retrieveHomesByOwner(String ownerEmail);

    /**
     * Saves a home in the database.
     *
     * @param homeBean The home to be saved.
     * @param ownerEmail The email of the owner.
     * @return The saved {@link Home} object.
     */

    Home saveHome(HomeBean homeBean, String ownerEmail);

    /**
     * Saves the image of a home.
     *
     * @param imageName The name of the image.
     * @param imageType The type of the image.
     * @param byteArray The byte array representing the image.
     * @param idHome The unique identifier of the home.
     */

    void saveHomeImage (String imageName, String imageType, byte[] byteArray, int idHome);

    /**
     * Retrieves the image of a home.
     *
     * @param idHome The unique identifier of the home.
     * @return The image of the home if found, otherwise null.
     */

    byte[] getHomeImage(int idHome);
}
