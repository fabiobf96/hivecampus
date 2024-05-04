package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.scene.Node;

public class CssDecoration extends Decorator {
    public CssDecoration(Component component) {
        super(component);
    }
    private Node applyCssDecoration(Node root) {
        root.setStyle("-fx-border-color: black; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10;");
        return root;
    }
    @Override
    public Node setup() {
        return (this.applyCssDecoration(super.setup()));
    }
}