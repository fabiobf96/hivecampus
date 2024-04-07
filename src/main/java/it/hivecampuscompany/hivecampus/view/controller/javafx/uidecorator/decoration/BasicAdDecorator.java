package it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.view.controller.javafx.HomePageJavaFxController;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class BasicAdDecorator extends Decorator{
    private AdBean adBean;
    protected BasicAdDecorator(Component component) {
        super(component);
    }

    public BasicAdDecorator (AdBean adBean, Component component) {
        this(component);
        this.adBean = adBean;
    }
    protected Node applyBasicAdDecoration(Node child) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        Text text = new Text(adBean.toString());
        // Imposta la dimensione del testo a 16 e rendilo grassetto
        text.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Aggiungi il testo alla VBox
        vBox.getChildren().add(text);

        // Aggiungi il componente child alla VBox e imposta la crescita verticale
        vBox.getChildren().add(child);
        VBox.setVgrow(child, Priority.ALWAYS);

        return vBox;
    }
    @Override
    public Node setup() {
        return this.applyBasicAdDecoration(super.setup());
    }
}
