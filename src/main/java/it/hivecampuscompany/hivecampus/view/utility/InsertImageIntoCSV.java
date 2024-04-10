package it.hivecampuscompany.hivecampus.view.utility;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.csv.CSVUtility;
import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertImageIntoCSV {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(InsertImageIntoCSV.class.getName());
    private Properties properties;
    static final String ERROR_ACCESS = "ERR_ACCESS";
    public InsertImageIntoCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")){
            properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("ROOM_IMAGES_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    public void saveRoom(String imageName, String imageType, byte[] byteArray, int idRoom, int idHome) {

        int idImage = getNextIdFromFile();

        String[] room = new String[6];
        room[0] = String.valueOf(idImage);
        room[1] = String.valueOf(idRoom);
        room[2] = String.valueOf(idHome);
        room[3] = imageName;
        room[4] = imageType;
        room[5] = CSVUtility.encodeBytesToBase64(byteArray);

        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            writer.writeNext(room);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), fd), e);
            System.exit(3);
        }
    }

    public int getNextIdFromFile() {
        int maxId = 0;

        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> imageTable = reader.readAll();
            imageTable.remove(0);
            for (String[] imageRecord : imageTable) {
                int id = Integer.parseInt(imageRecord[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty(ERROR_ACCESS), fd), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERR_PARSER"), fd), e);
            System.exit(3);
        }

        return (maxId == 0) ? 1 : maxId + 1;
    }

}
