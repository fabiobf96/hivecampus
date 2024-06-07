package it.hivecampuscompany.hivecampus.state;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public abstract class HomePage implements State{

    protected Context context;

    /**
     * Constructs a HomePage with the specified context.
     *
     * @param context the context of the application
     */

    protected HomePage(Context context){
        this.context = context;
    }

    /**
     * Creates and returns a TabPane with tabs based on the user's role.
     *
     * @return the created TabPane
     */

    public TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        Tab tab1;
        if (context.getSessionBean().getRole().equals("owner")) {
            tab1 = new Tab(context.getLanguage().getProperty("MANAGE_ADS_MSG"));
        } else {
            tab1 = new Tab(context.getLanguage().getProperty("SEARCH_ADS_MSG"));
        }
        Tab tab2 = new Tab(context.getLanguage().getProperty("MANAGE_REQUEST_MSG"));
        Tab tab3 = new Tab(context.getLanguage().getProperty("MANAGE_LEASE_MSG"));

        tabPane.getTabs().add(tab1);
        tab1.setClosable(false);

        tabPane.getTabs().add(tab2);
        tab2.setClosable(false);

        tabPane.getTabs().add(tab3);
        tab3.setClosable(false);

        context.setTabs(tabPane.getTabs());
        return tabPane;
    }

    /**
     * Navigates to the manage ads page.
     *
     * @param manageAdsPage The ManageAdsPage object representing the manage ads page.
     * @author Marina Sotiropoulos
     */
    public void goToManageAdsPage(ManageAdsPage manageAdsPage) {
        context.setState(manageAdsPage);
        context.request();
    }

    /**
     * Navigates to the manage rent rates page.
     *
     * @param manageRentRatesPage The ManageRentRatesPage object representing the manage rent rates page.
     * @author Fabio Barchiesi
     */

    public void goToManageRentRatesPage(ManageRentRatesPage manageRentRatesPage) {
        context.setState(manageRentRatesPage);
        context.request();
    }

    /**
     * Navigates to the manage request page.
     *
     * @param manageRequestsPage The ManageRequestsPage object representing the manage request page.
     * @author Fabio Barchiesi
     */

    public void goToManageRequestsPage(ManageRequestsPage manageRequestsPage) {
        context.setState(manageRequestsPage);
        context.request();
    }

    /**
     * Navigates to the manage lease page.
     *
     * @param manageRentPage The ManageLeasePage object representing the manage lease page.
     * @author Fabio Barchiesi
     */

    public void goToManageLeasePage(ManageLeasePage manageRentPage) {
        context.setState(manageRentPage);
        context.request();
    }

    /**
     * Navigates to the ad search page.
     *
     * @param adSearchPage The AdSearchPage object representing the ad search page.
     * @author Marina Sotiropoulos
     */

    public void goToAdSearchPage(AdSearchPage adSearchPage) {
        context.setState(adSearchPage);
        context.request();
    }
}