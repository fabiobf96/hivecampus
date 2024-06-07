package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.model.Session;
import it.hivecampuscompany.hivecampus.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {
    @Test
    public void testSingletonInstance() {
        SessionManager instance1 = SessionManager.getInstance();
        SessionManager instance2 = SessionManager.getInstance();

        assertNotNull(instance1, "Instance 1 should not be null");
        assertNotNull(instance2, "Instance 2 should not be null");
        assertSame(instance1, instance2, "Both instances should be the same");
    }
    @Test
    public void testSameUserSameSession() {
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
    public void testSessionInvalidAfterInvalidation() {
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
    public void testSessionInvalidAfter1Minutes() throws InterruptedException {
        // Create a mock user
        User user = new User("user@example.com", "pippo", "owner");
        Session session = new Session(user, 60);

        // Wait for 15 minutes (900 seconds)
        Thread.sleep(60 * 1000); // 900 seconds in milliseconds

        // Verify the session is invalid after 15 minutes
        assertFalse(session.isValid(), "Session should be invalid after 1 minutes");
    }
}

