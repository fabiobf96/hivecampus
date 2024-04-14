package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.SignUpPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.SignUpCLIPageController;

import java.security.NoSuchAlgorithmException;

public class SignUpCLIPage extends SignUpPage {
    SignUpCLIPageController controller;
    protected SignUpCLIPage(Context context) {
        super(context);
        controller = new SignUpCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        try {
            controller.homePage();
            UserBean userBean = controller.getUserInformation();
            AccountBean accountBean = controller.getAccountInformation();
            registerUser(userBean, accountBean);
            goToInitialPage(new InitialCLIPage(context));
        } catch (PasswordMismatchException | InvalidEmailException e) {
            controller.displayError(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            controller.displayError(e.getMessage());
            controller.exit();
        } catch (DuplicateRowException e) {
            goToInitialPage(new InitialCLIPage(context));
        }
        context.request();
    }
}
