package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.scene.Node;

public abstract class Decorator extends Component {
    private final Component component;

    protected Decorator(Component component){
        this.component = component;
    }
    @Override
    public Node setup() {
        return component.setup();
    }
}
