package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.boundary.OpenStreetMapApiBoundary;
import it.hivecampuscompany.hivecampus.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.model.Home;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.state.utility.Utility;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeDAOMySql implements HomeDAO {

    private final Connection connection = ConnectionManager.getInstance().getConnection();
    private static final Logger LOGGER = Logger.getLogger(HomeDAOMySql.class.getName());
    private final Properties properties = LanguageLoader.getLanguageProperties();

    @Override
    public Home retrieveHomeByID(int id) {
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.RETRIEVE_HOME_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()){
                rs.next();
                return new Home(
                        rs.getInt("idHome"),
                        new Point2D.Double(rs.getDouble("longitude"), rs.getDouble("latitude")),
                        rs.getString("address"),
                        rs.getString("homeType"),
                        rs.getInt("homeSurface"),
                        rs.getString("homeDescription")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_HOME_BY_ID"));
            return null;
        }
    }

    @Override
    public List<Home> retrieveHomesByDistance(Point2D unicoordinates, double distance) {
        List<Home> homes =  new ArrayList<>();

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_HOMES)) {
            if (cstmt.execute()) {  // execute the stored procedure and check if there is a result set
                try (ResultSet res = cstmt.getResultSet()) {
                    while (res.next()) {
                        if (Utility.calculateDistance(res.getDouble(3), res.getDouble(4), unicoordinates.getX(), unicoordinates.getY()) <= distance) {
                            Home home = fillHome(res);
                            homes.add(home);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_HOMES_BY_DISTANCE"));
        }
        return homes;
    }

    @Override
    public List<Home> retrieveHomesByOwner(String ownerEmail) {
        List<Home> homes =  new ArrayList<>();
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_HOMES_BY_OWNER)) {
            cstmt.setString(1, ownerEmail);

            if (cstmt.execute()) {  // execute the stored procedure and check if there is a result set
                try (ResultSet res = cstmt.getResultSet()) {

                    while (res.next()) {
                        Home home = fillHome(res);
                        homes.add(home);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_HOMES_BY_OWNER"));
        }
        return homes;
    }

    @Override
    public Home saveHome(HomeBean homeBean, String ownerEmail) {
        // Check if the home already exists
        int existingHomeId = isHomeAlreadyExists(homeBean);
        if (existingHomeId != -1) {
            return retrieveHomeByID(existingHomeId);
        }

        Point2D coordinates;
        try {
            coordinates = OpenStreetMapApiBoundary.getCoordinates(homeBean.getAddress());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,properties.getProperty("FAILED_RETRIEVE_COORDINATES"));
            return null;
        }

        int newHomeId;

        // Call the stored procedure to save the new home
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.SAVE_HOME)){
            cstmt.setString(1, homeBean.getAddress());
            cstmt.setDouble(2, coordinates.getY()); // Latitude
            cstmt.setDouble(3, coordinates.getX()); // Longitude
            cstmt.setString(4, homeBean.getType());
            cstmt.setDouble(5, homeBean.getSurface());
            cstmt.setInt(6, homeBean.getNRooms());
            cstmt.setInt(7, homeBean.getNBathrooms());
            cstmt.setInt(8, homeBean.getFloor());
            cstmt.setInt(9, homeBean.getElevator());
            cstmt.setString(10, homeBean.getDescription());
            cstmt.setString(11, ownerEmail);
            cstmt.registerOutParameter(12, java.sql.Types.INTEGER);
            cstmt.execute();
            newHomeId = cstmt.getInt(12);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_SAVE_HOME"));
            return null;
        }

        Integer[] features = {
                homeBean.getNRooms(),
                homeBean.getNBathrooms(),
                homeBean.getFloor(),
                homeBean.getElevator()
        };

        return new Home(newHomeId, coordinates, homeBean.getAddress(), homeBean.getType(), homeBean.getSurface(), homeBean.getDescription(), features);
    }

    private int isHomeAlreadyExists(HomeBean homeBean) {
        int existingHomeId = -1;

        // Call the stored procedure to check if the home already exists
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.IS_HOME_ALREADY_EXISTS)) {
            cstmt.setString(1, homeBean.getAddress());
            cstmt.setString(2, homeBean.getType());
            cstmt.setInt(3, homeBean.getSurface());
            cstmt.setInt(4, homeBean.getNRooms());
            cstmt.setInt(5, homeBean.getNBathrooms());
            cstmt.setInt(6, homeBean.getFloor());
            cstmt.setInt(7, homeBean.getElevator());
            cstmt.registerOutParameter(8, java.sql.Types.INTEGER);

            if (cstmt.execute()) {  // execute the stored procedure and check if there is a result set
                try (ResultSet res = cstmt.getResultSet()) {
                    res.first();
                    existingHomeId = res.getInt(8); // If the home already exists, it returns the ID of the existing home
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_IS_HOME_ALREADY_EXISTS"));
        }
        return existingHomeId; // If the home does not exist, it returns -1
    }

    @Override
    public void saveHomeImage(String imageName, String imageType, byte[] byteArray, int idHome) {
        // Check if the image already exists
        if (imageHomeAlreadyExists(imageName, idHome)) {
            return;
        }

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.SAVE_HOME_IMAGE)) {
            cstmt.setString(1, imageName);
            cstmt.setString(2, imageType);
            cstmt.setBytes(3, byteArray);
            cstmt.setInt(4, idHome);
            cstmt.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_SAVE_HOME_IMAGE"));
        }
    }

    private boolean imageHomeAlreadyExists(String imageName, int idHome) {
        boolean exists = false;

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_HOME_IMAGE)) {
            cstmt.setInt(1, idHome);

            if (cstmt.execute()) {  // execute the stored procedure and check if there is a result set
                try (ResultSet res = cstmt.getResultSet()) {
                    while (res.next()) {
                        if (res.getString(2).equals(imageName) && res.getInt(5) == idHome) {
                            exists = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_HOME_IMAGE"));
        }
        return exists;
    }

    @Override
    public byte[] getHomeImage(int idHome) {
        byte[] image = null;

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_HOME_IMAGE)) {
            cstmt.setInt(1, idHome);

            if (cstmt.execute()) {
                try (ResultSet res = cstmt.getResultSet()) {
                    res.first();
                    image = res.getBytes("image");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("FAILED_RETRIEVE_HOME_IMAGE"));
        }
        return image;
    }

    private Home fillHome(ResultSet res) throws SQLException {
        int idHome = res.getInt(1);
        String address = res.getString(2);
        Point2D coordinates = new Point2D.Double(res.getDouble(3), res.getDouble(4));
        String homeType = res.getString(5);
        int homeSurface = res.getInt(6);
        Integer[] features = {res.getInt(7), res.getInt(8), res.getInt(9), res.getInt(10)};
        String homeDescription = res.getString(11);

        return new Home(idHome, coordinates, address, homeType, homeSurface, homeDescription, features);
    }
}
