package it.hivecampuscompany.hivecampus.view.gui.javafx;

import it.hivecampuscompany.hivecampus.view.controller.javafx.LoginJavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.HBoxDecorator;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Properties;
import java.util.logging.Logger;

public class LoginJavaFxGUI extends Application {

    private final Logger LOGGER = Logger.getLogger(LoginJavaFxGUI.class.getName());
    protected Properties properties;

    public LoginJavaFxGUI(){
        properties = LanguageLoader.getLanguageProperties();
    }
    @Override
    public void start(Stage stage) {
        startLoginSection();
        HBoxDecorator hBoxDecorator = new HBoxDecorator(startLoginSection());

        Scene scene = new Scene((Parent) hBoxDecorator.setup());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    public BasicComponent startLoginSection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/loginSection-view.fxml"));
            Node loginComponent = loader.load();

            LoginJavaFxController controller = loader.getController();
            controller.initialize();

            return new BasicComponent(loginComponent);
        } catch (Exception e) {
            LOGGER.severe(properties.getProperty("ERROR_LOADING_MSG"));
            System.exit(1);
        }
        return null;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
