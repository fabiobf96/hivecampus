package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.javafx.controller.PreviewAdJavaFxController;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Logger;

public class PreviewRoomDecorator extends Decorator {

    private static final Logger LOGGER = Logger.getLogger(PreviewRoomDecorator.class.getName());
    private final AdBean adBean;
    private final Context context;

    public PreviewRoomDecorator(Component component, AdBean adBean, Context context) {
        super(component);
        this.adBean = adBean;
        this.context = context;
    }

    protected Node applyPreviewRoomDecoration(Node child) {
        try {
            VBox vBox = new VBox();
            vBox.paddingProperty().setValue(new javafx.geometry.Insets(10));
            vBox.alignmentProperty().setValue(Pos.CENTER_LEFT);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/previewRoom-card.fxml"));
            Node root = loader.load();

            PreviewAdJavaFxController controller = loader.getController();
            controller.setAdBean(adBean);
            controller.initializePreviewFeatures(context);

            vBox.getChildren().addAll(root, child);
            return vBox;
        } catch (IOException | IllegalStateException e) {
            displayGraphicErrorAlert();
            return null;
        }
    }

    @Override
    public Node setup() {
        return this.applyPreviewRoomDecoration(super.setup());
    }
}
