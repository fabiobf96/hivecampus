package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.OwnerHomePage;
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

public class OwnerHomeJavaFXPage extends OwnerHomePage {

    public OwnerHomeJavaFXPage(Context context) {
        super(context);
    }
    @Override
    public void handle() throws InvalidSessionException {

        CompositeTabPane tabPane = new CompositeTabPane();
        tabPane.setTabName("Search Room");
        tabPane.addChildren(addDynamicTab("/it/hivecampuscompany/hivecampus/tabRoomSearch-view.fxml"));

        tabPane.setTabName("Manage Requests");
        tabPane.addChildren(addDynamicTab("/it/hivecampuscompany/hivecampus/tabRoomSearch-view.fxml"));

        BarDecorator barDecorator = new BarDecorator(tabPane, context);

        Scene scene = new Scene((Parent) barDecorator.setup());
        Stage stage = context.getStage();
        stage.setScene(scene);
        stage.setTitle("Home Page GUI");
        stage.show();
    }

    private Component addDynamicTab(String contentFXML){

        try {
            // Carica il contenuto del tab da un file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentFXML));
            Node tabContent = loader.load();

            TabInitializerController controller = loader.getController();
            controller.initialize(context);

            return new BasicComponent(tabContent);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
