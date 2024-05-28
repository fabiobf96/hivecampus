package it.hivecampuscompany.hivecampus.state.javafx.ui.component;

import javafx.scene.Node;

/**
 * This class is a concrete implementation of the Component abstract class.
 * It is used to create a basic component in the JavaFX UI.
 * It extends the Component class and overrides the setup method to return the JavaFX node.
 *
 * @author Fabio Barchiesi
 */
public class BasicComponent extends Component{
    private final Node component;

    /**
     * Constructor for the BasicComponent class.
     *
     * @param component the JavaFX node to be used as the basic component
     * @author Fabio Barchiesi
     */
    public BasicComponent (Node component){
        this.component = component;
    }

    /**
     * This method sets up the JavaFX node for the basic component.
     *
     * @return the JavaFX node for the basic component
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        return component;
    }
}
