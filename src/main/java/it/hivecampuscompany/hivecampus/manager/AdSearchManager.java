package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.dao.UniversityDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.UniversityDAOCSV;
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
}
