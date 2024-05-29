package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.scene.Node;

/**
 * The Decorator class represents the base class for decorators in the Decorator pattern.
 * It extends the Component class and is used to decorate other components.
 *
 * @author Fabio Barchiesi
 */
public abstract class Decorator extends Component {
    private final Component component;

    /**
     * The constructor for the Decorator class.
     * It takes a Component object as a parameter and assigns it to the component field.
     *
     * @param component the component to be decorated
     * @author Fabio Barchiesi
     */
    protected Decorator(Component component){
        this.component = component;
    }

    /**
     * The setup method is overridden from the Component class.
     * It calls the setup method of the component field and returns the result.
     *
     * @return the JavaFX node
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        return component.setup();
    }
}