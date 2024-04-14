package it.hivecampuscompany.hivecampus.state;

public abstract class ManageRequestsPage implements State {
    protected Context context;
    protected ManageRequestsPage(Context context) {
        this.context = context;
    }
}
