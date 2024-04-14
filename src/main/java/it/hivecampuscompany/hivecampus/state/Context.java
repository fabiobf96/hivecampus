package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

public class Context {
    private State state;
    private SessionBean sessionBean;
    public void setState(State state) {
        this.state = state;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void request() {
        try {
            state.handle();
        } catch (InvalidSessionException e) {
            // TODO
        }
    }
}

