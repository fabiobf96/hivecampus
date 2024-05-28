package it.hivecampuscompany.hivecampus.state.javafx.ui.component;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class Composite extends Component{
    protected final List<Component> children = new ArrayList<>();

    public void addChildren(Component component) {
        children.add(component);
    }
    @Override
    public abstract Node setup();
}
