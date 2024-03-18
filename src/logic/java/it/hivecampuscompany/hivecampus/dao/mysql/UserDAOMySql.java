package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.model.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOMySql implements UserDAO {
    private final Connection connection = ConnectionManager.getConnection();
    private static final Logger LOGGER = Logger.getLogger(UserDAOMySql.class.getName());

    @Override
    public void saveUser(User user) throws DuplicateRowException {

        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.INSERT_USER)) {
            cstmt.setString(1, user.getEmail());
            cstmt.setString(2, user.getPassword());
            cstmt.setString(3, user.getRole());

            cstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DuplicateRowException("ACCOUNT_EXIST");
        }
    }

    @Override
    public User retrieveUserByEmail(String email) throws InvalidEmailException {

        if (checkUserExist(email)) {
            try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.RETRIEVE_USER_BY_EMAIL)) {
                cstmt.setString(1, email);
                ResultSet res = cstmt.executeQuery();
                res.first();

                String storedEmail = res.getString("email");
                String storedPassword = res.getString("password");
                String storedRole = res.getString("role");

                return new User(storedEmail, storedPassword, storedRole);

            } catch (SQLException e) {
                throw new InvalidEmailException("ACCOUNT_NOT_EXIST");
            }
        }
        else {
           throw new InvalidEmailException("ACCOUNT_NOT_EXIST");
        }
    }

    private boolean checkUserExist(String email) {
        try (CallableStatement cstmt = connection.prepareCall(StoredProcedures.CHECK_USER_EXIST)) {
            cstmt.setString(1, email);
            ResultSet res = cstmt.executeQuery();
            if (res.next()) {
                int emailCount = res.getInt(1);
                return emailCount > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to check if user exists", e);
        }
        return false;
    }
}
