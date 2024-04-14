package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.TenantHomePage;

public class TenantHomeCLIPage extends TenantHomePage {
    protected TenantHomeCLIPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {

    }
}
