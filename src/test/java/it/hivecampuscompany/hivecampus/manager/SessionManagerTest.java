package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.model.Session;
import it.hivecampuscompany.hivecampus.model.User;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

// @author Fabio Barchiesi
class SessionManagerTest {
    @Test
    void testSingletonInstance() {
        SessionManager instance1 = SessionManager.getInstance();
        SessionManager instance2 = SessionManager.getInstance();

        assertNotNull(instance1, "Instance 1 should not be null");
        assertNotNull(instance2, "Instance 2 should not be null");
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    void testSameUserSameSession() {
        SessionManager sessionManager = SessionManager.getInstance();

        // Create a mock user
        User user = new User("user@example.com", "pippo", "owner");

        // Create a session for the user
        Session firstSession = sessionManager.createSession(user);

        // Create another session for the same user
        Session secondSession = sessionManager.createSession(user);

        // Verify that the same session is returned
        assertNotNull(firstSession, "First session should not be null");
        assertNotNull(secondSession, "Second session should not be null");
        assertSame(firstSession, secondSession, "Both sessions should be the same");
    }

    @Test
    void testSessionInvalidAfterInvalidation() {
        SessionManager sessionManager = SessionManager.getInstance();

        // Create a mock user
        User user = new User("user@example.com", "pippo", "owner");


        // Create a session for the user
        Session session = sessionManager.createSession(user);

        // Verify the session is initially valid
        assertTrue(session.isValid(), "Session should initially be valid");

        // Invalidate the session
        sessionManager.deleteSession(new SessionBean(session));

        // Verify the session is invalid after being invalidated
        assertFalse(sessionManager.validSession(new SessionBean(session)), "Session should be invalid after being invalidated");
    }

    @Test
    void testSessionInvalidAfter1Minutes() {
        // Create a mock user
        User user = new User("user@example.com", "pippo", "owner");
        Session session = new Session(user, 59);

        Awaitility.await().atMost(Duration.ofMinutes(1)).until(() -> !session.isValid());

        // Verify the session is invalid after 15 minutes
        assertFalse(session.isValid(), "Session should be invalid after 1 minutes");
    }

}

