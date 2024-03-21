package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CompositeTabPane extends Composite{
    @Override
    public Node setup() {
        TabPane tabPane = new TabPane();
        for (Component component : children) {
            // Crea un nuovo Tab
            Tab tab = new Tab();
            // Imposta il contenuto del Tab con il Node restituito da setup() del componente
            tab.setContent(component.setup());
            tab.setClosable(false);
            tab.setStyle("-fx-background-color: yellow;");
            // Aggiungi il Tab al TabPane
            tabPane.getTabs().add(tab);
        }
        return tabPane;
    }
}
