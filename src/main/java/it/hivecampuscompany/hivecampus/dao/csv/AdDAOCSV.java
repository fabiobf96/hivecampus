package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.model.Room;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.ArrayList;
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
        List<String[]> adTable = readAdTable();
        adTable.removeFirst(); // Rimuove l'header
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

    }

    @Override
    public Ad retrieveAdByID(int id) {
        List<String[]> adTable = readAdTable();
        adTable.removeFirst(); // Rimuove l'header
        return adTable.stream()
                .filter(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == id)
                .findFirst()
                .map(adRecord -> new Ad(
                        Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                        Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS]),
                        Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                ))
                .orElse(null);

    }

    @Override
    public void updateAd(Ad ad) {
        File fdTmp = new File(fd.getAbsolutePath() + ".tmp");
        List<String[]> adTable = readAdTable();
        String[] header = adTable.getFirst();
        adTable.removeFirst();
        adTable.replaceAll(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == ad.getId() ? updateAdRecord(adRecord, ad) : adRecord);
        adTable.addFirst(header);
        // Sostituisci il file originale con il file temporaneo aggiornato
        try (CSVWriter writer = new CSVWriter(new FileWriter(fdTmp))) {
            writer.writeAll(adTable);
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

    private List<String[]> readAdTable() {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            return reader.readAll();
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
    public List<Ad> retrieveAdsByFilters(FiltersBean filtersBean, Point2D uniCoordinates) {
        AccountDAO accountDAO = new AccountDAOCSV();
        HomeDAO homeDAO = new HomeDAOCSV();
        RoomDAO roomDAO = new RoomDAOCSV();

        if (uniCoordinates == null) {
            LOGGER.log(Level.SEVERE, "University coordinates not found");
            System.exit(1);
        }

        List<Ad> ads = new ArrayList<>(); // Lista per accumulare gli annunci

        List<Home> homes = homeDAO.retrieveHomesByDistance(uniCoordinates, filtersBean.getDistance());
        for (Home home : homes) {
            List<Room> rooms = roomDAO.retrieveRoomsByFilters(home.getId(), filtersBean);
            for (Room room : rooms) {
                try (CSVReader reader = new CSVReader(new FileReader(fd))) {
                    List<String[]> adTable = reader.readAll();
                    adTable.removeFirst();
                    List<Ad> adsForRoom = adTable.stream()
                            .filter(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_HOME]) == home.getId() && Integer.parseInt(adRecord[AdAttributes.INDEX_ROOM]) == room.getIdRoom())
                            .map(adRecord -> new Ad(
                                    Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                                    accountDAO.retrieveAccountInformationByEmail(adRecord[AdAttributes.INDEX_OWNER]),
                                    home,
                                    room,
                                    Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS]),
                                    Integer.parseInt(adRecord[AdAttributes.INDEX_MONTH_AVAILABILITY]),
                                    Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                            )).toList();
                    ads.addAll(adsForRoom); // Aggiungi gli annunci trovati alla lista principale
                } catch (IOException | CsvException e) {
                    LOGGER.log(Level.SEVERE, "Failed to read ad table", e);
                    System.exit(3);
                }
            }
        }
        return ads; // Restituisci tutti gli annunci accumulati
    }


    private static class AdAttributes {
        private static final int INDEX_ID = 0;
        private static final int INDEX_OWNER = 1;
        private static final int INDEX_HOME = 2;
        private static final int INDEX_ROOM = 3;
        private static final int INDEX_STATUS = 4;
        private static final int INDEX_MONTH_AVAILABILITY = 5;
        private static final int INDEX_PRICE = 6;
    }
}
