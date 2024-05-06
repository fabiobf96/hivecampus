package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LeaseDecorator extends Decorator {
    public LeaseDecorator(Component component) {
        super(component);
    }

    protected Node applyLeaseDecoration(Node child) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        Button btnUpload = new Button("Upload");
        btnUpload.setId("btnUpload");
        Button btnDelete = new Button("Delete");
        btnDelete.setId("btnDelete");
        vBox.getChildren().addAll(btnUpload, btnDelete);
        Region region = new Region();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(child, region, vBox);
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        return hBox;
    }
    @Override
    public Node setup() {
        return this.applyLeaseDecoration(super.setup());
    }
}
