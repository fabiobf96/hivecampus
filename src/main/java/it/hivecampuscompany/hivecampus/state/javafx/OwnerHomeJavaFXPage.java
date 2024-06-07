package it.hivecampuscompany.hivecampus.state.javafx;

import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.HomePage;
import it.hivecampuscompany.hivecampus.state.javafx.ui.component.BasicComponent;
import it.hivecampuscompany.hivecampus.state.javafx.ui.decoration.BarDecorator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The OwnerHomeJavaFXPage class represents the owner home page in the JavaFX user interface.
 * It extends the OwnerHomePage class and provides methods for displaying the owner home page and handling user input.
 */

public class OwnerHomeJavaFXPage extends HomePage {

    /**
     * Constructs an OwnerHomeJavaFXPage object with the given context.
     * @param context The context object for the owner home page.
     * @author Marina Sotiropoulos
     */

    public OwnerHomeJavaFXPage(Context context) {
        super(context);
    }

    /**
     * Handles the setup and display of the main tabbed interface in the application.
     * This method initializes the tabs for managing ads, requests, and leases,
     * sets up the corresponding tab pages, and displays the main stage.
     *
     * @author Marina Sotiropoulos
     */

    @Override
    public void handle() {

        BasicComponent tabComponent = new BasicComponent(createTabPane());
        BarDecorator barDecorator = new BarDecorator(tabComponent, context);

        goToManageAdsPage(new ManageAdsJavaFXPage(context));
        context.getTab(1).setOnSelectionChanged(event -> goToManageRequestsPage(new ManageRequestsOwnerJavaFXPage(context)));
        context.getTab(2).setOnSelectionChanged(event -> goToManageLeasePage(new ManageLeaseOwnerJavaFXPage(context)));

        Scene scene = new Scene((Parent) barDecorator.setup());
        Stage stage = context.getStage();
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}
