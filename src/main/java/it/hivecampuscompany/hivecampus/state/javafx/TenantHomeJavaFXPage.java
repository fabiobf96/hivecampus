package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.TenantHomePage;
import it.hivecampuscompany.hivecampus.view.controller.javafx.TabInitializerController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.CompositeTabPane;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.BarDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Logger;

public class TenantHomeJavaFXPage extends TenantHomePage {

    private static final Logger LOGGER = Logger.getLogger(TenantHomeJavaFXPage.class.getName());

    public TenantHomeJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        CompositeTabPane tabPane = new CompositeTabPane();
        tabPane.setTabName("Room search");
        tabPane.addChildren(addDynamicTab("/it/hivecampuscompany/hivecampus/tabRoomSearch-view.fxml"));

        tabPane.setTabName("Manage Requests");
        tabPane.addChildren(addDynamicTab("/it/hivecampuscompany/hivecampus/tabManageRequests-view.fxml"));

        BarDecorator barDecorator = new BarDecorator(tabPane, context);

        Scene scene = new Scene((Parent) barDecorator.setup());
        Stage stage = context.getStage();
        stage.setScene(scene);
        stage.show();
    }

    private Component addDynamicTab(String contentFXML) {
        try {
            // Carica il contenuto del tab da un file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentFXML));
            Node tabContent = loader.load();

            TabInitializerController controller = loader.getController();
            controller.initialize(context);

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