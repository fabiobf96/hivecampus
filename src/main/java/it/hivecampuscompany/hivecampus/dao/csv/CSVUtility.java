package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVUtility {

    private static final Logger LOGGER = Logger.getLogger(CSVUtility.class.getName());
    private CSVUtility(){}
    public static String encodeBytesToBase64(byte[] content) {
        // Codifica l'array di byte in una stringa Base64
        return Base64.getEncoder().encodeToString(content);
    }

    public static byte[] decodeBase64ToBytes(String content) {
        // Decodifica la stringa Base64 in un array di byte
        return Base64.getDecoder().decode(content);
    }

    public static int findLastRowIndex(File fd) {
        int lastID = 0;
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            reader.readNext();
            while ((nextRecord = reader.readNext()) != null) {
                lastID = Integer.parseInt(nextRecord[0].trim());
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Enable to open file", e);
        }
        if (lastID == 0) {
            return 0;
        }
        else {
            return lastID + 1;
        }
    }


}
