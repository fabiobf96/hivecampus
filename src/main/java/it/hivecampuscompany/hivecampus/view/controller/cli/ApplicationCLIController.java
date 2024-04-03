package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;

public class ApplicationCLIController extends CLIController {
    LoginManager control;
    public ApplicationCLIController(){
        properties = LanguageLoader.getLanguageProperties();
        view = new CliGUI();
        control = new LoginManager();
        homePage();
    }
    public void homePage(){

        view.displayWelcomeMessage(properties.getProperty("WELCOME_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("LOGIN_MSG"));
        view.displayMessage("3. " + properties.getProperty("SIGN_UP_MSG"));
        view.displayMessage("4. " + properties.getProperty("EXIT_MSG"));
        try {

            switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
                case 1 -> {
                    view.clean();
                    new LanguageCLIController();
                    this.properties = LanguageLoader.getLanguageProperties();
                    homePage();
                }
                case 2 -> {
                    view.clean();
                    LoginCLIController loginCLIController = new LoginCLIController();
                    UserBean userBean = loginCLIController.getCredentials();
                    sessionBean = control.login(userBean);
                    if (sessionBean.getRole().equals("owner")) {
                        new OwnerHomeCLIController(sessionBean);
                    }
                    else new TenantHomeCLIController(sessionBean);
                }
                case 3 -> {
                    view.clean();
                    SignupCLIController signupCLIController = new SignupCLIController();
                    UserBean userBean = signupCLIController.createUserInformation();
                    AccountBean accountBean = signupCLIController.createAccountInformation(userBean);
                    control.signup(userBean, accountBean);
                    homePage();
                }
                case 4 -> exit();
                default -> invalidChoice();
            }
        } catch (InputMismatchException e){
            invalidChoice();
        } catch (DuplicateRowException | AuthenticateException | NoSuchAlgorithmException e){
            view.displayMessage(properties.getProperty(e.getMessage()));
            homePage();
        }
    }
}
