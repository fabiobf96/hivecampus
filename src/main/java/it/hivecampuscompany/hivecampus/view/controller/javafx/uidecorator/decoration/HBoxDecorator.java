package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HBoxDecorator extends Decorator{
    public HBoxDecorator(Component component) {
        super(component);
    }

    protected Node applyHBoxDecoration(Node child) {
        try {
            HBox hBox = new HBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/logoSection-view.fxml"));
            Node root = loader.load();

            //se devo passare un sessionBean devo ottenere il controller e chiamare initialize(sessionBean)
            hBox.getChildren().addAll(root, child);
            VBox.setVgrow(child, Priority.ALWAYS);
            return hBox;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Node setup() {
        return this.applyHBoxDecoration(super.setup());
    }

}
