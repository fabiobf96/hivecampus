package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * AdDAO interface for managing advertisement data.
 * Provides operations for retrieving and updating advertisements.
 */
public interface AdDAO {

    /**
     * Retrieves a list of ads based on the owner's account and the advertisement status.
     *
     * @param sessionBean The session bean containing the owner's account information.
     * @param adStatus    The status of the advertisements to retrieve. This operation
     *                    differentiates between two types of {@link AdStatus}:
     *                    - {@link AdStatus#AVAILABLE}: Returns all ads belonging to an owner for which
     *                    a rental request has not yet been accepted.
     *                    - {@link AdStatus#PROCESSING}: Returns ads for which a lease request has been
     *                    accepted.
     * @return A list of {@link Ad} objects matching the criteria. If no ads match the criteria,
     * an empty list is returned.
     * @author Fabio Barchiesi
     */
    List<Ad> retrieveAdsByOwner(SessionBean sessionBean, AdStatus adStatus);

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id The ID of the advertisement to retrieve.
     * @return The {@link Ad} object if found, otherwise null.
     * @author Fabio Barchiesi
     */
    Ad retrieveAdByID(int id);

    /**
     * Updates an existing advertisement's information in the database.
     *
     * @param ad The advertisement object to be updated.
     * @author Fabio Barchiesi
     */
    void updateAd(Ad ad);

    /**
     * Retrieves a list of ads based on the filters provided by the user.
     *
     * @param filtersBean    The filters bean containing the user's search criteria.
     * @param uniCoordinates The coordinates of the university to which the user is affiliated.
     * @return A list of {@link Ad} objects matching the criteria. If no ads match the criteria, an empty list is returned.
     * @author Marina Sotiropoulos
     */

    List<Ad> retrieveAdsByFilters(FiltersBean filtersBean, Point2D uniCoordinates);

    /**
     * Publishes a new advertisement in the database.
     * The owner enters the information via a form dedicated to creating the ad.
     * The advertisement is then published and made available to potential tenants.
     *
     * @param ad The advertisement to be published.
     * @return True if the advertisement was successfully published, otherwise false.
     * @author Marina Sotiropoulos
     */

    boolean publishAd(Ad ad);
}
