package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.LoginJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.TenantHomeJavaFXPage;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeaseRequestJavaFxController extends JavaFxController{

    private AdSearchPage adSearchPage;
    private LeaseRequestManager manager;

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblType;
    @FXML
    private HBox hboxType;
    @FXML
    private CheckBox ckbSix;
    @FXML
    private CheckBox ckbTwelve;
    @FXML
    private CheckBox ckbTwentyFour;
    @FXML
    private CheckBox ckbThirtySix;
    @FXML
    private Label lblStart;
    @FXML
    private VBox vboxStart;
    @FXML
    private CheckBox ckbSeptember;
    @FXML
    private CheckBox ckbOctober;
    @FXML
    private CheckBox ckbNovember;
    @FXML
    private CheckBox ckbDecember;
    @FXML
    private CheckBox ckbJanuary;
    @FXML
    private CheckBox ckbFebruary;
    @FXML
    private CheckBox ckbMarch;
    @FXML
    private CheckBox ckbApril;
    @FXML
    private CheckBox ckbMay;
    @FXML
    private CheckBox ckbJune;
    @FXML
    private CheckBox ckbJuly;
    @FXML
    private CheckBox ckbAugust;
    @FXML
    private Label lblMessage;
    @FXML
    private TextArea txaMessage;
    @FXML
    private Button btnSend;

    private Stage popUpStage;

    public LeaseRequestJavaFxController() {
        // Default constructor
    }

    public void initialize(Context context, AdSearchPage adSearchPage, AdBean adBean, Stage popUpStage) {
        this.context = context;
        this.adSearchPage = adSearchPage;
        this.popUpStage = popUpStage;
        this.manager = new LeaseRequestManager();

        lblTitle.setText(properties.getProperty("LEASE_REQUEST_MSG").toUpperCase());
        lblType.setText(properties.getProperty("STAY_TYPE_MSG"));
        ckbSix.setText(properties.getProperty("SIX_MONTHS_MSG").toUpperCase());
        ckbTwelve.setText(properties.getProperty("TWELVE_MONTHS_MSG").toUpperCase());
        ckbTwentyFour.setText(properties.getProperty("TWENTY_FOUR_MONTHS_MSG").toUpperCase());
        ckbThirtySix.setText(properties.getProperty("THIRTY_SIX_MONTHS_MSG").toUpperCase());
        lblStart.setText(properties.getProperty("START_STAY_MSG"));
        ckbSeptember.setText(properties.getProperty("SEPTEMBER_MSG").toUpperCase());
        ckbOctober.setText(properties.getProperty("OCTOBER_MSG").toUpperCase());
        ckbNovember.setText(properties.getProperty("NOVEMBER_MSG").toUpperCase());
        ckbDecember.setText(properties.getProperty("DECEMBER_MSG").toUpperCase());
        ckbJanuary.setText(properties.getProperty("JANUARY_MSG").toUpperCase());
        ckbFebruary.setText(properties.getProperty("FEBRUARY_MSG").toUpperCase());
        ckbMarch.setText(properties.getProperty("MARCH_MSG").toUpperCase());
        ckbApril.setText(properties.getProperty("APRIL_MSG").toUpperCase());
        ckbMay.setText(properties.getProperty("MAY_MSG").toUpperCase());
        ckbJune.setText(properties.getProperty("JUNE_MSG").toUpperCase());
        ckbJuly.setText(properties.getProperty("JULY_MSG").toUpperCase());
        ckbAugust.setText(properties.getProperty("AUGUST_MSG").toUpperCase());
        lblMessage.setText(properties.getProperty("MESSAGE_FOR_OWNER_MSG"));
        txaMessage.setPromptText(properties.getProperty("MESSAGE_PROMPT_MSG"));
        btnSend.setText(properties.getProperty("SEND_LEASE_REQUEST_MSG"));

        leaseTypeCheckBoxLogic();
        leaseStartCheckBoxLogic();

        btnSend.setOnAction(event -> handleSendRequest(adBean));
    }

    private void leaseStartCheckBoxLogic() {
        final CheckBox[] lastSelectedCheckBox = {null};

        for (Node node : hboxType.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                checkBox.setOnAction(event -> checkBoxBehavior(checkBox, lastSelectedCheckBox));
            }
        }
    }

    private void leaseTypeCheckBoxLogic() {
        final CheckBox[] lastSelectedCheckBox = {null};

        for (Node node : vboxStart.getChildren()) {
            if (node instanceof HBox hbox) {
                for (Node childNode : hbox.getChildren()) {
                    if (childNode instanceof CheckBox checkBox) {
                        checkBox.setOnAction(event -> checkBoxBehavior(checkBox, lastSelectedCheckBox));
                    }
                }
            }
        }
    }

    private void checkBoxBehavior(CheckBox checkBox, CheckBox[] lastSelectedCheckBox) {
        if (checkBox.isSelected()) {
            if (lastSelectedCheckBox[0] != null) {
                lastSelectedCheckBox[0].setSelected(false);
            }
            lastSelectedCheckBox[0] = checkBox;
        } else {
            lastSelectedCheckBox[0] = null;
        }
    }

    private void handleSendRequest(AdBean adBean) {
        // Get the selected type of permanence
        String type = getSelectedType();
        // Get the selected start of permanence
        String start = getSelectedStart();
        // Get the message
        String message = txaMessage.getText();

        if (type == null || start == null || message.isBlank()) {
            showAlert(WARNING, properties.getProperty(WARNING_TITLE_MSG), properties.getProperty("EMPTY_FIELDS_MSG"));
            return;
        }

        LeaseRequestBean leaseRequestBean = new LeaseRequestBean();
        leaseRequestBean.setAdBean(adBean);
        leaseRequestBean.setTenant(null);
        leaseRequestBean.setDuration(type);
        leaseRequestBean.setMonth(start);
        leaseRequestBean.setMessage(message);
        leaseRequestBean.setStatus(LeaseRequestStatus.PROCESSING);

        try {
            String msg = manager.sendLeaseRequest(context.getSessionBean(), leaseRequestBean);
            showAlert(INFORMATION, properties.getProperty(INFORMATION_TITLE_MSG), msg);
            popUpStage.close();
            goToHomepage();

        } catch (InvalidSessionException e) {
            showAlert(ERROR, properties.getProperty(ERROR_TITLE_MSG), properties.getProperty("INVALID_SESSION_MSG"));
            goToLogin();
        }
    }

    private String getSelectedType() {
        if (ckbSix.isSelected()) {
            return properties.getProperty("SIX_MONTHS_MSG");
        } else if (ckbTwelve.isSelected()) {
            return properties.getProperty("TWELVE_MONTHS_MSG");
        } else if (ckbTwentyFour.isSelected()) {
            return properties.getProperty("TWENTY_FOUR_MONTHS_MSG");
        } else if (ckbThirtySix.isSelected()) {
            return properties.getProperty("THIRTY_SIX_MONTHS_MSG");
        } else {
            return null;
        }
    }

    private String getSelectedStart() {
        if (ckbSeptember.isSelected()) {
            return properties.getProperty("SEPTEMBER_MSG");
        } else if (ckbOctober.isSelected()) {
            return properties.getProperty("OCTOBER_MSG");
        } else if (ckbNovember.isSelected()) {
            return properties.getProperty("NOVEMBER_MSG");
        } else if (ckbDecember.isSelected()) {
            return properties.getProperty("DECEMBER_MSG");
        } else if (ckbJanuary.isSelected()) {
            return properties.getProperty("JANUARY_MSG");
        } else if (ckbFebruary.isSelected()) {
            return properties.getProperty("FEBRUARY_MSG");
        } else if (ckbMarch.isSelected()) {
            return properties.getProperty("MARCH_MSG");
        } else if (ckbApril.isSelected()) {
            return properties.getProperty("APRIL_MSG");
        } else if (ckbMay.isSelected()) {
            return properties.getProperty("MAY_MSG");
        } else if (ckbJune.isSelected()) {
            return properties.getProperty("JUNE_MSG");
        } else if (ckbJuly.isSelected()) {
            return properties.getProperty("JULY_MSG");
        } else if (ckbAugust.isSelected()) {
            return properties.getProperty("AUGUST_MSG");
        } else {
            return null;
        }
    }

    private void goToHomepage() {
        adSearchPage.goToTenantHomePage(new TenantHomeJavaFXPage(context));
        context.request();
    }

    private void goToLogin() {
        adSearchPage.goToLoginPage(new LoginJavaFXPage(context));
        context.request();
    }
}

