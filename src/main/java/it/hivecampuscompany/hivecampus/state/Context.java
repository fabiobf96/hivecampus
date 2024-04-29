package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.SessionManager;
import it.hivecampuscompany.hivecampus.state.cli.InitialCLIPage;
import javafx.stage.Stage;

public class Context {
    private State state;
    private Stage stage;
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void request() {
        try {
            state.handle();
        } catch (InvalidSessionException e) {
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.deleteSession(sessionBean);
            setSessionBean(null);
            if (stage == null) {
                setState(new InitialCLIPage(this));
            }
        }
    }
}

