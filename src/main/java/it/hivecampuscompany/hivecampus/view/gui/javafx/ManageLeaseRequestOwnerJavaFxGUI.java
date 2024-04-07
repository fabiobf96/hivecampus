package it.hivecampuscompany.hivecampus.view.gui.javafx;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.CompositeVBox;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.LeaseRequestBasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.BasicAdDecorator;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class ManageLeaseRequestOwnerJavaFxGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        LoginManager loginManager = new LoginManager();
        UserBean userBean = new UserBean();
        userBean.setEmail("marco.neri@gmail.com");
        userBean.setPassword("pippo");
        SessionBean sessionBean = loginManager.login(userBean);
        AdManager adManager = new AdManager();
        LeaseRequestManager leaseRequestManager = new LeaseRequestManager();
        List<AdBean> adBeanList = adManager.searchAvailableAds(sessionBean);
        CompositeVBox vBox = new CompositeVBox();
        for (AdBean adBean : adBeanList) {
            CompositeVBox vBox1 = new CompositeVBox();
            vBox.addChildren(new BasicAdDecorator(adBean, vBox1));
            AdBean searchLease = new AdBean(adBean.getId(), null, null, 0);
            searchLease.setAdStatus(AdStatus.AVAILABLE);
            List<LeaseRequestBean> leaseRequestBeanList = leaseRequestManager.searchLeaseRequestsByAd(sessionBean, searchLease);
            for (LeaseRequestBean leaseRequestBean : leaseRequestBeanList) {
                vBox1.addChildren(new LeaseRequestBasicComponent(leaseRequestBean));
            }
        }
        Scene scene = new Scene((Parent) vBox.setup());
        stage.setScene(scene);
        stage.setTitle("Home Page GUI");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
