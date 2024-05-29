package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.TenantHomePage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicComponent;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.BarDecorator;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.Properties;

/**
 * The TenantHomeJavaFXPage class represents the tenant home page in the JavaFX user interface.
 * It extends the TenantHomePage class and provides methods for displaying the tenant home page and handling user input.
 */
public class TenantHomeJavaFXPage extends TenantHomePage {

    Properties properties = LanguageLoader.getLanguageProperties();

    /**
     * Constructs a TenantHomeJavaFXPage object with the given context.
     * @param context The context object for the tenant home page.
     * @author Marina Sotiropoulos
     */
    public TenantHomeJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the main tabbed interface in the application.
     * This method initializes the tabs for searching ads, managing requests, and managing leases,
     * sets up the corresponding tab pages, and displays the main stage.
     *
     * @author Marina Sotiropoulos
     */
    @Override
    public void handle() {

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab(properties.getProperty("SEARCH_ADS_MSG"));
        Tab tab2 = new Tab(properties.getProperty("MANAGE_REQUEST_MSG"));
        Tab tab3 = new Tab(properties.getProperty("MANAGE_LEASE_MSG"));

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

        tab2.setOnSelectionChanged(event -> goToManageRequestsPage(new ManageRequestsTenantJavaFXPage(context)));
        tab3.setOnSelectionChanged(event -> goToManageLeasePage(new ManageLeaseTenantJavaFXPage(context)));

        Scene scene = new Scene((Parent) barDecorator.setup());
        Stage stage = context.getStage();
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}