package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling CSV files and Base64 encoding/decoding operations.
 *
 * @author Fabio Barchiesi
 */
public class CSVUtility {
    private static Properties properties;
    private static final Logger LOGGER = Logger.getLogger(CSVUtility.class.getName());

    // Private constructor to prevent instantiation
    private CSVUtility() {
    }

    static {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    /**
     * Encodes a byte array to a Base64 string.
     *
     * @param content the byte array to encode
     * @return the encoded Base64 string
     * @author Fabio Barchiesi
     */
    public static String encodeBytesToBase64(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    /**
     * Decodes a Base64 string to a byte array.
     *
     * @param content the Base64 string to decode
     * @return the decoded byte array
     * @author Fabio Barchiesi
     */
    public static byte[] decodeBase64ToBytes(String content) {
        return Base64.getDecoder().decode(content);
    }

    /**
     * Finds the last row index in a CSV file.
     *
     * @param fd the file descriptor of the CSV file
     * @return the last row index, or 1 if the table is empty
     * @author Marina Sotiropoulos
     */
    public static int findLastRowIndex(File fd) {
        List<String[]> table = readAll(fd);
        table.removeFirst();  // Assuming the first row is the header
        if (table.isEmpty()) {
            return 1;
        } else {
            String lastRecord = table.getLast()[0];
            return Integer.parseInt(lastRecord);
        }
    }

    /**
     * Reads all rows from the specified CSV file.
     * This method reads the entire content of the given CSV file and returns it as
     * a list of string arrays, where each array represents a row in the CSV file.
     *
     * @param fd the file to be read
     * @return a list of string arrays, where each array represents a row in the CSV file
     * @throws NullPointerException if the file parameter is null
     *
     * <p>Note: This method is synchronized to ensure thread safety. In case of any
     * IOException or CsvException, it logs the error and exits the application
     * with an appropriate status code. If an error occurs, the method returns an
     * empty list.</p>
     *
     * @author Fabio Barchiesi
     */
    public static synchronized List<String[]> readAll(File fd) {
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

    /**
     * Updates the specified CSV file with a new header and table data.
     * This method writes the provided header and table data to a temporary file,
     * then replaces the original file with the temporary file.
     *
     * @param fd     the file to be updated
     * @param header the header to be written as the first row
     * @param table  the table data to be written after the header
     * @throws NullPointerException if any of the parameters are null
     *
     * <p>Note: This method is synchronized to ensure thread safety. In case of any
     * IOException or CsvException, it logs the error and exits the application
     * with an appropriate status code.</p>
     *
     * @author Fabio Barchiesi
     */
    public static synchronized void updateFile(File fd, String[] header, List<String[]> table) {
        File fdTmp = new File(fd.getAbsolutePath() + ".tmp");
        try (CSVWriter writer = new CSVWriter(new FileWriter(fdTmp))) {
            table.addFirst(header);  // Adding header as the first row
            writer.writeAll(table);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format(properties.getProperty("ERR_ACCESS"), fdTmp), e);
            System.exit(3);
        }
        try {
            Files.move(fdTmp.toPath(), fd.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Failed to move file from %s to %s", fdTmp, fd), e);
            System.exit(4);
        }
    }
}
