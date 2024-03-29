package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.view.controller.javafx.HomePageJavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BarDecorator extends Decorator{

    private SessionBean sessionBean;

    public BarDecorator(Component component, SessionBean sessionBean) {
        super(component);
        this.sessionBean = sessionBean;
    }
    protected Node applyBarDecoration(Node child) {
        try {
            VBox vBox = new VBox();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/homeBar-view.fxml"));
            Node root = loader.load();

            HomePageJavaFxController controller = loader.getController();
            controller.initializeHomeView();

            //se devo passare un sessionBean devo ottenere il controller e chiamare initialize(sessionBean)
            vBox.getChildren().addAll(root, child);
            VBox.setVgrow(child, Priority.ALWAYS);
            return vBox;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Node setup() {
        return this.applyBarDecoration(super.setup());
    }
}