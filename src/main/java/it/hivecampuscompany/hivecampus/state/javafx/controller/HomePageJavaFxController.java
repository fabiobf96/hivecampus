package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.OwnerHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.TenantHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
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

/**
 * The HomePageJavaFxController class represents a controller for the home page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the home view and handling user interactions.
 */

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

    /**
     * Initializes the home view with the user's account information.
     * It sets the labels and buttons with the user's name, notifications, and account settings.
     *
     * @param context The context object for the home page.
     */

    public void initializeHomeView(Context context) {
        this.context = context;
        AccountBean accountBean = getAccountInfo();

        mbtnNotifications.setText(properties.getProperty("NOTIFICATIONS_MSG"));
        mbtnMenuAccount.setText(properties.getProperty("ACCOUNT_MSG"));

        lblUsername.setText(accountBean.getName() + " " + accountBean.getSurname());
        lblSettings.setText(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        lblLanguage.setText(properties.getProperty("CHANGE_LANGUAGE_MSG"));
        lblLogout.setText(properties.getProperty("LOGOUT_MSG"));

        mibtnSettings.setOnAction(event -> handleAccountSettings());
        mibtnLanguage.setOnAction(event -> handleLanguageSettings());
        mibtnLogout.setOnAction(event -> handleLogout());

        // lvNotifications custom list cell as in AdSearchController
    }

    /**
     * Initializes the list view of notifications with the user's notifications.
     */

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

    /**
     * Handles the user's request to change the account settings.
     * It loads the account settings form in a modal window and waits for the user to close it.
     * After the modal window is closed, it updates the home page and the tabs.
     */

    private void handleAccountSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/accountSettingsForm-view.fxml"));
            Parent root = loader.load();

            AccountSettingsJavaFxController controller = loader.getController();
            controller.initializeAccountSettingsView(context);

            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);

            popUpStage.setResizable(false);
            popUpStage.setScene(scene);
            popUpStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("ERROR_ACCOUNT_SETTINGS_WINDOW_MSG"), e);
        }
        addNotifications();
    }

    /**
     * Handles the user's request to change the language settings.
     * It loads the language settings form in a modal window and waits for the user to close it.
     * After the modal window is closed, it updates the home page and the tabs.
     */

    private void handleLanguageSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/languageSettingsForm-view.fxml"));
            Parent root = loader.load();

            LanguageJavaFxController controller = loader.getController();
            controller.initializeLanguageSettingsView(context);

            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);

            popUpStage.setResizable(false);

            popUpStage.setOnCloseRequest(event -> {

                properties = LanguageLoader.getLanguageProperties();

                if (context.getSessionBean().getRole().equals("owner")) {
                    context.setState(new OwnerHomeJavaFXPage(context));
                } else {
                    context.setState(new TenantHomeJavaFXPage(context));
                }
                context.request();
            });

            popUpStage.setScene(scene);
            popUpStage.showAndWait();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, properties.getProperty("ERROR_LANGUAGE_SETTINGS_WINDOW_MSG"), e);
        }
    }

    /**
     * Handles the user's request to log out.
     * It sets the initial JavaFX page as the current state and requests the context to update the view.
     */

    @FXML
    private void handleLogout() {
        context.setState(new InitialJavaFXPage(context));
        context.request();
    }
}
