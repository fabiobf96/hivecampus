package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.logging.Logger;

public class LogoDecorator extends Decorator{
    private static final Logger LOGGER = Logger.getLogger(LogoDecorator.class.getName());
    public LogoDecorator(Component component) {
        super(component);
    }

    protected Node applyLogoDecoration(Node child) {
        try {
            HBox hBox = new HBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/logoSection-view.fxml"));
            Node root = loader.load();

            //se devo passare un sessionBean devo ottenere il controller e chiamare initialize(sessionBean)
            hBox.getChildren().addAll(root, child);
            VBox.setVgrow(child, Priority.ALWAYS);
            HBox.setHgrow(child, Priority.ALWAYS);
            return hBox;
        } catch (IOException e) {
            LOGGER.severe("Error while applying LogoDecorator");
            return child;
        }
    }
    @Override
    public Node setup() {
        return this.applyLogoDecoration(super.setup());
    }

}
