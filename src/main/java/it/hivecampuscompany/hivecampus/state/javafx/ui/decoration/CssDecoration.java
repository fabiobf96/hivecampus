package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The CssDecoration class is a concrete decorator class that extends the Decorator class.
 * It adds CSS styling to the JavaFX node by decorating it with a border, background, and padding.
 * The CSS styling is determined by the type of JavaFX node.
 *
 * @author Fabio Barchiesi
 */
public class CssDecoration extends Decorator {

    /**
     * The constructor for the CssDecoration class.
     * It takes a Component object as a parameter and assigns it to the component field.
     *
     * @param component the component to be decorated
     * @author Fabio Barchiesi
     */
    public CssDecoration(Component component) {
        super(component);
    }

    /**
     * The applyCssDecoration method adds CSS styling to the JavaFX node.
     * It sets the border color, width, and radius, the background radius, and the padding.
     *
     * @param root the JavaFX node
     * @return the decorated JavaFX node with CSS styling added
     * @author Fabio Barchiesi
     */
    private Node applyCssDecoration(Node root) {
        root.setStyle("-fx-border-color: black; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10;");
        HBox.setHgrow(root, Priority.ALWAYS);
        return root;
    }

    /**
     * The setup method is overridden from the Component class.
     * It calls the applyCssDecoration method and returns the result.
     *
     * @return the JavaFX node with CSS styling added
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        return (this.applyCssDecoration(super.setup()));
    }
}
