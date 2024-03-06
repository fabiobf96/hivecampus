package it.hivecampuscompany.hivecampus.dao.csv;

import com.opencsv.CSVWriter;
import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAOCSV implements AccountDAO {
    File fd;
    private static final Logger LOGGER = Logger.getLogger(AccountDAOCSV.class.getName());
    public AccountDAOCSV(){
        try (InputStream input = new FileInputStream("properties/csv.properties")){
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("ACCOUNT_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }
    @Override
    public void saveAccount(AccountBean accountBean) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] accountRecord = new String[4];
            accountRecord[AccountAttributesOrder.GET_INDEX_EMAIL] = accountBean.getEmail();
            accountRecord[AccountAttributesOrder.GET_INDEX_NAME] = accountBean.getName();
            accountRecord[AccountAttributesOrder.GET_INDEX_SURNAME] = accountBean.getSurname();
            accountRecord[AccountAttributesOrder.GET_INDEX_PHONE_NUMBER] = accountBean.getPhoneNumber();
            writer.writeNext(accountRecord);
            // Account created successfully
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save account", e);
            System.exit(2);
        }
    }
    private static class AccountAttributesOrder{
        static final int GET_INDEX_EMAIL = 0;
        static final int GET_INDEX_NAME = 1;
        static final int GET_INDEX_SURNAME = 2;
        static final int GET_INDEX_PHONE_NUMBER = 3;
    }
}
