package it.hivecampuscompany.hivecampus.dao.mysql;

import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.manager.ConnectionManager;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.queries.StoredProcedures;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.model.User;
import java.sql.*;


public class UserDAOMySql implements UserDAO {
    private final Connection connection = ConnectionManager.getConnection();

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
    public User verifyCredentials(User user) throws AuthenticateException {
        try (PreparedStatement pst = connection.prepareStatement(StoredProcedures.RETRIEVE_USER_BY_CREDENTIALS)) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());

            ResultSet res = pst.executeQuery();
            res.first();

            String storedEmail = res.getString("email");
            String storedPassword = res.getString("password");
            String storedRole = res.getString("role");

            return new User(storedEmail, storedPassword, storedRole);

        } catch (SQLException e) {
            throw new AuthenticateException("ACCOUNT_NOT_EXIST");
        }
    }
}
