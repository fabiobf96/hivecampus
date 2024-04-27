package it.hivecampuscompany.hivecampus.state;

public abstract class ManageAdsPage implements State {
    protected Context context;
    protected ManageAdsPage(Context context) {
        this.context = context;
    }

    public void goToOwnerHomePage(OwnerHomePage ownerHomePage) {
        context.setState(ownerHomePage);
    }

    public void goToManageAdsPage(ManageAdsPage manageAdsPage) {
        context.setState(manageAdsPage);
    }
}
