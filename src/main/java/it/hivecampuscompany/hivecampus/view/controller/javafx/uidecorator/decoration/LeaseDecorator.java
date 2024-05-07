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
    private Type type;
    public LeaseDecorator(Component component, Type type) {
        super(component);
        this.type = type;
    }
    protected Node applyOwnerLeaseDecoration(Node child) {
        VBox vBox = createVBox();
        Button btnUpload = new Button("Upload");
        btnUpload.setId("btnUpload");
        Button btnDelete = new Button("Delete");
        btnDelete.setId("btnDelete");
        vBox.getChildren().addAll(btnUpload, btnDelete);
        return createHBox(child, vBox);
    }
    protected Node applyTenantLeaseDecoration(Node child) {
        VBox vBox = createVBox();
        Button btnSign = new Button("Sign");
        btnSign.setId("btnSign");
        Button btnDownload = new Button("Download");
        btnDownload.setId("btnDownload");
        vBox.getChildren().addAll(btnSign, btnDownload);
        return createHBox(child, vBox);
    }
    @Override
    public Node setup() {
        return switch (type) {
            case OWNER -> this.applyOwnerLeaseDecoration(super.setup());
            case TENANT -> this.applyTenantLeaseDecoration(super.setup());
        };
    }
    private VBox createVBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
    private Region createRegion() {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }
    private HBox createHBox(Node child, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(child, createRegion(), vBox);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        return hBox;
    }
    public enum Type {
        OWNER,
        TENANT
    }
}
