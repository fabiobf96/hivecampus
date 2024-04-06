package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.manager.LoginManager;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;

public class ApplicationCLIController extends CLIController {
    LoginManager manager;
    public ApplicationCLIController(){
        view = new CliGUI();
        manager = new LoginManager();
        homePage();
    }
    public void homePage(){
        properties = LanguageLoader.getLanguageProperties();
        view.displayWelcomeMessage(properties.getProperty("WELCOME_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("LOGIN_MSG"));
        view.displayMessage("3. " + properties.getProperty("SIGN_UP_MSG"));
        view.displayMessage("4. " + properties.getProperty("EXIT_MSG"));
        CLIController controller = null;
        try {
            switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
                case 1 -> {
                    view.clean();
                    controller = new LanguageCLIController();
                }
                case 2 -> {
                    view.clean();
                    LoginCLIController loginCLIController = new LoginCLIController();
                    loginCLIController.homePage();
                    boolean invalid = true;
                    while (invalid){
                        UserBean userBean = loginCLIController.getCredentials();
                        invalid = false;
                        sessionBean = manager.login(userBean);
                        if (sessionBean.getRole().equals("owner")) {
                            controller = new OwnerHomeCLIController(sessionBean);
                        }
                        else controller = new TenantHomeCLIController(sessionBean);
                    }
                }
                case 3 -> {
                    view.clean();
                    SignupCLIController signupCLIController = new SignupCLIController();
                    UserBean userBean = signupCLIController.createUserInformation();
                    AccountBean accountBean = signupCLIController.createAccountInformation(userBean);
                    manager.signup(userBean, accountBean);
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
        } catch (InvalidEmailException e) {
            view.displayMessage(properties.getProperty(e.getMessage()));
            view.clean();
        }
        assert controller != null;
        controller.homePage();
        homePage();
    }
}
