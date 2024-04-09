package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

import java.io.IOException;

public class LeaseDecisionState implements State {
    @Override
    public void handle(Context context) throws InvalidSessionException {
        try {
            LeaseBean leaseBean = context.getController().getContractPath(context.getAdBean());
            if (leaseBean != null) {
                context.setLeaseBean(leaseBean);
                context.setState(new LoadLeaseState());
            } else context.setState(new AdSelectionState()); // Ritorna al chiamante
        } catch (IOException e) {
            context.getController().displayError(e.getMessage());
            context.setState(new LeaseDecisionState());
        }
    }
}
