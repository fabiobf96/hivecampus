package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

public class LoadLeaseState implements State{
    @Override
    public void handle(Context context) throws InvalidSessionException {
        context.getController().loadLeaseContract(context.getLeaseBean());
        context.setState(new AdSelectionState());
    }
}
