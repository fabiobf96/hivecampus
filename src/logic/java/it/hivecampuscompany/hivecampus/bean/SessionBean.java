package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Session;

public class SessionBean {
    private final int id;
    private final String role;
    public SessionBean(Session session) {
        id = session.getId();
        role = session.getUser().getRole();
    }
    public int getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
}
