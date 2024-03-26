package it.hivecampuscompany.hivecampus.view.gui.javafx;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.CompositeTabPane;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.BarDecorator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OwnerHomePageJavaFxGUI extends HomePageJavaFxGUI {

    /**
     * This method is empty because JavaFX Application requires a start method to launch the application.
     * In this case, the GUI is started using the startWithController method.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        // This method is intentionally left empty as GUI is started using startWithController method.
    }
    public void startWithSession(Stage stage, SessionBean sessionBean){
        CompositeTabPane tabPane = new CompositeTabPane();
        tabPane.setTabName("Search Room");
        tabPane.addChildren(addDynamicTab("/it/hivecampuscompany/hivecampus/tabRoomSearch-view.fxml", sessionBean));

        tabPane.setTabName("Manage Requests");
        tabPane.addChildren(addDynamicTab("/it/hivecampuscompany/hivecampus/tabRoomSearch-view.fxml", sessionBean));

        BarDecorator barDecorator = new BarDecorator(tabPane, sessionBean);

        Scene scene = new Scene((Parent) barDecorator.setup());
        stage.setScene(scene);
        stage.setTitle("Home Page GUI");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
