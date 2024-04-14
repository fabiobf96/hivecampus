package it.hivecampuscompany.hivecampus.state;

public abstract class TenantHomePage implements State {
    protected Context context;
    protected TenantHomePage(Context context) {
        this.context = context;
    }
}
