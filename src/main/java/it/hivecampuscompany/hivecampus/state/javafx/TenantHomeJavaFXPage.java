package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.TenantHomePage;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.component.BasicComponent;
import it.hivecampuscompany.hivecampus.view.controller.javafx.uidecorator.decoration.BarDecorator;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.Properties;


public class TenantHomeJavaFXPage extends TenantHomePage {

    Properties properties = LanguageLoader.getLanguageProperties();

    public TenantHomeJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() {

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab(properties.getProperty("SEARCH_ADS_MSG"));
        Tab tab2 = new Tab(properties.getProperty("MANAGE_REQUEST_MSG"));
        Tab tab3 = new Tab("Manage Lease");

        tabPane.getTabs().add(tab1);
        tab1.setClosable(false);

        tabPane.getTabs().add(tab2);
        tab2.setClosable(false);

        tabPane.getTabs().add(tab3);
        tab3.setClosable(false);

        context.setTabs(tabPane.getTabs());

        BasicComponent tabComponent = new BasicComponent(tabPane);

        BarDecorator barDecorator = new BarDecorator(tabComponent, context);

        goToAdSearchPage(new AdSearchJavaFXPage(context));

        tab2.setOnSelectionChanged(event -> goToManageRequestPage(new ManageRequestsTenantJavaFXPage(context)));
        tab3.setOnSelectionChanged(event -> goToManageLeasePage(new ManageLeaseTenantJavaFXPage(context)));

        Scene scene = new Scene((Parent) barDecorator.setup());
        Stage stage = context.getStage();
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}