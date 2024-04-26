package it.hivecampuscompany.hivecampus.state;

public abstract class LogoutState implements State {
    protected Context context;
    protected LogoutState(Context context) {
        this.context = context;
    }
}
