package it.hivecampuscompany.hivecampus.dao;

import it.hivecampuscompany.hivecampus.model.Lease;

public interface LeaseDAO {
    void saveLease(Lease lease);
    Lease retrieveUnsignedLeaseByTenant(String email);
    void updateLease(Lease lease);
}
