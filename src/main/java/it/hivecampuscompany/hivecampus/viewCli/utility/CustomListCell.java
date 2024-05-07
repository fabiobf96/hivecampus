package it.hivecampuscompany.hivecampus.viewCli.utility;

import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class CustomListCell extends ListCell<Node> {
    @Override
    protected void updateItem(Node item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setStyle("");
        } else {
            setGraphic(item);
            if (isHover()) {
                setStyle("-fx-border-color: #87CEFA; -fx-background-color: #C1E7FD; -fx-border-width: 2px; -fx-border-radius: 5px; fx-background-radius: 5px;");
            } else if (!isHover()) {
                setStyle("-fx-border-color: #808080; -fx-border-width: 1px; -fx-border-radius: 5px; fx-background-radius: 5px;");
            } else {
                setStyle("");
            }
        }
    }
}