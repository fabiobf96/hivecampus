package it.hivecampuscompany.hivecampus.view.controller.javafx;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ManageLeaseRequestJavaFxController extends JavaFxController{
    @FXML
    private ImageView imvUser;

    @FXML
    private Text txtName;

    @FXML
    private Text txtSurname;

    @FXML
    private Text txtStart;

    @FXML
    private Text txtDuration;

    @FXML
    private Text txtEmail;

    @FXML
    private Text txtPhoneNumber;

    @FXML
    private Text txtMessage;

    @FXML
    private Label lblMsgContent;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnReject;

    // Method to initialize anything you need when the UI first loads
    @FXML
    public void initialize(LeaseRequestBean leaseRequestBean) {
        txtName.setText(properties.getProperty("NAME_MSG") + ": " + leaseRequestBean.getTenant().getName());
        txtSurname.setText(properties.getProperty("SURNAME_MSG") + ": " + leaseRequestBean.getTenant().getSurname());
        txtStart.setText(properties.getProperty("STR_MONTH_MSG") + ": " + leaseRequestBean.getMonth());
        txtDuration.setText(properties.getProperty("DURATION_MSG") + ": " + leaseRequestBean.getDuration());
        txtEmail.setText(properties.getProperty("EMAIL_MSG") + ": " + leaseRequestBean.getTenant().getEmail());
        txtPhoneNumber.setText(properties.getProperty("PHONE_N_MSG") + ": " + leaseRequestBean.getTenant().getPhoneNumber());
        txtMessage.setText(properties.getProperty("MESSAGE_MSG"));
        lblMsgContent.setText(leaseRequestBean.getMessage());
        btnAccept.setText(properties.getProperty("ACCEPT_MSG"));
        btnReject.setText(properties.getProperty("ACCEPT_MSG"));
    }

}
