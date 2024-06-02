package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.dao.UserDAO;
import it.hivecampuscompany.hivecampus.dao.csv.UserDAOCSV;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.model.User;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marina Sotiropoulos
 */

class LoginTest {
    @Test
    void testLoginForUnregisteredUser() {

        LoginManager control = new LoginManager();
        UserBean userBean = new UserBean();

        try {
            userBean.setEmail("test@example.com");
        } catch (InvalidEmailException e) {
            throw new RuntimeException(e);
        }
        userBean.setPassword("password123");

        assertThrows(AuthenticateException.class, () -> control.login(userBean));
    }

        @Test
    void testLoginForRegisteredUserWithWrongPsw() {

        UserDAO userDAO = new UserDAOCSV();
        UserBean userBean = new UserBean();
        try {
            userBean.setEmail("marco.neri@gmail.com");
        } catch (InvalidEmailException e) {
            throw new RuntimeException(e);
        }
        userBean.setPassword("password123");

        assertThrows(AuthenticateException.class, () -> userDAO.verifyCredentials(new User(userBean)));
    }

    @Test
    void testLoginForRegisteredUser() {
        LoginManager control = new LoginManager();
        UserBean userBean = new UserBean();

        try {
            userBean.setEmail("marco.neri@gmail.com");
        } catch (InvalidEmailException e) {
            throw new RuntimeException(e);
        }
        userBean.setPassword("pippo");

        try {
            SessionBean sessionBean = control.login(userBean);
            assertNotNull(sessionBean); // Verifica che la sessione non sia nulla
            assertEquals(userBean.getEmail(), sessionBean.getEmail()); // Verifica che l'email dell'utente corrisponda

        } catch (AuthenticateException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}