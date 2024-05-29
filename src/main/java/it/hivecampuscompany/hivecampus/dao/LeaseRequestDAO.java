package it.hivecampuscompany.hivecampus.dao;


import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;

import java.util.List;

/**
 * LeaseRequestDAO interface for managing lease request data.
 * Provides methods to retrieve, save, update, and delete lease requests.
 */
public interface LeaseRequestDAO {

    /**
     * Retrieves a list of lease requests based on the advertisement's ID wrapped in an AdBean.
     * The operation behaves differently depending on the {@link AdStatus} within the AdBean:
     * <p>
     * - If the AdStatus is AVAILABLE: Returns all the lease requests that have a status of PROCESSING.
     * This typically means these requests are waiting for approval or action from the advertisement's owner.
     * <p>
     * - If the AdStatus is PROCESSING: Returns the lease request that have a status of ACCEPTED for the advertisement.
     * This implies there should only be one such request in a normal scenario as it represents the chosen lease.
     *
     * @param adBean The AdBean containing the advertisement's ID and status.
     * @return A list of {@link LeaseRequest} objects that match the criteria based on the advertisement's status.
     * If no lease requests match the criteria, an empty list is returned.
     * @author Fabio Barchiesi
     */
    List<LeaseRequest> retrieveLeaseRequestsByAdID(AdBean adBean);

    /**
     * Retrieves a specific lease request by its ID wrapped in a LeaseRequestBean.
     *
     * @param leaseRequestBean The LeaseRequestBean containing the lease request's ID.
     * @return The {@link LeaseRequest} object if found, otherwise null.
     * @author Fabio Barchiesi
     */
    LeaseRequest retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean, boolean isDecorated);

    /**
     * Updates an existing lease request's information in the database.
     *
     * @param leaseRequest The lease request object to be updated.
     * @author Fabio Barchiesi
     */
    void updateLeaseRequest(LeaseRequest leaseRequest);

    /**
     * Saves a new lease request in the database.
     *
     * @param leaseRequest The lease request object to be saved.
     * @author Marina Sotiropoulos
     */
    void saveLeaseRequest(LeaseRequest leaseRequest);

    /**
     * Checks if a lease request is valid. A lease request sent by a tenant is valid if the tenant has not already
     * sent a lease request for the advertisement.
     *
     * @param email The email of the tenant associated with the lease request.
     * @param id    The ID of the advertisement associated with the lease request.
     * @return True if the lease request is valid, otherwise false.
     * @author Marina Sotiropoulos
     */
    boolean validRequest(String email, int id);

    /**
     * Retrieves a list of lease requests based on the tenant's email.
     *
     * @param sessionBean The SessionBean containing the tenant's email.
     * @return A list of {@link LeaseRequest} objects that match the tenant's email.
     * If no lease requests match the criteria, an empty list is returned.
     * @author Marina Sotiropoulos
     */

    List<LeaseRequest> retrieveLeaseRequestsByTenant(SessionBean sessionBean, boolean isDecorated);

    /**
     * Deletes a lease request from the database.
     * This operation is typically used when a tenant wants to cancel a lease request.
     *
     * @param requestBean The LeaseRequestBean containing the lease request's ID.
     * @author Marina Sotiropoulos
     */
    void deleteLeaseRequest(LeaseRequestBean requestBean);
}
