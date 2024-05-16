package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.InitialPage;
import it.hivecampuscompany.hivecampus.state.javafx.controller.InitialJavaFXPageController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.LogoDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class InitialJavaFXPage extends InitialPage {
    private static final Logger LOGGER = Logger.getLogger(InitialJavaFXPage.class.getName());

    public InitialJavaFXPage(Context context) {
        super(context);
    }
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

    public Context getContext() {
        return context;
    }
}
