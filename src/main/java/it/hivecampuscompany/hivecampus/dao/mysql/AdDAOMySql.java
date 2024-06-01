package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.AdDAO;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.model.*;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.Component;
import it.hivecampuscompany.hivecampus.model.pattern_decorator.ImageDecorator;

import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdDAOMySql implements AdDAO {

    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(AdDAOMySql.class.getName());

    @Override  //Fabio
    public List<Ad> retrieveAdsByOwner(SessionBean sessionBean, AdStatus adStatus) {
        HomeDAO homeDAO = new HomeDAOMySql();
        RoomDAO roomDAO = new RoomDAOMySql();
        AccountDAO accountDAO = new AccountDAOMySql();
        List<Ad> adList = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.RETRIEVE_ADS_BY_OWNER(adStatus))) {
            pst.setString(1, sessionBean.getEmail());
            if (adStatus != null) {
                pst.setString(2, adStatus.name());
            }
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    adList.add(new Ad(
                            rs.getInt("idAd"),
                            accountDAO.retrieveAccountInformationByEmail(sessionBean.getEmail()),
                            homeDAO.retrieveHomeByID(rs.getInt("home")),
                            roomDAO.retrieveRoomByID(rs.getInt("home"), rs.getInt("room")),
                            adStatus == null ? rs.getInt("availability") : -1,
                            adStatus == null ? rs.getInt("monthAvailability") : -1,
                            rs.getInt("price")
                    ));
                }
            }
            return adList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override   //Fabio
    public Ad retrieveAdByID(int id, boolean isDecorated) {
        HomeDAO homeDAO = new HomeDAOMySql();
        RoomDAO roomDAO = new RoomDAOMySql();
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.RETRIEVE_AD_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                rs.next();
                Component<RoomBean> room = roomDAO.retrieveRoomByID(rs.getInt("home"), rs.getInt("room"));
                if (isDecorated) {
                    byte[] image = roomDAO.getRoomImage((Room) room);
                    room = new ImageDecorator<>(room, image);
                }
                return new Ad(
                        rs.getInt("idAd"),
                        homeDAO.retrieveHomeByID(rs.getInt("home")),
                        room,
                        rs.getInt("price")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override   //Fabio
    public void updateAd(Ad ad) {
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.UPDATE_AD)) {
            pst.setInt(1, ad.getAdStatus().getId());
            pst.setInt(2, ad.getPrice());
            pst.setInt(3, ad.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Ad> retrieveAdsByFilters(FiltersBean filtersBean, Point2D uniCoordinates) {
        AccountDAO accountDAO = new AccountDAOMySql();
        HomeDAO homeDAO = new HomeDAOMySql();
        RoomDAO roomDAO = new RoomDAOMySql();

        if (uniCoordinates == null) {
            return new ArrayList<>();
        }

        List<Ad> ads = new ArrayList<>();

        List<Home> homes = homeDAO.retrieveHomesByDistance(uniCoordinates, filtersBean.getDistance());
        for (Home home : homes) {
            List<Room> rooms = roomDAO.retrieveRoomsByFilters(home.getId(), filtersBean);
            for (Room room : rooms) {
                try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_ADS_BY_FILTERS)) {
                    cstmt.setInt(1, home.getId());
                    cstmt.setInt(2, room.getIdRoom());
                    cstmt.setString(3, AdStatus.AVAILABLE.toString().toLowerCase()); // Get only available ads

                    try (ResultSet rs = cstmt.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            int price = rs.getInt(2);
                            int status = AdStatus.AVAILABLE.getId();
                            int month = rs.getInt(4);
                            Account owner = accountDAO.retrieveAccountInformationByEmail(rs.getString(7));
                            Ad ad = new Ad(id, owner, home, room, status, month, price);
                            ads.add(ad);
                        }
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "FAILED_RETRIEVE_ADS_BY_FILTERS");
                }
            }
        }
        return ads;
    }

    @Override
    public boolean publishAd(Ad ad) {
        boolean result = false;
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.PUBLISH_AD)) {
            cstmt.setInt(1, ad.getHome().getId());
            cstmt.setInt(2, ad.getRoom().getIdRoom());
            cstmt.setString(3, ad.getAdStatus().toString().toLowerCase());
            cstmt.setInt(4, ad.getAdStart().getMonth());
            cstmt.setInt(5, ad.getPrice());
            cstmt.execute();
            result = cstmt.getUpdateCount() != 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "FAILED_PUBLISH_AD");
        }
        return result;
    }
}
