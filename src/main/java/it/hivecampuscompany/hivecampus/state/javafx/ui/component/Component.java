package it.hivecampuscompany.hivecampus.state.javafx.ui.component;

import javafx.scene.Node;

/**
 * In the context of JavaFX UI development, the Decorator pattern was used to create custom components in the UI
 * with new behavior or appearance, applied to existing JavaFX nodes.
 *
 * <h2>Intent</h2>
 * <ul>
 *     <li>Add additional behavior or appearance to JavaFX nodes dynamically.</li>
 *     <li>Extend functionality of UI components without altering their base classes.</li>
 *     <li>Allow for flexible and reusable UI component customization.</li>
 * </ul>
 *
 * @author Fabio Barchiesi
 */

public abstract class Component {

    /**
     * This method is used to set up the JavaFX node.
     * It is implemented by concrete classes that extend this abstract class.
     *
     * @return the JavaFX node
     * @author Fabio Barchiesi
     */
    public abstract Node setup();
}
