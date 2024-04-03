package it.hivecampuscompany.hivecampus.view.gui.javafx;

import it.hivecampuscompany.hivecampus.view.controller.javafx.SignUpJavaFxController;
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

public class SignUpJavaFxGUI extends Application {

    private final Logger LOGGER = Logger.getLogger(SignUpJavaFxGUI.class.getName());
    protected Properties properties;

    public SignUpJavaFxGUI(){
        properties = LanguageLoader.getLanguageProperties();
    }

    @Override
    public void start(Stage stage) {
        startSignUpSection();
        HBoxDecorator hBoxDecorator = new HBoxDecorator(startSignUpSection());

        Scene scene = new Scene((Parent) hBoxDecorator.setup());
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

    public BasicComponent startSignUpSection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/signUpSection-view.fxml"));
            Node signUpComponent = loader.load();

            SignUpJavaFxController controller = loader.getController();
            controller.initializeSignUpView();

            return new BasicComponent(signUpComponent);
        } catch (Exception e) {
            LOGGER.severe(properties.getProperty("ERROR_LOADING_MSG"));
            System.exit(1);
        }
        return null;
    }
}
