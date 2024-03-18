package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.model.Account;

import java.sql.CallableStatement;
import java.sql.Connection;
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
            LOGGER.severe("Error while saving account.");
        }
    }
}
