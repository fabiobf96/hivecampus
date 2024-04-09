package it.hivecampuscompany.hivecampus.view.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

public class LeaseRequestCLIController extends CLIController {
    AdBean adBean;

    public LeaseRequestCLIController(AdBean adBean) {
        view = new CliGUI();
        this.adBean = adBean;
        homePage();
    }
    @Override
    public void homePage() {
        view.displayMessage("1. " + properties.getProperty("SEND_LEASE_REQUEST_MSG"));
        view.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));

        switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))){
            case 1:
                leaseRequestForm(adBean);
                break;
            case 2:
                view.clean();
                break;
            default:
                view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
                homePage();
        }
    }

    private void leaseRequestForm(AdBean adBean) {
        view.displayMessage(properties.getProperty("LEASE_REQUEST_FORM_MSG"));
        view.displayMessage(properties.getProperty("TYPE_PERMANENCE_MSG"));
        view.displayMessage(properties.getProperty("START_PERMANENCE_MSG"));
    }
}
