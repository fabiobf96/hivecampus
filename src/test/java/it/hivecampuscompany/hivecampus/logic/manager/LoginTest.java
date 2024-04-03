package it.hivecampuscompany.hivecampus.logic.manager;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author marina sotiropoulos
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

        assertThrows(InvalidEmailException.class, () -> control.login(userBean));
    }

    @Test
    void testLoginForRegisteredUserWithWrongPsw() {
        LoginManager control = new LoginManager();
        UserBean userBean = new UserBean();
        try {
            userBean.setEmail("marco.neri@gmail.com");
        } catch (InvalidEmailException e) {
            throw new RuntimeException(e);
        }
        userBean.setPassword("password123");

        assertThrows(PasswordMismatchException.class, () -> control.login(userBean));
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

        int hashEmail = Objects.hash(userBean.getEmail());

        try {
            SessionBean sessionBean = control.login(userBean);
            assertEquals(hashEmail, sessionBean.getId());
        } catch (AuthenticateException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}