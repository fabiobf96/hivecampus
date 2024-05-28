package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.cli.ui.FormCliGUI;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for the Lease Request CLI page. It extends the CLIController class and uses the LeaseRequestManager class to send lease requests.
 * It provides methods to display the lease request form and send and manage lease requests.
 */

public class LeaseRequestCLIPageController extends CLIController {

    FormCliGUI formView;
    LeaseRequestManager manager = new LeaseRequestManager();

    /**
     * Constructor for the LeaseRequestCLIPageController class.
     */

    public LeaseRequestCLIPageController() {
        formView = new FormCliGUI();
    }

    /**
     * Method to display the home page.
     * It displays a welcome message and the lease request form.
     *
     * @author Marina Sotiropoulos
     */

    @Override
    public void homePage() {
        formView.clean();
        formView.displayWelcomeMessage(properties.getProperty("LEASE_REQUEST_FORM_MSG").toUpperCase());
        formView.displayMessage(properties.getProperty("STAY_TYPE_MSG"));
        formView.displayTypesPermanence();
        formView.displayMessage("\n" + properties.getProperty("START_STAY_MSG"));
        formView.displayMonths();
    }

    /**
     * Method to display the lease request form.
     * It gets the user's input for the lease request and sends the lease request.
     *
     * @param sessionBean The session bean of the user.
     * @param adBean The ad bean of the ad.
     * @return True if the lease request is sent, false otherwise.
     * @throws InvalidSessionException If the session is invalid.
     * @author Marina Sotiropoulos
     */

    public boolean leaseRequestForm(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        int typePermanence = convertTypePermanence(formView.getIntUserInput("\n" + properties.getProperty("PERMANENCE_TYPE_MSG")));
        int startPermanence = convertStartPermanence(formView.getIntUserInput(properties.getProperty("START_PERMANENCE_MSG")));
        String message = formView.getStringUserInput(properties.getProperty("MESSAGE_FOR_OWNER_MSG"));

        formView.displayMessage("1. " + properties.getProperty("SEND_LEASE_REQUEST_MSG"));
        formView.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));

        int choice = formView.getIntUserInput(properties.getProperty("CHOICE_MSG"));
        if (choice == 1) {
            LeaseRequestBean leaseRequestBean = new LeaseRequestBean();
            leaseRequestBean.setAdBean(adBean);
            leaseRequestBean.setTenant(null);
            leaseRequestBean.setDuration(typePermanence);
            leaseRequestBean.setLeaseMonth(startPermanence);
            leaseRequestBean.setMessage(message);
            leaseRequestBean.setStatus(LeaseRequestStatus.PROCESSING);

            String msg = manager.sendLeaseRequest(sessionBean, leaseRequestBean);
            formView.displayMessage(msg);
            pause();
            return true;
        } else return false;
    }

    /**
     * Method to convert the type of permanence.
     * It converts the input number into the type of permanence.
     *
     * @param inputNumber The input number.
     * @return The type of permanence.
     * @author Marina Sotiropoulos
     */

    private int convertTypePermanence(int inputNumber) {
        List<Integer> typesPermanence = Arrays.asList(6, 12, 24, 36);

        while (inputNumber < 1 || inputNumber > 4) {
            inputNumber = formView.getIntUserInput(properties.getProperty("PERMANENCE_TYPE_MSG"));
        }
        return typesPermanence.get(inputNumber - 1);
    }

    /**
     * Method to convert the start of permanence.
     * It converts the input number into the start of permanence.
     *
     * @param inputNumber The input number.
     * @return The start of permanence.
     * @author Marina Sotiropoulos
     */

    private int convertStartPermanence(int inputNumber) {
        while (inputNumber < 1 || inputNumber > 12) {
            inputNumber = formView.getIntUserInput(properties.getProperty("START_PERMANENCE_MSG"));
        }
        return inputNumber;
    }
}