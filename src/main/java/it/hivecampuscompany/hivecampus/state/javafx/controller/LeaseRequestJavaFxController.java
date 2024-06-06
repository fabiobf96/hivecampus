package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.Month;
import it.hivecampuscompany.hivecampus.model.Permanence;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.AdSearchPage;
import it.hivecampuscompany.hivecampus.state.Context;
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

/**
 * The LeaseRequestJavaFxController class represents a controller for the lease request page in the JavaFX user interface.
 * It extends the JavaFxController class and provides methods for initializing the lease request view and handling user interactions.
 */

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
    int type = 0;
    int start = 0;

    public LeaseRequestJavaFxController() {
        // Default constructor
    }

    /*
    * Initializes the lease request view with the ad information and the user's input.
    * It sets the labels, checkboxes, and text area with the ad details and user's message.
    * It also handles the user's request to send the lease request to the ad owner.
    *
    * @param context The context object for the lease request page.
    * @param adSearchPage The ad search page object for navigation.
    * @param adBean The ad bean object with the ad information.
    * @param popUpStage The pop-up stage for the lease request view.
    *
    * @author Marina Sotiropoulos
    */
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

    /*
     * Initializes the logic for the lease start checkboxes.
     * It handles the selection of the start of permanence for the lease request.
     *
     * @author Marina Sotiropoulos
     */

    private void leaseStartCheckBoxLogic() {
        final CheckBox[] lastSelectedCheckBox = {null};

        for (Node node : hboxType.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                checkBox.setOnAction(event -> checkBoxBehavior(checkBox, lastSelectedCheckBox));
            }
        }
    }

    /*
     * Initializes the logic for the lease type checkboxes.
     * It handles the selection of the type of permanence for the lease request.
     *
     * @author Marina Sotiropoulos
     */

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

    /*
     * Handles the selection of a checkbox.
     * It sets the selected checkbox as the last selected checkbox.
     * If another checkbox is selected, it deselects the last selected checkbox.
     *
     * @param checkBox The checkbox object to be selected.
     * @param lastSelectedCheckBox The last selected checkbox object.
     *
     * @author Marina Sotiropoulos
     */

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

    /*
     * Handles the user's request to send the lease request to the ad owner.
     * It retrieves the selected type of permanence, the selected start of permanence, and the user's message.
     * It sends the lease request to the ad owner and displays a message to the user.
     *
     * @param adBean The ad bean object with the ad information.
     * @author Marina Sotiropoulos
     */

    private void handleSendRequest(AdBean adBean) {

        type = getSelectedType();
        start = getSelectedStart();
        String message = txaMessage.getText();

        if (type == 0 || start == 0 || message.isEmpty()){
            showAlert(WARNING, properties.getProperty(WARNING_TITLE_MSG), properties.getProperty("EMPTY_FIELDS_MSG"));
            return;
        }

        LeaseRequestBean leaseRequestBean = new LeaseRequestBean();
        leaseRequestBean.setAdBean(adBean);
        leaseRequestBean.setTenant(null);
        leaseRequestBean.setDuration(type);
        leaseRequestBean.setLeaseMonth(start);
        leaseRequestBean.setMessage(message);
        leaseRequestBean.setStatus(LeaseRequestStatus.PROCESSING);

        try {
            String msg = manager.sendLeaseRequest(context.getSessionBean(), leaseRequestBean);
            showAlert(INFORMATION, properties.getProperty(INFORMATION_TITLE_MSG), msg);
            popUpStage.close();
            goToHomepage();

        } catch (InvalidSessionException e) {
            context.invalidSessionExceptionHandle();
        }
    }

    /*
     * Retrieves the selected type of permanence.
     * It checks which checkbox is selected and returns the corresponding int value.
     *
     * @return The int representing the selected type of permanence.
     * @author Marina Sotiropoulos
     */

    private int getSelectedType() {
        if (ckbSix.isSelected()) {
            return Permanence.SIX_MONTHS.getPermanence();
        } else if (ckbTwelve.isSelected()) {
            return Permanence.TWELVE_MONTHS.getPermanence();
        } else if (ckbTwentyFour.isSelected()) {
            return Permanence.TWENTY_FOUR_MONTHS.getPermanence();
        } else if (ckbThirtySix.isSelected()) {
            return Permanence.THIRTY_SIX_MONTHS.getPermanence();
        } else {
            return 0;
        }
    }

    /*
     * Retrieves the selected start of permanence.
     * It checks which checkbox is selected and returns the corresponding int value.
     *
     * @return The int representing the selected start of permanence.
     * @author Marina Sotiropoulos
     */

    private int getSelectedStart() {
        if (ckbSeptember.isSelected()) {
            return Month.SEPTEMBER.getMonth();
        } else if (ckbOctober.isSelected()) {
            return Month.OCTOBER.getMonth();
        } else if (ckbNovember.isSelected()) {
            return Month.NOVEMBER.getMonth();
        } else if (ckbDecember.isSelected()) {
            return Month.DECEMBER.getMonth();
        } else if (ckbJanuary.isSelected()) {
            return Month.JANUARY.getMonth();
        } else if (ckbFebruary.isSelected()) {
            return Month.FEBRUARY.getMonth();
        } else if (ckbMarch.isSelected()) {
            return Month.MARCH.getMonth();
        } else if (ckbApril.isSelected()) {
            return Month.APRIL.getMonth();
        } else if (ckbMay.isSelected()) {
            return Month.MAY.getMonth();
        } else if (ckbJune.isSelected()) {
            return Month.JUNE.getMonth();
        } else if (ckbJuly.isSelected()) {
            return Month.JULY.getMonth();
        } else if (ckbAugust.isSelected()) {
            return Month.AUGUST.getMonth();
        } else {
            return 0;
        }
    }

    /*
     * Navigates the user to the homepage.
     *
     * @author Marina Sotiropoulos
     */

    private void goToHomepage() {
        adSearchPage.goToTenantHomePage(new TenantHomeJavaFXPage(context));
        context.request();
    }
}