package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.model.Room;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.Component;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.Decorator;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.ImageDecorator;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdDAOCSV implements AdDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(AdDAOCSV.class.getName());
    Properties languageProperties = LanguageLoader.getLanguageProperties();

    public AdDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("AD_PATH"));
        } catch (IOException e) {
            Properties languageProperties = LanguageLoader.getLanguageProperties();
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_LOADING_CSV_PROPERTIES"), e);
            System.exit(1);
        }
    }

    // Modificato aggiunto accountDAO per il recupero delle info owner in AD
    @Override
    public List<Ad> retrieveAdsByOwner(SessionBean sessionBean, AdStatus adStatus) {
        HomeDAO homeDAO = new HomeDAOCSV();
        RoomDAO roomDAO = new RoomDAOCSV();
        AccountDAO accountDAO = new AccountDAOCSV();
        List<String[]> adTable = CSVUtility.readAll(fd);
        adTable.removeFirst(); // Rimuove l'header
        return adTable.stream()
                // Ensure to separate the conditions correctly and parse the status value appropriately
                .filter(adRecord -> adRecord[AdAttributes.INDEX_OWNER].equals(sessionBean.getEmail()))
                .filter(adRecord -> (adStatus != null) == (AdStatus.fromInt(Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS])) == adStatus))
                .map(adRecord ->
                        // Pass the actual values from adRecord to retrieveHomeByID and retrieveRoomByID
                        new Ad(
                                Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                                accountDAO.retrieveAccountInformationByEmail(adRecord[AdAttributes.INDEX_OWNER]),
                                homeDAO.retrieveHomeByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME])),
                                roomDAO.retrieveRoomByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME]), Integer.parseInt(adRecord[AdAttributes.INDEX_ROOM])),
                                adStatus == null ? Integer.parseInt(adRecord[AdAttributes.INDEX_STATUS]) : -1,
                                adStatus == null ? Integer.parseInt(adRecord[AdAttributes.INDEX_MONTH_AVAILABILITY]) : -1,
                                Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                        )
                ).toList();

    }

    @Override
    public Ad retrieveAdByID(int id, boolean isDecorated) {
        RoomDAO roomDAO = new RoomDAOCSV();
        HomeDAO homeDAO = new HomeDAOCSV();
        List<String[]> adTable = CSVUtility.readAll(fd);
        adTable.removeFirst(); // Rimuove l'header
        return adTable.stream()
                .filter(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == id)
                .findFirst()
                .map(adRecord -> {
                    Component<RoomBean> room = roomDAO.retrieveRoomByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME]), Integer.parseInt(adRecord[AdAttributes.INDEX_ROOM]));
                    if (isDecorated) {
                        byte[] image = roomDAO.getRoomImage((Room) room);
                        room = new ImageDecorator<>(room, image);
                    }
                    return new Ad(
                            Integer.parseInt(adRecord[AdAttributes.INDEX_ID]),
                            homeDAO.retrieveHomeByID(Integer.parseInt(adRecord[AdAttributes.INDEX_HOME])),
                            room,
                            Integer.parseInt(adRecord[AdAttributes.INDEX_PRICE])
                    );
                })
                .orElse(null);

    }

    @Override
    public void updateAd(Ad ad) {
        List<String[]> adTable = CSVUtility.readAll(fd);
        String[] header = adTable.removeFirst();
        adTable.replaceAll(adRecord -> Integer.parseInt(adRecord[AdAttributes.INDEX_ID]) == ad.getId() ? updateAdRecord(adRecord, ad) : adRecord);
        CSVUtility.updateFile(fd, header, adTable);
    }

    private String[] updateAdRecord(String[] adRecord, Ad ad) {
        adRecord[AdAttributes.INDEX_STATUS] = String.valueOf(ad.getAdStatus().getId());
        adRecord[AdAttributes.INDEX_PRICE] = String.valueOf(ad.getPrice());
        return adRecord;
    }

    @Override
    public List<Ad> retrieveAdsByFilters(FiltersBean filtersBean, Point2D uniCoordinates) {
        AccountDAO accountDAO = new AccountDAOCSV();
        HomeDAO homeDAO = new HomeDAOCSV();
        RoomDAO roomDAO = new RoomDAOCSV();

        if (uniCoordinates == null) {
            return new ArrayList<>();
        }

        List<Ad> ads = new ArrayList<>();

        // Retrieve homes within the specified distance from the university
        List<Home> homes = homeDAO.retrieveHomesByDistance(uniCoordinates, filtersBean.getDistance());
        for (Home home : homes) {
            // Retrieve rooms that match the filters for each home
            List<Room> rooms = roomDAO.retrieveRoomsByFilters(home.getId(), filtersBean);
            for (Room room : rooms) {
                List<String[]> adTable = CSVUtility.readAll(fd);
                adTable.removeFirst();
                // Filter ads that match the home and room IDs
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
                ads.addAll(adsForRoom);
            }
        }
        // Return the list of available ads
        return filterAvailableAds(ads);
    }

    @Override
    public boolean publishAd(Ad ad) {
        // Retrieve the last ID from the ad table
        int lastID = CSVUtility.findLastRowIndex(fd);
        // Create a new ad record with the last ID incremented by 1
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] adRecord = new String[7];
            adRecord[AdAttributes.INDEX_ID] = String.valueOf(lastID + 1);
            adRecord[AdAttributes.INDEX_OWNER] = ad.getOwner().getEmail();
            adRecord[AdAttributes.INDEX_HOME] = String.valueOf(ad.getHome().getId());
            adRecord[AdAttributes.INDEX_ROOM] = String.valueOf(ad.getRoom().getIdRoom());
            adRecord[AdAttributes.INDEX_STATUS] = String.valueOf(ad.getAdStatus().getId());
            adRecord[AdAttributes.INDEX_MONTH_AVAILABILITY] = String.valueOf(ad.getAdStart().getMonth());
            adRecord[AdAttributes.INDEX_PRICE] = String.valueOf(ad.getPrice());
            // Write the ad record to the ad table
            writer.writeNext(adRecord);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, languageProperties.getProperty("FAILED_WRITE_FILE"), e);
            return false;
        }
    }

    private List<Ad> filterAvailableAds(List<Ad> ads) {
        // Create a new list of ads that have the status AVAILABLE to it.
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
