package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.*;
import it.hivecampuscompany.hivecampus.boundary.OpenStreetMapApiBoundary;
import it.hivecampuscompany.hivecampus.dao.*;
import it.hivecampuscompany.hivecampus.dao.csv.*;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenStreetMapAPIException;
import it.hivecampuscompany.hivecampus.model.*;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.ImageDecorator;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdManager {
    /**
     * Searches for ads owned by the session owner.
     *
     * @param sessionBean The session bean representing the current session.
     * @param adBean      The ad bean containing search criteria.
     * @return A list of ad beans matching the search criteria.
     * @throws InvalidSessionException If the session is invalid or expired.
     * @author Fabio Barchiesi
     */

    public List<AdBean> searchAdsByOwner(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        AdDAO adDAO = new AdDAOCSV();

        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }

        List<Ad> adList = adDAO.retrieveAdsByOwner(sessionBean, adBean.getAdStatus());
        List<AdBean> adBeanList = new ArrayList<>();

        for (Ad ad : adList) {
            if (sessionBean.getClient() == SessionBean.Client.CLI) {
                adBeanList.add(ad.toBean());
            } else {
                return getDecoratedAdsByOwner(adList);
            }
        }
        return adBeanList;
    }

    public List<AdBean> getDecoratedAdsByOwner(List<Ad> adList) {
        List<AdBean> adBeanList = new ArrayList<>();
        List<Ad> decoratedAds = getDecoratedAds(adList); // Apply pattern decorator
        for (Ad ad : decoratedAds) {
            AdBean adBean = ad.toBean();
            adBeanList.add(adBean); // Convert the ad to a bean
        }
        return adBeanList;
    }

    /**
     * Method to retrieve the homes owned by the user.
     * It checks if the session is valid and then retrieves the homes owned by the user.
     *
     * @param sessionBean The session of the user
     * @return The list of homes owned by the user
     * @throws InvalidSessionException If the session is invalid
     * @author Marina Sotiropoulos
     */

    public List<HomeBean> getHomesByOwner(SessionBean sessionBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        HomeDAO homeDAO = new HomeDAOCSV();
        if (sessionManager.validSession(sessionBean)) {
            List<Home> homeList = homeDAO.retrieveHomesByOwner(sessionBean.getEmail());
            List<HomeBean> homeBeanList = new ArrayList<>();
            for (Home home : homeList) {
                homeBeanList.add(home.toBean());
            }
            return homeBeanList;
        }
        throw new InvalidSessionException();
    }

    /**
     * Method to publish an ad. It checks if the session is valid,
     * retrieves the owner of the ad and saves the home and the room objects
     * and rispettive images by calling the appropriate methods in the DAOs.
     * Then it creates the ad object and saves it in the database.
     * If the ad is published correctly, it returns true, otherwise false.
     *
     * @param sessionBean The session of the user
     * @return The list of rooms owned by the user
     * @author Marina Sotiropoulos
     */

    public boolean publishAd(SessionBean sessionBean, HomeBean homeBean, RoomBean roomBean, int price, Month adStart) {
        SessionManager sessionManager = SessionManager.getInstance();
        HomeDAO homeDAO = new HomeDAOCSV();
        RoomDAO roomDAO = new RoomDAOCSV();
        AccountDAO accountDAO = new AccountDAOCSV();
        AdDAO adDAO = new AdDAOCSV();

        if (sessionManager.validSession(sessionBean)) {

            Account owner = accountDAO.retrieveAccountInformationByEmail(sessionBean.getEmail());

            if (owner != null) {

                Home home = homeDAO.saveHome(homeBean, owner.getEmail());

                if (home != null) {
                    homeBean.setIdHome(home.getId());
                    saveHomeImage(homeBean);

                    Room room = roomDAO.saveRoom(home.getId(), roomBean);

                    if (room != null) {
                        roomBean.setIdRoom(room.getIdRoom());
                        roomBean.setIdHome(home.getId());
                        saveRoomImage(roomBean);

                        Ad ad = new Ad(0, owner, home, room, AdStatus.AVAILABLE.getId(), adStart.getMonth(), price);

                        return adDAO.publishAd(ad);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method to save the image of a home. It checks if the image is not null,
     * retrieves the image name and type, and saves the image in the database.
     *
     * @param homeBean The home object
     * @author Marina Sotiropoulos
     */

    public void saveHomeImage(HomeBean homeBean) {
        if (homeBean.getImage() != null && homeBean.getImageName() != null) {
            HomeDAO homeDAO = new HomeDAOCSV();
            String imageName = homeBean.getImageName();
            String imageType = imageName.substring(imageName.lastIndexOf('.') + 1);
            homeDAO.saveHomeImage(imageName, imageType, homeBean.getImage(), homeBean.getId());
        }
    }

    /**
     * Method to save the image of a room. It checks if the image is not null,
     * retrieves the image name and type, and saves the image in the database.
     *
     * @param roomBean The room object
     * @author Marina Sotiropoulos
     */

    public void saveRoomImage(RoomBean roomBean) {
        if (roomBean.getImage() != null) {
            RoomDAO roomDAO = new RoomDAOCSV();
            String imageName = roomBean.getImageName();
            String imageType = imageName.substring(imageName.lastIndexOf('.') + 1);
            roomDAO.saveRoomImage(imageName, imageType, roomBean.getImage(), roomBean.getIdRoom(), roomBean.getIdHome());
        }
    }

    /**
     * Method to retrieve the ads by filters. It retrieves the coordinates of the university,
     * retrieves the ads by filters and calculates the distance between the home and the university.
     * Then it creates the adBean object and adds it to the list of adBean objects.
     *
     * @param filtersBean The filters to apply to the search
     * @return The list of ads that match the filters
     * @author Marina Sotiropoulos
     */

    public List<AdBean> searchAdsByFilters(SessionBean sessionBean, FiltersBean filtersBean) {
        UniversityDAO universityDAO = new UniversityDAOCSV();
        AdDAOCSV adDAO = new AdDAOCSV();
        Point2D uniCoordinates = universityDAO.getUniversityCoordinates(filtersBean.getUniversity());

        // Calcoliamo la distanza per ciascun annuncio una sola volta
        List<Ad> ads = adDAO.retrieveAdsByFilters(filtersBean, uniCoordinates);
        List<AdBean> adBeanList = new ArrayList<>();
        Map<Integer, Double> adDistanceMap = new HashMap<>();
        for (Ad ad : ads) {
            double distance = ad.getHome().calculateDistance(uniCoordinates);
            adDistanceMap.put(ad.getId(), distance);
        }
        // Applichiamo il pattern decorator solo se il client è diverso dal CLI
        if (sessionBean.getClient() != SessionBean.Client.CLI) {
            ads = new ArrayList<>(getDecoratedAds(ads));
        }
        // Costruiamo gli oggetti AdBean utilizzando i valori calcolati precedentemente
        for (Ad ad : ads) {
            AdBean adBean = ad.toBean();
            adBean.setUniversity(filtersBean.getUniversity());
            double distance = adDistanceMap.get(ad.getId());
            adBean.setDistance(distance);
            adBeanList.add(adBean);
        }
        return adBeanList;
    }

    /**
     * Method to apply the pattern decorator to the ads.
     * It retrieves the images of the room and the home and applies the pattern decorator
     * to add the images to the room and the home.
     *
     * @param adList The list of ads
     * @return The list of ads with the images of the room and the home added to them
     * @author Marina Sotiropoulos
     */
    private List<Ad> getDecoratedAds(List<Ad> adList) {
        for (Ad ad : adList) {
            // Retrieve the images from persistence and apply pattern decorator to add the images to the room and home
            ImageDecorator<RoomBean> decoratedRoom = getDecoratedRoom(ad.getRoom());
            ImageDecorator<HomeBean> decoratedHome = getDecoratedHome(ad.getHome());

            if (decoratedRoom != null && decoratedHome != null) {
                ad.setRoom(decoratedRoom); // Set the decorated room to the ad
                ad.setHome(decoratedHome); // Set the decorated home to the ad
            }
        }
        return adList;
    }

    /**
     * Method to retrieve the images of the room.
     * It retrieves the image of the room from the persistence layer and returns it as a decorated object.
     *
     * @param room The room object
     * @return The decorated room object with the image added to it, or null if the image is not found
     * @author Marina Sotiropoulos
     */
    private ImageDecorator<RoomBean> getDecoratedRoom(Room room) {
        RoomDAO roomDAO = new RoomDAOCSV();
        byte[] roomBytes = roomDAO.getRoomImage(room);
        if (roomBytes != null) {
            return new ImageDecorator<>(room, roomBytes);
        }
        return null;
    }

    /**
     * Method to retrieve the images of the home.
     * It retrieves the image of the home from the persistence layer and returns it as a decorated object.
     *
     * @param home The home object
     * @return The decorated home object with the image added to it, or null if the image is not found
     * @author Marina Sotiropoulos
     */
    private ImageDecorator<HomeBean> getDecoratedHome(Home home) {
        HomeDAO homeDAO = new HomeDAOCSV();
        byte[] homeBytes = homeDAO.getHomeImage(home.getId());
        if (homeBytes != null) {
            return new ImageDecorator<>(home, homeBytes);
        }
        return null;
    }

    /**
     * Method to retrieve the map of the home. It checks if the session is valid,
     * retrieves the map of the home and sets it in the adBean object.
     *
     * @param sessionBean The session of the user
     * @param adBean      The ad object
     * @throws InvalidSessionException       If the session is invalid
     * @throws MockOpenStreetMapAPIException If the OpenStreetMap API is not available
     * @author Marina Sotiropoulos
     */

    public void getHomeMap(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException, MockOpenStreetMapAPIException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)) {
            throw new InvalidSessionException();
        }
        try {
            OpenStreetMapApiBoundary openStreetMapApiBoundary = new OpenStreetMapApiBoundary();
            adBean.setMap(openStreetMapApiBoundary.getMap(adBean.getHomeBean().getAddress()));
        } catch (MockOpenStreetMapAPIException e) {
            throw new MockOpenStreetMapAPIException(e.getMessage());
        }
    }

    /**
     * Method to check if the maximum number of rooms is reached.
     * It checks if the type of home allows the insertion of a new room and
     * verifies if the number of rooms in the home is less than the maximum allowed.
     *
     * @param homeBean The home object
     * @return True if the maximum number of rooms is reached, false otherwise
     * @author Marina Sotiropoulos
     */

    public boolean isMaxRoomsReached(HomeBean homeBean) {
        RoomDAO roomDAO = new RoomDAOCSV();
        int roomsCount = (int) roomDAO.getRoomsAlreadyPresent(homeBean.getId());
        return roomsCount >= homeBean.getNRooms();
    }
}