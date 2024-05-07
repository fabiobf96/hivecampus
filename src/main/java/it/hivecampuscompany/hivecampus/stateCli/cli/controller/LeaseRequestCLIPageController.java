package it.hivecampuscompany.hivecampus.stateCli.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.viewCli.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.viewCli.gui.cli.FormCliGUI;

import java.util.Arrays;
import java.util.List;

public class LeaseRequestCLIPageController extends CLIController {

    FormCliGUI formView;
    LeaseRequestManager manager = new LeaseRequestManager();

    public LeaseRequestCLIPageController() {
        formView = new FormCliGUI();
    }

    @Override
    public void homePage() {
        formView.clean();
        formView.displayWelcomeMessage(properties.getProperty("LEASE_REQUEST_FORM_MSG").toUpperCase());
        formView.displayMessage(properties.getProperty("STAY_TYPE_MSG"));
        formView.displayTypesPermanence();
        formView.displayMessage(properties.getProperty("START_STAY_MSG"));
        formView.displayMonths();
    }

    public boolean leaseRequestForm(SessionBean sessionBean, AdBean adBean) throws InvalidSessionException {
        String typePermanence = convertTypePermanence(formView.getStringUserInput("\n" + properties.getProperty("PERMANENCE_TYPE_MSG")));
        String startPermanence = convertStartPermanence(formView.getStringUserInput(properties.getProperty("START_PERMANENCE_MSG")));
        String message = formView.getStringUserInput(properties.getProperty("MESSAGE_FOR_OWNER_MSG"));

        formView.displayMessage("1. " + properties.getProperty("SEND_LEASE_REQUEST_MSG"));
        formView.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));

        int choice = formView.getIntUserInput(properties.getProperty("CHOICE_MSG"));
        if (choice == 1) {
            LeaseRequestBean leaseRequestBean = new LeaseRequestBean();
            leaseRequestBean.setAdBean(adBean);
            leaseRequestBean.setTenant(null);
            leaseRequestBean.setDuration(typePermanence);
            leaseRequestBean.setMonth(startPermanence);
            leaseRequestBean.setMessage(message);
            leaseRequestBean.setStatus(LeaseRequestStatus.PROCESSING);

            String msg = manager.sendLeaseRequest(sessionBean, leaseRequestBean);
            formView.displayMessage(msg);
            pause();
            return true;
        } else return false;
    }

    private String convertTypePermanence(String userInput) {
        List<String> typesPermanence = Arrays.asList("6", "12", "24", "36");
        try {
            int inputNumber = Integer.parseInt(userInput);
            if (inputNumber >= 1 && inputNumber <= 4) {
               return typesPermanence.get(inputNumber - 1);
            }
        } catch (NumberFormatException e) {
           formView.displayMessage(properties.getProperty("INVALID_INPUT_MSG"));
        }
        return convertTypePermanence(formView.getStringUserInput(properties.getProperty("PERMANENCE_TYPE_MSG")));
    }

    private String convertStartPermanence(String userInput) {
        List<String> months = Arrays.asList("1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12");
        try {
            int inputNumber = Integer.parseInt(userInput);
            if (inputNumber >= 1 && inputNumber <= 12) {
              return months.get(inputNumber - 1);
            }
        } catch (NumberFormatException e) {
            formView.displayMessage(properties.getProperty("INVALID_INPUT_MSG"));
        }
        return convertStartPermanence(formView.getStringUserInput(properties.getProperty("START_PERMANENCE_MSG")));
    }
}