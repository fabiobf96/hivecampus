package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.dao.csv.HomeDAOCSV;
import it.hivecampuscompany.hivecampus.dao.mysql.HomeDAOMySql;
import it.hivecampuscompany.hivecampus.dao.mysql.RoomDAOMySql;
import it.hivecampuscompany.hivecampus.model.Home;

import java.awt.geom.Point2D;
import java.util.List;

public class MainSQL {
    public static void main(String[] args) {
        HomeDAO homeDAO = new HomeDAOMySql();
        HomeBean homeBean = new HomeBean("Via Enrico Fermi, 45, Roma", "Two-room apartment", 80, new Integer[]{2, 1, 3, 1}, "...");
        homeDAO.saveHome(homeBean, "elisa.gialli@gmail.com");


        /*
        RoomDAO roomDAO = new RoomDAOMySql();
        RoomBean roomBean = new RoomBean("Double", 25, new boolean[] {false, false, false, false}, "La camera è completamente arredata e luminosa.");
        roomDAO.saveRoom(4, roomBean);


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
    }
}
