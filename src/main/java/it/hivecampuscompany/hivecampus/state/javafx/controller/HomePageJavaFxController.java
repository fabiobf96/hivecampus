package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.OwnerHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.TenantHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
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

    public HomePageJavaFxController() {
        // Default constructor
    }

    /**
     * Initializes the home view with the user's account information.
     * It sets the labels and buttons with the user's name, notifications, and account settings.
     *
     * @param context The context object for the home page.
     * @author Marina Sotiropoulos
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
        mbtnNotifications.setOnMouseClicked(event -> handleNotification());
        mibtnLogout.setOnAction(event -> handleLogout());
    }

    /**
     * Handles the user's request to change the account settings.
     * It loads the account settings form in a modal window and waits for the user to close it.
     * After the modal window is closed, it updates the home page and the tabs.
     *
     * @author Marina Sotiropoulos
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
    }

    /**
     * Handles the user's request to change the language settings.
     * It loads the language settings form in a modal window and waits for the user to close it.
     * After the modal window is closed, it updates the home page and the tabs.
     *
     * @author Marina Sotiropoulos
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
     * Handles the user's request to view a notification.
     * It displays an alert with the notification message.
     *
     * @author Marina Sotiropoulos
     */
    private void handleNotification() {
        showAlert(INFORMATION, properties.getProperty(INFORMATION_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    /**
     * Handles the user's request to log out.
     * It sets the initial JavaFX page as the current state and requests the context to update the view.
     *
     * @author Marina Sotiropoulos
     */

    @FXML
    private void handleLogout() {
        context.setState(new InitialJavaFXPage(context));
        context.setFirst(true);
        context.request();
    }
}
