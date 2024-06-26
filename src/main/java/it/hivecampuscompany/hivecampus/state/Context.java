package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.SessionManager;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import it.hivecampuscompany.hivecampus.state.cli.InitialCLIPage;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The Context class represents the context of the application, including the current state, session information, tabs, language properties, and filters.
 * @author Fabio Barchiesi
 */
public class Context {
    private State state;
    private Stage stage;
    private SessionBean sessionBean;
    private List<Tab> tabs = new ArrayList<>();
    private Properties properties = LanguageLoader.getLanguageProperties();
    private FiltersBean filtersBean;
    private boolean firstLease = true;

    private boolean firstRequest = true;

    /**
     * Sets the state of the context.
     *
     * @param state The state to set.
     * @author Fabio Barchiesi
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Gets the session bean.
     *
     * @return The session bean.
     * @author Fabio Barchiesi
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * Sets the session bean.
     *
     * @param sessionBean The session bean to set.
     * @author Fabio Barchiesi
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        if (sessionBean != null) {
            if (stage == null) {
                this.sessionBean.setClient(SessionBean.Client.CLI);
            } else {
                this.sessionBean.setClient(SessionBean.Client.JAVA_FX);
            }
        }
    }

    /**
     * Gets the stage.
     *
     * @return The stage.
     * @author Fabio Barchiesi
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage The stage to set.
     * @author Fabio Barchiesi
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the tab at the specified index.
     *
     * @param index The index of the tab.
     * @return The tab at the specified index.
     * @author Marina Sotiropoulos
     */
    public Tab getTab(int index) {
        return tabs.get(index);
    }

    /**
     * Sets the tabs.
     *
     * @param tabs The tabs to set.
     * @author Marina Sotiropoulos
     */
    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    /**
     * Gets the language properties.
     *
     * @return The language properties.
     * @author Marina Sotiropoulos
     */
    public Properties getLanguage() {
        return properties;
    }

    /**
     * Sets the language properties.
     *
     * @param properties The language properties to set.
     * @author Marina Sotiropoulos
     */
    public void setLanguage(Properties properties) {
        this.properties = properties;
    }

    /**
     * Gets the filters bean.
     *
     * @return The filters bean.
     * @author Marina Sotiropoulos
     */
    public FiltersBean getFiltersBean() {
        return filtersBean;
    }

    /**
     * Sets the filters bean.
     *
     * @param filtersBean The filters bean to set.
     * @author Marina Sotiropoulos
     */
    public void setFiltersBean(FiltersBean filtersBean) {
        this.filtersBean = filtersBean;
    }

    /**
     * Requests handling of the current state.
     * If an invalid session exception occurs, it handles it by deleting the session and setting the state to the initial CLI page.
     * Otherwise, it handles the current state.
     *
     * @author Fabio Barchiesi
     */
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
            request();
        }
    }

    /**
     * Handles the invalid session exception by displaying a warning alert and setting the state to the initial JavaFX page.
     *
     * @author Fabio Barchiesi
     */
    public void invalidSessionExceptionHandle() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(properties.getProperty("INVALID_SESSION_EXCEPTION"));
        alert.showAndWait();
        state = new InitialJavaFXPage(this);
        request();
    }

    public boolean isFirstLease() {
        return firstLease;
    }

    public void setFirstLease(boolean firstLease) {
        this.firstLease = firstLease;
    }

    public boolean isFirstRequest() {
        return firstRequest;
    }

    public void setFirstRequest(boolean firstRequest) {
        this.firstRequest = firstRequest;
    }
}