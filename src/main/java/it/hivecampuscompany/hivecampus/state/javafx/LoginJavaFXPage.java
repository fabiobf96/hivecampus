package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.LoginPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.LoginJavaFXPageController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.HBoxDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class LoginJavaFXPage extends LoginPage {
    private static final Logger LOGGER = Logger.getLogger(LoginJavaFXPage.class.getName());
    public LoginJavaFXPage(Context context) {
        super(context);
    }
    @Override
    public void handle() throws InvalidSessionException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/loginSection-view.fxml"));
            Node loginComponent = loader.load();

            LoginJavaFXPageController controller = loader.getController();
            controller.initialize(context, this);

            BasicComponent basicComponent = new BasicComponent(loginComponent);
            HBoxDecorator hBoxDecorator = new HBoxDecorator(basicComponent);

            Scene scene = new Scene((Parent) hBoxDecorator.setup());
            Stage stage = context.getStage();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();


        } catch (Exception e) {
            LOGGER.severe("Error while handling LoginJavaFXPage");
        }
    }
}
