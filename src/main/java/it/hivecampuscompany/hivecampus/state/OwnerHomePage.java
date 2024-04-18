package it.hivecampuscompany.hivecampus.state;

public abstract class OwnerHomePage implements State {
    protected Context context;
    protected OwnerHomePage(Context context) {
        this.context = context;
    }
    public void goToManageAdsPage(ManageAdsPage manageAdsPage) {
        context.setState(manageAdsPage);
    }
    public void goToManageContractPage(ManageContractsPage manageContractsPage) {
        context.setState(manageContractsPage);
    }
    public void goToManageRequestPage(ManageRequestsPage manageRequestsPage) {
        context.setState(manageRequestsPage);
    }
    public void goToManageLeasePage(ManageLeasePage manageRentPage) {
        context.setState(manageRentPage);
    }

}
