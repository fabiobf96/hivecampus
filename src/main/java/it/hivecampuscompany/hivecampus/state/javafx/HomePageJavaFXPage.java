package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.HomePage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicComponent;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.BarDecorator;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Properties;

public class HomePageJavaFXPage extends HomePage {

    private final Properties properties = LanguageLoader.getLanguageProperties();

    public HomePageJavaFXPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        BasicComponent tabComponent = new BasicComponent(getTabPane());
        BarDecorator barDecorator = new BarDecorator(tabComponent, context);

        Scene scene = new Scene((Parent) barDecorator.setup());
        Stage stage = context.getStage();
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    protected TabPane getTabPane() {
        TabPane tabPane = new TabPane();
        Tab tab1;
        if (Objects.equals(context.getSessionBean().getRole(), "owner")) {
            tab1 = new Tab(properties.getProperty("MANAGE_ADS_MSG"));
            goToManageAdsPage(new ManageAdsJavaFXPage(context));
        } else {
            tab1 = new Tab(properties.getProperty("SEARCH_ADS_MSG"));
            goToAdSearchPage(new AdSearchJavaFXPage(context));
        }
        Tab tab2 = new Tab(properties.getProperty("MANAGE_REQUEST_MSG"));
        Tab tab3 = new Tab(properties.getProperty("MANAGE_LEASE_MSG"));

        tabPane.getTabs().add(tab1);
        tab1.setClosable(false);

        tabPane.getTabs().add(tab2);
        tab2.setClosable(false);

        tabPane.getTabs().add(tab3);
        tab3.setClosable(false);

        tab2.setOnSelectionChanged(event -> goToManageRequestsPage(new ManageRequestsOwnerJavaFXPage(context)));
        tab3.setOnSelectionChanged(event -> goToManageLeasePage(new ManageLeaseOwnerJavaFXPage(context)));

        context.setTabs(tabPane.getTabs());
        return tabPane;
    }
}
