package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.controller.HomePageJavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Logger;

public class BarDecorator extends Decorator{

    private static final Logger LOGGER = Logger.getLogger(BarDecorator.class.getName());

    private final Context context;

    public BarDecorator(Component component, Context context) {
        super(component);
        this.context =  context;
    }
    protected Node applyBarDecoration(Node child) {
        try {
            VBox vBox = new VBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/homeBar-view.fxml"));
            Node root = loader.load();

            HomePageJavaFxController controller = loader.getController();
            controller.initializeHomeView(context);

            //se devo passare un sessionBean devo ottenere il controller e chiamare initialize(sessionBean)
            vBox.getChildren().addAll(root, child);
            VBox.setVgrow(child, Priority.ALWAYS);
            return vBox;
        } catch (IOException e) {
            LOGGER.severe("Error while applying BarDecorator");
            return child;
        }
    }
    @Override
    public Node setup() {
        return this.applyBarDecoration(super.setup());
    }
}
