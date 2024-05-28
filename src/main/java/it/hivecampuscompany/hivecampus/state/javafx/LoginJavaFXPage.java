package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.LoginPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.LoginJavaFXPageController;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicComponent;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LogoDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * The LoginJavaFXPage class represents the login page in the JavaFX user interface.
 * It extends the LoginPage class and provides methods for displaying the login page and handling user input.
 */
public class LoginJavaFXPage extends LoginPage {
    private static final Logger LOGGER = Logger.getLogger(LoginJavaFXPage.class.getName());

    /**
     * Constructs a LoginJavaFXPage object with the given context.
     * @param context The context object for the login page.
     * @author Marina Sotiropoulos
     */
    public LoginJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the login section of the application.
     * This method loads the login view, initializes the corresponding controller,
     * applies the necessary decorations, and displays the scene on the main stage.
     *
     * @throws InvalidSessionException if the session is invalid or expired
     * @author Marina Sotiropoulos
     */
    @Override
    public void handle() throws InvalidSessionException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/loginSection-view.fxml"));
            Node loginComponent = loader.load();

            LoginJavaFXPageController controller = loader.getController();
            controller.initialize(context, this);

            BasicComponent basicComponent = new BasicComponent(loginComponent);
            LogoDecorator logoDecorator = new LogoDecorator(basicComponent);

            Scene scene = new Scene((Parent) logoDecorator.setup());
            Stage stage = context.getStage();
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            LOGGER.severe(context.getLanguage().getProperty("ERROR_HANDLING_LOGIN_PAGE"));
        }
    }
}
