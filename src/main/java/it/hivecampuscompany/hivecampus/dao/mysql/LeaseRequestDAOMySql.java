package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseRequestDAOMySql implements LeaseRequestDAO {

    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(LeaseRequestDAOMySql.class.getName());
    private Properties properties = LanguageLoader.getLanguageProperties();

    @Override  //Fabio
    public List<LeaseRequest> retrieveLeaseRequestsByAdID(AdBean adBean) {
        AccountDAO accountDAO = new AccountDAOMySql();
        List<LeaseRequest> requestList = new ArrayList<>();
        LeaseRequestStatus leaseRequestStatus = adBean.getAdStatus() == AdStatus.AVAILABLE ? LeaseRequestStatus.PROCESSING : LeaseRequestStatus.ACCEPTED;
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.RETRIEVE_LEASE_REQUESTS_BY_AD_ID)) {
            pst.setInt(1, adBean.getId());
            pst.setInt(2, leaseRequestStatus.getId());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    requestList.add(new LeaseRequest(
                            rs.getInt("idRequest"),
                            null,
                            accountDAO.retrieveAccountInformationByEmail(rs.getString("tenant")),
                            rs.getInt("startPermanence"),
                            rs.getInt("typePermanence"),
                            rs.getString("message"),
                            -1
                    ));
                }
            }

            return requestList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override   //Fabio
    public LeaseRequest retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean, boolean isDecorated) {
        AccountDAO accountDAO = new AccountDAOMySql();
        AdDAO adDAO = new AdDAOMySql();
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.RETRIEVE_LEASE_REQUEST_BY_ID)) {
            pst.setInt(1, leaseRequestBean.getId());
            try (ResultSet rs = pst.executeQuery()) {
                rs.next();
                return new LeaseRequest(
                        rs.getInt("idRequest"),
                        adDAO.retrieveAdByID(rs.getInt("ad"), isDecorated),
                        accountDAO.retrieveAccountInformationByEmail(rs.getString("tenant")),
                        rs.getInt("startPermanence"),
                        rs.getInt("typePermanence"),
                        rs.getString("message"),
                        rs.getInt("requestStatus")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override   //Fabio
    public void updateLeaseRequest(LeaseRequest leaseRequest) {
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.UPDATE_LEASE_REQUEST)) {
            pst.setInt(1, leaseRequest.getStatus().getId());
            pst.setInt(2, leaseRequest.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_SAVE_LEASE_REQUEST"));
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
            result = cstmt.getBoolean(3);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_VALID_REQUEST"));
        }
        return result;
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(SessionBean sessionBean, boolean isDecorated) {
        AccountDAO accountDAO = new AccountDAOMySql();
        AdDAO adDAO = new AdDAOMySql();

        List<LeaseRequest> leaseRequests = new ArrayList<>();

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_LEASE_REQUESTS_BY_TENANT)) {
            cstmt.setString(1, sessionBean.getEmail());

            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    LeaseRequest leaseRequest = new LeaseRequest(
                            rs.getInt(1),
                            adDAO.retrieveAdByID(rs.getInt(2), isDecorated),
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
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_LEASE_REQUESTS_BY_TENANT"));
        }
        return leaseRequests;
    }

    @Override
    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.DELETE_LEASE_REQUEST)) {
            cstmt.setInt(1, requestBean.getId());

            cstmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_DELETE_LEASE_REQUEST"));
        }
    }
}