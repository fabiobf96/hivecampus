package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.SessionManager;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private State state;
    private Stage stage;
    private SessionBean sessionBean;
    private List<Tab> tabs = new ArrayList<>();
    public void setState(State state) {
        this.state = state;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Tab getTab(int index) {
        return tabs.get(index);
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public void request() {
        try {
            state.handle();
        } catch (InvalidSessionException e) {
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.deleteSession(sessionBean);
            setSessionBean(null);
            if (stage == null) {
                //setState(new InitialCLIPage(this));
            }
            request();
        }
    }
}

