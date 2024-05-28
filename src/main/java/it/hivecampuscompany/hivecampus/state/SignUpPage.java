package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;

import java.security.NoSuchAlgorithmException;

/**
 * The SignUpPage abstract class represents the sign-up page in the application.
 * It implements the State interface and provides methods for navigating to initial and login pages, as well as registering a user.
 *
 * @author Fabio Barchiesi
 */
public abstract class SignUpPage implements State {

    protected Context context;

    /**
     * Constructs a SignUpPage object with the given context.
     *
     * @param context The context object for the sign-up page.
     * @author Fabio Barchiesi
     */
    protected SignUpPage(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the initial page.
     *
     * @param initialPage The InitialPage object representing the initial page.
     * @author Fabio Barchiesi
     */
    public void goToInitialPage(InitialPage initialPage) {
        context.setState(initialPage);
    }

    /**
     * Navigates to the login page.
     *
     * @param loginPage The LoginPage object representing the login page.
     * @author Marina Sotiropoulos
     */
    public void goToLoginPage(LoginPage loginPage) {
        context.setState(loginPage);
        context.request();
    }

    /**
     * Registers a new user.
     *
     * @param userBean    The UserBean object representing the user's information.
     * @param accountBean The AccountBean object representing the user's account information.
     * @throws NoSuchAlgorithmException if the specified algorithm is not available.
     * @throws DuplicateRowException   if the user already exists.
     *
     * @author Fabio Barchiesi
     */
    public void registerUser(UserBean userBean, AccountBean accountBean) throws NoSuchAlgorithmException, DuplicateRowException {
        LoginManager loginManager = new LoginManager();
        loginManager.signup(userBean, accountBean);
    }
}