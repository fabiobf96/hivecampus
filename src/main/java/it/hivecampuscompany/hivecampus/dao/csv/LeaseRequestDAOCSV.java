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


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class LeaseRequestDAOCSV implements LeaseRequestDAO {
    private final File fd;
    public LeaseRequestDAOCSV() {
        fd = new File("db/csv/lease_request-table.csv");
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
                            Integer.parseInt(leaseRequestRecord[LeaseRequestAttributes.INDEX_STATUS]),
                            leaseRequestRecord[LeaseRequestAttributes.INDEX_MESSAGE]
                    ))
                    .toList();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
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
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
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
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }

        // Sostituisci il file originale con il file temporaneo aggiornato
        try {
            Files.move(fdTmp.toPath(), fd.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
