package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BarDecorator extends Decorator{
    public BarDecorator(Component component) {
        super(component);
    }
    protected Node applyHomeDecoration(Node child) {
        try {
            VBox vBox = new VBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/decoratorfxml/hBoxBar-view.fxml"));
            vBox.getChildren().addAll(loader.load(), child);
            VBox.setVgrow(child, Priority.ALWAYS);
            return vBox;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Node setup() {
        return this.applyHomeDecoration(super.setup());
    }
}
