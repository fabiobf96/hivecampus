package it.hivecampuscompany.hivecampus.state;

public abstract class ManageContractsPage implements State {
    protected Context context;
    protected ManageContractsPage (Context context) {
        this.context = context;
    }
}
