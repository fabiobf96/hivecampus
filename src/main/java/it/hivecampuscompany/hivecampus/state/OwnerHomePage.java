package it.hivecampuscompany.hivecampus.state;

/**
 * The OwnerHomePage abstract class represents the owner's home page in the application.
 * It implements the State interface and provides methods for navigating to various pages related to owner's tasks.
 */
public abstract class OwnerHomePage implements State {

    protected Context context;

    /**
     * Constructs an OwnerHomePage object with the given context.
     *
     * @param context The context object for the owner's home page.
     */
    protected OwnerHomePage(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the manage ads page.
     *
     * @param manageAdsPage The ManageAdsPage object representing the manage ads page.
     */
    public void goToManageAdsPage(ManageAdsPage manageAdsPage) {
        context.setState(manageAdsPage);
        context.request();
    }

    /**
     * Navigates to the manage rent rates page.
     *
     * @param manageRentRatesPage The ManageRentRatesPage object representing the manage rent rates page.
     */
    public void goToManageRentRatesPage(ManageRentRatesPage manageRentRatesPage) {
        context.setState(manageRentRatesPage);
        context.request();
    }

    /**
     * Navigates to the manage request page.
     *
     * @param manageRequestsPage The ManageRequestsPage object representing the manage request page.
     */
    public void goToManageRequestPage(ManageRequestsPage manageRequestsPage) {
        context.setState(manageRequestsPage);
        context.request();
    }

    /**
     * Navigates to the manage lease page.
     *
     * @param manageRentPage The ManageLeasePage object representing the manage lease page.
     */
    public void goToManageLeasePage(ManageLeasePage manageRentPage) {
        context.setState(manageRentPage);
        context.request();
    }
}