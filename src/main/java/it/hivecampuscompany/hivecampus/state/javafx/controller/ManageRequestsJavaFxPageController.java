package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.view.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * The ManageRequestsJavaFxPageController represents the controller for the manage requests page in the JavaFX user interface.
 * It is responsible for managing the requests of the tenants.
 */

public class ManageRequestsJavaFxPageController extends JavaFxController {

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

    /**
     * Initializes the controller with the lease request bean.
     * It sets the text fields with the information of the lease request.
     *
     * @param leaseRequestBean the lease request bean
     */

    @FXML
    public void initialize(LeaseRequestBean leaseRequestBean) {
        txtName.setText(leaseRequestBean.getTenant().getName());
        txtSurname.setText(leaseRequestBean.getTenant().getSurname());
        txtStart.setText(properties.getProperty("MONTH_MSG") + ": " + leaseRequestBean.getLeaseMonth());
        txtDuration.setText(properties.getProperty("DURATION_MSG") + ": " + leaseRequestBean.getDuration() + " " + properties.getProperty("MONTHS_MSG"));
        txtEmail.setText(properties.getProperty("EMAIL_MSG") + ": " + leaseRequestBean.getTenant().getEmail());
        txtPhoneNumber.setText(properties.getProperty("PHONE_N_MSG") + ": " + leaseRequestBean.getTenant().getPhoneNumber());
        txtMessage.setText(properties.getProperty("MESSAGE_MSG"));
        lblMsgContent.setText(Utility.formatText(leaseRequestBean.getMessage()));
    }
}
