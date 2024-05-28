package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.controller.HomePageJavaFxController;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * The BarDecorator class is a concrete decorator class that extends the Decorator class.
 * It adds a bar to the JavaFX node by decorating it with a bar section and is added to a VBox along with the original JavaFX node.
 *
 * @author Marina Sotiropoulos
 */
public class BarDecorator extends Decorator{

    private static final Logger LOGGER = Logger.getLogger(BarDecorator.class.getName());

    private final Context context;

    /**
     * The constructor for the BarDecorator class.
     * It takes a Component object as a parameter and assigns it to the component field.
     *
     * @param component the component to be decorated
     * @param context the context to be passed to the controller
     * @author Marina Sotiropoulos
     */
    public BarDecorator(Component component, Context context) {
        super(component);
        this.context =  context;
    }

    /**
     * The applyBarDecoration method adds a bar to the JavaFX node.
     * It loads the homeBar-view.fxml file and adds it to a VBox along with the original JavaFX node.
     *
     * @param child the original JavaFX node
     * @return the decorated JavaFX node with a bar added
     * @author Marina Sotiropoulos
     */
    protected Node applyBarDecoration(Node child) {
        try {
            VBox vBox = new VBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/homeBar-view.fxml"));
            Node root = loader.load();

            HomePageJavaFxController controller = loader.getController();
            controller.initializeHomeView(context);

            vBox.getChildren().addAll(root, child);
            VBox.setVgrow(child, Priority.ALWAYS);
            return vBox;
        } catch (IOException e) {
            LOGGER.severe("Error while applying BarDecorator");
            return child;
        }
    }

    /**
     * The setup method is overridden from the Component class.
     * It calls the applyBarDecoration method and returns the result.
     *
     * @return the JavaFX node with the bar added
     * @author Marina Sotiropoulos
     */
    @Override
    public Node setup() {
        return this.applyBarDecoration(super.setup());
    }
}
