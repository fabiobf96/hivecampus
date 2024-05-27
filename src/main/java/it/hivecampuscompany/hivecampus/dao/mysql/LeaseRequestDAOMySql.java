package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseRequestDAOMySql implements LeaseRequestDAO {

    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(LeaseRequestDAOMySql.class.getName());

    @Override  //Fabio
    public List<LeaseRequest> retrieveLeaseRequestsByAdID(AdBean adBean) {
        return List.of();
    }

    @Override   //Fabio
    public LeaseRequest retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean) {
        return null;
    }

    @Override   //Fabio
    public void updateLeaseRequest(LeaseRequest leaseRequest) {

    }

    @Override
    public void saveLeaseRequest(LeaseRequest leaseRequest) {
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.SAVE_LEASE_REQUEST)) {
            cstmt.setInt(1, leaseRequest.getAd().getId());
            cstmt.setString(2, leaseRequest.getTenant().getEmail());
            cstmt.setInt(3, leaseRequest.getStatus().getId());
            cstmt.setInt(4, leaseRequest.getLeaseMonth().getMonth());
            cstmt.setInt(5, leaseRequest.getDuration().getPermanence());
            cstmt.setString(6, leaseRequest.getMessage());

            cstmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "FAILED_SAVE_LEASE_REQUEST", e);
        }
    }

    @Override
    public boolean validRequest(String email, int id) {
        boolean result = false;
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.VALID_REQUEST)) {
            cstmt.setString(1, email);
            cstmt.setInt(2, id);
            cstmt.registerOutParameter(3, java.sql.Types.BOOLEAN);

            cstmt.execute();
            result =  cstmt.getBoolean(3);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "FAILED_VALID_REQUEST", e);
        }
        return result;
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(SessionBean sessionBean) {
        AccountDAO accountDAO = new AccountDAOMySql();
        AdDAO adDAO = new AdDAOMySql();

        List<LeaseRequest> leaseRequests = new ArrayList<>();

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_LEASE_REQUESTS_BY_TENANT)) {
            cstmt.setString(1, sessionBean.getEmail());

            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    LeaseRequest leaseRequest = new LeaseRequest(
                            rs.getInt(1),
                            adDAO.retrieveAdByID(rs.getInt(2)),
                            accountDAO.retrieveAccountInformationByEmail(rs.getString(3)),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getString(7),
                            rs.getInt(4)
                    );
                    leaseRequests.add(leaseRequest);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "FAILED_RETRIEVE_LEASE_REQUESTS_BY_TENANT", e);
        }
        return leaseRequests;
    }

    @Override
    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.DELETE_LEASE_REQUEST)) {
            cstmt.setInt(1, requestBean.getId());

            cstmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "FAILED_DELETE_LEASE_REQUEST", e);
        }
    }
}