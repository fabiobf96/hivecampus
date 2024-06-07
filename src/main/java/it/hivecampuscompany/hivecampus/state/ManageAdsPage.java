package it.hivecampuscompany.hivecampus.state;

/**
 * The ManageAdsPage abstract class serves as a base class for pages related to managing ads in the application.
 * It implements the State interface and provides methods for navigating to the owner's home page and the manage ads page.
 *
 * @author Marina Sotiropoulos
 */
public abstract class ManageAdsPage implements State {
    protected Context context;

    /**
     * Creates a new ManageAdsPage.
     *
     * @param context The context of the application.
     * @author Marina Sotiropoulos
     */
    protected ManageAdsPage(Context context) {
        this.context = context;
    }

    /**
     * Goes to the owner home page.
     *
     * @param ownerHomePage The owner home page.
     * @author Marina Sotiropoulos
     */
    public void goToOwnerHomePage(HomePage ownerHomePage) {
        context.setState(ownerHomePage);
    }

    /**
     * Goes to the manage ads page.
     *
     * @param manageAdsPage The manage ads page.
     * @author Marina Sotiropoulos
     */
    public void goToManageAdsPage(ManageAdsPage manageAdsPage) {
        context.setState(manageAdsPage);
    }
}
