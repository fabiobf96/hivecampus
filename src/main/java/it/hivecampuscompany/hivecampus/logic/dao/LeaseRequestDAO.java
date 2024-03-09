package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;

import java.util.List;

public interface LeaseRequestDAO {
    List<LeaseRequest> retrieveLeaseRequestsByEmail (String email);
    List<LeaseRequest> retrieveLeaseRequestsByRoomID (int idHome, int idRoom);
    void saveLeaseRequest(LeaseRequest leaseRequest);
    void updateLeaseRequest(LeaseRequest leaseRequest);
    void deleteLeaseRequest(int idLeaseRequest);
}
