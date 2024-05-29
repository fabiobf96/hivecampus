package it.hivecampuscompany.hivecampus.state.javafx.ui.composite;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * The CompositeVBox class represents a composite component in the Composite pattern.
 * It extends the Composite class and arranges its child components in a vertical box layout (VBox).
 *
 * @author Fabio Barchiesi
 */
public class CompositeVBox extends Composite {

    /**
     * Sets up the composite VBox component by arranging its child components in a vertical box layout.
     *
     * @return the node representing the composite VBox component
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        VBox vBox = new VBox(10);
        for (Component component : children) {
            vBox.getChildren().add(component.setup());
        }
        return vBox;
    }
}