package it.hivecampuscompany.hivecampus.view.gui.javafx;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.view.controller.javafx.TabInitializerController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class HomePageJavaFxGUI extends Application {

    protected static final Logger LOGGER = Logger.getLogger(HomePageJavaFxGUI.class.getName());
    protected Properties properties;

    protected HomePageJavaFxGUI(){
        properties = LanguageLoader.getLanguageProperties();
    }

    public abstract void startWithSession(Stage stage, SessionBean sessionBean);
    protected Component addDynamicTab(String contentFXML, SessionBean sessionBean){

        try {
            // Carica il contenuto del tab da un file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentFXML));
            Node tabContent = loader.load();

            TabInitializerController controller = loader.getController();
            controller.initialize(sessionBean);

            return new BasicComponent(tabContent);
        } catch (IOException | RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Error while loading.", e);
            System.exit(1);
        }
        return null;
    }

    @Override
    public abstract void start(Stage stage) throws Exception;
}
