package it.hivecampuscompany.hivecampus.stateCli;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;

import java.security.NoSuchAlgorithmException;

public abstract class SignUpPage implements State {
    protected Context context;
    protected SignUpPage(Context context) {
        this.context = context;
    }
    public void goToInitialPage(InitialPage initialPage) {
        context.setState(initialPage);
    }
    // usato da signUp per tornare alla pagina di login
    public void goToLoginPage(LoginPage loginPage) {
        context.setState(loginPage);
        context.request();
    }
    public void registerUser(UserBean userBean, AccountBean accountBean) throws NoSuchAlgorithmException, DuplicateRowException {
        LoginManager loginManager = new LoginManager();
        loginManager.signup(userBean, accountBean);
    }
}
