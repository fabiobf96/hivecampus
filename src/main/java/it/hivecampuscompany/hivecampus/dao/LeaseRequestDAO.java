package it.hivecampuscompany.hivecampus.dao;



import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;

import java.util.List;

/**
 * LeaseRequestDAO interface for managing lease request data.
 * Provides operations for retrieving and updating lease requests.
 */
public interface LeaseRequestDAO {

    /**
     * Retrieves a list of lease requests based on the advertisement's ID wrapped in an AdBean.
     * The operation behaves differently depending on the {@link AdStatus} within the AdBean:

     * - If the AdStatus is AVAILABLE: Returns all the lease requests that have a status of PROCESSING.
     *   This typically means these requests are waiting for approval or action from the advertisement's owner.

     * - If the AdStatus is PROCESSING: Returns the lease request that have a status of ACCEPTED for the advertisement.
     *   This implies there should only be one such request in a normal scenario as it represents the chosen lease.
     *
     * @param adBean The AdBean containing the advertisement's ID and status.
     * @return A list of {@link LeaseRequest} objects that match the criteria based on the advertisement's status.
     *         If no lease requests match the criteria, an empty list is returned.
     */
    List<LeaseRequest> retrieveLeaseRequestsByAdID(AdBean adBean);

    /**
     * Retrieves a specific lease request by its ID wrapped in a LeaseRequestBean.
     *
     * @param leaseRequestBean The LeaseRequestBean containing the lease request's ID.
     * @return The {@link LeaseRequest} object if found, otherwise null.
     */
    LeaseRequest retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean);

    /**
     * Updates an existing lease request's information in the database.
     *
     * @param leaseRequest The lease request object to be updated.
     */
    void updateLeaseRequest(LeaseRequest leaseRequest);

    void saveLeaseRequest(LeaseRequest leaseRequest);

    boolean validRequest(String email, int id);

    List<LeaseRequest> retrieveLeaseRequestsByTenant(SessionBean sessionBean);

    void deleteLeaseRequest(LeaseRequestBean requestBean);
}
