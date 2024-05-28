package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

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
        HBox.setHgrow(root, Priority.ALWAYS);
        return root;
    }
    @Override
    public Node setup() {
        return (this.applyCssDecoration(super.setup()));
    }
}
