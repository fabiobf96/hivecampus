package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component;

import javafx.scene.Node;

public class BasicComponent extends Component{
    private final Node component;
    public BasicComponent (Node component){
        this.component = component;
    }
    @Override
    public Node setup() {
        return component;
    }
}
