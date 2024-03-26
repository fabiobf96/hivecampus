package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.view.gui.javafx.LoginJavaFxGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePageJavaFxController extends JavaFxController{

    private static final Logger logger = Logger.getLogger(HomePageJavaFxController.class.getName());

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

    @FXML
    private Label lblSettingsTitle;
    @FXML
    private Label lblName;
    @FXML
    private Label lblSurname;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblRole;
    @FXML
    private Label lblPhone;
    @FXML
    private Button btnPic;
    @FXML
    private Button btnPsw;

    @FXML
    private Label lblLanguageTitle;
    @FXML
    private Label lblEnglish;
    @FXML
    private Label lblItalian;
    @FXML
    private Button btnEnglish;
    @FXML
    private Button btnItalian;

    private static final String ERROR_TITLE_MSG = "ERROR_TITLE_MSG";
    private static final String ERROR = "ERROR";


    public HomePageJavaFxController() {
        // Default constructor
    }

    public void initializeHomeView() {
        //this.sessionBean = sessionBean;
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

    public void initializeAccountSettingsView() {
        lblSettingsTitle.setText(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        lblName.setText(properties.getProperty("NAME_MSG"));
        lblSurname.setText(properties.getProperty("SURNAME_MSG"));
        lblEmail.setText(properties.getProperty("EMAIL_MSG"));
        lblPassword.setText(properties.getProperty("PASSWORD_MSG"));
        lblRole.setText(properties.getProperty("ROLE_MSG"));
        lblPhone.setText(properties.getProperty("PHONE_N_MSG"));

        btnPic.setText(properties.getProperty("CHANGE_PIC_MSG"));
        btnPsw.setText(properties.getProperty("CHANGE_PSW_MSG"));

        btnPic.setOnAction(event -> handlePictureChange());
        btnPsw.setOnAction(event -> handlePasswordChange());
    }

    public void initializeLanguageSettingsView() {
        lblLanguageTitle.setText(properties.getProperty("LANGUAGE_SETTINGS_MSG"));
        lblEnglish.setText(properties.getProperty("ENGLISH_MSG"));
        lblItalian.setText(properties.getProperty("ITALIAN_MSG"));
        btnEnglish.setText(properties.getProperty("CHANGE_ENGLISH_MSG"));
        btnItalian.setText(properties.getProperty("CHANGE_ITALIAN_MSG"));

        btnEnglish.setOnAction(event -> handleChangeLanguage(0));
        btnItalian.setOnAction(event -> handleChangeLanguage(1));
    }

    private void handleAccountSettings() {
        try {
            // Carica il file FXML per la finestra modale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/accountSettingsForm-view.fxml"));
            Parent root = loader.load();

            // Ottieni il controller dalla finestra modale
            HomePageJavaFxController controller = loader.getController();
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
            logger.log(Level.SEVERE, "Error loading account settings form.", e);
        }
    }

    private void handleLanguageSettings() {
        try {
            // Carica il file FXML per la finestra modale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/languageSettingsForm-view.fxml"));
            Parent root = loader.load();

            // Ottieni il controller dalla finestra modale
            HomePageJavaFxController controller = loader.getController();
            controller.initializeLanguageSettingsView();

            // Crea e visualizza la finestra modale con il form per le impostazioni della lingua
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);

            // Impostare la finestra come non ridimensionabile
            popUpStage.setResizable(false);
            popUpStage.setScene(scene);
            popUpStage.showAndWait();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading language settings form.", e);
        }
    }

    private void handlePictureChange() {
        showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    private void handlePasswordChange() {
        showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    private void handleChangeLanguage(int choice) {
        LanguageLoader.loadLanguage(choice);
        properties = LanguageLoader.getLanguageProperties();
        //initializeHomeView();
        initializeLanguageSettingsView();
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) mbtnMenuAccount.getScene().getWindow();

        LoginJavaFxGUI login = new LoginJavaFxGUI();
        try {
            login.start(stage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error logging out.", e);
        }
    }
}
