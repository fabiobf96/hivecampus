package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.LoginPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.LoginCLIPageController;

import java.security.NoSuchAlgorithmException;

public class LoginCLIPage extends LoginPage {
    private LoginCLIPageController controller;
    protected LoginCLIPage(Context context) {
        super(context);
        controller = new LoginCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        try {
            controller.homePage();
            SessionBean sessionBean = authenticate(controller.getCredentials());
            switch (sessionBean.getRole()) {
                case "tenat" -> goToTenantHomePage(new TenantHomeCLIPage(context));
                case "owner" -> goToOwnerHomePage(new OwnerHomeCLIPage(context));
                default      -> controller.displayError("");
            }
        } catch (AuthenticateException e) {
            controller.displayError(e.getMessage());
            goToInitialPage(new InitialCLIPage(context));
        } catch (NoSuchAlgorithmException e) {
            controller.displayError(e.getMessage());
            System.exit(1);
        } catch (InvalidEmailException e) {
            controller.displayError(e.getMessage());
        }
        context.request();
    }
}
