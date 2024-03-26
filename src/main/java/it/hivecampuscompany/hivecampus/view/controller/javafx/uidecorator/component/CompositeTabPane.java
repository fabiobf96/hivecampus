package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;

public class CompositeTabPane extends Composite{

    List<String> tabsName = new ArrayList<>();

    public void setTabName(String tabName) {
        tabsName.add(tabName);
    }

    @Override
    public Node setup() {
        TabPane tabPane = new TabPane();
        for (Component component : children) {
            // Crea un nuovo Tab
            Tab tab = new Tab(tabsName.get(children.indexOf(component)));
            // Imposta il contenuto del Tab con il Node restituito da setup() del componente
            tab.setContent(component.setup());
            tab.setClosable(false);

            // Aggiungi il Tab al TabPane
            tabPane.getTabs().add(tab);
        }
        return tabPane;
    }
}
