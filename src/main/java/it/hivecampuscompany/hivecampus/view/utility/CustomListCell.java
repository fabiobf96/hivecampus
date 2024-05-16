package it.hivecampuscompany.hivecampus.view.utility;

import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 * Class for customizing the cells of a ListView.
 */

public class CustomListCell extends ListCell<Node> {

    /**
     * Updates the cell with the given item.
     * If the item is null or the cell is empty, the cell is set to null.
     * Otherwise, the cell is set to the item. It also sets the style of the cell.
     * Particularly, if the cell is hovered, the border color is set to light blue and the background color to light blue.
     *
     * @param item the item to be displayed
     * @param empty whether the cell is empty
     */
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