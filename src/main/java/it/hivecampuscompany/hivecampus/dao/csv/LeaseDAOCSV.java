package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.model.Lease;

import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseDAOCSV implements LeaseDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseDAOCSV.class.getName());
    private Properties properties;

    public LeaseDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("LEASE_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    @Override
    public void saveLease(Lease lease) {
        String[] leaseRecord = new String[8];
        leaseRecord[LeaseAttributes.GET_INDEX_ID] = String.valueOf(CSVUtility.findLastRowIndex(fd));
        leaseRecord[LeaseAttributes.GET_INDEX_AD] = String.valueOf(lease.getAd().getId());
        leaseRecord[LeaseAttributes.GET_INDEX_TENANT] = lease.getTenant().getEmail();
        leaseRecord[LeaseAttributes.GET_INDEX_STARTING] = lease.getStarting();
        leaseRecord[LeaseAttributes.GET_INDEX_DURATION] = lease.getDuration();
        leaseRecord[LeaseAttributes.GET_INDEX_SIGNED] = String.valueOf(lease.isSigned());
        leaseRecord[LeaseAttributes.GET_INDEX_TIMESTAMP] = lease.getTimeStamp().toString();
        leaseRecord[LeaseAttributes.GET_INDEX_CONTRACT] = CSVUtility.encodeBytesToBase64(lease.getContract());
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            writer.writeNext(leaseRecord);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease", e);
            System.exit(2);
        }
    }

    @Override
    public Lease retrieveUnsignedLeaseByTenant(String email) {
        AdDAO adDAO = new AdDAOCSV();
        List<String[]> leaseTable = CSVUtility.readAll(fd);
        return leaseTable.stream()
                .filter(leaseRecord -> leaseRecord[LeaseAttributes.GET_INDEX_TENANT].equals(email) && !Boolean.parseBoolean(leaseRecord[LeaseAttributes.GET_INDEX_SIGNED]))
                .findFirst()
                .map(leaseRecord -> new Lease(
                        Integer.parseInt(leaseRecord[LeaseAttributes.GET_INDEX_ID]),
                        adDAO.retrieveAdByID(Integer.parseInt(leaseRecord[LeaseAttributes.GET_INDEX_AD])),
                        leaseRecord[LeaseAttributes.GET_INDEX_STARTING],
                        leaseRecord[LeaseAttributes.GET_INDEX_DURATION],
                        CSVUtility.decodeBase64ToBytes(leaseRecord[LeaseAttributes.GET_INDEX_CONTRACT]),
                        Boolean.parseBoolean(leaseRecord[LeaseAttributes.GET_INDEX_SIGNED]),
                        Instant.parse(leaseRecord[LeaseAttributes.GET_INDEX_TIMESTAMP])
                ))
                .orElse(null);
    }

    @Override
    public void updateLease(Lease lease) {

    }

    private static class LeaseAttributes {
        static final int GET_INDEX_ID = 0;
        static final int GET_INDEX_AD = 1;
        static final int GET_INDEX_TENANT = 2;
        static final int GET_INDEX_STARTING = 3;
        static final int GET_INDEX_DURATION = 4;
        static final int GET_INDEX_SIGNED = 5;
        static final int GET_INDEX_TIMESTAMP = 6;
        static final int GET_INDEX_CONTRACT = 7;
    }
}
