package it.hivecampuscompany.hivecampus.logic.dao.csv;

import it.hivecampuscompany.hivecampus.logic.dao.UserDAO;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.model.User;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOCSV implements UserDAO {
    File fd;
    private static final Logger LOGGER = Logger.getLogger(UserDAOCSV.class.getName());
    public UserDAOCSV(){
        try (InputStream input = new FileInputStream("properties/csv.properties")){
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("USER_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }
    @Override
    public void saveUser(User user) throws DuplicateRowException {
        if (userExists(user.getEmail())) {
            throw new DuplicateRowException("ACCOUNT_EXIST");
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] userRecord = new String[3];
            userRecord[UserAttributesOrder.GET_INDEX_EMAIL] = user.getEmail();
            userRecord[UserAttributesOrder.GET_INDEX_PASSWORD] = user.getPassword();
            userRecord[UserAttributesOrder.GET_INDEX_ROLE] = user.getRole();
            writer.writeNext(userRecord);
            // User created successfully
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save user", e);
            System.exit(2);
        }
    }

    private boolean userExists(String email) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String storedEmail = nextRecord[0].trim();
                if (email.equals(storedEmail)) {
                    return true; // User found
                }
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to check if user exists", e);
        }

        return false; // User not found
    }

    private static class UserAttributesOrder{
        static final int GET_INDEX_EMAIL = 0;
        static final int GET_INDEX_PASSWORD = 1;
        static final int GET_INDEX_ROLE = 2;
    }
}
