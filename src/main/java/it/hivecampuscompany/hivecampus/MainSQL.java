package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.bean.*;
import it.hivecampuscompany.hivecampus.dao.*;
import it.hivecampuscompany.hivecampus.dao.csv.AdDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.dao.csv.UniversityDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.*;
import it.hivecampuscompany.hivecampus.model.*;

import java.awt.geom.Point2D;
import java.util.List;

public class MainSQL {
    public static void main(String[] args) {
        HomeDAO homeDAO = new HomeDAOMySql();
        RoomDAO roomDAO = new RoomDAOMySql();

        /*
        HomeBean homeBean = new HomeBean("Via Enrico Fermi, 45, Roma", "Two-room apartment", 80, new Integer[]{2, 1, 3, 1}, "...");
        homeDAO.saveHome(homeBean, "elisa.gialli@gmail.com");


        // "1","5","Double","20","0","0","1","1","La camera matrimoniale ha pareti dipinte di un color avorio. Il letto è situato al centro della stanza, accanto ad alla finestra scorrevole che inonda la stanza di luce naturale. Di fronte al letto, una spaziosa scrivania con una sedia ergonomica crea un angolo dedicato allo studio o al lavoro. Completa l'arredamento un ampio armadio a doppia anta."
        RoomBean roomBean = new RoomBean("Double", 20, new boolean[] {false, false, true, true}, "La camera matrimoniale ha pareti dipinte di un color avorio. Il letto è situato al centro della stanza, accanto ad alla finestra scorrevole che inonda la stanza di luce naturale. Di fronte al letto, una spaziosa scrivania con una sedia ergonomica crea un angolo dedicato allo studio o al lavoro. Completa l'arredamento un ampio armadio a doppia anta.");
        roomDAO.saveRoom(5, roomBean);

         */

        /*
        // retrieveHomesByDistance(12.628676196631913, 41.85375195050985, 15);

        System.out.println("Lato CSV");
        HomeDAO homeDAO = new HomeDAOCSV();
        List<Home> homes = homeDAO.retrieveHomesByDistance(new Point2D.Double(12.628676196631913, 41.85375195050985), 5);

        for (Home home : homes) {
            System.out.println(home + " Distance: " + home.calculateDistance(new Point2D.Double(12.628676196631913, 41.85375195050985)) + " km");
        }

        System.out.println("\nLato MySQL");
        homeDAO = new HomeDAOMySql();
        homes = homeDAO.retrieveHomesByDistance(new Point2D.Double(12.628676196631913, 41.85375195050985), 5);

        for (Home home : homes) {
            System.out.println(home + " Distance: " + home.calculateDistance(new Point2D.Double(12.628676196631913, 41.85375195050985)) + " km");
        }
        */

        //homeDAO.saveHomeImage("pinco.jpg", "jpg", new byte[]{1, 2, 3, 4}, 1);

        // String university, Float distance, Integer maxPrice, Boolean privateBathroom, Boolean balcony, Boolean conditioner, Boolean tvConnection


        //FiltersBean filtersBean = new FiltersBean("tor vergata", 15F, 1000, false, false, false, false);
        /*
        List<Room> rooms = roomDAO.retrieveRoomsByFilters(1, filtersBean);
        for (Room room : rooms) {
            System.out.println(room.getIdRoom() + " "  + room.getTypeRoom() + " " + room.getSurface() + " " + room.getDescription());
        }
        */

        //roomDAO.saveRoomImage("doubleCaffeRoom.jpg", "jpg", new byte[]{1, 2, 3, 4}, 1, 1);
        //System.out.println(roomDAO.getRoomsAlreadyPresent(1));

        /*
        University: tor vergata
        Distance: 15.0
        Max Price: 1000
        Private Bathroom: false
        Balcony: false
        Conditioner: false
        TV Connection: false
         */


        //FiltersBean filtersBean = new FiltersBean("tor vergata", 15F, 1000, false, false, false, false);
        /*
        UniversityDAO universityDAO = new UniversityDAOMySql();
        Point2D coordinates = universityDAO.getUniversityCoordinates("tor vergata");
        System.out.println(coordinates.getX() + " " + coordinates.getY());


        System.out.println("Lato CSV");
        AdDAO adDAOCSV = new AdDAOCSV();
        List<Ad> adsCSV = adDAOCSV.retrieveAdsByFilters(filtersBean, coordinates);
        if (adsCSV.isEmpty()) {
            System.out.println("Nessun annuncio trovato");
        }
        for (Ad ad : adsCSV) {
            System.out.println(ad.getId() + " " + ad.getHome().getAddress() + " " + ad.getRoom().getTypeRoom() + " " + ad.getPrice() + " " + ad.getAdStatus() + " " + ad.getAdStart() + " " + ad.getOwner().getEmail());
        }

        System.out.println("\nLato MySQL");
        AdDAO adDAO = new AdDAOMySql();
        List<Ad> ads = adDAO.retrieveAdsByFilters(filtersBean, coordinates);
        if (ads.isEmpty()) {
            System.out.println("Nessun annuncio trovato");
        }
        for (Ad ad : ads) {
            System.out.println(ad.getId() + " " + ad.getHome().getAddress() + " " + ad.getRoom().getTypeRoom() + " " + ad.getPrice() + " " + ad.getAdStatus() + " " + ad.getAdStart() + " " + ad.getOwner().getEmail());
        }
        */

        /*
        Home home = new Home(1, new Point2D.Double(0,0), "Via Pinco Panco, 2, Roma", "Two-room apartment", 80, "...", new Integer[]{2, 1, 3, 1});
        Room room = new Room (1, 10, 20, "single", new boolean[] {false, false, true, true}, "...");
        Ad ad = new Ad(0, null, home, room, 1, 9, 0);

        AccountDAO accountDAO = new AccountDAOMySql();
        Account account = accountDAO.retrieveAccountInformationByEmail("mara.arancio@gmail.com");

        // AdDAO adDAO = new AdDAOMySql();
        // System.out.println(adDAO.publishAd(ad));

        LeaseRequest leaseRequest = new LeaseRequest(ad, account, 9, 6, LeaseRequestStatus.PROCESSING.getId(), "message");

        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOMySql();
        leaseRequestDAO.saveLeaseRequest(leaseRequest);
        */

        /*
        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOMySql();
        //LeaseRequestBean reqBean = new LeaseRequestBean(1, null, null, 9, 6, "message", LeaseRequestStatus.PROCESSING);
        //leaseRequestDAO.deleteLeaseRequest(reqBean);
        User user = new User("marta.rossi@gmail.com", "pippo", "tenant");
        Session session = new Session(user);
        SessionBean sessionBean = new SessionBean(session);

        List<LeaseRequest> requests = leaseRequestDAO.retrieveLeaseRequestsByTenant(sessionBean);
        for (LeaseRequest request : requests) {
            System.out.println("ID: " + request.getID() + " Tenant: " + request.getTenant().getEmail() + " Month: " + request.getLeaseMonth().getMonth() + " Duration: " + request.getDuration().getPermanence() + " Message: " + request.getMessage());
        }

         */
        User user = new User("marta.rossi@gmail.com", "pippo", "tenant");
        Session session = new Session(user);
        SessionBean sessionBean = new SessionBean(session);

        System.out.println("Lato CSV");
        LeaseRequestDAO leaseRequestDAO = new LeaseRequestDAOCSV();
//LeaseRequestBean reqBean = new LeaseRequestBean(1, null, null, 9, 6, "message", LeaseRequestStatus.PROCESSING);
//leaseRequestDAO.deleteLeaseRequest(reqBean);

        List<LeaseRequest> requests = leaseRequestDAO.retrieveLeaseRequestsByTenant(sessionBean, false);
        for (LeaseRequest request : requests) {
            System.out.println("ID: " + request.getID() + " Tenant: " + request.getTenant().getEmail() + " Month: " + request.getLeaseMonth().getMonth() + " Duration: " + request.getDuration().getPermanence() + " Message: " + request.getMessage());
        }


        System.out.println("Lato MySql");
        LeaseRequestDAO reqSql = new LeaseRequestDAOMySql();
        List<LeaseRequest> requests1 = reqSql.retrieveLeaseRequestsByTenant(sessionBean, false);
        for (LeaseRequest request : requests1) {
            System.out.println("ID: " + request.getID() + " Tenant: " + request.getTenant().getEmail() + " Month: " + request.getLeaseMonth().getMonth() + " Duration: " + request.getDuration().getPermanence() + " Message: " + request.getMessage());
        }
        }

}
