package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LeaseRequestDecorator extends Decorator {
    public LeaseRequestDecorator(Component component) {
        super(component);
    }

    protected Node applyLeaseRequestDecoration(Node child) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        Button btnAccept = new Button("Accept");
        btnAccept.setId("btnAccept");
        Button btnReject = new Button("Reject");
        btnReject.setId("btnReject");
        vBox.getChildren().addAll(btnAccept, btnReject);
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
        return this.applyLeaseRequestDecoration(super.setup());
    }
}
