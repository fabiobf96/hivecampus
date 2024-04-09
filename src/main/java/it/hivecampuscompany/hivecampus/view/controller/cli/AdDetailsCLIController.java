package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.view.controller.LeaseRequestCLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.util.List;

public class AdDetailsCLIController extends CLIController{

    List<AdBean> adBeans;

    public AdDetailsCLIController(List<AdBean> adBeans){
        this.adBeans = adBeans;
        view = new CliGUI();
        homePage();
    }
    @Override
    public void homePage() {
        view.displayMessage("1. " + properties.getProperty("AD_DETAILS_MSG"));
        view.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));

        switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))){
            case 1:
                int index = Integer.parseInt(getField(properties.getProperty("AD_INDEX_REQUEST_MSG"), false));
                // Display the details of the selected ad
                showAdDetails(adBeans, index);
                break;
            case 2:
                view.clean();
                break;
            default:
                view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
                homePage();
        }
    }

    private void showAdDetails(List<AdBean> adBeans, int index) {
        view.clean();
        view.displayMessage(properties.getProperty("ROOM_TYPE_MSG") + adBeans.get(index - 1).getDetails());
        // Call the controller to manage the lease request
        new LeaseRequestCLIController(adBeans.get(index - 1));
    }
}
