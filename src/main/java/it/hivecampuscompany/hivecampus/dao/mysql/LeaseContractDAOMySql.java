package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseContractDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.model.LeaseContract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaseContractDAOMySql implements LeaseContractDAO {
    private final Connection connection = ConnectionManager.getConnection();
    @Override
    public void saveLease(LeaseContract leaseContract) {
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.SAVE_LEASE)){
            pst.setInt(1, leaseContract.getAd().getId());
            pst.setString(2, leaseContract.getTenant().getEmail());
            pst.setInt(3, leaseContract.getLeaseMonth().getMonth());
            pst.setInt(4, leaseContract.getDuration().getPermanence());
            pst.setBoolean(5, leaseContract.isSigned());
            pst.setBytes(6, leaseContract.getContract());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LeaseContract retrieveUnsignedLeaseByTenant(String email, boolean isDecorated) {
        AdDAO adDAO = new AdDAOMySql();
        try (PreparedStatement prs = connection.prepareStatement(StoredProcedures.RETRIEVE_UNSIGNED_LEASE_BY_TENANT)){
            prs.setString(1, email);
            try (ResultSet rs = prs.executeQuery()){
                rs.next();
                return new LeaseContract(
                        rs.getInt("idContract"),
                        adDAO.retrieveAdByID(rs.getInt("ad"), isDecorated),
                        rs.getInt("startPermanence"),
                        rs.getInt("typePermanence"),
                        rs.getBytes("data"),
                        rs.getBoolean("active")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLease(LeaseContract leaseContract) {
        try (PreparedStatement prs = connection.prepareStatement(StoredProcedures.UPDATE_LEASE)){
            prs.setBoolean(1, leaseContract.isSigned());
            prs.setInt(2, leaseContract.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
