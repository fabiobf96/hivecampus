package it.hivecampuscompany.hivecampus.state;

/**
 * The AdSearchPage class represents the state of the application when the tenant is searching for an ad.
 * @author Marina Sotiropoulos
 */
public abstract class AdSearchPage implements State {
    protected Context context;

    /**
     * Creates a new AdSearchPage.
     *
     * @param context The context of the application.
     * @author Marina Sotiropoulos
     */
    protected AdSearchPage(Context context) {
        this.context = context;
    }

    /**
     * Goes to the tenant home page.
     *
     * @param tenantHomePage The tenant home page.
     * @author Marina Sotiropoulos
     */
    public void goToTenantHomePage(HomePage tenantHomePage) {
        context.setState(tenantHomePage);
    }
}