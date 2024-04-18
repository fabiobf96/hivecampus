package it.hivecampuscompany.hivecampus;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.InitialJavaFXPage;
import javafx.application.Application;
import javafx.stage.Stage;

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
