package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.LeaseContractDAO;
import it.hivecampuscompany.hivecampus.model.LeaseContract;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseContractDAOCSV implements LeaseContractDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseContractDAOCSV.class.getName());

    public LeaseContractDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("LEASE_PATH"));
        } catch (IOException e) {
            Properties languageProperties = LanguageLoader.getLanguageProperties();
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), e);
            System.exit(1);
        }
    }

    @Override
    public void saveLease(LeaseContract leaseContract) {
        String[] leaseRecord = new String[7];
        leaseRecord[LeaseAttributes.GET_INDEX_ID] = String.valueOf(CSVUtility.findLastRowIndex(fd));
        leaseRecord[LeaseAttributes.GET_INDEX_AD] = String.valueOf(leaseContract.getAd().getId());
        leaseRecord[LeaseAttributes.GET_INDEX_TENANT] = leaseContract.getTenant().getEmail();
        leaseRecord[LeaseAttributes.GET_INDEX_STARTING] = String.valueOf(leaseContract.getLeaseMonth().getMonth());
        leaseRecord[LeaseAttributes.GET_INDEX_DURATION] = String.valueOf(leaseContract.getDuration().getPermanence());
        leaseRecord[LeaseAttributes.GET_INDEX_SIGNED] = String.valueOf(leaseContract.isSigned());
        leaseRecord[LeaseAttributes.GET_INDEX_CONTRACT] = CSVUtility.encodeBytesToBase64(leaseContract.getContract());
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            writer.writeNext(leaseRecord);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease", e);
            System.exit(2);
        }
    }

    @Override
    public LeaseContract retrieveUnsignedLeaseByTenant(String email, boolean isDecorated) {
        AdDAO adDAO = new AdDAOCSV();
        List<String[]> leaseTable = CSVUtility.readAll(fd);
        return leaseTable.stream()
                .filter(leaseRecord -> leaseRecord[LeaseAttributes.GET_INDEX_TENANT].equals(email) && !Boolean.parseBoolean(leaseRecord[LeaseAttributes.GET_INDEX_SIGNED]))
                .findFirst()
                .map(leaseRecord -> new LeaseContract(
                        Integer.parseInt(leaseRecord[LeaseAttributes.GET_INDEX_ID]),
                        adDAO.retrieveAdByID(Integer.parseInt(leaseRecord[LeaseAttributes.GET_INDEX_AD]), isDecorated),
                        Integer.parseInt(leaseRecord[LeaseAttributes.GET_INDEX_STARTING]),
                        Integer.parseInt(leaseRecord[LeaseAttributes.GET_INDEX_DURATION]),
                        CSVUtility.decodeBase64ToBytes(leaseRecord[LeaseAttributes.GET_INDEX_CONTRACT]),
                        Boolean.parseBoolean(leaseRecord[LeaseAttributes.GET_INDEX_SIGNED])
                ))
                .orElse(null);
    }

    @Override
    public void updateLease(LeaseContract leaseContract) {
        List<String[]> leaseTable = CSVUtility.readAll(fd);
        String[] header = leaseTable.removeFirst();
        leaseTable.replaceAll(leaseRecord -> Integer.parseInt(leaseRecord[LeaseAttributes.GET_INDEX_ID]) == leaseContract.getId() ? updateLeaseRecord(leaseRecord, leaseContract) : leaseRecord);
        CSVUtility.updateFile(fd, header, leaseTable);
    }

    private String[] updateLeaseRecord(String[] leaseRecord, LeaseContract leaseContract) {
        leaseRecord[LeaseAttributes.GET_INDEX_SIGNED] = String.valueOf(leaseContract.isSigned());
        return leaseRecord;
    }

    private static class LeaseAttributes {
        static final int GET_INDEX_ID = 0;
        static final int GET_INDEX_AD = 1;
        static final int GET_INDEX_TENANT = 2;
        static final int GET_INDEX_STARTING = 3;
        static final int GET_INDEX_DURATION = 4;
        static final int GET_INDEX_SIGNED = 5;
        static final int GET_INDEX_CONTRACT = 6;
    }
}
