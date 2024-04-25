package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.util.List;

public class ManageRequestsOwnerCLIPageController extends CLIController {
    public ManageRequestsOwnerCLIPageController() {
        view = new CliGUI();
    }
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage("Manage Lease Request");
    }

    public AdBean selectAd(List<AdBean> adBeanList) {
        view.displayMessage("seleziona un annuncio per gestirlo o seleziona l'ultima opzione per tornare indietro");
        return selectFromList(adBeanList, "");
    }

    public LeaseRequestBean selectRequest (List<LeaseRequestBean> leaseRequestBeanList) {
        view.displayMessage("seleziona un richiesta di affitto o seleziona l'ultima opzione per tornare indietro");
        return selectFromList(leaseRequestBeanList, "");
    }

    public int getChoice(AdBean adBean, LeaseRequestBean leaseRequestBean) {
        view.displayMessage(adBean.toString());
        view.displayMessage(leaseRequestBean.toString());
        view.displayMessage("\n");
        view.displayMessage("1. " + "accetta richiesta");
        view.displayMessage("2. " + "rifiuta richiesta");
        view.displayMessage("3. " + "torna indietro");
        return view.getIntUserInput("choice");
    }
}
