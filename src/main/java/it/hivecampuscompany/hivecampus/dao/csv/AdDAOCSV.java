package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AdDAOCSV implements AdDAO {
    private final File fd;

    public AdDAOCSV() {
        fd = new File("db/csv/ad-table.csv");
    }

    @Override
    public List<Ad> retrieveAdsByOwner(SessionBean sessionBean, AdStatus adStatus) {
        HomeDAO homeDAO = new HomeDAOCSV();
        RoomDAO roomDAO = new RoomDAOCSV();
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> adTable = reader.readAll();
            adTable.removeFirst();
            return adTable.stream()
                    // Ensure to separate the conditions correctly and parse the status value appropriately
                    .filter(adRecord -> adRecord[AdAttributes.INDEX_OWNER].equals(sessionBean.getEmail()) && AdStatus.fromInt(Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS])) == adStatus)
                    .map(adRecord ->
                            // Pass the actual values from adRecord to retrieveHomeByID and retrieveRoomByID
                            new Ad(
                                    Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                                    homeDAO.retrieveHomeByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME])),
                                    roomDAO.retrieveRoomByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME]), Integer.parseInt(adRecord[AdAttributes.INDEX_ROOM])),
                                    Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                            )
                    ).toList();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ad retrieveAdByID(int id) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> adTable = reader.readAll();
            adTable.removeFirst();
            return adTable.stream()
                    .filter(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == id)
                    .findFirst()
                    .map(adRecord -> new Ad (
                            Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                            Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS]),
                            Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                    ))
                    .orElse(null);
        }
        catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAd(Ad ad) {
        File fdTmp = new File(fd.getAbsolutePath() + ".tmp");
        try (CSVReader reader = new CSVReader(new FileReader(fd));
             CSVWriter writer = new CSVWriter(new FileWriter(fdTmp))) {
            List<String[]> adTable = reader.readAll();
            String[] header = adTable.getFirst();
            adTable.removeFirst();
            adTable.replaceAll(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == ad.getId() ? updateAdRecord(adRecord, ad) : adRecord);
            adTable.addFirst(header);
            writer.writeAll(adTable);
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

    private String[] updateAdRecord(String[] adRecord, Ad ad){
        adRecord[AdAttributes.INDEX_STATUS] = String.valueOf(ad.getAdStatus().getId());
        adRecord[AdAttributes.INDEX_PRICE] = String.valueOf(ad.getPrice());
        return adRecord;
    }

    private static class AdAttributes {
        private static final int INDEX_ID = 0;
        private static final int INDEX_OWNER = 1;
        private static final int INDEX_HOME = 2;
        private static final int INDEX_ROOM = 3;
        private static final int INDEX_STATUS = 4;
        private static final int INDEX_PRICE = 5;
    }
}
