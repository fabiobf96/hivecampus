package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.util.List;

public class AdCLIController extends CLIController{
    AdManager adManager;
    public AdCLIController(SessionBean sessionBean) {
        adManager = new AdManager();
        view = new CliGUI();
        this.sessionBean = sessionBean;
    }
    @Override
    public void homePage() {
        view.displayWelcomeMessage("ad".toUpperCase());
    }

    public AdBean getAvailableAdBean(){
        try {
            List<AdBean> adBeanList = adManager.searchAvailableAds(sessionBean);
            view.displayMessage("Select an ad for manage lease request: ");
            for (AdBean adBean : adBeanList){
                view.displayMessage(adBeanList.indexOf(adBean) + ") " +adBean.toString());
            }
            return adBeanList.get(view.getIntUserInput("choice"));
        } catch (InvalidSessionException e) {
            throw new RuntimeException(e);
        }
    }
}
