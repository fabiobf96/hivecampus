package it.hivecampuscompany.hivecampus.state.javafx.ui.component;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CompositeVBox extends Composite {
    @Override
    public Node setup() {
        VBox vBox = new VBox(10);
        for (Component component : children) {
            vBox.getChildren().add(component.setup());
        }
        return vBox;
    }
}
