package it.hivecampuscompany.hivecampus.state;

public abstract class InitialPage implements State{
    protected Context context;
    protected InitialPage (Context context) {
        this.context = context;
    }

    public void goToLoginPage(LoginPage loginPage) {
        context.setState(loginPage);
        context.request();
    }

    public void goToSignUpPage(SignUpPage signUpPage) {
        context.setState(signUpPage);
        context.request();
    }
}
