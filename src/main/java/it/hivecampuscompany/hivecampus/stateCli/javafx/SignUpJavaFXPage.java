package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.SignUpPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.SignUpJavaFXPageController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.LogoDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class SignUpJavaFXPage extends SignUpPage {
    private static final Logger LOGGER = Logger.getLogger(SignUpJavaFXPage.class.getName());
    public SignUpJavaFXPage(Context context) {
        super(context);
    }
    @Override
    public void handle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/signUpSection-view.fxml"));
            Node loginComponent = loader.load();

            SignUpJavaFXPageController controller = loader.getController();
            controller.initializeSignUpView(context, this);

            BasicComponent basicComponent = new BasicComponent(loginComponent);
            LogoDecorator logoDecorator = new LogoDecorator(basicComponent);

            Scene scene = new Scene((Parent) logoDecorator.setup());
            Stage stage = context.getStage();
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            LOGGER.severe("Error while handling SignUpJavaFXPage");
        }

    }
}
