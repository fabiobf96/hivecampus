package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseRequestDAOCSV implements LeaseRequestDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseRequestDAOCSV.class.getName());
    private Properties properties;
    private static final String ERR_ACCESS = "ERR_ACCESS";
    private static final String ERR_PARSER = "ERR_PARSER";
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
        try (CSVReader reader = new CSVReader(new FileReader(fd))){
            List<String[]> leaseRequestList = reader.readAll();
            leaseRequestList.removeFirst();
            LeaseRequestStatus leaseRequestStatus = adBean.getAdStatus() == AdStatus.AVAILABLE ? LeaseRequestStatus.PROCESSING : LeaseRequestStatus.ACCEPTED;
            return leaseRequestList.stream()
                    .filter(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD]) == adBean.getId() && LeaseRequestStatus.fromInt(Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS])) == leaseRequestStatus)
                    .map(leaseRequestRecord -> new LeaseRequest(
                            Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]),
                            accountDAO.retrieveAccountInformationByEmail(leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT]),
                            leaseRequestRecord[LeaseRequestAttributes.INDEX_START],
                            leaseRequestRecord[LeaseRequestAttributes.INDEX_DURATION],
                            leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE]
                    ))
                    .toList();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERR_ACCESS), fd), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERR_PARSER), fd), e);
            System.exit(3);
        }
        return Collections.emptyList();
    }

    @Override
    public LeaseRequest retrieveLeaseRequestByID(LeaseRequestBean leaseRequestBean) {
        AccountDAO accountDAO = new AccountDAOCSV();
        AdDAO adDAO = new AdDAOCSV();
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> leaseRequestTable = reader.readAll();
            leaseRequestTable.removeFirst();
            return leaseRequestTable.stream()
                    .filter(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == leaseRequestBean.getId())
                    .findFirst()
                    .map(leaseRequestRecord -> new LeaseRequest(
                            Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]),
                            adDAO.retrieveAdByID(Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_AD])),
                            accountDAO.retrieveAccountInformationByEmail(leaseRequestRecord[LeaseRequestAttributes.INDEX_TENANT]),
                            leaseRequestRecord[LeaseRequestAttributes.INDEX_START],
                            leaseRequestRecord[LeaseRequestAttributes.INDEX_DURATION],
                            leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE],
                            Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS])
                    ))
                    .orElse(null);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERR_ACCESS), fd), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERR_PARSER), fd), e);
            System.exit(3);
        }
        return null;
    }

    @Override
    public void updateLeaseRequest(LeaseRequest leaseRequest) {
        File fdTmp = new File(fd.getAbsolutePath() + ".tmp");
        try (CSVReader reader = new CSVReader(new FileReader(fd));
             CSVWriter writer = new CSVWriter(new FileWriter(fdTmp))) {
            List<String[]> leaseRequestTable = reader.readAll();
            String[] header = leaseRequestTable.getFirst();
            leaseRequestTable.removeFirst();
            leaseRequestTable.replaceAll(leaseRequestRecord -> Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_ID]) == leaseRequest.getID() ? leaseRequest.toCSVString() : leaseRequestRecord);
            leaseRequestTable.addFirst(header);
            writer.writeAll(leaseRequestTable);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERR_ACCESS), fd), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERR_PARSER), fd), e);
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
