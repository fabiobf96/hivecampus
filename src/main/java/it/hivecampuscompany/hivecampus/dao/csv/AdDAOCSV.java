package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.model.Room;

import java.awt.geom.Point2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdDAOCSV implements AdDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(AdDAOCSV.class.getName());

    public AdDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
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
        List<String[]> adTable = CSVUtility.readAll(fd);
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
        RoomDAO roomDAO = new RoomDAOCSV();
        HomeDAO homeDAO = new HomeDAOCSV();
        List<String[]> adTable = CSVUtility.readAll(fd);
        adTable.removeFirst(); // Rimuove l'header
        return adTable.stream()
                .filter(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == id)
                .findFirst()
                .map(adRecord -> new Ad(
                        Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                        homeDAO.retrieveHomeByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME])),
                        roomDAO.retrieveRoomByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME]), Integer.parseInt(adRecord[AdAttributes.INDEX_ROOM])),
                        Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                ))
                .orElse(null);

    }

    @Override
    public void updateAd(Ad ad) {
        File fdTmp = new File(fd.getAbsolutePath() + ".tmp");
        List<String[]> adTable = CSVUtility.readAll(fd);
        String[] header = adTable.getFirst();
        adTable.removeFirst();
        adTable.replaceAll(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == ad.getId() ? updateAdRecord(adRecord, ad) : adRecord);
        adTable.addFirst(header);
        // scrivi la tabella sul file temporaneo
        try (CSVWriter writer = new CSVWriter(new FileWriter(fdTmp))) {
            writer.writeAll(adTable);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("failure to write to temporary file %s", fdTmp), e);
            System.exit(4);
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

    public List<Ad> retrieveOwnerAds(SessionBean sessionBean) {
        HomeDAO homeDAO = new HomeDAOCSV();
        RoomDAO roomDAO = new RoomDAOCSV();
        List<String[]> adTable = CSVUtility.readAll(fd);
        adTable.removeFirst(); // Rimuove l'header
        return adTable.stream()
                .filter(adRecord -> adRecord[AdAttributes.INDEX_OWNER].equals(sessionBean.getEmail()))
                .map(adRecord -> new Ad(
                        Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                        homeDAO.retrieveHomeByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME])),
                        roomDAO.retrieveRoomByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME]), Integer.parseInt(adRecord[AdAttributes.INDEX_ROOM])),
                        Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS]),
                        Integer.parseInt(adRecord[AdAttributes.INDEX_MONTH_AVAILABILITY]),
                        Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                )).toList();
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
                List<String[]> adTable = CSVUtility.readAll(fd);
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
            }
        }
        // Filtra gli annunci disponibili
        return filterAvailableAds(ads); // Restituisci tutti gli annunci disponibili
    }

    @Override
    public boolean publishAd(Ad ad) {
        int lastID = CSVUtility.findLastRowIndex(fd);
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] adRecord = new String[7];
            adRecord[AdAttributes.INDEX_ID] = String.valueOf(lastID);
            adRecord[AdAttributes.INDEX_OWNER] = ad.getOwner().getEmail();
            adRecord[AdAttributes.INDEX_HOME] = String.valueOf(ad.getHome().getId());
            adRecord[AdAttributes.INDEX_ROOM] = String.valueOf(ad.getRoom().getIdRoom());
            adRecord[AdAttributes.INDEX_STATUS] = String.valueOf(ad.getAdStatus().getId());
            adRecord[AdAttributes.INDEX_MONTH_AVAILABILITY] = String.valueOf(ad.getAdStart().getMonth());
            adRecord[AdAttributes.INDEX_PRICE] = String.valueOf(ad.getPrice());
            writer.writeNext(adRecord);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to write to file", e);
            return false;
        }
    }

    private List<Ad> filterAvailableAds(List<Ad> ads) {
        List<Ad> availableAds = new ArrayList<>();
        for (Ad ad : ads) {
            if (ad.getAdStatus() == AdStatus.AVAILABLE) {
                availableAds.add(ad);
            }
        }
        return availableAds;
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
