package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.SignUpPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.SignUpJavaFXPageController;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicComponent;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LogoDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * The SignUpJavaFXPage class represents the sign up page in the JavaFX user interface.
 * It extends the SignUpPage class and provides methods for displaying the sign up page and handling user input.
 */
public class SignUpJavaFXPage extends SignUpPage {
    private static final Logger LOGGER = Logger.getLogger(SignUpJavaFXPage.class.getName());

    /**
     * Constructs a SignUpJavaFXPage object with the given context.
     * @param context The context object for the sign-up page.
     * @author Marina Sotiropoulos
     */
    public SignUpJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the sign-up section of the application.
     * This method loads the sign-up view, initializes the corresponding controller,
     * applies the necessary decorations, and displays the scene on the main stage.
     *
     * @author Marina Sotiropoulos
     */
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
            LOGGER.severe(context.getLanguage().getProperty("ERROR_HANDLING_SIGN_UP_PAGE"));
        }

    }
}
