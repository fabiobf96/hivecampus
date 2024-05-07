package it.hivecampuscompany.hivecampus.state;

public abstract class TenantHomePage implements State {
    protected Context context;

    protected TenantHomePage(Context context) {
        this.context = context;
    }

    public void goToAdSearchPage(AdSearchPage adSearchPage) {
       context.setState(adSearchPage);
       context.request();
    }

    public void goToManageRequestPage(ManageRequestsPage manageRequestsPage) {
        context.setState(manageRequestsPage);
        context.request();
    }

    public void goToManageLeasePage (ManageLeasePage manageLeasePage) {
        context.setState(manageLeasePage);
        context.request();
    }
}
