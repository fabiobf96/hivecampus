package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.model.Account;

/**
 * AccountDAO interface for managing accounts.
 * Provides operations to save and retrieve account information.
 */
public interface AccountDAO {

    /**
     * Saves an account in the database.
     *
     * @param account The account to be saved.
     */
    void saveAccount(Account account);

    /**
     * Retrieves account information by email.
     *
     * @param email The email associated with the account to retrieve.
     * @return The retrieved account if present, otherwise null.
     */
    Account retrieveAccountInformationByEmail(String email);
}
