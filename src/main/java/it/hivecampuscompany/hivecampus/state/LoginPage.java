package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import java.security.NoSuchAlgorithmException;

/**
 * The LoginPage abstract class serves as a base class for login pages in the application.
 * It implements the State interface and provides methods for navigating to various pages and authenticating users.
 * @author Fabio Barchiesi
 */
public abstract class LoginPage implements State {

    protected Context context;

    /**
     * Constructs a LoginPage object with the given context.
     *
     * @param context The context object for the login page.
     * @author Fabio Barchiesi
     */
    protected LoginPage(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the owner's home page by updating the context's state.
     *
     * @param ownerHomePage The OwnerHomePage object representing the owner's home page.
     * @author Fabio Barchiesi
     */
    public void goToOwnerHomePage(HomePage ownerHomePage) {
        context.setState(ownerHomePage);
    }

    /**
     * Navigates to the tenant's home page by updating the context's state.
     *
     * @param tenantHomePage The TenantHomePage object representing the tenant's home page.
     * @author Fabio Barchiesi
     */
    public void goToTenantHomePage(HomePage tenantHomePage) {
        context.setState(tenantHomePage);
    }

    /**
     * Navigates to the initial page by updating the context's state.
     *
     * @param initialPage The InitialPage object representing the initial page.
     * @author Fabio Barchiesi
     */
    public void goToInitialPage(InitialPage initialPage) {
        context.setState(initialPage);
    }

    /**
     * Navigates to the sign-up page by updating the context's state and making a request.
     *
     * @param signUpPage The SignUpPage object representing the sign-up page.
     * @author Marina Sotiropoulos
     */
    public void goToSignUpPage(SignUpPage signUpPage) {
        context.setState(signUpPage);
        context.request();
    }

    /**
     * Authenticates the user using the provided user credentials.
     *
     * @param userBean The UserBean object containing user credentials.
     * @return The SessionBean object representing the authenticated session.
     * @throws AuthenticateException   if authentication fails.
     * @throws NoSuchAlgorithmException if the specified algorithm is not available.
     * @author Fabio Barchiesi
     */
    public SessionBean authenticate(UserBean userBean) throws AuthenticateException, NoSuchAlgorithmException {
        LoginManager loginManager = new LoginManager();
        return loginManager.login(userBean);
    }
}

