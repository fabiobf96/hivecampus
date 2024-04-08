package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.model.Ad;
import java.util.ArrayList;
import java.util.List;

public class RoomSearchManager {
    private final AdDAOCSV adDAOCSV = new AdDAOCSV();

    public RoomSearchManager() {
        // Default constructor
    }

    public List<AdBean> searchAdsByFilters(FiltersBean filtersBean) {
        List<AdBean> adBeanList = new ArrayList<>();
        List<Ad> ads = adDAOCSV.retrieveAdsByFilters(filtersBean);
        for (Ad ad : ads) {
            AdBean adBean = new AdBean(ad);
            adBeanList.add(adBean);
            System.out.println(adBean);
        }
        return adBeanList;
    }
}
