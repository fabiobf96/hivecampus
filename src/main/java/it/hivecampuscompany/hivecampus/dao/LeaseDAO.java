package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.model.Lease;

/**
 * LeaseDAO is an interface for managing lease contracts.
 * It provides methods to save, retrieve, and update lease contracts.
 */
public interface LeaseDAO {

    /**
     * Saves a lease contract in the system.
     *
     * @param lease the lease contract to be saved
     * @author Fabio Barchiesi
     */
    void saveLease(Lease lease);

    /**
     * Retrieves an unsigned lease contract for a given tenant, identified by their email.
     *
     * @param email the email of the tenant
     * @param isDecorated a boolean indicating if the lease object should be decorated
     * @return the unsigned lease contract corresponding to the tenant,
     * or null if there is no unsigned lease for that email
     * @author Fabio Barchiesi
     */
    Lease retrieveUnsignedLeaseByTenant(String email, boolean isDecorated);

    /**
     * Updates a lease contract in the system.
     *
     * @param lease the lease contract to be updated
     * @author Fabio Barchiesi
     */
    void updateLease(Lease lease);
}
