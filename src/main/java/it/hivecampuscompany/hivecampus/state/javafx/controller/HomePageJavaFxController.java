package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePageJavaFxController extends JavaFxController {
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
    private MenuItem mibtnLanguage;
    @FXML
    private Label lblLanguage;
    @FXML
    private MenuItem mibtnLogout;
    @FXML
    private Label lblLogout;
    @FXML
    private ListView<String> lvNotifications;

    public HomePageJavaFxController() {
        // Default constructor
    }

    public void initializeHomeView(Context context) {
        this.context = context;

        mbtnNotifications.setText(properties.getProperty("NOTIFICATIONS_MSG"));
        mbtnMenuAccount.setText(properties.getProperty("ACCOUNT_MSG"));

        lblUsername.setText("Pippo Pluto"); // devo recuperare il nome dell'utente loggato
        lblSettings.setText(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        lblLanguage.setText(properties.getProperty("CHANGE_LANGUAGE_MSG"));
        lblLogout.setText(properties.getProperty("LOGOUT_MSG"));

        mibtnSettings.setOnAction(event -> handleAccountSettings());
        mibtnLanguage.setOnAction(event -> handleLanguageSettings());
        mibtnLogout.setOnAction(event -> handleLogout());

        // lvNotifications custom list cell as in AdSearchController
    }

    @FXML
    public void initialize() {
        // Listener per il cambiamento nell'elenco di elementi della ListView
        lvNotifications.getItems().addListener((ListChangeListener<String>) change -> {
            while (change.next()) {
                // Verifica se sono stati aggiunti nuovi elementi
                if (change.wasAdded()) {
                    // Modifica il colore del pulsante delle notifiche
                    mbtnNotifications.setStyle("-fx-background-color: #ff9933");
                }
            }
        });

        // Listener per il click sul pulsante delle notifiche per rimuovere il colore arancione
        lvNotifications.setOnMouseClicked(event -> {
            // Verifica se il colore attuale del pulsante è arancione
            if (mbtnNotifications.getStyle().contains("-fx-background-color: #ff9933")) {
                // Rimuovi il colore arancione dal pulsante delle notifiche
                mbtnNotifications.setStyle(""); // Rimuove lo stile impostato e ripristina lo stile predefinito
            }
        });

    }

    // Metodo di prova
    public void addNotifications() {
        // Aggiungere nuove notifiche alla lista
        lvNotifications.getItems().addAll("Notifica 1", "Notifica 2", "Notifica 3");
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

        addNotifications();
    }

    private void handleLanguageSettings() {
        try {
            // Carica il file FXML per la finestra modale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/languageSettingsForm-view.fxml"));
            Parent root = loader.load();

            // Ottieni il controller dalla finestra modale
            LanguageJavaFxController controller = loader.getController();
            controller.initializeLanguageSettingsView(context);

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
                context.request();
            });

            popUpStage.setScene(scene);
            popUpStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("ERROR_LANGUAGE_SETTINGS_WINDOW_MSG"), e);
        }
    }

    @FXML
    private void handleLogout() {
        context.setState(new InitialJavaFXPage(context));
        context.request();
    }
}
