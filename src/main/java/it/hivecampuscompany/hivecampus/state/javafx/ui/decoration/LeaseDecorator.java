package it.hivecampuscompany.hivecampus.state.javafx.ui.decoration;

import it.hivecampuscompany.hivecampus.state.javafx.ui.component.Component;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The LeaseDecorator class is a concrete decorator class that extends the Decorator class.
 * It adds lease-specific functionality to the JavaFX node by decorating it with buttons for actions such as upload, delete, sign, and download.
 * The lease-specific functionality is determined by the type of lease (owner or tenant).
 *
 * @author Fabio Barchiesi
 */
public class LeaseDecorator extends Decorator {
    private Type type;

    /**
     * The constructor for the LeaseDecorator class.
     * It takes a Component object and a Type object as parameters and assigns them to the component and type fields respectively.
     *
     * @param component the component to be decorated
     * @param type      the type of lease (owner or tenant)
     * @author Fabio Barchiesi
     */
    public LeaseDecorator(Component component, Type type) {
        super(component);
        this.type = type;
    }

    /**
     * The applyOwnerLeaseDecoration method adds owner-specific functionality to the JavaFX node.
     * It creates buttons for upload and delete actions and adds them to a VBox.
     * The VBox is then added to an HBox along with the original JavaFX node.
     *
     * @param child the original JavaFX node
     * @return the decorated JavaFX node with owner-specific functionality added
     * @author Fabio Barchiesi
     */
    protected Node applyOwnerLeaseDecoration(Node child) {
        VBox vBox = createVBox();

        Button btnUpload = new Button("Upload");
        btnUpload.setId("btnUpload");
        btnUpload.setPrefWidth(75);
        btnUpload.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

        Button btnDelete = new Button("Delete");
        btnDelete.setId("btnDelete");
        btnDelete.setPrefWidth(75);
        btnDelete.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

        vBox.getChildren().addAll(btnUpload, btnDelete);

        return createHBox(child, vBox);
    }

    /**
     * The applyTenantLeaseDecoration method adds tenant-specific functionality to the JavaFX node.
     * It creates buttons for sign and download actions and adds them to a VBox.
     * The VBox is then added to an HBox along with the original JavaFX node.
     *
     * @param child the original JavaFX node
     * @return the decorated JavaFX node with tenant-specific functionality added
     * @author Fabio Barchiesi
     */
    protected Node applyTenantLeaseDecoration(Node child) {
        VBox vBox = createVBox();

        Button btnSign = new Button("Sign");
        btnSign.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnSign.setId("btnSign");
        btnSign.setPrefWidth(75);

        Button btnDownload = new Button("Download");
        btnDownload.setId("btnDownload");
        btnDownload.setPrefWidth(75);
        btnDownload.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

        vBox.getChildren().addAll(btnSign, btnDownload);

        return createHBox(child, vBox);
    }

    /**
     * The setup method is overridden from the Component class.
     * It calls the applyOwnerLeaseDecoration or applyTenantLeaseDecoration method based on the type of lease and returns the result.
     *
     * @return the decorated JavaFX node with lease-specific functionality added based on the type of lease (owner or tenant)
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        return switch (type) {
            case OWNER -> this.applyOwnerLeaseDecoration(super.setup());
            case TENANT -> this.applyTenantLeaseDecoration(super.setup());
        };
    }

    /**
     * The createVBox method is a utility method that creates a new VBox with spacing and alignment properties set.
     *
     * @return the new VBox object with spacing and alignment properties set to default values for lease decoration buttons
     * @author Fabio Barchiesi
     */
    private VBox createVBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * The createRegion method is a utility method that creates a new Region object.
     *
     * @return the new Region object
     * @author Fabio Barchiesi
     */
    private Region createRegion() {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

    /**
     * The createHBox method is a utility method that creates a new HBox with the specified child node and VBox.
     *
     * @param child the child node to be added to the HBox
     * @param vBox  the VBox to be added to the HBox
     * @return the new HBox object with the specified child node and VBox added
     * @author Fabio Barchiesi
     */
    private HBox createHBox(Node child, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(child, createRegion(), vBox);
        HBox.setHgrow(hBox, Priority.ALWAYS);
        return hBox;
    }

    /**
     * The Type enum represents the type of lease (owner or tenant).
     * It is used to determine the lease-specific functionality to be added to the JavaFX node.
     *
     * @author Fabio Barchiesi
     */
    public enum Type {
        OWNER,
        TENANT
    }
}
