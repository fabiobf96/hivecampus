package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.*;
import it.hivecampuscompany.hivecampus.boundary.OpenStreetMapApiBoundary;
import it.hivecampuscompany.hivecampus.dao.*;
import it.hivecampuscompany.hivecampus.dao.csv.*;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenStreetMapAPIException;
import it.hivecampuscompany.hivecampus.model.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AdManager {
    public List<AdBean> searchAdsByOwner(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        AdDAO adDAO = new AdDAOCSV();
        if (sessionManager.validSession(sessionBean)) {
            List<Ad> adList = adDAO.retrieveAdsByOwner(sessionBean, adBean.getAdStatus());
            List<AdBean> adBeanList = new ArrayList<>();
            for (Ad ad : adList) {
                if (sessionBean.getClient() == SessionBean.Client.CLI) {
                    adBeanList.add(ad.toBean());
                } else {
                    adBeanList.add(ad.toBeanWithImage());
                }
            }
            return adBeanList;
        }
        throw new InvalidSessionException();
    }

    /**
     * Method to retrieve the decorated ads by owner. It retrieves the ads by owner
     * and then retrieves the images of the rooms and homes of the ads.
     *
     * @param sessionBean The session of the user
     * @param adBean The ad object
     * @return The list of decorated ads owned by the user
     * @throws InvalidSessionException If the session is invalid
     */

    public List<AdBean> getDecoratedAdsByOwner(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        List<AdBean> adBeanList = searchAdsByOwner(sessionBean, adBean);
        return getAdBeansWithImage(adBeanList);
    }

    /**
     * Method to retrieve the homes owned by the user.
     * It checks if the session is valid and then retrieves the homes owned by the user.
     *
     * @param sessionBean The session of the user
     * @return The list of homes owned by the user
     * @throws InvalidSessionException If the session is invalid
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
     */

    public void saveHomeImage(HomeBean homeBean) {
        if (homeBean.getImage() != null) {
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
     */

    public List<AdBean> searchAdsByFilters(FiltersBean filtersBean) {
        UniversityDAO universityDAO = new UniversityDAOCSV();
        AdDAOCSV adDAO = new AdDAOCSV();
        Point2D uniCoordinates = universityDAO.getUniversityCoordinates(filtersBean.getUniversity());
        List<AdBean> adBeanList = new ArrayList<>();
        List<Ad> ads = adDAO.retrieveAdsByFilters(filtersBean, uniCoordinates);
        for (Ad ad : ads) {
            double distance = ad.getHome().calculateDistance(uniCoordinates);
            AdBean adBean = new AdBean(ad, filtersBean.getUniversity(), distance);
            adBeanList.add(adBean);
        }
        return adBeanList;
    }

    /**
     * Method to retrieve the decorated ads by filters. It retrieves the ads by filters
     * and then retrieves the images of the rooms and homes of the ads.
     *
     * @param filtersBean The filters to apply to the search
     * @return The list of decorated ads that match the filters
     */

    public List<AdBean> searchDecoratedAdsByFilters(FiltersBean filtersBean) {
        List<AdBean> adBeanList = searchAdsByFilters(filtersBean);
        return getAdBeansWithImage(adBeanList);
    }

    /**
     * Method to retrieve the decorated ads by owner. It retrieves the ads by owner
     * and then retrieves the images of the rooms and homes of the ads.
     *
     * @param adBeanList The list of ads to decorate
     * @return The list of decorated ads
     */

    private List<AdBean> getAdBeansWithImage(List<AdBean> adBeanList) {
        RoomDAO roomDAO = new RoomDAOCSV();
        HomeDAO homeDAO = new HomeDAOCSV();
        for (AdBean adBean : adBeanList) {
            byte[] roomBytes = roomDAO.getRoomImage(adBean.getRoomBean().getIdRoom(), adBean.getHomeBean().getId());
            byte[] homeBytes = homeDAO.getHomeImage(adBean.getHomeBean().getId());
            if (roomBytes != null && homeBytes != null) {
                adBean.getRoomBean().setImage(roomBytes);
                adBean.getHomeBean().setImage(homeBytes);
            }
        }
        return adBeanList;
    }

    /**
     * Method to retrieve the map of the home. It checks if the session is valid,
     * retrieves the map of the home and sets it in the adBean object.
     *
     * @param sessionBean The session of the user
     * @param adBean The ad object
     * @throws InvalidSessionException If the session is invalid
     * @throws MockOpenStreetMapAPIException If the OpenStreetMap API is not available
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
     */

    public boolean isMaxRoomsReached(HomeBean homeBean) {
        RoomDAO roomDAO = new RoomDAOCSV();
        int roomsCount = (int) roomDAO.getRoomsAlreadyPresent(homeBean.getId());
        return roomsCount >= homeBean.getNRooms();
    }
}
