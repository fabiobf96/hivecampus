package it.hivecampuscompany.hivecampus.state.javafx.controller;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.javafx.ManageAdsJavaFXPage;
import it.hivecampuscompany.hivecampus.state.javafx.OwnerHomeJavaFXPage;
import it.hivecampuscompany.hivecampus.view.controller.javafx.JavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.TabInitializerController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.CompositeTabPane;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.BarDecorator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
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
            CompositeTabPane tabPane = new CompositeTabPane();
            tabPane.setTabName("Manage Ads");
            tabPane.addChildren(addDynamicTab("/it/hivecampuscompany/hivecampus/createAdForm-view.fxml"));

            BarDecorator barDecorator = new BarDecorator(tabPane, context);

            Scene scene = new Scene((Parent) barDecorator.setup());
            Stage stage = context.getStage();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            LOGGER.severe("Error while handling CreateAd");
        }
    }

    private void handleBack() {
        manageAdsPage.goToOwnerHomePage(new OwnerHomeJavaFXPage(context));
    }


    private Component addDynamicTab(String contentFXML){
        try {
            // Carica il contenuto del tab da un file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentFXML));
            Node tabContent = loader.load();

            ManageAdsJavaFXPageController controller = loader.getController();
            controller.initializeCreateAd(context, new ManageAdsJavaFXPage(context));

            // Verifica se il nodo caricato è null
            if (tabContent != null) {
                return new BasicComponent(tabContent);
            } else {
                LOGGER.severe("Node loaded from FXML is null");
                return null;
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.severe("Error while adding dynamic tab: " + e.getMessage());
            // Gestisci l'eccezione qui, ad esempio mostrando un messaggio di errore all'utente
            return null;
        }
    }
}
