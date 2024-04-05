package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.RoomDAOCSV;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomSearchManager {

    private final HomeDAOCSV homeDAOCSV = new HomeDAOCSV();
    private final RoomDAOCSV roomDAOCSV = new RoomDAOCSV();

    public RoomSearchManager() {
        // Default constructor
    }

    public List<HomeBean> searchHomesByFilters(FiltersBean filtersBean){
        List<HomeBean> homeBeanList = new ArrayList<>();
        List<Home> homes = homeDAOCSV.retrieveHomesByDistance(filtersBean.getUniversity(), filtersBean.getDistance());
        for (Home home : homes) {
            HomeBean homeBean = new HomeBean(home);
            homeBeanList.add(homeBean);
        }
        return homeBeanList;
    }

    public List<RoomBean> searchRoomsByFilters(HomeBean homeBean, FiltersBean filtersBean) {
        List<RoomBean> roomBeanList = new ArrayList<>();
        List<Room> rooms = roomDAOCSV.retrieveRoomsByFilters(homeBean.getIdHome(), filtersBean);
        for (Room room : rooms) {
            RoomBean roomBean = new RoomBean(room);
            roomBeanList.add(roomBean);
        }
        return roomBeanList;
    }
}
