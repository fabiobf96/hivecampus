package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.InitialPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.InitialJavaFXPageController;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicComponent;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.LogoDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * The InitialJavaFXPage class represents the initial page in the JavaFX user interface.
 * It extends the InitialPage class and provides methods for displaying the initial page and handling user input.
 */
public class InitialJavaFXPage extends InitialPage {
    private static final Logger LOGGER = Logger.getLogger(InitialJavaFXPage.class.getName());

    /**
     * Constructs an InitialJavaFXPage object with the given context.
     * @param context The context object for the initial page.
     * @author Marina Sotiropoulos
     */
    public InitialJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the initial page in the application.
     * This method loads the main view, initializes the corresponding controller,
     * applies the necessary decorations, and displays the scene on the main stage.
     *
     * @throws InvalidSessionException if the session is invalid or expired
     * @author Marina Sotiropoulos
     */
    @Override
    public void handle() throws InvalidSessionException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/main-view.fxml"));
            Parent root = loader.load();

            InitialJavaFXPageController controller = loader.getController();
            controller.initialize(context, this);

            BasicComponent basicComponent = new BasicComponent(root);
            LogoDecorator logoDecorator = new LogoDecorator(basicComponent);

            Scene scene = new Scene((Parent) logoDecorator.setup());
            Stage stage = context.getStage();
            stage.setScene(scene);
            stage.setTitle("HiveCampus");
            stage.show();

        } catch (Exception e) {
            LOGGER.severe(context.getLanguage().getProperty("ERROR_HANDLING_INITIAL_PAGE"));
        }
    }

    /**
     * Returns the context object for the initial page.
     * @return The context object for the initial page.
     * @author Marina Sotiropoulos
     */
    public Context getContext() {
        return context;
    }
}
