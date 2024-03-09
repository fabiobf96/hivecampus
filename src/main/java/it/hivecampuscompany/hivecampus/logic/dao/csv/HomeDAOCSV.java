package it.hivecampuscompany.hivecampus.logic.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.logic.dao.HomeDAO;
import it.hivecampuscompany.hivecampus.logic.model.Home;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeDAOCSV implements HomeDAO {
    File fd;
    private static final Logger LOGGER = Logger.getLogger(HomeDAOCSV.class.getName());
    public HomeDAOCSV(){
        try (InputStream input = new FileInputStream("properties/csv.properties")){
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("HOME_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }
    @Override
    public List<Home> retrieveHomesByEmail(String email) {
        List<Home> homeList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] homeRecord;
            while ((homeRecord = reader.readNext()) != null) {
                String owner = homeRecord[HomeAttributesOrder.GET_INDEX_OWNER];
                if (owner.equals(email)) {
                    Home home = new Home();
                    home.setId(Integer.parseInt(homeRecord[HomeAttributesOrder.GET_INDEX_ID]));
                    home.setOwner(owner);
                    home.setAddress(homeRecord[HomeAttributesOrder.GET_INDEX_ADDRESS]);
                    home.setCity(homeRecord[HomeAttributesOrder.GET_INDEX_CITY]);
                    home.setLongitude(Double.parseDouble(homeRecord[HomeAttributesOrder.GET_INDEX_LONGITUDE]));
                    home.setLatitude(Double.parseDouble(homeRecord[HomeAttributesOrder.GET_INDEX_LATITUDE]));
                    home.setHouseType(homeRecord[HomeAttributesOrder.GET_INDEX_HOUSE_TYPE]);
                    home.setSurface(Integer.parseInt(homeRecord[HomeAttributesOrder.GET_INDEX_SURFACE]));
                    home.setSurface(Integer.parseInt(homeRecord[HomeAttributesOrder.GET_INDEX_N_ROOM]));
                    home.setNBathroom(Integer.parseInt(homeRecord[HomeAttributesOrder.GET_INDEX_N_BATHROOM]));
                    home.setDescription(homeRecord[HomeAttributesOrder.GET_INDEX_DESCRIPTION]);
                    homeList.add(home);
                }
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve home", e);
        }
        return homeList;
    }

    @Override
    public List<Home> retrieveHomesByUniversity(String universityName) {
        return null;
    }

    private static class HomeAttributesOrder{
        static final int GET_INDEX_ID = 0;
        static final int GET_INDEX_OWNER = 1;
        static final int GET_INDEX_ADDRESS = 2;
        static final int GET_INDEX_CITY = 3;
        static final int GET_INDEX_LONGITUDE = 4;
        static final int GET_INDEX_LATITUDE= 5;
        static final int GET_INDEX_HOUSE_TYPE = 6;
        static final int GET_INDEX_SURFACE = 7;
        static final int GET_INDEX_N_ROOM = 8;
        static final int GET_INDEX_N_BATHROOM = 9;
        static final int GET_INDEX_DESCRIPTION = 10;
    }

}
