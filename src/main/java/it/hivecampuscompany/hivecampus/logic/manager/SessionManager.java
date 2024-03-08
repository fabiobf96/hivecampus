package it.hivecampuscompany.hivecampus.logic.manager;

import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.model.Session;
import it.hivecampuscompany.hivecampus.logic.model.User;

import java.util.HashMap;

public class SessionManager {
    private final HashMap<Integer, Session> sessionHashMap;
    private static SessionManager instance;

    private SessionManager(){
        sessionHashMap = new HashMap<>();
    }

    public static SessionManager getInstance(){
        if (instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public Session createSession(User user){
        Session session = new Session(user);
        if (sessionHashMap.get(session.getId()) == null){
            sessionHashMap.put(session.getId(), session);
            return session;
        }
        else return sessionHashMap.get(session.getId());
    }

    public boolean validSession(SessionBean sessionBean){
        return sessionHashMap.get(sessionBean.getId()).isValid();
    }
}
