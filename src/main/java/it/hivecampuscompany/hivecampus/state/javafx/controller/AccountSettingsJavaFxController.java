package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.state.Context;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountSettingsJavaFxController extends JavaFxController {

    @FXML
    private Label lblSettingsTitle;
    @FXML
    private Label lblName;
    @FXML
    private TextField txfName;
    @FXML
    private Label lblSurname;
    @FXML
    private TextField txfSurname;
    @FXML
    private Label lblEmail;
    @FXML
    private TextField txfEmail;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblRole;
    @FXML
    private TextField txfRole;
    @FXML
    private Label lblPhone;
    @FXML
    private TextField txfPhone;
    @FXML
    private Button btnPic;
    @FXML
    private Button btnPsw;

    public AccountSettingsJavaFxController() {
        // Default constructor
    }

    public void initializeAccountSettingsView(Context context) {
        this.context = context;
        AccountBean accountBean = getAccountInfo();

        lblSettingsTitle.setText(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        lblName.setText(properties.getProperty("NAME_MSG"));
        txfName.setText(accountBean.getName());
        lblSurname.setText(properties.getProperty("SURNAME_MSG"));
        txfSurname.setText(accountBean.getSurname());
        lblEmail.setText(properties.getProperty("EMAIL_MSG"));
        txfEmail.setText(accountBean.getEmail());
        lblPassword.setText(properties.getProperty("PASSWORD_MSG"));
        lblRole.setText(properties.getProperty("ROLE_MSG"));
        txfRole.setText(context.getSessionBean().getRole());
        lblPhone.setText(properties.getProperty("PHONE_N_MSG"));
        txfPhone.setText(accountBean.getPhoneNumber());

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
