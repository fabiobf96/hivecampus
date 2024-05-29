package it.hivecampuscompany.hivecampus.state;

/**
 * The TenantHomePage abstract class serves as a base class for pages related to the tenant's home page in the application.
 * It implements the State interface and provides methods for navigating to the ad search page, manage requests page, and manage lease page.
 *
 * @author Fabio Barchiesi
 */
public abstract class TenantHomePage implements State {
    protected Context context;

    /**
     * Constructs a TenantHomePage object with the given context.
     *
     * @param context The context object for the tenant's home page.
     * @author Marina Sotiropoulos
     */
    protected TenantHomePage(Context context) {
        this.context = context;
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

    /**
     * Navigates to the manage requests page.
     *
     * @param manageRequestsPage The ManageRequestsPage object representing the manage requests page.
     * @author Marina Sotiropoulos
     */
    public void goToManageRequestsPage(ManageRequestsPage manageRequestsPage) {
        context.setState(manageRequestsPage);
        context.request();
    }

    /**
     * Navigates to the manage lease page.
     *
     * @param manageLeasePage The ManageLeasePage object representing the manage lease page.
     * @author Fabio Barchiesi
     */
    public void goToManageLeasePage (ManageLeasePage manageLeasePage) {
        context.setState(manageLeasePage);
        context.request();
    }
}
