package it.hivecampuscompany.hivecampus.state.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AccountSettingsJavaFxController extends JavaFxController {

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

    public AccountSettingsJavaFxController() {
        // Default constructor
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

    private void handlePictureChange() {
        showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }

    private void handlePasswordChange() {
        showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("NOT_IMPLEMENTED_MSG"));
    }
}
