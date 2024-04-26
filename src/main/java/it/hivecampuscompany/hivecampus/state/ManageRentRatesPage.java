package it.hivecampuscompany.hivecampus.state;

public abstract class ManageRentRatesPage implements State {
    protected Context context;
    protected ManageRentRatesPage(Context context) {
        this.context = context;
    }
}
