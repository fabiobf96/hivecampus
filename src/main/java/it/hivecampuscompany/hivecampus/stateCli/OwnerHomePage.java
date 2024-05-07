package it.hivecampuscompany.hivecampus.stateCli;

public abstract class OwnerHomePage implements State {
    protected Context context;
    protected OwnerHomePage(Context context) {
        this.context = context;
    }
    public void goToManageAdsPage(ManageAdsPage manageAdsPage) {
        context.setState(manageAdsPage);
        context.request();
    }
    public void goToManageRentRatesPage(ManageRentRatesPage manageRentRatesPage) {
        context.setState(manageRentRatesPage);
    }
    public void goToManageRequestPage(ManageRequestsPage manageRequestsPage) {
        context.setState(manageRequestsPage);
        context.request();
    }
    public void goToManageLeasePage(ManageLeasePage manageRentPage) {
        context.setState(manageRentPage);
        context.request();
    }
}
