package it.hivecampuscompany.hivecampus.bean;

import it.hivecampuscompany.hivecampus.model.Session;

public class SessionBean {
    private final int id;
    private final String email;
    private final String role;
    public SessionBean(Session session) {
        id = session.getId();
        email = session.getUser().getEmail();
        role = session.getUser().getRole();
    }
    public int getId() {
        return id;
    }
    public String getEmail(){ return email; }
    public String getRole() {
        return role;
    }
}
