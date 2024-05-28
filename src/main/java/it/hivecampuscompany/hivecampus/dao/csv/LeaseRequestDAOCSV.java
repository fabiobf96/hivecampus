package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseRequestDAOCSV implements LeaseRequestDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseRequestDAOCSV.class.getName());

    public LeaseRequestDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("LEASE_REQUEST_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByAdID(AdBean adBean) {
        List<String[]> leaseRequestList = CSVUtility.readAll(fd);
        leaseRequestList.removeFirst();
        LeaseRequestStatus leaseRequestStatus = adBean.getAdStatus() == AdStatus.AVAILABLE ? LeaseRequestStatus.PROCESSING : LeaseRequestStatus.ACCEPTED;
        return leaseRequestList.stream()
                .filter(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD]) == adBean.getId() && LeaseRequestStatus.fromInt(Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS])) == leaseRequestStatus)
                .map(leaseRequestRecord -> fillLeaseRequest(leaseRequestRecord, false))
                .toList();
    }

    @Override
    public LeaseRequest retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean) {
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        leaseRequestTable.removeFirst();
        return leaseRequestTable.stream()
                .filter(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == leaseRequestBean.getId())
                .findFirst()
                .map(leaseRequestRecord -> fillLeaseRequest(leaseRequestRecord, true))
                .orElse(null);

    }

    @Override
    public void updateLeaseRequest(LeaseRequest leaseRequest) {
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        String[] header = leaseRequestTable.removeFirst();
        leaseRequestTable.replaceAll(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == leaseRequest.getID() ? updateLeaseRequestRecord(leaseRequestRecord, leaseRequest) : leaseRequestRecord);
        CSVUtility.updateFile(fd, header, leaseRequestTable);
    }

    @Override
    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        String[] header = leaseRequestTable.removeFirst();
        if (leaseRequestTable.removeIf(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == requestBean.getId())) {
            CSVUtility.updateFile(fd, header, leaseRequestTable);
        }
    }

    @Override
    public void saveLeaseRequest(LeaseRequest leaseRequest) {
        // Find the last index in the file and increment it by 1
        int lastId = CSVUtility.findLastRowIndex(fd);
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] leaseRequestRecord = new String[7];
            leaseRequest.setID(lastId + 1);
            writer.writeNext(updateLeaseRequestRecord(leaseRequestRecord, leaseRequest));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease request", e);
            System.exit(2);
        }
    }

    @Override
    public boolean validRequest(String email, int id) {
        // Check if the tenant has already sent a request for the same ad
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        leaseRequestTable.removeFirst();
        return leaseRequestTable.stream()
                .noneMatch(leaseRequestRecord -> leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT].equals(email) && Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD]) == id);
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(SessionBean sessionBean) {
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        leaseRequestTable.removeFirst();
        return leaseRequestTable.stream()
                .filter(leaseRequestRecord -> leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT].equals(sessionBean.getEmail()))
                .map(leaseRequestRecord -> fillLeaseRequest(leaseRequestRecord, true))
                .toList();
    }

    private String[] updateLeaseRequestRecord(String[] requestRecord, LeaseRequest request) {
        requestRecord[LeaseRequestAttributes.INDEX_ID] = String.valueOf(request.getID());
        requestRecord[LeaseRequestAttributes.INDEX_AD] = String.valueOf(request.getAd().getId());
        requestRecord[LeaseRequestAttributes.INDEX_TENANT] = request.getTenant().getEmail();
        requestRecord[LeaseRequestAttributes.INDEX_STATUS] = String.valueOf(request.getStatus().getId());
        requestRecord[LeaseRequestAttributes.INDEX_START] = String.valueOf(request.getLeaseMonth().getMonth());
        requestRecord[LeaseRequestAttributes.INDEX_DURATION] = String.valueOf(request.getDuration().getPermanence());
        requestRecord[LeaseRequestAttributes.INDEX_MESSAGE] = request.getMessage();
        return requestRecord;
    }

    private LeaseRequest fillLeaseRequest(String[] leaseRequestRecord, boolean isFull) {
        AdDAO adDAO = new AdDAOCSV();
        AccountDAO accountDAO = new AccountDAOCSV();
        return new LeaseRequest(
                Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]),
                isFull ? adDAO.retrieveAdByID(Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD])) : null,
                accountDAO.retrieveAccountInformationByEmail(leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT]),
                Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_START]),
                Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_DURATION]),
                leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE],
                isFull ? Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS]) : -1
        );
    }

    private static class LeaseRequestAttributes {
        private static final int INDEX_ID = 0;
        private static final int INDEX_AD = 1;
        private static final int INDEX_TENANT = 2;
        private static final int INDEX_STATUS = 3;
        private static final int INDEX_START = 4;
        private static final int INDEX_DURATION = 5;
        private static final int INDEX_MESSAGE = 6;
    }
}
