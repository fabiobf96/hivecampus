package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.boundary.OpenStreetMapApiBoundary;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.RoomDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.UniversityDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenStreetMapAPIException;
import it.hivecampuscompany.hivecampus.model.Ad;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AdSearchManager {
    private final UniversityDAO universityDAO = new UniversityDAOCSV();
    private final AdDAOCSV adDAOCSV = new AdDAOCSV();

    public AdSearchManager() {
        // Default constructor
    }

    public List<AdBean> searchAdsByFilters(FiltersBean filtersBean) {

        Point2D uniCoordinates = universityDAO.getUniversityCoordinates(filtersBean.getUniversity());
        List<AdBean> adBeanList = new ArrayList<>();
        List<Ad> ads = adDAOCSV.retrieveAdsByFilters(filtersBean, uniCoordinates);
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
