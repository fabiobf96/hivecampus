package it.hivecampuscompany.hivecampus.graphic.cli.controller;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.bean.UserBean;
import it.hivecampuscompany.hivecampus.logic.manager.LoginManager;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.graphic.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.graphic.cli.view.CLIView;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidEmailException;
import it.hivecampuscompany.hivecampus.logic.exception.PasswordMismatchException;

public class ApplicationCLIController extends CLIController{
    LoginManager control;
    public ApplicationCLIController(){
        properties = LanguageLoader.getLanguageProperties();
        view = new CLIView();
        control = new LoginManager();
        homePage();
    }
    public void homePage(){

        view.displayWelcomeMessage(properties.getProperty("WELCOME_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("LOGIN_MSG"));
        view.displayMessage("3. " + properties.getProperty("SIGN_UP_MSG"));
        view.displayMessage("4. " + properties.getProperty("EXIT_MSG"));

        switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))){
            case 1 -> {
                new LanguageCLIController();
                this.properties = LanguageLoader.getLanguageProperties();
                homePage();
            }
            case 2 -> {
                SessionBean sessionBean;
                LoginCLIController loginCLIController = new LoginCLIController();
                UserBean userBean = loginCLIController.getCredentials();
                try {
                    sessionBean = control.login(userBean);
                    if(sessionBean.getRole().equals("owner")){
                        new OwnerHomeCLIController();
                    }
                } catch (InvalidEmailException | PasswordMismatchException e) {
                    view.displayMessage(e.getMessage());
                    homePage();
                }
            }
            case 3 -> {
                SignupCLIController signupCLIController = new SignupCLIController();
                UserBean userBean = signupCLIController.createUserInformation();
                AccountBean accountBean = signupCLIController.createAccountInformation(userBean);
                try {
                    control.signup(userBean, accountBean);
                } catch (DuplicateRowException e) {
                    view.displayMessage(properties.getProperty(e.getMessage()));
                }
                homePage();
            }
            case 4 -> exit();
            default -> {
                invalidChoice();
                homePage();
            }
        }
    }
}
