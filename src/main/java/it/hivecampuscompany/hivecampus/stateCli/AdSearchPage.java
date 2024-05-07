package it.hivecampuscompany.hivecampus.stateCli;

public abstract class AdSearchPage implements State {
    protected Context context;

    protected AdSearchPage(Context context) {
        this.context = context;
    }

    public void goToTenantHomePage(TenantHomePage tenantHomePage) {
        context.setState(tenantHomePage);
    }
}