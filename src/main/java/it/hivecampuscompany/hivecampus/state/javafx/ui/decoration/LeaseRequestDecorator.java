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
 * The LeaseRequestDecorator class is a concrete decorator class that extends the Decorator class.
 * It adds lease request-specific functionality to the JavaFX node by decorating it with buttons for actions such as accept and reject.
 * The lease request-specific functionality is determined by the type of lease request (accept or reject).
 *
 * @author Fabio Barchiesi
 */
public class LeaseRequestDecorator extends Decorator {

    /**
     * The constructor for the LeaseRequestDecorator class.
     * It takes a Component object as a parameter and assigns it to the component field.
     *
     * @param component the component to be decorated
     * @author Fabio Barchiesi
     */
    public LeaseRequestDecorator(Component component) {
        super(component);
    }

    /**
     * The applyLeaseRequestDecoration method adds lease request-specific functionality to the JavaFX node.
     * It creates buttons for accept and reject actions and adds them to a VBox.
     * The VBox is then added to an HBox along with the original JavaFX node.
     *
     * @param child the original JavaFX node
     * @return the decorated JavaFX node with lease request-specific functionality added
     * @author Fabio Barchiesi
     */
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

    /**
     * The setup method is overridden from the Decorator class.
     * It calls the applyLeaseRequestDecoration method and returns the result.
     *
     * @return the JavaFX node with lease request-specific functionality added
     * @author Fabio Barchiesi
     */
    @Override
    public Node setup() {
        return this.applyLeaseRequestDecoration(super.setup());
    }
}
