package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.model.Home;

/**
 * HomeDAO interface for managing home data.
 * Provides an operation for retrieving a home by its ID.
 */
public interface HomeDAO {

    /**
     * Retrieves a home by its ID.
     *
     * @param id The unique identifier of the home to retrieve.
     * @return The {@link Home} object if found, otherwise null.
     */
    Home retrieveHomeByID(int id);
}
