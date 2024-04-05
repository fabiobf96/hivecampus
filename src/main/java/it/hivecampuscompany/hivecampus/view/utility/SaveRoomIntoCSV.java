package it.hivecampuscompany.hivecampus.view.utility;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import it.hivecampuscompany.hivecampus.dao.csv.CSVUtility;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveRoomIntoCSV {
    private File fd;
    public SaveRoomIntoCSV() {
        fd = new File("db/csv/room_images-table.csv");
    }

    public void saveRoom(byte[] byteArray, String imageName, String imageType) {
        int idImage = 1;
        int idRoom = 1;
        int idHome = 1;

        String[] room = new String[6];
        room[0] = String.valueOf(idImage);
        room[1] = String.valueOf(idRoom);
        room[2] = String.valueOf(idHome);
        room[3] = imageName;
        room[4] = imageType;
        //room[5] = CSVUtility.encodeBytesToBase64(byteArray);
        room[5] = byteArray.toString();

        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            writer.writeNext(room);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public byte[] retrieveRoom(int idImage) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            List<String[]> imageTable = reader.readAll();
            String[] imageRecord = imageTable.get(1);
            //return CSVUtility.decodeBase64ToBytes(imageRecord[5]);
            return imageRecord[5].getBytes();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
