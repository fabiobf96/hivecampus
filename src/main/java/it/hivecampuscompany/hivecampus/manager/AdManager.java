package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.Ad;
import it.hivecampuscompany.hivecampus.model.AdStatus;

import java.util.ArrayList;
import java.util.List;

public class AdManager {
    public List<AdBean> searchAvailableAds(SessionBean sessionBean) throws InvalidSessionException {
        SessionManager sessionManager = SessionManager.getInstance();
        AdDAO adDAO = new AdDAOCSV();
        if (sessionManager.validSession(sessionBean)) {
            List<Ad> adList = adDAO.retrieveAdsByOwner(sessionBean, AdStatus.AVAILABLE);
            List<AdBean> adBeanList = new ArrayList<>();
            for (Ad ad : adList){
                adBeanList.add(ad.toBasicBean());
            }
            return adBeanList;
        }
        throw new InvalidSessionException();
    }

    public List<AdBean> searchProcessingAds(SessionBean sessionBean) throws InvalidSessionException{
        SessionManager sessionManager = SessionManager.getInstance();
        AdDAO adDAO = new AdDAOCSV();
        if (sessionManager.validSession(sessionBean)) {
            List<Ad> adList = adDAO.retrieveAdsByOwner(sessionBean, AdStatus.PROCESSING);
            List<AdBean> adBeanList = new ArrayList<>();
            for (Ad ad : adList){
                adBeanList.add(ad.toBasicBean());
            }
            return adBeanList;
        }
        throw new InvalidSessionException();
    }
}
