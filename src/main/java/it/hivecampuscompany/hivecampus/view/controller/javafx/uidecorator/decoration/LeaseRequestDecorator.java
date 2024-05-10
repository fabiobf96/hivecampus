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
        // Creation of VBox that will contain the buttons
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        // Creation button accept
        Button btnAccept = new Button("Accept");
        btnAccept.setId("btnAccept");
        btnAccept.setPrefWidth(75);
        btnAccept.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        // Creation button reject
        Button btnReject = new Button("Reject");
        btnReject.setId("btnReject");
        btnReject.setPrefWidth(75);
        btnReject.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        // Adding buttons at VBox
        vBox.getChildren().addAll(btnAccept, btnReject);
        Region region = new Region();
        // Creation HBox that will contain child, region and vBox
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(child, region, vBox);
        // Added horizontal expansion property
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        return hBox;
    }

    @Override
    public Node setup() {
        return this.applyLeaseRequestDecoration(super.setup());
    }
}
