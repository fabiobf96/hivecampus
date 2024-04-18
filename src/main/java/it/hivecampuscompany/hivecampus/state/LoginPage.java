package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import java.security.NoSuchAlgorithmException;

public abstract class LoginPage implements State {
    protected Context context;
    protected LoginPage (Context context) {
        this.context = context;
    }

    public void goToOwnerHomePage(OwnerHomePage ownerHomePage) {
        context.setState(ownerHomePage);
    }
    public void goToTenantHomePage(TenantHomePage tenantHomePage) {
        context.setState(tenantHomePage);
    }
    public void goToInitialPage(InitialPage initialPage) {
        context.setState(initialPage);
    }
    // usato da Login per tornare alla pagina di signUp
    public void goToSignUpPage(SignUpPage signUpPage) {
        context.setState(signUpPage);
        context.request();
    }

    public SessionBean authenticate(UserBean userBean) throws AuthenticateException, NoSuchAlgorithmException {
        LoginManager loginManager = new LoginManager();
        return loginManager.login(userBean);
    }
}
