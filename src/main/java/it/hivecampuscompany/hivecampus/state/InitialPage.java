package it.hivecampuscompany.hivecampus.state;

/**
 * The InitialPage abstract class serves as a base class for initial pages in the application.
 * It implements the State interface and provides methods for navigating to the login page and sign-up page.
 *
 * @author Fabio Barchiesi
 */
public abstract class InitialPage implements State {
    protected Context context;

    /**
     * Constructs an InitialPage object with the given context.
     *
     * @param context The context object for the initial page.
     * @author Fabio Barchiesi
     */
    protected InitialPage(Context context) {
        this.context = context;
    }

    /**
     * Navigates to the login page by updating the context's state and making a request.
     *
     * @param loginPage The LoginPage object representing the login page.
     * @author Fabio Barchiesi
     */
    public void goToLoginPage(LoginPage loginPage) {
        context.setState(loginPage);
        context.request();
    }

    /**
     * Navigates to the sign-up page by updating the context's state and making a request.
     *
     * @param signUpPage The SignUpPage object representing the sign-up page.
     * @author Fabio Barchiesi
     */
    public void goToSignUpPage(SignUpPage signUpPage) {
        context.setState(signUpPage);
        context.request();
    }
}

