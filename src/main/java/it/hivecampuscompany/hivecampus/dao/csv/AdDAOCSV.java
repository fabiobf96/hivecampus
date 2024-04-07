package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdDAOCSV implements AdDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(AdDAOCSV.class.getName());
    private Properties properties;
    private static final String ERR_ACCESS = "ERR_ACCESS";
    private static final String ERR_PARSER = "ERR_PARSER";
    public AdDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("AD_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
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
    public Ad retrieveAdByID(int id) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> adTable = reader.readAll();
            adTable.removeFirst();
            return adTable.stream()
                    .filter(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == id)
                    .findFirst()
                    .map(adRecord -> new Ad(
                            Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                            Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS]),
                            Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
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

    private String[] updateAdRecord(String[] adRecord, Ad ad) {
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
