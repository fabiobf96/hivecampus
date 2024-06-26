package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.model.Account;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAOCSV implements AccountDAO {
    private File fd;
    private static final Logger LOGGER = Logger.getLogger(AccountDAOCSV.class.getName());
    private final Properties language = LanguageLoader.getLanguageProperties();

    public AccountDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("ACCOUNT_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, language.getProperty("FAILED_LOADING_CSV_PROPERTIES"), e);
            System.exit(1);
        }
    }

    @Override
    public void saveAccount(Account account) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] accountRecord = new String[4];
            accountRecord[AccountAttributes.GET_INDEX_EMAIL] = account.getEmail();
            accountRecord[AccountAttributes.GET_INDEX_NAME] = account.getName();
            accountRecord[AccountAttributes.GET_INDEX_SURNAME] = account.getSurname();
            accountRecord[AccountAttributes.GET_INDEX_PHONE_NUMBER] = account.getPhoneNumber();
            writer.writeNext(accountRecord);
            // Account created successfully
        } catch (IOException e) {
            LOGGER.severe(language.getProperty("FAILED_SAVE_ACCOUNT"));
            System.exit(2);
        }
    }

    @Override
    public Account retrieveAccountInformationByEmail(String email) {
        List<String[]> accountTable = CSVUtility.readAll(fd);
        accountTable.removeFirst();
        return accountTable.stream()
                .filter(accountRecord -> accountRecord[AccountAttributes.GET_INDEX_EMAIL].equals(email))
                .findFirst()
                .map(accountRecord -> new Account(
                        accountRecord[AccountAttributes.GET_INDEX_EMAIL],
                        accountRecord[AccountAttributes.GET_INDEX_NAME],
                        accountRecord[AccountAttributes.GET_INDEX_SURNAME],
                        accountRecord[AccountAttributes.GET_INDEX_PHONE_NUMBER]
                ))
                .orElse(null);
    }

    private static class AccountAttributes {
        static final int GET_INDEX_EMAIL = 0;
        static final int GET_INDEX_NAME = 1;
        static final int GET_INDEX_SURNAME = 2;
        static final int GET_INDEX_PHONE_NUMBER = 3;
    }
}
