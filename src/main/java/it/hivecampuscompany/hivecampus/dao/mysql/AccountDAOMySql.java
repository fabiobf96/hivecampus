package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.model.Account;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AccountDAOMySql implements AccountDAO {

    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(AccountDAOMySql.class.getName());

    @Override
    public void saveAccount(Account account) {
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.INSERT_ACCOUNT)) {
            cstmt.setString(1, account.getEmail());
            cstmt.setString(2, account.getName());
            cstmt.setString(3, account.getSurname());
            cstmt.setString(4, account.getPhoneNumber());

            cstmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.severe("FAILED_SAVE_ACCOUNT");
        }
    }

    @Override
    public Account retrieveAccountInformationByEmail(String email) {
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_ACCOUNT_BY_EMAIL)) {
            cstmt.setString(1, email);

            ResultSet res = cstmt.executeQuery();
            res.first();

            String storedEmail = res.getString(1);
            String storedName = res.getString(2);
            String storedSurname = res.getString(3);
            String storedTelephone = res.getString(4);

            return new Account(storedEmail, storedName, storedSurname, storedTelephone);

        } catch (SQLException e) {
            LOGGER.severe("FAILED_RETRIEVE_ACCOUNT_INFORMATION_BY_EMAIL");
            return null;
        }
    }
}
