package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.scene.Node;


public abstract class Decorator extends Component {
    private Component component;

    public Decorator(Component component){
        this.component = component;
    }
    @Override
    public Node setup() {
        return component.setup();
    }
}
