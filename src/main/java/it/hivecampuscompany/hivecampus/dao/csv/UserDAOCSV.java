package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.model.User;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOCSV implements UserDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(UserDAOCSV.class.getName());

    public UserDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("USER_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "FAILED_LOADING_CSV_PROPERTIES", e);
            System.exit(1);
        }
    }

    @Override
    public void saveUser(User user) throws DuplicateRowException {
        if (!checkUserExist(user.getEmail())) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
                String[] userRecord = new String[3];
                userRecord[UserAttributesOrder.GET_INDEX_EMAIL] = user.getEmail();
                userRecord[UserAttributesOrder.GET_INDEX_PASSWORD] = user.getPassword();
                userRecord[UserAttributesOrder.GET_INDEX_ROLE] = user.getRole();
                writer.writeNext(userRecord);
                // User created successfully
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "FAILED_SAVE_USER", e); // Failed to save user
                System.exit(2);
            }
        } else {
            throw new DuplicateRowException("ACCOUNT_EXIST");
        }
    }

    @Override
    public User verifyCredentials(User user) throws AuthenticateException {
        String email = user.getEmail();
        String psw = user.getPassword();
        List<String[]> userTable = CSVUtility.readAll(fd);
        String[] nextRecord;
        while (!userTable.isEmpty()) {
            nextRecord = userTable.removeFirst();
            String storedEmail = nextRecord[UserAttributesOrder.GET_INDEX_EMAIL].trim();
            String storedPassword = nextRecord[UserAttributesOrder.GET_INDEX_PASSWORD].trim();
            if (email.equals(storedEmail) && psw.equals(storedPassword)) {
                return new User(storedEmail, storedPassword, nextRecord[UserAttributesOrder.GET_INDEX_ROLE]);
            }
        }
        throw new AuthenticateException("INCORRECT_CREDENTIALS"); // Email and/or password incorrect
    }

    private boolean checkUserExist(String email) {
        List<String[]> userTable = CSVUtility.readAll(fd);
        String[] nextRecord;
        while ((nextRecord = userTable.removeFirst()) != null) {
            String storedEmail = nextRecord[UserAttributesOrder.GET_INDEX_EMAIL].trim();
            if (email.equals(storedEmail)) {
                return true;
            }
        }
        return false;
    }

    private static class UserAttributesOrder {
        static final int GET_INDEX_EMAIL = 0;
        static final int GET_INDEX_PASSWORD = 1;
        static final int GET_INDEX_ROLE = 2;
    }
}
