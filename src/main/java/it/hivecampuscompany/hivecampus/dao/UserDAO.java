package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.model.User;

/**
 * UserDAO interface for managing user data.
 * Provides operations to save and verify user credentials.
 */

public interface UserDAO {

    /**
     * Saves a user in the database.
     *
     * @param user The user to be saved.
     * @throws DuplicateRowException If the user already exists in the database.
     */

    void saveUser(User user) throws DuplicateRowException;

    /**
     * Verifies user credentials.
     * It checks if the user exists in the database and if the email and password match.
     *
     * @param user The user to be verified.
     * @return The verified user.
     * @throws AuthenticateException If the user does not exist or the credentials do not match.
     */

    User verifyCredentials(User user) throws AuthenticateException;
}