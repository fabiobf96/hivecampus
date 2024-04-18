package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.javafx.ManageAdsJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.OwnerHomeJavaFXPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.logging.Logger;

public class ManageAdsJavaFXPageController extends JavaFxController implements TabInitializerController{

    private ManageAdsPage manageAdsPage;
    private static final Logger LOGGER = Logger.getLogger(ManageAdsJavaFXPageController.class.getName());

    @FXML
    Button btnCreate;
    @FXML
    Button btnBack;
    @FXML
    Button btnPublish;
    @FXML
    ListView<Node> lvRooms;

    public ManageAdsJavaFXPageController() {
        // Default constructor
    }

    public void initialize(Context context) {
        this.context = context;
        btnCreate.setOnAction(event -> handleCreateAd());
    }

    public void initializeCreateAd(Context context, ManageAdsPage manageAdsPage) {
        this.context = context;
        this.manageAdsPage = manageAdsPage;
        btnBack.setOnAction(event -> handleBack());
    }

    private void handleCreateAd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/createAdForm-view.fxml"));
            context.getTab(0).setContent(loader.load()); // Cariamento del tab createAdForm (possibili modifiche)
            ManageAdsJavaFXPageController controller = loader.getController();
            controller.initializeCreateAd(context, new ManageAdsJavaFXPage(context));

        } catch (Exception e) {
            LOGGER.severe("Error while handling CreateAd");
        }
    }

    private void handleBack() {
        manageAdsPage.goToOwnerHomePage(new OwnerHomeJavaFXPage(context));
        context.request();
    }
}