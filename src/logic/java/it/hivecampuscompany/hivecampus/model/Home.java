package it.hivecampuscompany.hivecampus.model;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private int id;
    private String owner;
    private String address;
    private String city;
    private double latitude;
    private double longitude;
    private String houseType;
    private int surface;
    private int nRoom;
    private int nBathroom;
    private String description;
    private float distance;
    private List<Room> roomList;

    public boolean isInRadiusFromUniversity (FiltersBean filtersBean){
        /* il metodo da implementare prende come input il FilterBean estrapola il nome dell'università
        calcola la distanza dall'università e controlla se è nel raggio specificato
         */
        return true;
    }

    public void getRoomsByFilters (FiltersBean filtersBean){
        List<Room> tmpListRoom = new ArrayList<>();
        RoomDAO roomDAO = null;
        tmpListRoom = roomDAO.retrieveAvailableRoomsByIDHome(id);
    }

    public void getRoomByIDHome () {
        RoomDAO roomDAO = null;
        roomDAO.retrieveRoomsByIDHome(id);
    }

    public HomeBean getHomeBean(){
        /* il metodo ritorna un home bean */
        HomeBean homeBean = new HomeBean();
        return homeBean;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public void setNRoom(int nRoom) {
        this.nRoom = nRoom;
    }

    public void setNBathroom(int nBathroom) {
        this.nBathroom = nBathroom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
