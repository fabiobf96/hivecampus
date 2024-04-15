package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.InitialPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.InitialJavaFXPageController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.TabInitializerController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.HBoxDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class InitialJavaFXPage extends InitialPage {

    public InitialJavaFXPage(Context context) {
        super(context);
    }
    @Override
    public void handle() throws InvalidSessionException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/main-view.fxml"));
            Parent root = loader.load();

            InitialJavaFXPageController controller = loader.getController();
            controller.initialize(this);

            BasicComponent basicComponent = new BasicComponent(root);

            HBoxDecorator hBoxDecorator = new HBoxDecorator(basicComponent);

            Scene scene = new Scene((Parent) hBoxDecorator.setup());
            Stage stage = context.getStage();
            stage.setScene(scene);
            stage.setTitle("HiveCampus");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Component addDynamicTab(String contentFXML, SessionBean sessionBean){

        try {
            // Carica il contenuto del tab da un file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentFXML));
            Node tabContent = loader.load();

            TabInitializerController controller = loader.getController();
            //controller.initialize(sessionBean);

            return new BasicComponent(tabContent);
        } catch (IOException | RuntimeException e) {
            //LOGGER.log(Level.SEVERE, "Error while loading.", e);
            System.exit(1);
        }
        return null;
    }

    public Context getContext() {
        return context;
    }
}
