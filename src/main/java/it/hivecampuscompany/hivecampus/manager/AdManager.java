package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.RoomDAOCSV;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.*;

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
                Home home = homeDAO.saveHome(homeBean, owner.getEmail());
                // Controllo se la casa è stata salvata con successo
                if (home != null) {
                    // Controllo se la stanza è stata salvata con successo
                    Room room = roomDAO.saveRoom(home.getId(), roomBean);
                    if (room != null) {
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


}
