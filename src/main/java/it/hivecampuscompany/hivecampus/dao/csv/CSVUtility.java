package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVUtility {
    private static Properties properties;
    private static final Logger LOGGER = Logger.getLogger(CSVUtility.class.getName());

    private CSVUtility(){}
    static {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }
    public static String encodeBytesToBase64(byte[] content) {
        // Codifica l'array di byte in una stringa Base64
        return Base64.getEncoder().encodeToString(content);
    }

    public static byte[] decodeBase64ToBytes(String content) {
        // Decodifica la stringa Base64 in un array di byte
        return Base64.getDecoder().decode(content);
    }

    public static int findLastRowIndex(File fd) {
        List<String[]> table = readAll(fd);
        String lastRecord = table.getLast()[0];
        if (lastRecord.isBlank()) {
            return 1;
        }
        else {
            return Integer.parseInt(lastRecord) + 1;
        }
    }

    public static List<String[]> readAll(File fd) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            return reader.readAll();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERR_ACCESS"), fd), e);
            System.exit(3);
        } catch (CsvException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERR_PARSER"), fd), e);
            System.exit(3);
        }
        return Collections.emptyList();
    }
}
