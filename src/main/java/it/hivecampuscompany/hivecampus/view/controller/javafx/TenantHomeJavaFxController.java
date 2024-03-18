package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.view.gui.javafx.LoginJavaFxGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TenantHomeJavaFxController {

    private static final Logger logger = Logger.getLogger(TenantHomeJavaFxController.class.getName());
    private SessionBean sessionBean;
    @FXML
    private TabPane tabPane;

    @FXML
    private MenuButton btnMenuAccount;

    @FXML
    private MenuItem btnLogout;

    public TenantHomeJavaFxController() {
        // Default constructor
    }

    public void initialize(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        btnLogout.setOnAction(event -> handleLogout());
        addRoomSearchTab("Ricerca Camera", "/it/hivecampuscompany/hivecampus/tabRoomSearch-view.fxml");
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) btnMenuAccount.getScene().getWindow();

        LoginJavaFxGUI login = new LoginJavaFxGUI();
        try {
            login.start(stage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error logging out.", e);
        }
    }

    private void addRoomSearchTab(String tabName, String contentFXML) {
        try {
            // Carica il contenuto del tab da un file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentFXML));

           // RoomSearchJavaFxController roomSearchJavaFxController = new RoomSearchJavaFxController();

            // Associa l'istanza del controller al caricatore FXML
            //loader.setController(roomSearchJavaFxController);

            // Carica il contenuto e imposta il contenuto del nuovo tab
            Tab newTab = new Tab(tabName);
            newTab.setContent(loader.load());

            // Imposta closable a false per impedire la chiusura del tab
            newTab.setClosable(false);

            // Aggiungi il nuovo tab al tuo TabPane
            tabPane.getTabs().add(newTab);

            // Inizializza il controller dopo aver caricato il contenuto
            //roomSearchJavaFxController.initialize(sessionBean);

        } catch (IOException | RuntimeException  e) {
            logger.log(Level.SEVERE, "Error while loading.", e);
        }
    }
}