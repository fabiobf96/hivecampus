package it.hivecampuscompany.hivecampus.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AccountBean;
import it.hivecampuscompany.hivecampus.bean.UserBean;
import it.hivecampuscompany.hivecampus.control.LoginControl;
import it.hivecampuscompany.hivecampus.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.cli.view.CLIView;

public class ApplicationCLIController extends CLIController{
    LoginControl control;
    public ApplicationCLIController(){
        properties = LanguageLoader.getLanguageProperties();
        view = new CLIView();
        control = new LoginControl();
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
                LoginCLIController loginCLIController = new LoginCLIController();
                UserBean userBean = loginCLIController.getCredentials();


            }
            case 3 -> {
                SignupCLIController signupCLIController = new SignupCLIController();
                UserBean userBean = signupCLIController.createUserInformation();
                AccountBean accountBean = signupCLIController.createAccountInformation(userBean);
                try {
                    control.signup(userBean, accountBean);
                } catch (DuplicateRowException e) {
                    view.displayMessage(properties.getProperty(e.getMessage()));
                    homePage();
                }
            }
            case 4 -> exit();
            default -> {
                invalidChoice();
                homePage();
            }
        }
    }
}
