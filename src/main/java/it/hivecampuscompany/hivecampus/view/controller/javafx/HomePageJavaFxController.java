package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.view.gui.javafx.LoginJavaFxGUI;
import it.hivecampuscompany.hivecampus.view.gui.javafx.TenantHomePageJavaFxGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePageJavaFxController extends JavaFxController{

    private static final Logger LOGGER = Logger.getLogger(HomePageJavaFxController.class.getName());

    @FXML
    private MenuButton mbtnNotifications;
    @FXML
    private MenuButton mbtnMenuAccount;
    @FXML
    private Label lblUsername;
    @FXML
    private MenuItem mibtnSettings;
    @FXML
    private Label lblSettings;
    @FXML
    private MenuItem mibtmLanguage;
    @FXML
    private Label lblLanguage;
    @FXML
    private MenuItem mibtnLogout;
    @FXML
    private Label lblLogout;

    public HomePageJavaFxController() {
        // Default constructor
    }

    public void initializeHomeView() {
        //sessionBean
        mbtnNotifications.setText(properties.getProperty("NOTIFICATIONS_MSG"));
        mbtnMenuAccount.setText(properties.getProperty("ACCOUNT_MSG"));

        lblUsername.setText("Pippo Pluto"); // devo recuperare il nome dell'utente loggato
        lblSettings.setText(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        lblLanguage.setText(properties.getProperty("CHANGE_LANGUAGE_MSG"));
        lblLogout.setText(properties.getProperty("LOGOUT_MSG"));

        mibtnSettings.setOnAction(event -> handleAccountSettings());
        mibtmLanguage.setOnAction(event -> handleLanguageSettings());
        mibtnLogout.setOnAction(event -> handleLogout());
    }

    private void handleAccountSettings() {
        try {
            // Carica il file FXML per la finestra modale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/accountSettingsForm-view.fxml"));
            Parent root = loader.load();

            // Ottieni il controller dalla finestra modale
            AccountSettingsJavaFxController controller = loader.getController();
            controller.initializeAccountSettingsView();

            // Crea e visualizza la finestra modale con il form per le impostazioni dell'account
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);

            // Impostare la finestra come non ridimensionabile
            popUpStage.setResizable(false);
            popUpStage.setScene(scene);
            popUpStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("ERROR_ACCOUNT_SETTINGS_WINDOW_MSG"), e);
        }
    }

    private void handleLanguageSettings() {
        try {
            Stage stage = (Stage) mbtnMenuAccount.getScene().getWindow();

            // Carica il file FXML per la finestra modale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/languageSettingsForm-view.fxml"));
            Parent root = loader.load();

            // Ottieni il controller dalla finestra modale
            LanguageJavaFxController controller = loader.getController();
            controller.initializeLanguageSettingsView();

            // Crea e visualizza la finestra modale con il form per le impostazioni della lingua
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);

            // Impostare la finestra come non ridimensionabile
            popUpStage.setResizable(false);

            // Aggiungi un listener per l'evento di chiusura della finestra modale
            popUpStage.setOnCloseRequest(event -> {
                // Aggiorna le proprietà della lingua
                properties = LanguageLoader.getLanguageProperties();
                // Dopo che la finestra modale è stata chiusa, esegue il codice per aggiornare la home page e i tab
                new TenantHomePageJavaFxGUI().startWithSession(stage, sessionBean);
            });

            popUpStage.setScene(scene);
            popUpStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("ERROR_LANGUAGE_SETTINGS_WINDOW_MSG"), e);
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) mbtnMenuAccount.getScene().getWindow();

        LoginJavaFxGUI login = new LoginJavaFxGUI();
        try {
            login.start(stage);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("ERROR_LOGOUT_MSG"), e);
        }
    }
}
