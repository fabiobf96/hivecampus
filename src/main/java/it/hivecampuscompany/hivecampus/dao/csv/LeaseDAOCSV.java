package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.model.Lease;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseDAOCSV implements LeaseDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseDAOCSV.class.getName());
    private Properties properties;
    public LeaseDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")){
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
        String[] leaseRecord = new String[7];
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
    private static class LeaseAttributes{
        static final int GET_INDEX_AD = 0;
        static final int GET_INDEX_TENANT = 1;
        static final int GET_INDEX_STARTING = 2;
        static final int GET_INDEX_DURATION = 3;
        static final int GET_INDEX_SIGNED = 4;
        static final int GET_INDEX_TIMESTAMP = 5;
        static final int GET_INDEX_CONTRACT = 6;
    }
}
