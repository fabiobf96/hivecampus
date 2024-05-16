package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the JavaFX application.
 * This class initializes the JavaFX application by setting the initial state to the initial JavaFX page.
 * It then requests the initial state to display the initial page.
 * The application will continue to run until the user exits the application.
 */

public class MainJavaFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Context context = new Context();
        context.setStage(stage);
        context.setState(new InitialJavaFXPage(context));
        context.request();
    }
}
