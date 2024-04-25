package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.io.IOException;
import java.util.List;

public class ManageLeaseOwnerCLIPageController extends CLIController {
    public ManageLeaseOwnerCLIPageController() {
        view = new CliGUI();
    }
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage("Manage Lease");
    }
    public int getChoice() {
        view.displayMessage("1. " + "Carica contratto");
        view.displayMessage("2. " + "Disdici contratto");
        view.displayMessage("3. " + "Torna indietro");
        return view.getIntUserInput("Scelta");
    }
    public AdBean selectAd(List<AdBean> adBeanList) {
        view.displayMessage("seleziona un annuncio per gestire l'affitto o l'ultima opzione per tornare indietro");
        return selectFromList(adBeanList, "");
    }
    public LeaseBean getLease(AdBean adBean, LeaseRequestBean leaseRequestBean) {
        while (true) {
            try {
                leaseRequestBean.setAdBean(adBean);
                view.displayMessage("inserisci un path per caricare un contratto o nulla per tornare indietro");
                view.displayMessage(adBean.toString());
                view.displayMessage(leaseRequestBean.toString());
                String path = getField("path",true);
                if (path.isBlank()) {
                    return null;
                }
                return new LeaseBean(leaseRequestBean, path);
            } catch (IOException e) {
                displayError("il path che hai inserito non è valido");
            }
        }
    }
}
