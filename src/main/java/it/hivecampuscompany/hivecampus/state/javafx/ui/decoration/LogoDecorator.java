package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The LogoDecorator class is a concrete decorator class that extends the Decorator class.
 * It adds a logo to the JavaFX node by decorating it with a logo section and is added to an HBox along with the original JavaFX node.
 *
 * @author Marina Sotiropoulos
 */
public class LogoDecorator extends Decorator{
    private static final Logger LOGGER = Logger.getLogger(LogoDecorator.class.getName());

    /**
     * The constructor for the LogoDecorator class.
     * It takes a Component object as a parameter and assigns it to the component field.
     *
     * @param component the component to be decorated
     * @author Marina Sotiropoulos
     */
    public LogoDecorator(Component component) {
        super(component);
    }

    /**
     * The applyLogoDecoration method adds a logo to the JavaFX node.
     * It loads the logoSection-view.fxml file and adds it to an HBox along with the original JavaFX node.
     *
     * @param child the original JavaFX node
     * @return the decorated JavaFX node with a logo added
     * @author Marina Sotiropoulos
     */
    protected Node applyLogoDecoration(Node child) {
        try {
            HBox hBox = new HBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/logoSection-view.fxml"));
            Node root = loader.load();

            //se devo passare un sessionBean devo ottenere il controller e chiamare initialize(sessionBean)
            hBox.getChildren().addAll(root, child);
            VBox.setVgrow(child, Priority.ALWAYS);
            HBox.setHgrow(child, Priority.ALWAYS);
            return hBox;
        } catch (IOException e) {
            LOGGER.severe("Error while applying LogoDecorator");
            return child;
        }
    }

    /**
     * The setup method is overridden from the Component class.
     * It calls the applyLogoDecoration method and returns the result.
     *
     * @return the JavaFX node with the logo added
     * @author Marina Sotiropoulos
     */
    @Override
    public Node setup() {
        return this.applyLogoDecoration(super.setup());
    }

}
