package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.*;
import it.hivecampuscompany.hivecampus.boundary.OpenStreetMapApiBoundary;
import it.hivecampuscompany.hivecampus.dao.*;
import it.hivecampuscompany.hivecampus.dao.csv.*;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenStreetMapAPIException;
import it.hivecampuscompany.hivecampus.model.*;
import it.hivecampuscompany.hivecampus.view.utility.ImageSaverCSV;

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
            for (Ad ad : adList){
                adBeanList.add(ad.toBean());
            }
            return adBeanList;
        }
        throw new InvalidSessionException();
    }

    public List<AdBean> getDecoratedAdsByOwner(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        List<AdBean> adBeanList = searchAdsByOwner(sessionBean, adBean);
        RoomDAO roomDAO = new RoomDAOCSV();
        HomeDAO homeDAO = new HomeDAOCSV();
        for (AdBean adBeanWithImage : adBeanList){
            byte[] roomBytes = roomDAO.getRoomImage(adBeanWithImage.getRoomBean().getIdRoom(), adBeanWithImage.getHomeBean().getId());
            byte[] homeBytes = homeDAO.getHomeImage(adBeanWithImage.getHomeBean().getId());
            if (roomBytes != null && homeBytes != null) {
                adBeanWithImage.getRoomBean().setImage(roomBytes);
                adBeanWithImage.getHomeBean().setImage(homeBytes);
            }
        }
        return adBeanList;
    }

    public List<HomeBean> getHomesByOwner(SessionBean sessionBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        HomeDAO homeDAO = new HomeDAOCSV();
        if (sessionManager.validSession(sessionBean)) {
            List<Home> homeList = homeDAO.retrieveHomesByOwner(sessionBean.getEmail());
            List<HomeBean> homeBeanList = new ArrayList<>();
            for (Home home : homeList){
                homeBeanList.add(home.toBean());
            }
            return homeBeanList;
        }
        throw new InvalidSessionException();
    }

    public boolean publishAd(SessionBean sessionBean, HomeBean homeBean, RoomBean roomBean, int price, AdStart adStart) {
        SessionManager sessionManager = SessionManager.getInstance();
        HomeDAO homeDAO = new HomeDAOCSV();
        RoomDAO roomDAO = new RoomDAOCSV();
        AccountDAO accountDAO = new AccountDAOCSV();
        AdDAO adDAO = new AdDAOCSV();

        if (sessionManager.validSession(sessionBean)) {
            // Recupero l'account proprietario
            Account owner = accountDAO.retrieveAccountInformationByEmail(sessionBean.getEmail());

            if (owner != null) {
                // Salvo la casa
                Home home = homeDAO.saveHome(homeBean, owner.getEmail());

                // Controllo se la casa è stata salvata con successo
                if (home != null) {
                    homeBean.setIdHome(home.getId());
                    saveHomeImage(homeBean);
                    // Salvo la stanza
                    Room room = roomDAO.saveRoom(home.getId(), roomBean);

                    // Controllo se la stanza è stata salvata con successo
                    if (room != null) {
                        roomBean.setIdRoom(room.getIdRoom());
                        roomBean.setIdHome(home.getId());
                        saveRoomImage(roomBean);
                        // Creo l'annuncio
                        Ad ad = new Ad(0, owner, home, room, AdStatus.AVAILABLE.getId(), adStart.getMonth(), price);
                        // Pubblico l'annuncio
                        return adDAO.publishAd(ad);
                    }
                }
            }
        }
        // Se una delle operazioni fallisce o la sessione non è valida, restituisco false
        return false;
    }

    public void saveHomeImage(HomeBean homeBean) {
        if (homeBean.getImage() != null) {
            ImageSaverCSV imageSaver = new ImageSaverCSV();
            String imageName = homeBean.getImageName();
            String imageType = imageName.substring(imageName.lastIndexOf('.') + 1);
            imageSaver.saveHomeImage(imageName, imageType, homeBean.getImage(), homeBean.getId());
        }
    }

    public void saveRoomImage(RoomBean roomBean) {
        if (roomBean.getImage() != null) {
            ImageSaverCSV imageSaver = new ImageSaverCSV();
            String imageName = roomBean.getImageName();
            String imageType = imageName.substring(imageName.lastIndexOf('.') + 1);
            imageSaver.saveRoomImage(imageName, imageType, roomBean.getImage(), roomBean.getIdRoom(), roomBean.getIdHome());
        }
    }

    public List<AdBean> searchAdsByFilters(FiltersBean filtersBean) {
        UniversityDAO universityDAO = new UniversityDAOCSV();
        AdDAOCSV adDAO = new AdDAOCSV();
        Point2D uniCoordinates = universityDAO.getUniversityCoordinates(filtersBean.getUniversity());
        List<AdBean> adBeanList = new ArrayList<>();
        List<Ad> ads = adDAO.retrieveAdsByFilters(filtersBean, uniCoordinates);
        for (Ad ad : ads) {
            double distance = ad.getHome().calculateDistance(uniCoordinates);
            AdBean adBean = new AdBean(ad,filtersBean.getUniversity(), distance);
            adBeanList.add(adBean);
        }
        return adBeanList;
    }

    public List<AdBean> searchDecoratedAdsByFilters(FiltersBean filtersBean) {
        List<AdBean> adBeanList = searchAdsByFilters(filtersBean);
        RoomDAO roomDAO = new RoomDAOCSV();
        HomeDAO homeDAO = new HomeDAOCSV();
        for (AdBean adBean : adBeanList){
            byte[] roomBytes = roomDAO.getRoomImage(adBean.getRoomBean().getIdRoom(), adBean.getHomeBean().getId());
            byte[] homeBytes = homeDAO.getHomeImage(adBean.getHomeBean().getId());
            if (roomBytes != null && homeBytes != null) {
                adBean.getRoomBean().setImage(roomBytes);
                adBean.getHomeBean().setImage(homeBytes);
            }
        }
        return adBeanList;
    }

    public void getHomeMap(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException, MockOpenStreetMapAPIException {
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.validSession(sessionBean)){
            throw new InvalidSessionException();
        }
        try {
            OpenStreetMapApiBoundary openStreetMapApiBoundary = new OpenStreetMapApiBoundary();
            adBean.setMap(openStreetMapApiBoundary.getMap(adBean.getHomeBean().getAddress()));
        } catch (MockOpenStreetMapAPIException e) {
            throw new MockOpenStreetMapAPIException(e.getMessage());
        }
    }
}
