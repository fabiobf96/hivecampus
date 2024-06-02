package it.hivecampuscompany.hivecampus.manager;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.model.Session;
import it.hivecampuscompany.hivecampus.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages user sessions within the system.
 *
 * @author Fabio Barchiesi
 */
public class SessionManager {
    private final HashMap<Integer, Session> sessionHashMap;
    private static SessionManager instance;
    private static final Logger LOGGER = Logger.getLogger(SessionManager.class.getName());

    static {
        try {
            instance = new SessionManager();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while creating singleton instance", e);
            System.exit(4);
        }
    }

    /**
     * Private constructor to prevent direct instantiation.
     *
     * @author Fabio Barchiesi
     */
    private SessionManager() {
        sessionHashMap = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of SessionManager.
     *
     * @return The SessionManager instance.
     * @author Fabio Barchiesi
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Creates a new session for the given user.
     *
     * @param user The user for whom the session is created.
     * @return The created session.
     * @author Fabio Barchiesi
     */
    public Session createSession(User user) {
        Collection<Session> sessionList = sessionHashMap.values();
        for (Session session : sessionList) {
            if (session.getUser() == user) {
                return session;
            }
        }
        Session session = new Session(user);
        sessionHashMap.put(session.getId(), session);
        return session;
    }

    /**
     * Checks if the provided session is valid.
     *
     * @param sessionBean The session bean to be validated.
     * @return true if the session is valid, false otherwise.
     * @author Fabio Barchiesi
     */
    public boolean validSession(SessionBean sessionBean) {
        Session session = sessionHashMap.get(sessionBean.getId());
        if (session == null) {
            return false;
        }
        return session.isValid();
    }

    /**
     * Deletes the session associated with the provided session bean.
     *
     * @param sessionBean The session bean representing the session to be deleted.
     * @author Fabio Barchiesi
     */
    public void deleteSession(SessionBean sessionBean) {
        sessionHashMap.remove(sessionBean.getId());
    }
}

