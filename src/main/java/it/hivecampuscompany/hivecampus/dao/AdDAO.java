package it.hivecampuscompany.hivecampus.dao;



import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;

import java.util.List;

/**
 * AdDAO interface for managing advertisement data.
 * Provides operations for retrieving and updating advertisements.
 */
public interface AdDAO {

    /**
     * Retrieves a list of ads based on the owner's account and the advertisement status.
     *
     * @param accountBean The account of the owner of the advertisements.
     * @param adStatus The status of the advertisements to retrieve. This operation
     *                 differentiates between two types of {@link AdStatus}:
     *                 - {@link AdStatus#AVAILABLE}: Returns all ads belonging to an owner for which
     *                   a rental request has not yet been accepted.
     *                 - {@link AdStatus#PROCESSING}: Returns ads for which a lease request has been
     *                   accepted.
     * @return A list of {@link Ad} objects matching the criteria. If no ads match the criteria,
     *         an empty list is returned.
     */
    List<Ad> retrieveAdsByOwner(AccountBean accountBean, AdStatus adStatus);

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id The ID of the advertisement to retrieve.
     * @return The {@link Ad} object if found, otherwise null.
     */
    Ad retrieveAdByID(int id);

    /**
     * Updates an existing advertisement's information in the database.
     *
     * @param ad The advertisement object to be updated.
     */
    void updateAd(Ad ad);

    List<Ad> retrieveAdsByFilters(FiltersBean filtersBean);
}
