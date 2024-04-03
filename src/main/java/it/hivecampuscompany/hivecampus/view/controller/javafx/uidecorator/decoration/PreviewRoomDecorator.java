package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.view.controller.javafx.PreviewRoomJavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PreviewRoomDecorator extends Decorator{
    public PreviewRoomDecorator(Component component) {
        super(component);
    }

    protected Node applyPreviewRoomDecoration(Node child) {
        try {
            VBox vBox = new VBox();
            vBox.paddingProperty().setValue(new javafx.geometry.Insets(10));
            vBox.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);
            vBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5;");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/previewRoom-card.fxml"));
            Node root = loader.load();

            PreviewRoomJavaFxController controller = loader.getController();
            controller.initializePreviewFeatures();

            //se devo passare un sessionBean devo ottenere il controller e chiamare initialize(sessionBean)
            vBox.getChildren().addAll(root, child);
            return vBox;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Node setup() {
        return this.applyPreviewRoomDecoration(super.setup());
    }
}
