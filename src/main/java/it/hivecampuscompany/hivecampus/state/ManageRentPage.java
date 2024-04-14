package it.hivecampuscompany.hivecampus.state;

public abstract class ManageRentPage implements State {
    protected Context context;
    protected ManageRentPage(Context context) {
        this.context = context;
    }
}
