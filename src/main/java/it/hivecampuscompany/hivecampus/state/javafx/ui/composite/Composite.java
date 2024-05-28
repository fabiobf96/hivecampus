package it.hivecampuscompany.hivecampus.state.javafx.ui.composite;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * The Composite class represents a composite component in the Composite pattern.
 * It extends the Component class and serves as a container for other components.
 * After composing child components, this composite element can be used for further decorations.
 *
 * @author Fabio Barchiesi
 */
 public abstract class Composite extends Component {
    protected final List<Component> children = new ArrayList<>();

    /**
     * Adds a child component to the composite.
     * @param component the child component to add
     * @author Fabio Barchiesi
     */
    public void addChildren(Component component) {
        children.add(component);
    }

    /**
     * Sets up the composite component by setting up its child components.
     *
     * @return the node representing the composite component
     * @author Fabio Barchiesi
     */
    @Override
    public abstract Node setup();
}
