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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LeaseRequestDAOCSV class for managing lease request data in a CSV file.
 * It implements the LeaseRequestDAO interface and provides methods to retrieve, save, update, and delete lease requests.
 */

public class LeaseRequestDAOCSV implements LeaseRequestDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseRequestDAOCSV.class.getName());
    private Properties properties;

    /**
     * Constructor for the LeaseRequestDAOCSV class.
     * It initializes the file path of the lease request data CSV file.
     */

    public LeaseRequestDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("LEASE_REQUEST_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByAdID(AdBean adBean) {
        AccountDAO accountDAO = new AccountDAOCSV();
        List<String[]> leaseRequestList = CSVUtility.readAll(fd);
        leaseRequestList.removeFirst();
        LeaseRequestStatus leaseRequestStatus = adBean.getAdStatus() == AdStatus.AVAILABLE ? LeaseRequestStatus.PROCESSING : LeaseRequestStatus.ACCEPTED;
        return leaseRequestList.stream()
                .filter(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD]) == adBean.getId() && LeaseRequestStatus.fromInt(Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS])) == leaseRequestStatus)
                .map(leaseRequestRecord -> new LeaseRequest(
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]),
                        accountDAO.retrieveAccountInformationByEmail(leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT]),
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_START]),
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_DURATION]),
                        leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE]
                ))
                .toList();
    }

    @Override
    public LeaseRequest retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean) {
        AccountDAO accountDAO = new AccountDAOCSV();
        AdDAO adDAO = new AdDAOCSV();
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        leaseRequestTable.removeFirst();
        return leaseRequestTable.stream()
                .filter(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == leaseRequestBean.getId())
                .findFirst()
                .map(leaseRequestRecord -> new LeaseRequest(
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]),
                        adDAO.retrieveAdByID(Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD])),
                        accountDAO.retrieveAccountInformationByEmail(leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT]),
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_START]),
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_DURATION]),
                        leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE],
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS])
                ))
                .orElse(null);

    }

    @Override
    public void updateLeaseRequest(LeaseRequest leaseRequest) {
        File fdTmp = new File(fd.getAbsolutePath() + ".tmp");
        try (CSVWriter writer = new CSVWriter(new FileWriter(fdTmp))) {
            List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
            String[] header = leaseRequestTable.getFirst();
            leaseRequestTable.removeFirst();
            leaseRequestTable.replaceAll(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == leaseRequest.getID() ? leaseRequest.toCSVString() : leaseRequestRecord);
            leaseRequestTable.addFirst(header);
            writer.writeAll(leaseRequestTable);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERR_ACCESS"), fdTmp), e);
            System.exit(3);
        }
        // Sostituisci il file originale con il file temporaneo aggiornato
        try {
            Files.move(fdTmp.toPath(), fd.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Failed to move file from %s to %s", fdTmp, fd), e);
            System.exit(4);
        }
    }

    /**
     * Method to save a lease request to the CSV file. It finds the last index from the file by calling the
     * findLastRowIndex method and increments it by 1 to assign a new index to the lease request.
     * Then it writes the lease request to the file by creating a new record and appending it to the end of the file.
     *
     * @param leaseRequest The lease request to save.
     */

    @Override
    public void saveLeaseRequest(LeaseRequest leaseRequest) {

        int lastId = CSVUtility.findLastRowIndex(fd);
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] leaseRequestRecord = new String[7];
            leaseRequestRecord[LeaseRequestAttributes.INDEX_ID] = String.valueOf(lastId + 1);
            leaseRequestRecord[LeaseRequestAttributes.INDEX_AD] = String.valueOf(leaseRequest.getAd().getId());
            leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT] = leaseRequest.getTenant().getEmail();
            leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS] = String.valueOf(leaseRequest.getStatus().getId());
            leaseRequestRecord[LeaseRequestAttributes.INDEX_START] = String.valueOf(leaseRequest.getLeaseMonth().getMonth());
            leaseRequestRecord[LeaseRequestAttributes.INDEX_DURATION] = String.valueOf(leaseRequest.getDuration().getPermanence());
            leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE] = leaseRequest.getMessage();
            writer.writeNext(leaseRequestRecord);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease request", e);
            System.exit(2);
        }
    }

    /**
     * Method to check if a lease request is valid.
     * It reads all the records from the file and checks if the tenant has already sent a request for the same ad.
     * If the tenant has already sent a request for the same ad, it returns false, otherwise true.
     *
     * @param email The email of the tenant.
     * @param id The ID of the ad.
     * @return True if the lease request is valid, otherwise false.
     */


    @Override
    public boolean validRequest(String email, int id) {
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        leaseRequestTable.removeFirst();
        return leaseRequestTable.stream()
                .noneMatch(leaseRequestRecord -> leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT].equals(email) && Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD]) == id);
    }

    /**
     * Method to retrieve all the lease requests of a tenant.
     * It reads all the records from the file and filters the records by the tenant's email.
     * It then maps the records to LeaseRequest objects and returns a list of LeaseRequest objects.
     *
     * @param sessionBean The session bean from which to retrieve the tenant's email.
     * @return List of LeaseRequest objects.
     */

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(SessionBean sessionBean) {
        AccountDAO accountDAO = new AccountDAOCSV();
        AdDAO adDAO = new AdDAOCSV();
        List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
        leaseRequestTable.removeFirst();
        return leaseRequestTable.stream()
                .filter(leaseRequestRecord -> leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT].equals(sessionBean.getEmail()))
                .map(leaseRequestRecord -> new LeaseRequest(
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]),
                        adDAO.retrieveAdByID(Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD])),
                        accountDAO.retrieveAccountInformationByEmail(leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT]),
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_START]),
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_DURATION]),
                        leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE],
                        Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS])
                ))
                .toList();
    }

    /**
     * Method to delete a lease request from the CSV file.
     * It reads all the records from the file and filters the records by the lease request's ID.
     * It then removes the record from the list and writes the updated list to a temporary file.
     * Finally, it moves the temporary file to the original file to update the file.
     *
     * @param requestBean The lease request to delete.
     */

    @Override
    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        File fdTmp = new File(fd.getAbsolutePath() + ".tmp");
        try (CSVWriter writer = new CSVWriter(new FileWriter(fdTmp))) {
            List<String[]> leaseRequestTable = CSVUtility.readAll(fd);
            String[] header = leaseRequestTable.getFirst();
            leaseRequestTable.removeFirst();
            leaseRequestTable.removeIf(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == requestBean.getId());
            leaseRequestTable.addFirst(header);
            writer.writeAll(leaseRequestTable);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERR_ACCESS"), fdTmp), e);
            System.exit(3);
        }
        try {
            Files.move(fdTmp.toPath(), fd.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Failed to move file from %s to %s", fdTmp, fd), e);
            System.exit(4);
        }
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
