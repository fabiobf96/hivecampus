package it.hivecampuscompany.hivecampus.model.pattern_decorator;

public abstract class Decorator<T> extends Component<T> {
    protected Component<T> component;

    protected Decorator(Component<T> component){
        this.component = component;
    }

    @Override
    public T toBean() {
        return component.toBean();
    }
}
